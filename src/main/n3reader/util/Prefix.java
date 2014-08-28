package main.n3reader.util;

import java.util.HashMap;

/**
 * Prefix を処理するユーティリティークラス。
 */
public class Prefix {
    private Prefix() {
    }

    /**
     * 省略されたURIを元の形に変換する。
     * <p>
     * 例<br>
     * 変換前："dbpedia-ja:ソメイヨシノ"<br>
     * 変換後："http://ja.dbpedia.org/resource/ソメイヨシノ"
     * 
     * @param s
     *            省略された URI を含む文字列
     * @param uris
     *            Prefix と URI の対応を表すマップ
     * @return 省略されたURIを元の形に変換した文字列
     */
    public static String notOmit(String s, HashMap<String, String> uris) {
        String result = s;

        int pos = result.indexOf(":");

        // abc:xyz の形式で表現されていない場合
        if (pos < 1) {
            return result;
        }

        if (pos + 1 < result.length()) {
            // Prefix の部分を切り出す
            String prefix = result.substring(0, pos + 1);

            // prefix に対応する URI を取り出す
            String uri = uris.get(prefix);

            // Prefix を URI で置換
            if (uri != null) {
                result = uri + result.substring(pos + 1);
            }
        }

        return result;
    }
}
