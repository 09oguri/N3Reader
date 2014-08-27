package main.n3reader.util;

import java.util.ArrayList;

public class Format {
    private Format() {
    }

    public static String decodeUnicode(String s) {
        int length = s.length();
        StringBuffer sb = new StringBuffer(length);

        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (c != '\\') {
                sb.append(c);
                continue;
            }

            if (i + 1 < length && s.charAt(i + 1) != 'u') {
                sb.append(c);
                continue;
            }

            if (i + 5 < length) {
                String unicodeStr = s.substring(i + 2, i + 6);
                c = (char) Integer.parseInt(unicodeStr, 16);
                sb.append(c);
                i = i + 5;
                continue;
            }

            sb.append(c);
        }

        return sb.toString();
    }

    public static String format(String s) {
        String result = s;

        result = result.replaceAll("(\"@.*)$", "");
        result = result.replaceAll("^<|>$", "");
        result = result.replaceAll("^\"|\"$", "");

        if (result.contains("\\u")) {
            result = decodeUnicode(result);
        }

        return result;
    }

    public static String replaceWithSpace(String s) {
        String result = s;

        result = result.replaceAll("\t+", " ");
        result = result.replaceAll("　+", " ");
        result = result.replaceAll(" +", " ");
        result = result.replaceAll("^ +| +$", "");

        result = result.replaceAll(" *[.,;]$", "");

        return result;
    }

    public static ArrayList<String> splitBySpace(String line) {
        ArrayList<String> results = new ArrayList<String>(4);

        if (line.equals("")) {
            return results;
        }

        String s = line;
        int length = s.length();
        StringBuffer sb = new StringBuffer(length);

        char c;
        for (int i = 0; i < length; i++) {
            c = s.charAt(i);

            switch (c) {
            case '"':
                int next = s.indexOf('"', i + 1);
                if (next < 0) {
                    sb.append(c);
                    break;
                }

                for (; i < next + 1; i++) {
                    c = s.charAt(i);
                    sb.append(c);
                }
                i--;

                break;
            case '<':
                next = s.indexOf('>', i + 1);
                if (next < 0) {
                    sb.append(c);
                    break;
                }

                for (; i < next + 1; i++) {
                    c = s.charAt(i);
                    sb.append(c);
                }
                i--;

                break;
            case ' ':
                String result = sb.toString();
                results.add(result);
                sb.delete(0, result.length());
                break;
            default:
                sb.append(c);
                break;
            }
        }

        if (sb.length() != 0) {
            results.add(sb.toString());
        }

        return results;
    }
}
