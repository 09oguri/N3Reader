package main.n3reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import main.n3reader.util.Format;
import main.n3reader.util.Prefix;

/**
 * N3形式のファイルを読み込むためのクラス。
 */
public class N3Reader {
    // 読み込むN3ファイルの文字コード。とりあえずUTF-8
    private static final String CHARACTER_CODE = "utf-8";
    private final String n3Path;

    /**
     * 指定されたN3ファイルのパスからインスタンスを生成。
     * 
     * @param n3Path
     *            N3ファイルのパス
     */
    public N3Reader(String n3Path) {
        this.n3Path = n3Path;
    }

    /**
     * トリプルを表現した1行を整形し要素ごとに分割。
     * 
     * @param line
     *            トリプルを表現した行
     * @return 分割された要素のリスト
     */
    private ArrayList<String> parse(String line) {
        String unFormatedLine = line;

        // 空白相当文字で行を要素に分割
        unFormatedLine = Format.replaceWithSpace(unFormatedLine);
        ArrayList<String> elements = Format.splitBySpace(unFormatedLine);

        // それぞれの要素を整形
        for (int i = 0; i < elements.size(); i++) {
            String unFormatedElem = elements.get(i);
            elements.set(i, Format.format(unFormatedElem));
        }

        return elements;
    }

    /**
     * N3形式のファイルを読み込んで<code> N3 </code>のインスタンスを返す。
     * 
     * @return <code> N3 </code>
     * @throws IOException
     *             N3ファイルにアクセスできないなどのI/Oエラーが発生した場合
     */
    public N3 readN3() throws IOException {
        // XXX URIは空文字、最終更新時間は0に設定されるので、ファイルのメタ情報から設定できるようにする
        N3 n3 = new N3("", 0L);

        // HashMap<prefix, URI>
        // 例：<"dbpedia-ja:", "http://ja.dbpedia.org/resource/">
        HashMap<String, String> uris = new HashMap<String, String>();
        String subject = "";
        String predicate = "";
        String object = "";

        FileInputStream fin = new FileInputStream(n3Path);
        InputStreamReader isr = new InputStreamReader(fin, CHARACTER_CODE);
        BufferedReader br = new BufferedReader(isr);

        String line = br.readLine();
        while (line != null) {
            ArrayList<String> elements = parse(line);
            int elemSize = elements.size();

            // 空行の場合は無視
            if (elemSize == 0) {
                line = br.readLine();
                continue;
            }

            // prefix 宣言の場合は uris に登録
            if (elements.get(0).equals("@prefix")) {
                String key = elements.get(1);
                String value = elements.get(2);
                uris.put(key, value);

                line = br.readLine();
                continue;
            }

            // トリプルの場合
            if (elemSize == 3) {
                subject = elements.get(0);
                predicate = elements.get(1);
                object = elements.get(2);
            } else if (elemSize == 2) {
                predicate = elements.get(0);
                object = elements.get(1);
            } else if (elemSize == 1) {
                object = elements.get(0);
            } else {
                System.err.println("[Warn] Cannot parse this line: " + line);
            }

            // prefix による省略を元に戻す
            subject = Prefix.notOmit(subject, uris);
            predicate = Prefix.notOmit(predicate, uris);
            object = Prefix.notOmit(object, uris);

            n3.addTriple(new Triple(subject, predicate, object));

            line = br.readLine();
        }
        br.close();

        return n3;
    }

}
