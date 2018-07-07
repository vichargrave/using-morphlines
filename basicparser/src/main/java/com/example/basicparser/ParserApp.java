package com.example.basicparser;

import org.kitesdk.morphline.api.Command;
import org.kitesdk.morphline.api.MorphlineContext;
import org.kitesdk.morphline.api.Record;
import org.kitesdk.morphline.base.Compiler;
import org.kitesdk.morphline.base.Fields;
import org.kitesdk.morphline.base.Notifications;

import java.io.*;
import java.util.Arrays;

public class ParserApp {
    private final MorphlineContext morphlineContext;
    private final Command morphline;

    private static void usage() {
        System.out.println("usage: java ... <morphline.conf> <morphlineId> <dataFile1> ... <dataFileN>");
        System.exit(1);
    }

    public ParserApp(File morphlineFile, String morphlineId) {
        this.morphlineContext = new MorphlineContext.Builder().build();
        this.morphline = new Compiler().compile(morphlineFile, morphlineId, morphlineContext, null);
    }

    public boolean[] process(String[] inputs) throws IOException {
        boolean[] outcome = new boolean[inputs.length];
        // Process each input data file
        Notifications.notifyBeginTransaction(morphline);
        try {
            for (int i = 0; i < inputs.length; i++) {
                InputStream in = new BufferedInputStream(new FileInputStream(new File(inputs[i])));
                Record record = new Record();
                record.put(Fields.ATTACHMENT_BODY, in);
                Notifications.notifyStartSession(morphline);
                outcome[i] = morphline.process(record);
                if (!outcome[i]) {
                    System.out.println("Morphline failed to process record: " + record + "for file " + inputs[i]);
                }
                in.close();
            }
            Notifications.notifyCommitTransaction(morphline);
        } catch (RuntimeException e) {
            Notifications.notifyRollbackTransaction(morphline);
            morphlineContext.getExceptionHandler().handleException(e, null);
        } catch (IOException e) {
            Notifications.notifyRollbackTransaction(morphline);
            throw e;
        }
        Notifications.notifyShutdown(morphline);
        return outcome;
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            usage();
        }

        File morphlineFile = new File(args[0]);
        if (!morphlineFile.canRead() || !morphlineFile.exists()) {
            System.err.println("unable to read " + args[0]);
            System.exit(-1);
        }

        String morphlineId = args[1];
        ParserApp myMorphlineDriver = new ParserApp(morphlineFile, morphlineId);
        boolean[] outcome = myMorphlineDriver.process(Arrays.copyOfRange(args, 2, args.length));
    }
}