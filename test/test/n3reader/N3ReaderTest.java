package test.n3reader;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import main.n3reader.N3;
import main.n3reader.N3Reader;

public class N3ReaderTest {
    // http://ja.dbpedia.org/data/ソメイヨシノ.n3
    private static final String FILEPATH = "./somei-yoshino.n3";
    private N3Reader n3reader;

    @Before
    public void setUp() {
        this.n3reader = new N3Reader(FILEPATH);
    }

    @Test
    public void readN3FormatTest() throws IOException {
        String expected = "http://ja.dbpedia.org/resource/高森町_(長野県)";

        N3 n3 = n3reader.readN3();
        String actual = n3.getTriple(1).getSubject();

        assertThat(actual, is(expected));
    }

    @Test
    public void readN3Test() throws IOException {
        String expected = "http://dbpedia.org/ontology/Species";

        N3 n3 = n3reader.readN3();
        String actual = n3.getTriple(18).getObject();

        assertThat(actual, is(expected));
    }
}
