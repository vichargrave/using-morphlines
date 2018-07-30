package io.github.vichargrave.morphlineparser;

import java.io.*;
import java.util.List;

import org.apache.solr.common.util.ContentStreamBase;
import org.kitesdk.morphline.api.Command;
import org.kitesdk.morphline.api.MorphlineCompilationException;
import org.kitesdk.morphline.api.MorphlineContext;
import org.kitesdk.morphline.api.Record;
import org.kitesdk.morphline.base.Compiler;
import org.kitesdk.morphline.base.Fields;
import org.kitesdk.morphline.base.Notifications;

public class MorphlineParser {
    private Collector collector;
    private MorphlineContext morphlineContext;
    private File morphlineFile;
    private String morphlineId;
    private Command morphline;

    public MorphlineParser(String morphlineFile) throws MorphlineCompilationException {
        collector = new Collector();
        this.morphlineFile = new File(morphlineFile);
        this.morphlineId = null;
    }

    public MorphlineParser(String morphlineFile, String morphlineId) throws MorphlineCompilationException {
        collector = new Collector();
        this.morphlineFile = new File(morphlineFile);
        this.morphlineId = morphlineId;
    }

    private void createMorphline() {
        morphlineContext = new MorphlineContext.Builder().build();
        morphline = new Compiler().compile(morphlineFile, morphlineId, morphlineContext, collector);
    }

    /** Parses lines from any InputStream. The other two parse methods call this one. */
    public List<Record> parse(InputStream in) {
        collector.reset();
        createMorphline();
        Record record = new Record();
        record.put(Fields.ATTACHMENT_BODY, in);
        Notifications.notifyStartSession(morphline);
        morphline.process(record);
        return collector.getRecords();
    }

    /** Parses all the lines in a file. */
    public List<Record> parse(File fileToParse) throws IOException, FileNotFoundException {
        InputStream in = new BufferedInputStream(new FileInputStream(fileToParse));
        return parse(in);
    }

    /** Parse 1 or more lines in a String buffer. */
    public List<Record> parse(String linesToParse) throws IOException {
        ContentStreamBase.StringStream stream = new ContentStreamBase.StringStream(linesToParse);
        InputStream in = stream.getStream();
        return parse(in);
    }
}
