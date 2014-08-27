package test.n3reader.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;

import main.n3reader.util.Prefix;

import org.junit.Test;

public class PrefixTest {
    @Test
    public void notOmitTest() {
        String expected = "http://ja.dbpedia.org/resource/ソメイヨシノ";

        String encoded = "dbpedia-ja:ソメイヨシノ";
        HashMap<String, String> uris = new HashMap<String, String>();
        uris.put("dbpedia-ja:", "http://ja.dbpedia.org/resource/");

        String actual = Prefix.notOmit(encoded, uris);

        assertThat(actual, is(expected));
    }
}
