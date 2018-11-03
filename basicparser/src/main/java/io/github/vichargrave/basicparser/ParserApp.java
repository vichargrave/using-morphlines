/*
   PqrserApp
   Application that demonstrates basic Morphlines capabilities.

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

package io.github.vichargrave.basicparser;

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
        System.out.println("usage: java ... <morphline.conf> <dataFile1> ... <dataFileN>");
        System.exit(1);
    }

    public ParserApp(File morphlineFile) {
        this.morphlineContext = new MorphlineContext.Builder().build();
        this.morphline = new Compiler().compile(morphlineFile, null, morphlineContext, null);
    }

    public boolean[] process(String[] inputs) throws IOException {
        boolean[] outcome = new boolean[inputs.length];
        // Process each input data file
        Notifications.notifyBeginTransaction(morphline);
        for (int i = 0; i < inputs.length; i++) {
            InputStream in = new BufferedInputStream(new FileInputStream(new File(inputs[i])));
            Record record = new Record();
            record.put(Fields.ATTACHMENT_BODY, in);
            Notifications.notifyStartSession(morphline);
            outcome[i] = morphline.process(record);
            if (outcome[i] == false) {
                System.out.println("Morphline failed to process record: " + record + "for file " + inputs[i]);
            }
            in.close();
        }
        Notifications.notifyShutdown(morphline);
        return outcome;
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            usage();
        }

        ParserApp app = new ParserApp(new File(args[0]));
        app.process(Arrays.copyOfRange(args, 1, args.length));
    }
}