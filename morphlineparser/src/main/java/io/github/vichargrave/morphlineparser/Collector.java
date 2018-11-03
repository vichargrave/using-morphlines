/*
   Collector
   Collects parsed records for a Morphlines based parser.

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

import java.util.ArrayList;
import java.util.List;

import org.kitesdk.morphline.base.Notifications;
import org.kitesdk.morphline.api.Command;
import org.kitesdk.morphline.api.Record;

import com.google.common.base.Preconditions;

public class Collector implements Command {

    final private List<Record> records = new ArrayList<>();

    /**
     * Clears the current list of Records.
     */
    public void reset() {
        records.clear();
    }

    /**
     * Not tracking parent, so returns null.
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
     * Called after parsing to add a record to list.
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
     * Returns the list of parsed records.
     * @return records
     */
    public List<Record> getRecords() {
        return records;
    }
}