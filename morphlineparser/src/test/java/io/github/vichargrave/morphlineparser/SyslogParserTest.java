/*
   SyslogParserTest
   Tests syslog log parsing.

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

import org.junit.Test;
import org.kitesdk.morphline.api.Record;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SyslogParserTest {

    @Test
    public void testSyslogLineParser() throws Exception {
        String lineToParse = "<132>Jul  8 10:58:09 ossec-server ossec: Alert Level: 3; Rule: 5501 - Login session opened.; Location: ossec-server->/var/log/secure; classification:  pam,syslog,authentication_success,; Jul  8 10:58:08 ossec-server su: pam_unix(su-l:session): session opened for user root by ossec(uid=0)";

        MorphlineParser parser = new MorphlineParser("../conf/parsers.conf", "syslog");
        List<Record> records = parser.parse(lineToParse);
        if (records.size() > 0) {
            Record out = records.get(0);
            System.out.println(out);
            assertEquals("syslog_timestamp", "Jul  8 10:58:09", out.get("syslog_timestamp").get(0));
            assertEquals("syslog_host", "ossec-server", out.get("syslog_host").get(0));
            assertEquals("syslog_program", "ossec", out.get("syslog_program").get(0));
            assertEquals("Alert_Level", "3", out.get("Alert_Level").get(0));
            assertEquals("Rule", "5501", out.get("Rule").get(0));
            assertEquals("Description", "Login session opened.", out.get("Description").get(0));
            assertEquals("Details", "ossec-server->/var/log/secure; classification:  pam,syslog,authentication_success,; Jul  8 10:58:08 ossec-server su: pam_unix(su-l:session): session opened for user root by ossec(uid=0)", out.get("Details").get(0));
        } else {
            System.out.println("No parsed records produced");
        }
    }

    @Test
    public void testSyslogFileParser() throws Exception {
        MorphlineParser parser = new MorphlineParser("../conf/parsers.conf", "syslog");
        File fileToParse = new File("../data/ossec.syslog");
        List<Record> records = parser.parse(fileToParse);
        if (records.size() > 0) {
            Record out = records.get(0);
            System.out.println(out);
            assertEquals("syslog_timestamp", "Jul  8 10:58:09", out.get("syslog_timestamp").get(0));
            assertEquals("syslog_host", "ossec-server", out.get("syslog_host").get(0));
            assertEquals("syslog_program", "ossec", out.get("syslog_program").get(0));
            assertEquals("Alert_Level", "3", out.get("Alert_Level").get(0));
            assertEquals("Rule", "5501", out.get("Rule").get(0));
            assertEquals("Description", "Login session opened.", out.get("Description").get(0));
            assertEquals("Details", "ossec-server->/var/log/secure; classification:  pam,syslog,authentication_success,; Jul  8 10:58:08 ossec-server su: pam_unix(su-l:session): session opened for user root by ossec(uid=0)", out.get("Details").get(0));
        } else {
            System.out.println("No parsed records produced");
        }
    }

}
