package test.n3reader;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import main.n3reader.N3;
import main.n3reader.Triple;

public class N3Test {
	private N3 n3;
	private String uri = "URI";
	private long lastModified = 0L;

	final private String subject = "Subject";
	final private String predicate = "Predicate";
	final private String object = "Object";

	@Before
	public void setUp() {
		this.n3 = new N3(uri, lastModified);
	}

	@Test
	public void addTripleTest() {
		int expected = 1;

		n3.addTriple(new Triple(subject, predicate, object));
		int actual = n3.getTripleCount();

		assertThat(actual, is(expected));
	}

	@Test
	public void diffTest() {
		int expected = 1;

		n3.addTriple(new Triple(subject, predicate, object));
		n3.addTriple(new Triple(subject, predicate, object));

		N3 targetN3 = new N3(uri, lastModified);
		targetN3.addTriple(new Triple(subject, predicate, object));
		targetN3.addTriple(new Triple(subject, predicate, object + "000"));

		ArrayList<Integer> diffTripleIds = n3.diff(targetN3);
		int actual = diffTripleIds.get(0);

		assertThat(actual, is(expected));
	}

	@Test
	public void equalsTest() {
		boolean expected = true;

		N3 targetN3 = new N3(uri, lastModified);
		boolean actual = n3.equals(targetN3);

		assertThat(actual, is(expected));
	}

	@Test
	public void getLastModifiedTest() {
		long expected = lastModified;
		long actual = n3.getLastModified();

		assertThat(actual, is(expected));
	}

	@Test
	public void getTripleTest() {
		boolean expected = true;

		Triple triple = new Triple(subject, predicate, object);
		n3.addTriple(triple);
		boolean actual = triple.equals(n3.getTriple(0));

		assertThat(actual, is(expected));
	}

	@Test
	public void getTriplesTest() {
		int expected = 2;

		n3.addTriple(new Triple(subject, predicate, object));
		n3.addTriple(new Triple(subject, predicate, object));
		int actual = n3.getTriples().size();

		assertThat(actual, is(expected));
	}

	@Test
	public void getTripleCountTest() {
		int expected = 1;

		n3.addTriple(new Triple(subject, predicate, object));
		int actual = n3.getTripleCount();

		assertThat(actual, is(expected));
	}

	@Test
	public void getUriTest() {
		String expected = uri;
		String actual = n3.getUri();

		assertThat(actual, is(expected));
	}

	@Test
	public void noDiffTest() {
		int expected = 0;

		n3.addTriple(new Triple(subject, predicate, object));

		N3 targetN3 = new N3(uri, lastModified);
		targetN3.addTriple(new Triple(subject, predicate, object));

		ArrayList<Integer> diffTripleIds = n3.diff(targetN3);
		int actual = diffTripleIds.size();

		assertThat(actual, is(expected));
	}

	@Test
	public void notEqualsTest() {
		boolean expected = false;

		n3.addTriple(new Triple(subject, predicate, object));

		N3 targetN3 = new N3(uri, lastModified);
		targetN3.addTriple(new Triple(subject, predicate, object + "000"));
		boolean actual = n3.equals(targetN3);

		assertThat(actual, is(expected));
	}
}
