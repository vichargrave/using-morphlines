package io.github.vichargrave.morphlineparser;

import java.util.ArrayList;
import java.util.List;

import org.kitesdk.morphline.base.Notifications;
import org.kitesdk.morphline.api.Command;
import org.kitesdk.morphline.api.Record;

import com.google.common.base.Preconditions;

public class Collector implements Command {

    private Command parent;
    private List<Record> records;

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