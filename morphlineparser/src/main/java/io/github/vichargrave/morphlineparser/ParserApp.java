package io.github.vichargrave.morphlineparser;

import org.kitesdk.morphline.api.MorphlineCompilationException;
import org.kitesdk.morphline.api.Record;

import java.io.*;
import java.util.List;

import com.google.common.collect.ListMultimap;

public class ParserApp {

    private static void usage() {
        System.out.println("usage: java ... <morphline conf> <data file> <morphline ID>");
        System.exit(1);
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            usage();
        }

        try {
            MorphlineParser parser = new MorphlineParser(args[0], args[2]);
            List<Record> records = parser.parse(new File(args[1]));
            if (records.size() > 0) {
                for (Record record : records) {
                    ListMultimap out = record.getFields();
                    System.out.println(out.toString());
                }
            }
            else {
                System.out.println("No parsed records produced");
                System.exit(-1);
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
        catch (MorphlineCompilationException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
    }
}
