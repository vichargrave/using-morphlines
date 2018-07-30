package io.github.vichargrave.basicparser;

import org.junit.Test;
import org.kitesdk.morphline.api.MorphlineCompilationException;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertTrue;

public class BasicParserTest {

    @Test
    public void testBasicParser() throws Exception {
        ParserApp app = new ParserApp(new File("../conf/jsonParser.conf"));
        assertTrue(app.process(new String[] {"../data/tweets.json"})[0]);
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
