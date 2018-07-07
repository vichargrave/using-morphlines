package com.example.basicparser;

import org.junit.Test;
import org.kitesdk.morphline.api.MorphlineCompilationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BasicParserTest {

    @Test(expected = MorphlineCompilationException.class)
    public void testBasicParserApp(){
        ParserApp app = new ParserApp( null, null);
    }

    @Test
    public void testBasicParserApp02() throws IOException {
        ParserApp app = new ParserApp( new File("../conf/parsers.conf"), "json");
        app.process(new String[] {"../data/tweets.json"});
    }

    @Test (expected = MorphlineCompilationException.class)
    public void testBasicParserApp03() throws IOException {
        ParserApp app = new ParserApp( new File("../conf/_some_file_that_does_not_exist.conf"), "json");
        app.process(new String[] {"../data/tweets.json"});
    }

    @Test (expected = MorphlineCompilationException.class)
    public void testBasicParserAppr04() throws IOException {
        ParserApp app = new ParserApp( new File("../conf/parsers.conf"), "_some_morphline_that_does_not_exist");
        app.process(new String[] {"../data/tweets.json"});
    }

    @Test (expected = FileNotFoundException.class)
    public void testBasicParserApp05() throws IOException {
        ParserApp app = new ParserApp( new File("../conf/parsers.conf"), "json");
        app.process(new String[] {"../data/_some_file_that_does_not_exist.json"});
    }
}
