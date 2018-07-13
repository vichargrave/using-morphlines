package io.github.vichargrave.morphlineparser;

import com.google.common.collect.ListMultimap;
import org.junit.Test;
import org.kitesdk.morphline.api.Record;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CefParserTest {

    @Test
    public void testCefLineParser() throws IOException {
        String lineToParse = "Dec 19 00:38:09 <local0.info> 10.217.31.247 CEF:0|Citrix|NetScaler|NS10.0|APPFW|APPFW_SAFECOMMERCE_XFORM|6|src=10.217.253.78 spt=56116 method=GET request=http://vpx247.example.net/FFC/CreditCardMind.html msg= Transformed (xout) potential credit card numbers seen in server response cn1=652 cn2=610 cs1=pr_ffc cs2=PPE0 cs3=li8MdGfW49uG8tGdSV85ech41a0A000 cs4=ALERT cs5=2012 act=transformed";

        MorphlineParser parser = new MorphlineParser("../conf/parsers.conf", "cef");
        List<Record> records = parser.parse(lineToParse);
        if (records.size() == 1) {
            ListMultimap out = records.get(0).getFields();
            System.out.println(out);
            assertEquals("Violation", "APPFW_SAFECOMMERCE_XFORM", out.get("Violation").get(0));
            assertEquals("Company", "Citrix", out.get("Company").get(0));
            assertEquals("Action", "transformed", out.get("Action").get(0));
            assertEquals("HTTP_Return_Code", "610", out.get("HTTP_Return_Code").get(0));
            assertEquals("Message", " Transformed (xout) potential credit card numbers seen in server response", out.get("Message").get(0));
            assertEquals("Appfw_Session_ID", "li8MdGfW49uG8tGdSV85ech41a0A000", out.get("Appfw_Session_ID").get(0));
            assertEquals("Request", "http://vpx247.example.net/FFC/CreditCardMind.html", out.get("Request").get(0));
            assertEquals("Severity", "6", out.get("Severity").get(0));
            assertEquals("Client_Port", "56116", out.get("Client_Port").get(0));
            assertEquals("Method", "GET", out.get("Method").get(0));
            assertEquals("Event_ID", "652", out.get("Event_ID").get(0));
            assertEquals("Client_IP", "10.217.253.78", out.get("Client_IP").get(0));
            assertEquals("Current_Year", "2012", out.get("Current_Year").get(0));
            assertEquals("Processed_By1", "pr_ffc", out.get("Processed_By1").get(0));
            assertEquals("Processed_By2", "PPE0", out.get("Processed_By2").get(0));
            assertEquals("Appliance", "NetScaler", out.get("Appliance").get(0));
            assertEquals("Log_Format", "CEF:0", out.get("Log_Format").get(0));
            assertEquals("Version", "NS10.0", out.get("Version").get(0));
            assertEquals("Level", "local0.info", out.get("Level").get(0));
            assertEquals("VIP", "10.217.31.247", out.get("VIP").get(0));
            assertEquals("Module", "APPFW", out.get("Module").get(0));
            assertEquals("Security_Severity", "ALERT", out.get("Security_Severity").get(0));
            assertEquals("timestamp", "Dec 19 00:38:09", out.get("timestamp").get(0));
        } else {
            System.out.println("Parsing failure");
        }
    }

    @Test
    public void testCefFileParser() throws IOException {
        MorphlineParser parser = new MorphlineParser("../conf/parsers.conf", "cef");
        File fileToParse = new File("../data/netscaler.cef");
        List<Record> records = parser.parse(fileToParse);
        if (records.size() > 0) {
            ListMultimap out = records.get(0).getFields();
            System.out.println(out);
            assertEquals("Violation", "APPFW_SAFECOMMERCE_XFORM", out.get("Violation").get(0));
            assertEquals("Company", "Citrix", out.get("Company").get(0));
            assertEquals("Action", "transformed", out.get("Action").get(0));
            assertEquals("HTTP_Return_Code", "610", out.get("HTTP_Return_Code").get(0));
            assertEquals("Message", " Transformed (xout) potential credit card numbers seen in server response", out.get("Message").get(0));
            assertEquals("Appfw_Session_ID", "li8MdGfW49uG8tGdSV85ech41a0A000", out.get("Appfw_Session_ID").get(0));
            assertEquals("Request", "http://vpx247.example.net/FFC/CreditCardMind.html", out.get("Request").get(0));
            assertEquals("Severity", "6", out.get("Severity").get(0));
            assertEquals("Client_Port", "56116", out.get("Client_Port").get(0));
            assertEquals("Method", "GET", out.get("Method").get(0));
            assertEquals("Event_ID", "652", out.get("Event_ID").get(0));
            assertEquals("Client_IP", "10.217.253.78", out.get("Client_IP").get(0));
            assertEquals("Current_Year", "2012", out.get("Current_Year").get(0));
            assertEquals("Processed_By1", "pr_ffc", out.get("Processed_By1").get(0));
            assertEquals("Processed_By2", "PPE0", out.get("Processed_By2").get(0));
            assertEquals("Appliance", "NetScaler", out.get("Appliance").get(0));
            assertEquals("Log_Format", "CEF:0", out.get("Log_Format").get(0));
            assertEquals("Version", "NS10.0", out.get("Version").get(0));
            assertEquals("Level", "local0.info", out.get("Level").get(0));
            assertEquals("VIP", "10.217.31.247", out.get("VIP").get(0));
            assertEquals("Module", "APPFW", out.get("Module").get(0));
            assertEquals("Security_Severity", "ALERT", out.get("Security_Severity").get(0));
            assertEquals("timestamp", "Dec 19 00:38:09", out.get("timestamp").get(0));
        } else {
            System.out.println("Parsing failure");
        }
    }
}
