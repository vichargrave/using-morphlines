package io.github.vichargrave.morphlineparser;

import org.kitesdk.morphline.api.Record;

import java.io.*;
import java.util.List;

import com.google.common.collect.ListMultimap;

public class ParserApp {

    private static void usage() {
        System.out.println("usage: java ... <morphline.conf> <data file> <morphline ID>");
        System.exit(1);
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
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

        MorphlineParser parser = new MorphlineParser(args[0], args[2]);
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
