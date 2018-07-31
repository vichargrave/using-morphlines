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
