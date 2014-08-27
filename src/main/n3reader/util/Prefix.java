package main.n3reader.util;

import java.util.HashMap;

public class Prefix {
    private Prefix() {
    }

    public static String notOmit(String s, HashMap<String, String> uris) {
        String result = s;

        int pos = result.indexOf(":");
        if (pos < 1) {
            return result;
        }

        if (pos + 1 < result.length()) {
            String prefix = result.substring(0, pos + 1);

            String uri = uris.get(prefix);
            if (uri != null) {
                result = uri + result.substring(pos + 1);
            }
        }

        return result;
    }
}
