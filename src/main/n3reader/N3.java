package main.n3reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class N3 {
    private long lastModified;
    private int tripleId;
    private HashMap<Integer, Triple> triples;
    final private String uri;

    public N3(String uri, long lastModified) {
        this.lastModified = lastModified;
        this.tripleId = 0;
        this.triples = new HashMap<Integer, Triple>();
        this.uri = uri;
    }

    public void addTriple(Triple triple) {
        Triple prevTriple = triples.put(tripleId, triple);
        if (prevTriple == null) {
            tripleId++;
        }
        setLastModified(System.currentTimeMillis());
    }

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

    public long getLastModified() {
        return lastModified;
    }

    public Triple getTriple(int tripleId) {
        return triples.get(tripleId);
    }

    public HashMap<Integer, Triple> getTriples() {
        return triples;
    }

    public int getTripleCount() {
        return tripleId;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public int hashCode() {
        int h = 1;
        h = h * 31 + uri.hashCode();
        return h;
    }

    private void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
