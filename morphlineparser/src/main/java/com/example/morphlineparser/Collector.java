package com.example.morphlineparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kitesdk.morphline.base.Notifications;
import org.kitesdk.morphline.api.Command;
import org.kitesdk.morphline.api.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public final class Collector implements Command {

    private Command parent;
    private List<Record> records;
    private boolean clearAttachments = false;

    private static final Logger LOG = LoggerFactory.getLogger(Collector.class);

    public Collector() {
        reset();
    }

    public Collector(boolean clearAttachments) {
        this.clearAttachments = clearAttachments;
        reset();
    }

    public void reset() {
        records = new ArrayList<Record>();
    }

    @Override
    public Command getParent() {
        return parent;
    }

    @Override
    public void notify(Record notification) {
        Notifications.containsLifecycleEvent(notification, Notifications.LifecycleEvent.START_SESSION);
    }

    @Override
    public boolean process(Record record) {
        Preconditions.checkNotNull(record);
        if (clearAttachments == true) {
            records.add(clearAttachments(record));
        }
        else {
            records.add(record);
        }
        return true;
    }

    public List<Record> getRecords() {
        return records;
    }

    private Record clearAttachments(Record record) {
        Map parsedOutput = (Map) record.getFields().asMap();
        parsedOutput.remove("_attachment_body");
        parsedOutput.remove("_attachment_mimetype");
        return record;
    }
}