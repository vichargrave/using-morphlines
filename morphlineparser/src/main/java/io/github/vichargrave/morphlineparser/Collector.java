package io.github.vichargrave.morphlineparser;

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

    private static final Logger LOG = LoggerFactory.getLogger(Collector.class);

    public Collector() {
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
        records.add(record);
        return true;
    }

    public List<Record> getRecords() {
        return records;
    }
}