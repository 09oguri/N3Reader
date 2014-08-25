package main.n3reader;

import java.util.ArrayList;
import java.util.Iterator;

public class N3 {
	private long lastModified;
	private int tripleCount;
	private ArrayList<Triple> triples;
	final private String uri;

	public N3(String uri, long lastModified) {
		this.lastModified = lastModified;
		this.tripleCount = 0;
		this.triples = new ArrayList<Triple>();
		this.uri = uri;
	}

	public void addTriple(Triple triple) {
		boolean isAdded = triples.add(triple);
		if (isAdded) {
			tripleCount++;
			setLastModified(System.currentTimeMillis());
		}
	}

	public ArrayList<Integer> diff(N3 target) {
		ArrayList<Integer> diffTripleIds = new ArrayList<Integer>();

		Iterator<Triple> thisIter = triples.iterator();
		Iterator<Triple> targetIter = target.getTriples().iterator();

		while (thisIter.hasNext() && targetIter.hasNext()) {
			Triple thisTriple = thisIter.next();
			Triple targetTriple = targetIter.next();

			if (!thisTriple.equals(targetTriple)) {
				diffTripleIds.add(thisTriple.getTripleId());
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

		return true;
	}

	public long getLastModified() {
		return lastModified;
	}

	public Triple getTriple(int tripleId) {
		return triples.get(tripleId);
	}

	public ArrayList<Triple> getTriples() {
		return triples;
	}

	public int getTripleCount() {
		return tripleCount;
	}

	public String getUri() {
		return uri;
	}

	@Override
	public int hashCode() {
		int h = 1;
		h = h * 31 + tripleCount;
		return h;
	}

	private void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
}
