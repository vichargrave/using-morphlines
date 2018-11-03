/*
   BasicParserTest
   ParserApp unit tests.

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

package io.github.vichargrave.basicparser;

import org.junit.Test;
import org.kitesdk.morphline.api.MorphlineCompilationException;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertTrue;

public class BasicParserTest {

    @Test
    public void testJsonBasicParser() throws Exception {
        ParserApp app = new ParserApp(new File("../conf/jsonParser.conf"));
        assertTrue(app.process(new String[] {"../data/tweets.json"})[0]);
    }

    @Test
    public void testSyslogBasicParser() throws Exception {
        ParserApp app = new ParserApp(new File("../conf/syslogParser.conf"));
        assertTrue(app.process(new String[] {"../data/ossec.syslog"})[0]);
    }

    @Test (expected = MorphlineCompilationException.class)
    public void testBasicParserNonexistentMorphlineFile() throws Exception {
        ParserApp app = new ParserApp(new File("../conf/_some_file_that_does_not_exist.conf"));
        app.process(new String[] {"../data/tweets.json"});
    }

    @Test (expected = FileNotFoundException.class)
    public void testBasicParserAppNonExistentDataFile() throws Exception {
        ParserApp app = new ParserApp( new File("../conf/parsers.conf"));
        app.process(new String[] {"../data/_some_file_that_does_not_exist.json"});
    }
}
