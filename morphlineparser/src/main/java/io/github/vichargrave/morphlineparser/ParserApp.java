/*
   PqrserApp
   Application that uses more advanced Morphlines capabilities.

   ------------------------------------------
   Copyright (c) 2018 Vic Hargrave
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package io.github.vichargrave.morphlineparser;

import org.kitesdk.morphline.api.Record;

import java.io.*;
import java.util.List;

public class ParserApp {

    private static void usage() {
        System.out.println("usage: java ... <morphline conf> <data file> <morphline ID>");
        System.exit(1);
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            usage();
        }

        try {
            final MorphlineParser parser = new MorphlineParser(args[0], args[2]);
            final List<Record> records = parser.parse(new File(args[1]));
            if (records.size() > 0) {
                for (Record record : records) {
                    System.out.println(record.toString());
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
    }
}
