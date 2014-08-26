package test.n3reader.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import main.n3reader.util.Format;

import org.junit.Test;

public class FormatTest {
	@Test
	public void decodeUnicodeTest() {
		String expected = "japanese \\ 日本語 \\ \\u";

		String encoded = "japanese \\ \\u65e5\\u672c\\u8a9e \\ \\u";
		String actual = Format.decodeUnicode(encoded);

		assertThat(actual, is(expected));
	}

	@Test
	public void formatTest() {
		String expected = "http://ja.dbpedia.org/resource/御津町_(愛知県)";

		String encoded = "<http://ja.dbpedia.org/resource/\\u5FA1\\u6D25\\u753A_(\\u611B\\u77E5\\u770C)>";
		String actual = Format.format(encoded);

		assertThat(actual, is(expected));
	}

	@Test
	public void replaceWithSpaceTest() {
		String expected = "rdfs:comment \"\u30BD\u30E1\u30A4\u30E8\u30B7\u30CE\uFF08\u67D3\u4E95\u5409\u91CE\u3001\u5B66\u540D: Cerasus \u00D7yedoensis A.V. Vassil. \u2018Somei-yoshino\u2019\uFF09\u3068\u306F\u305D\u308C\u305E\u308C\u65E5\u672C\u539F\u7523\u7A2E\u306E\u30A8\u30C9\u30D2\u30AC\u30F3\u7CFB\u306E\u685C\u3068\u30AA\u30AA\u30B7\u30DE\u30B6\u30AF\u30E9\u306E\u4EA4\u914D\u3067\u751F\u307E\u308C\u305F\u3068\u8003\u3048\u3089\u308C\u308B\u65E5\u672C\u7523\u306E\u5712\u82B8\u54C1\u7A2E\u3067\u3042\u308B\u3002\u30BD\u30E1\u30A4\u30E8\u30B7\u30CE\u306F\u307B\u307C\u5168\u3066\u30AF\u30ED\u30FC\u30F3\u3067\u3042\u308B\u3002 \u65E5\u672C\u3067\u306F\u660E\u6CBB\u306E\u4E2D\u9803\u3088\u308A\u3001\u30B5\u30AF\u30E9\u306E\u4E2D\u3067\u5727\u5012\u7684\u306B\u591A\u304F\u690D\u3048\u3089\u308C\u305F\u54C1\u7A2E\u3067\u3042\u308A\u3001\u4ECA\u65E5\u3067\u306F\u3001\u30E1\u30C7\u30A3\u30A2\u306A\u3069\u3067\u300C\u685C\u304C\u958B\u82B1\u3057\u305F\u300D\u3068\u3044\u3046\u3068\u304D\u306E\u300C\u685C\u300D\u306F\u30BD\u30E1\u30A4\u30E8\u30B7\u30CE\uFF08\u306E\u4E2D\u306E\u3001\u6C17\u8C61\u53F0\u304C\u5B9A\u3081\u308B\u306A\u3069\u3057\u305F\u7279\u5B9A\u306E\u682A\uFF09\u3092\u610F\u5473\u3059\u308B\u306A\u3069\u3001\u73FE\u4EE3\u306E\u89B3\u8CDE\u7528\u306E\u30B5\u30AF\u30E9\u306E\u4EE3\u8868\u7A2E\u3067\u3042\u308B\u3002\"@ja";

		String encoded = "	rdfs:comment	\"\u30BD\u30E1\u30A4\u30E8\u30B7\u30CE\uFF08\u67D3\u4E95\u5409\u91CE\u3001\u5B66\u540D: Cerasus \u00D7yedoensis A.V. Vassil. \u2018Somei-yoshino\u2019\uFF09\u3068\u306F\u305D\u308C\u305E\u308C\u65E5\u672C\u539F\u7523\u7A2E\u306E\u30A8\u30C9\u30D2\u30AC\u30F3\u7CFB\u306E\u685C\u3068\u30AA\u30AA\u30B7\u30DE\u30B6\u30AF\u30E9\u306E\u4EA4\u914D\u3067\u751F\u307E\u308C\u305F\u3068\u8003\u3048\u3089\u308C\u308B\u65E5\u672C\u7523\u306E\u5712\u82B8\u54C1\u7A2E\u3067\u3042\u308B\u3002\u30BD\u30E1\u30A4\u30E8\u30B7\u30CE\u306F\u307B\u307C\u5168\u3066\u30AF\u30ED\u30FC\u30F3\u3067\u3042\u308B\u3002 \u65E5\u672C\u3067\u306F\u660E\u6CBB\u306E\u4E2D\u9803\u3088\u308A\u3001\u30B5\u30AF\u30E9\u306E\u4E2D\u3067\u5727\u5012\u7684\u306B\u591A\u304F\u690D\u3048\u3089\u308C\u305F\u54C1\u7A2E\u3067\u3042\u308A\u3001\u4ECA\u65E5\u3067\u306F\u3001\u30E1\u30C7\u30A3\u30A2\u306A\u3069\u3067\u300C\u685C\u304C\u958B\u82B1\u3057\u305F\u300D\u3068\u3044\u3046\u3068\u304D\u306E\u300C\u685C\u300D\u306F\u30BD\u30E1\u30A4\u30E8\u30B7\u30CE\uFF08\u306E\u4E2D\u306E\u3001\u6C17\u8C61\u53F0\u304C\u5B9A\u3081\u308B\u306A\u3069\u3057\u305F\u7279\u5B9A\u306E\u682A\uFF09\u3092\u610F\u5473\u3059\u308B\u306A\u3069\u3001\u73FE\u4EE3\u306E\u89B3\u8CDE\u7528\u306E\u30B5\u30AF\u30E9\u306E\u4EE3\u8868\u7A2E\u3067\u3042\u308B\u3002\"@ja .";
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
