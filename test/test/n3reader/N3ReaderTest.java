package test.n3reader;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import main.n3reader.N3;
import main.n3reader.N3Reader;

public class N3ReaderTest {
	private final String filepath = "./somei-yoshino.n3";
	private N3Reader n3reader;

	@Before
	public void setUp() {
		this.n3reader = new N3Reader(filepath);
	}

	@Test
	public void readN3Test() {
		String expected = "http://ja.dbpedia.org/resource/四国";

		N3 n3 = n3reader.readN3();
		String actual = n3.getTriple(0).getSubject();

		assertThat(actual, is(expected));
	}

	@Test
	public void readN3Test2() {
		String expected = "http://ja.dbpedia.org/resource/高森町_(長野県)";

		N3 n3 = n3reader.readN3();
		String actual = n3.getTriple(5).getSubject();

		assertThat(actual, is(expected));
	}

	@Test
	public void readN3Test3() {
		String expected = "http://dbpedia.org/ontology/Species";

		N3 n3 = n3reader.readN3();
		String actual = n3.getTriple(12).getObject();

		assertThat(actual, is(expected));
	}
}
