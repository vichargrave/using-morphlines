/*
   MorphlineParser
   Encapsulates core Morphlines parsing commands.

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

import java.io.*;
import java.util.List;

import org.kitesdk.morphline.api.Command;
import org.kitesdk.morphline.api.MorphlineContext;
import org.kitesdk.morphline.api.Record;
import org.kitesdk.morphline.base.Compiler;
import org.kitesdk.morphline.base.Fields;
import org.kitesdk.morphline.base.Notifications;

public class MorphlineParser {
    final private Collector collector = new Collector();
    final private Command morphline;

    /**
     * Creates the Morphlines parser.
     * @param morphlineFile  File containing the Morphlines script.
     */
    public MorphlineParser(final String morphlineFile) {
        morphline = new Compiler().compile(new File(morphlineFile),
                null,
                new MorphlineContext.Builder().build(),
                collector);
    }

    /**
     * Creates the Morphlines parser.
     * @param morphlineFile  File containing the Morphlines script(s).
     */
    public MorphlineParser(final String morphlineFile, final String morphlineId) {
        morphline = new Compiler().compile(new File(morphlineFile),
                morphlineId,
                new MorphlineContext.Builder().build(),
                collector);
    }

    /**
     * Parses lines from any InputStream. The other two parse methods call this one.
     * @param in  InputStream of raw records.
     * @return List of parsed records.
     */
    private List<Record> parse(final InputStream in) {
        collector.reset();
        final Record record = new Record();
        record.put(Fields.ATTACHMENT_BODY, in);
        Notifications.notifyStartSession(morphline);
        morphline.process(record);
        return collector.getRecords();
    }

    /**
     * Parses all the lines in a file.
     * @param fileToParse  File containing the raw records to parse.
     * @return List of parsed records.
     * @throws FileNotFoundException
     */
    public List<Record> parse(final File fileToParse) throws FileNotFoundException {
        return parse(new BufferedInputStream(new FileInputStream(fileToParse)));
    }

    /**
     * Parse 1 or more lines in a String buffer.
     * @param linesToParse  One or more raw lines to parse.
     * @return List of parsed records.
     */
    public List<Record> parse(final String linesToParse) {
        return parse(new ByteArrayInputStream(linesToParse.getBytes()));
    }
}
