package io.github.vichargrave.morphlineparser;

import java.io.*;
import java.util.List;

import org.apache.solr.common.util.ContentStreamBase;
import org.kitesdk.morphline.api.Command;
import org.kitesdk.morphline.api.MorphlineContext;
import org.kitesdk.morphline.api.Record;
import org.kitesdk.morphline.base.Compiler;
import org.kitesdk.morphline.base.Fields;
import org.kitesdk.morphline.base.Notifications;

public class MorphlineParser {
    final private Collector collector;
    final private Command morphline;

    /**
     * Creates a Collector and a Morphlines parser.
     * @param morphlineFile  File containing the Morphlines script.
     */
    public MorphlineParser(final String morphlineFile) {
        collector = new Collector();
        morphline = new Compiler().compile(new File(morphlineFile),
                null,
                new MorphlineContext.Builder().build(),
                collector);
    }

    /**
     * Creates a Collector and a Morphlines parser.
     * @param morphlineFile  File containing the Morphlines scripts.
     * @param morphlineId  ID of the script to use from the file.
     */
    public MorphlineParser(final String morphlineFile, final String morphlineId) {
        collector = new Collector();
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
        final InputStream in = new BufferedInputStream(new FileInputStream(fileToParse));
        return parse(in);
    }

    /**
     * Parse 1 or more lines in a String buffer.
     * @param linesToParse
     * @return List of parsed records.
     * @throws IOException
     */
    public List<Record> parse(final String linesToParse) throws IOException {
        final ContentStreamBase.StringStream stream = new ContentStreamBase.StringStream(linesToParse);
        final InputStream in = stream.getStream();
        return parse(in);
    }
}
