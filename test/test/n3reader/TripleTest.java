package test.n3reader;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import main.n3reader.Triple;

public class TripleTest {
    private Triple triple;
    private String subject = "Subject";
    private String predicate = "Predicate";
    private String object = "Object";

    @Before
    public void setUp() {
        this.triple = new Triple(subject, predicate, object);
    }

    @Test
    public void equalsTest() {
        boolean expected = true;

        Triple targetTriple = new Triple(subject, predicate, object);
        boolean actual = triple.equals(targetTriple);

        assertThat(actual, is(expected));
    }

    @Test
    public void getSubjectTest() {
        String expected = subject;
        String actual = triple.getSubject();

        assertThat(actual, is(expected));
    }

    @Test
    public void getPredicateTest() {
        String expected = predicate;
        String actual = triple.getPredicate();

        assertThat(actual, is(expected));
    }

    @Test
    public void getObjectTest() {
        String expected = object;
        String actual = triple.getObject();

        assertThat(actual, is(expected));
    }

    @Test
    public void notEqualsTest() {
        boolean expected = false;

        Triple targetTriple = new Triple(subject, predicate, object + "000");
        boolean actual = triple.equals(targetTriple);

        assertThat(actual, is(expected));
    }
}
