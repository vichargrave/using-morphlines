/*
   MorphlineParserTest
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

package io.github.vichargrave.morphlineparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;
import org.kitesdk.morphline.api.MorphlineCompilationException;
import org.kitesdk.morphline.api.Record;

import static org.junit.Assert.assertTrue;

public class MorphlineParserTest {

    @Test
    public void testMorphlineParser() throws Exception {
        MorphlineParser parser = new MorphlineParser("../conf/parsers.conf", "json");
        File fileToParse = new File("../data/tweets.json");
        List<Record> records = parser.parse(fileToParse);
        assertTrue(records.size() > 0);
    }

    @Test (expected = MorphlineCompilationException.class)
    public void testMorphlineParserNonexistentMorphlineFile() throws Exception {
        MorphlineParser parser = new MorphlineParser("../conf/_some_file_that_does_not_exist.conf");
        File fileToParse = new File("../data/tweets.json");
        parser.parse(fileToParse);
    }

    @Test (expected = FileNotFoundException.class)
    public void testMorphlineNonExistentDataFile() throws Exception {
        MorphlineParser parser = new MorphlineParser("../conf/parsers.conf");
        File fileToParse = new File("../data/_some_file_that_does_not_exist.json");
        parser.parse(fileToParse);
    }
}
