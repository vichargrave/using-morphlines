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
    private Collector collector;
    private MorphlineContext morphlineContext;
    private File morphlineFile;
    private String morphlineId;
    private Command morphline;

    public MorphlineParser(String morphlineFile) {
        collector = new Collector();
        this.morphlineFile = new File(morphlineFile);
        this.morphlineId = null;
    }

    public MorphlineParser(String morphlineFile, String morphlineId) {
        collector = new Collector();
        this.morphlineFile = new File(morphlineFile);
        this.morphlineId = morphlineId;
    }

    private void createMorphline() throws IOException {
        morphlineContext = new MorphlineContext.Builder().build();
        morphline = new Compiler().compile(morphlineFile, morphlineId, morphlineContext, collector);
    }

    private List<Record> parseStream(InputStream in) throws IOException {
        collector.reset();
        createMorphline();
        Record record = new Record();
        record.put(Fields.ATTACHMENT_BODY, in);
        Notifications.notifyStartSession(morphline);
        morphline.process(record);
        return collector.getRecords();
    }

    public List<Record> parse(File fileToParse) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream(fileToParse));
        return parseStream(in);
    }

    public List<Record> parse(String lineToParse) throws IOException {
        ContentStreamBase.StringStream stream = new ContentStreamBase.StringStream(lineToParse);
        InputStream in = stream.getStream();
        return parseStream(in);
    }
}
