package test.n3reader;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.n3reader.util.FormatTest;
import test.n3reader.util.PrefixTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ N3ReaderTest.class, N3Test.class, TripleTest.class,
        FormatTest.class, PrefixTest.class, })
public class _TestSuite {

}
