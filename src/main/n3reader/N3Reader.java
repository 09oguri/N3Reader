package main.n3reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import main.n3reader.util.Format;
import main.n3reader.util.Prefix;

public class N3Reader {
    private static final String CHARACTER_CODE = "utf-8";
    private final String n3Path;

    public N3Reader(String n3Path) {
        this.n3Path = n3Path;
    }

    private ArrayList<String> parse(String line) {
        String unFormatedLine = line;

        unFormatedLine = Format.replaceWithSpace(unFormatedLine);
        ArrayList<String> elements = Format.splitBySpace(unFormatedLine);

        for (int i = 0; i < elements.size(); i++) {
            String unFormatedElem = elements.get(i);
            elements.set(i, Format.format(unFormatedElem));
        }

        return elements;
    }

    public N3 readN3() throws IOException {
        N3 n3 = new N3("", 0L);

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

            if (elemSize == 0) {
                line = br.readLine();
                continue;
            }

            if (elements.get(0).equals("@prefix")) {
                String key = elements.get(1);
                String value = elements.get(2);
                uris.put(key, value);

                line = br.readLine();
                continue;
            }

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
