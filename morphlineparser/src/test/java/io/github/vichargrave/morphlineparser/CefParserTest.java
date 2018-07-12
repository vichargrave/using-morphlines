package io.github.vichargrave.morphlineparser;

import com.google.common.collect.ListMultimap;
import org.junit.Test;
import org.kitesdk.morphline.api.Record;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CefParserTest {

    @Test
    public void testSyslogLineParser() throws IOException {
        String lineToParse = "Dec 18 21:46:17 <local0.info> 10.217.31.247 CEF:0|Citrix|NetScaler|NS10.0|APPFW|APPFW_STARTURL|6|src=10.217.253.78 spt=54711 method=GET request=http://vpx247.example.net/FFC/login_post.html?abc\\=def msg=Disallow Illegal URL. cn1=465 cn2=535 cs1=profile1 cs2=PPE0 cs3=IliG4Dxp1SjOhKVRDVBXmqvAaIcA000 cs4=ALERT cs5=2012 act=not blocked";

        MorphlineParser parser = new MorphlineParser("../conf/parsers.conf", "cef");
        List<Record> records = parser.parse(lineToParse);
        if (records.size() == 1) {
            ListMultimap out = records.get(0).getFields();
            System.out.println(out);
        } else {
            System.out.println("Parsing failure");
        }
    }
}
