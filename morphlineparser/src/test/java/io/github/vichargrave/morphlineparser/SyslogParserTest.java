package io.github.vichargrave.morphlineparser;

import com.google.common.collect.ListMultimap;
import org.junit.Test;
import org.kitesdk.morphline.api.Record;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SyslogParserTest {
    @Test
    public void testSyslogLineParser() throws IOException {
        String lineToParse = "<132>Jul  8 10:58:09 ossec-server ossec: Alert Level: 3; Rule: 5501 - Login session opened.; Location: ossec-server->/var/log/secure; classification:  pam,syslog,authentication_success,; Jul  8 10:58:08 ossec-server su: pam_unix(su-l:session): session opened for user root by ossec(uid=0)";

        MorphlineParser parser = new MorphlineParser("../conf/parsers.conf", "syslog");
        List<Record> records = parser.parse(lineToParse);
        if (records.size() == 1) {
            ListMultimap out = records.get(0).getFields();
            System.out.println(out);
            assertNotNull("message", out.get("message").get(0));
            assertEquals("Alert_Level", "3", out.get("Alert_Level").get(0));
        } else {
            System.out.println("Parsing failure");
        }
    }
}
