package io.github.vichargrave.morphlineparser;

import java.util.ArrayList;
import java.util.List;

import org.kitesdk.morphline.base.Notifications;
import org.kitesdk.morphline.api.Command;
import org.kitesdk.morphline.api.Record;

import com.google.common.base.Preconditions;

public class Collector implements Command {

    final private List<Record> records;

    /**
     * Creates a new Record list.
     */
    public Collector() {
        records = new ArrayList<>();
    }

    /**
     * Clears the current list of Records.
     */
    public void reset() {
        records.clear();
    }

    /**
     * Not tracking parent,so returns null.
     * @return null
     */
    @Override
    public Command getParent() {
        return null;
    }

    /**
     * Indicates the start of a parsing session.
     * @param notification
     */
    @Override
    public void notify(final Record notification) {
        Notifications.containsLifecycleEvent(notification, Notifications.LifecycleEvent.START_SESSION);
    }

    /**
     * Called after parsing to add Records to list.
     * @param record  Parsed Record
     * @return true
     */
    @Override
    public boolean process(final Record record) {
        Preconditions.checkNotNull(record);
        records.add(record);
        return true;
    }

    /**
     * Returns the list of parsed Records.
     * @return records
     */
    public List<Record> getRecords() {
        return records;
    }
}