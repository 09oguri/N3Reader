package test.n3reader.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import main.n3reader.util.Format;

import org.junit.Test;

public class FormatTest {
    @Test
    public void decodeUnicodeTest() {
        String expected = "http://ja.dbpedia.org/resource/御津町_(愛知県)";

        String encoded = "http://ja.dbpedia.org/resource/\\u5FA1\\u6D25\\u753A_(\\u611B\\u77E5\\u770C)";
        String actual = Format.decodeUnicode(encoded);

        assertThat(actual, is(expected));
    }

    @Test
    public void formatTest() {
        String expected = "御津町_(愛知県)";

        String encoded = "<\"\\u5FA1\\u6D25\\u753A_(\\u611B\\u77E5\\u770C)\"@ja>";
        String actual = Format.format(encoded);

        assertThat(actual, is(expected));
    }

    @Test
    public void replaceWithSpaceTest() {
        String expected = "aaa bbb ccc";

        String encoded = "\taaa　　bbb  ccc .";
        String actual = Format.replaceWithSpace(encoded);

        assertThat(actual, is(expected));
    }

    @Test
    public void splitBySpaceTest() {
        int expected = 4;

        String encoded = "aa bb \"c c\" <d  d>";
        int actual = Format.splitBySpace(encoded).size();

        assertThat(actual, is(expected));
    }
}
