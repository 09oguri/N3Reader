package main.n3reader;

/**
 * Tripleを扱うクラス。
 */
public class Triple {
    final private String subject;
    final private String predicate;
    final private String object;

    /**
     * 指定された要素を持つトリプルのインスタンスを生成。
     * 
     * @param s
     *            主語(Subject)
     * @param p
     *            述語(Predicate)
     * @param o
     *            目的語(Object)
     */
    public Triple(String s, String p, String o) {
        this.subject = s;
        this.predicate = p;
        this.object = o;
    }

    /**
     * 指定されたオブジェクトと等しいかどうか判定。
     * <p>
     * 主語、述語、目的語が等しい場合に<code> true </code>を返す。
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;

        Triple triple = (Triple) obj;
        if (!this.getSubject().equals(triple.getSubject()))
            return false;
        if (!this.getPredicate().equals(triple.getPredicate()))
            return false;
        if (!this.getObject().equals(triple.getObject()))
            return false;

        return true;
    }

    /**
     * このトリプルの主語を返す。
     * 
     * @return 主語
     */
    public String getSubject() {
        return subject;
    }

    /**
     * このトリプルの述語を返す。
     * 
     * @return 述語
     */
    public String getPredicate() {
        return predicate;
    }

    /**
     * このトリプルの目的語を返す。
     * 
     * @return 目的語
     */
    public String getObject() {
        return object;
    }

    /**
     * ハッシュコードを計算。
     */
    @Override
    public int hashCode() {
        int h = 1;
        h = h * 31 + subject.hashCode() + predicate.hashCode()
                + object.hashCode();
        return h;
    }
}
