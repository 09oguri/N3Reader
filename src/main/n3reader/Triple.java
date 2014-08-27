package main.n3reader;

public class Triple {
    final private String subject;
    final private String predicate;
    final private String object;

    public Triple(String s, String p, String o) {
        this.subject = s;
        this.predicate = p;
        this.object = o;
    }

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

    public String getSubject() {
        return subject;
    }

    public String getPredicate() {
        return predicate;
    }

    public String getObject() {
        return object;
    }

    @Override
    public int hashCode() {
        int h = 1;
        h = h * 31 + subject.hashCode();
        return h;
    }
}
