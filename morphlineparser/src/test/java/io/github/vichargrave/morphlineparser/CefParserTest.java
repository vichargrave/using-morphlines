/*
   CefParserTest
   Tests CEF log parsing.

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

public class CefParserTest {

    @Test
    public void testCefLineParser() throws Exception {
        String lineToParse = "Dec 19 00:38:09 <local0.info> 10.217.31.247 CEF:0|Citrix|NetScaler|NS10.0|APPFW|APPFW_SAFECOMMERCE_XFORM|6|src=10.217.253.78 spt=56116 method=GET request=http://vpx247.example.net/FFC/CreditCardMind.html msg= Transformed (xout) potential credit card numbers seen in server response cn1=652 cn2=610 cs1=pr_ffc cs2=PPE0 cs3=li8MdGfW49uG8tGdSV85ech41a0A000 cs4=ALERT cs5=2012 act=transformed\n";

        MorphlineParser parser = new MorphlineParser("../conf/parsers.conf", "cef");
        List<Record> records = parser.parse(lineToParse);
        if (records.size() > 0) {
            Record out = records.get(0);
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
            System.out.println("No parsed records produced");
        }
    }

    @Test
    public void testCefFileParser() throws Exception {
        MorphlineParser parser = new MorphlineParser("../conf/parsers.conf", "cef");
        File fileToParse = new File("../data/netscaler.cef");
        List<Record> records = parser.parse(fileToParse);
        if (records.size() > 0) {
            Record out = records.get(0);
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
            System.out.println("No parsed records produced");
        }
    }
}
