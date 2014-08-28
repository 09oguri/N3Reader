package main.n3reader.util;

import java.util.ArrayList;

/**
 * 文字列の整形を行うユーティリティークラス。
 */
public class Format {
    private Format() {
    }

    /**
     * ユニコードで表現された文字列を通常の文字列に変換する。
     * <p>
     * 例<br>
     * 変換前："\\u5FA1\\u6D25\\u753A"<br>
     * 変換後："御津町"
     * 
     * @param s
     *            ユニコードで表現された文字列
     * @return 変換された文字列
     */
    public static String decodeUnicode(String s) {
        int length = s.length();
        StringBuffer sb = new StringBuffer(length);

        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);

            // "\"でない場合は変換しない
            if (c != '\\') {
                sb.append(c);
                continue;
            }

            // "\"の次の文字が"u"でない場合は変換しない
            if (i + 1 < length && s.charAt(i + 1) != 'u') {
                sb.append(c);
                continue;
            }

            // "\u0000"の場合は変換する
            if (i + 5 < length) {
                String unicodeStr = s.substring(i + 2, i + 6);
                c = (char) Integer.parseInt(unicodeStr, 16);
                sb.append(c);
                i = i + 5;
                continue;
            }

            sb.append(c);
        }

        return sb.toString();
    }

    /**
     * 指定された文字列を整形する。
     * <p>
     * 例<br>
     * 整形前：&lt;\"\\u5FA1\\u6D25\\u753A_(\\u611B\\u77E5\\u770C)\"@ja&gt;<br>
     * 整形後：御津町_(愛知県)
     * 
     * @param s
     *            整形する文字列
     * @return 整形された文字列
     */
    public static String format(String s) {
        String result = s;

        result = result.replaceAll("(\"@.*)$", "");
        result = result.replaceAll("^<|>$", "");
        result = result.replaceAll("^\"|\"$", "");

        if (result.contains("\\u")) {
            result = decodeUnicode(result);
        }

        return result;
    }

    /**
     * 指定された文字列に含まれる空白相当文字(タブ、半角スペース、全角スペース)を半角スペースに置換する。
     * <p>
     * 行頭と行末の空白相当文字は除去する。<br>
     * 連続する複数の半角スペースは半角スペース1つに置換する。
     * <p>
     * 例<br>
     * 置換前："\taaa　　bbb  ccc ."<br>
     * 置換後："aaa bbb ccc"
     * 
     * @param s
     *            置換する文字列
     * @return 置換された文字列
     */
    public static String replaceWithSpace(String s) {
        String result = s;

        result = result.replaceAll("\t+", " ");
        result = result.replaceAll("　+", " ");
        result = result.replaceAll(" +", " ");
        result = result.replaceAll("^ +| +$", "");

        // TODO formatで処理させるべき
        // 行末の"."や","、";"を除去
        result = result.replaceAll(" *[.,;]$", "");

        return result;
    }

    /**
     * 指定された文字列を半角スペースで分割する。
     * <p>
     * ""や&lt;&gt;で囲まれた文字列中の半角スペースでは分割しない。
     * <p>
     * 例<br>
     * 分割前："aa bb \"c c\" &lt;d  d&gt;"<br>
     * 分割後：aa, bb, \"c c\", &lt;d d&gt;
     * 
     * @param line
     *            分割する文字列
     * @return 分割された要素のリスト
     */
    public static ArrayList<String> splitBySpace(String line) {
        ArrayList<String> results = new ArrayList<String>(4);

        // 空文字の場合
        if (line.equals("")) {
            return results;
        }

        String s = line;
        int length = s.length();
        StringBuffer sb = new StringBuffer(length);

        char c;
        for (int i = 0; i < length; i++) {
            c = s.charAt(i);

            switch (c) {
            case '"':
                int next = s.indexOf('"', i + 1);
                // ""で囲まれているかどうか
                if (next < 0) {
                    sb.append(c);
                    break;
                }

                // ""で囲まれた文字列の処理
                for (; i < next + 1; i++) {
                    c = s.charAt(i);
                    sb.append(c);
                }
                i--;

                break;
            case '<':
                next = s.indexOf('>', i + 1);
                // <>で囲まれているかどうか
                if (next < 0) {
                    sb.append(c);
                    break;
                }

                // <>で囲まれた文字列の処理
                for (; i < next + 1; i++) {
                    c = s.charAt(i);
                    sb.append(c);
                }
                i--;

                break;
            case ' ':
                // 半角スペースで分割した文字列を保存
                String result = sb.toString();
                results.add(result);

                // バッファを初期化
                sb.delete(0, result.length());
                break;
            default:
                sb.append(c);
                break;
            }
        }

        if (sb.length() != 0) {
            results.add(sb.toString());
        }

        return results;
    }
}
