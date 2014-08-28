package main.n3reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * N3ファイルを扱うクラス。
 */
public class N3 {
    // 最終更新時間(ミリ秒)
    private long lastModified;
    private int tripleId;

    // HashMap<tripleId, triple>
    private HashMap<Integer, Triple> triples;

    // N3ファイルの識別子として利用
    final private String uri;

    /**
     * N3ファイルのURIと最終更新時間からインスタンスを生成。
     * <p>
     * TODO N3のメタデータから生成できるようにする
     * 
     * @param uri
     *            N3ファイルのURI
     * @param lastModified
     *            N3ファイルの最終更新時間(ミリ秒)
     */
    public N3(String uri, long lastModified) {
        this.lastModified = lastModified;
        this.tripleId = 0;
        this.triples = new HashMap<Integer, Triple>();
        this.uri = uri;
    }

    /**
     * トリプルを追加。
     * <p>
     * トリプルIDは追加されるたびに1ずつ増加し、最終更新時間が更新される。
     * 
     * @param triple
     *            追加するトリプル
     */
    public void addTriple(Triple triple) {
        Triple prevTriple = triples.put(tripleId, triple);
        if (prevTriple == null) {
            tripleId++;
        }
        setLastModified(System.currentTimeMillis());
    }

    /**
     * 指定されたN3の格納しているトリプルと異なるトリプルIDを検索。
     * <p>
     * 格納しているトリプルの個数が異なる場合は、小さい方の個数か呼び出し側の個数だけ検索される。
     * <p>
     * TODO 比較がしやすい戻り値にする
     * 
     * @param target
     *            比較するN3
     * @return 呼び出し側が格納している異なるトリプルIDのリスト
     */
    public ArrayList<Integer> diff(N3 target) {
        ArrayList<Integer> diffTripleIds = new ArrayList<Integer>();

        Iterator<Map.Entry<Integer, Triple>> thisIter = triples.entrySet()
                .iterator();
        Iterator<Map.Entry<Integer, Triple>> targetIter = target.getTriples()
                .entrySet().iterator();

        while (thisIter.hasNext() && targetIter.hasNext()) {
            Map.Entry<Integer, Triple> thisEntry = thisIter.next();
            Map.Entry<Integer, Triple> targetEntry = targetIter.next();

            if (!thisEntry.equals(targetEntry)) {
                diffTripleIds.add(thisEntry.getKey());
            }
        }

        return diffTripleIds;
    }

    /**
     * 指定されたオブジェクトと等しいかどうか判定。
     * <p>
     * 最終更新時間とトリプルの総数、URI、各トリプルが等しい場合に<code> true </code>を返す。
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;

        N3 n3 = (N3) obj;
        if (n3.getLastModified() != this.getLastModified())
            return false;
        if (n3.getTripleCount() != this.getTripleCount())
            return false;
        if (!this.getUri().equals(n3.getUri()))
            return false;
        if (!this.getTriples().equals(n3.getTriples()))
            return false;

        return true;
    }

    /**
     * 最終更新時間をミリ秒で返す。
     * 
     * @return 最終更新時間(ミリ秒)
     */
    public long getLastModified() {
        return lastModified;
    }

    /**
     * 指定されたトリプルIDのトリプルを返す。
     * 
     * @param tripleId
     *            トリプルID
     * @return 指定されたIDのトリプル
     */
    public Triple getTriple(int tripleId) {
        return triples.get(tripleId);
    }

    /**
     * トリプルIDとトリプルのハッシュマップを返す。
     * 
     * @return トリプルIDとトリプルのハッシュマップ
     */
    public HashMap<Integer, Triple> getTriples() {
        return triples;
    }

    /**
     * 格納されたトリプルの総数を返す。
     * 
     * @return 格納されたトリプルの総数
     */
    public int getTripleCount() {
        return tripleId;
    }

    /**
     * URIを返す。
     * 
     * @return N3のURI
     */
    public String getUri() {
        return uri;
    }

    /**
     * ハッシュコードを計算。
     * <p>
     * URIのハッシュコードを利用している。
     */
    @Override
    public int hashCode() {
        int h = 1;
        h = h * 31 + uri.hashCode();
        return h;
    }

    /**
     * 指定された最終更新時間を設定。
     * 
     * @param lastModified
     *            最終更新時間(ミリ秒)
     */
    private void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
