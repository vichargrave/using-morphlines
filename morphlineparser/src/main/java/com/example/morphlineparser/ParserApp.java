package com.example.morphlineparser;

import org.kitesdk.morphline.api.Record;

import java.io.*;
import java.util.List;

import com.google.common.collect.ListMultimap;

public class ParserApp {

    private static void usage() {
        System.out.println("usage: java ... <morphline.conf> <data file> <morphline ID> <clear attachments>");
        System.exit(1);
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 4) {
            usage();
        }

        File morphlineFile = new File(args[0]);
        if (!morphlineFile.canRead() || !morphlineFile.exists()) {
            System.err.println("Unable to read " + args[0]);
            System.exit(-1);
        }

        File fileToParse = new File(args[1]);
        if (!fileToParse.canRead() || !fileToParse.exists()) {
            System.err.println("Unable to read " + args[1]);
            System.exit(-1);
        }

        if (!args[3].contentEquals("0") && !args[3].contentEquals("1")) {
            System.err.println("Clear attachments flag must be either 0 or 1");
            System.exit(-1);
        }

        Boolean clearAttachments = false;
        if (args[3].contentEquals("1")) {
            clearAttachments = true;
        }

        MorphlineParser parser = new MorphlineParser(args[0], args[2], clearAttachments);
        List<Record> records = parser.parse(fileToParse);
        if (records.size() > 0) {
            for (Record record : records) {
                ListMultimap out = record.getFields();
                System.out.println(out.toString());
            }
        }
        else {
            System.out.println("Parsing failure");
        }
    }
}
