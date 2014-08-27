package test.n3reader.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;

import main.n3reader.util.Prefix;

import org.junit.Before;
import org.junit.Test;

public class PrefixTest {
    private HashMap<String, String> uris;

    @Before
    public void setUp() {
        this.uris = new HashMap<String, String>();
        uris.put("dbpedia-ja:", "http://ja.dbpedia.org/resource/");
    }

    @Test
    public void notOmitTest() {
        String expected = "http://ja.dbpedia.org/resource/ソメイヨシノ";

        String omittedElm = "dbpedia-ja:ソメイヨシノ";
        String actual = Prefix.notOmit(omittedElm, uris);

        assertThat(actual, is(expected));
    }
}
