package main.n3reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import main.n3reader.util.Format;
import main.n3reader.util.Prefix;

public class N3Reader {
    final private String n3Path;

    public N3Reader(String n3Path) {
        this.n3Path = n3Path;
    }

    public N3 readN3() {
        N3 n3 = new N3("", 0L);

        HashMap<String, String> uris = new HashMap<String, String>();

        try {
            FileInputStream fin = new FileInputStream(n3Path);
            InputStreamReader isr = new InputStreamReader(fin, "utf-8");
            BufferedReader br = new BufferedReader(isr);

            String s = "";
            String p = "";
            String o = "";
            String line = br.readLine();
            while (line != null) {
                ArrayList<String> elements = parse(line);

                int length = elements.size();

                if (length == 0) {
                    line = br.readLine();
                    continue;
                }

                if (length == 3 && elements.get(0).equals("@prefix")) {
                    String key = elements.get(1);
                    String value = elements.get(2).replaceAll("^<|>$", "");
                    uris.put(key, value);

                    line = br.readLine();
                    continue;
                }

                if (length == 3) {
                    s = Format.format(elements.get(0));
                    p = Format.format(elements.get(1));
                    o = Format.format(elements.get(2));
                } else if (length == 2) {
                    p = Format.format(elements.get(0));
                    o = Format.format(elements.get(1));
                } else if (length == 1) {
                    o = Format.format(elements.get(0));
                } else {
                    System.err.println(": [Warn] Cannot parse this line. : "
                            + line);
                }

                s = Prefix.notOmit(s, uris);
                p = Prefix.notOmit(p, uris);
                o = Prefix.notOmit(o, uris);

                n3.addTriple(new Triple(s, p, o));

                line = br.readLine();
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return n3;
    }

    private ArrayList<String> parse(String line) {
        String unFormatedLine = line;

        unFormatedLine = Format.replaceWithSpace(unFormatedLine);
        ArrayList<String> elements = Format.splitBySpace(unFormatedLine);

        return elements;
    }
}
