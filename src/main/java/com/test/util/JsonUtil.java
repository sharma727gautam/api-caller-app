package com.test.util;

public class JsonUtil {

    public static String prettyPrint(String json) {

        if (json == null) {
            return null;
        }

        json = json.trim();

        if (!(json.startsWith("{") || json.startsWith("["))) {
            return json;
        }

        StringBuilder result = new StringBuilder();
        int indent = 0;
        boolean inQuotes = false;

        for (char c : json.toCharArray()) {

            switch (c) {

                case '"' :
                    result.append(c);

                    if (c == '"' && (result.length() == 0
                            || result.charAt(result.length() - 1) != '\\')) {
                        inQuotes = !inQuotes;
                    }
                    break;

                case '{':
                case '[':

                    result.append(c);

                    if (!inQuotes) {
                        result.append("\n");
                        indent++;
                        appendIndent(result, indent);
                    }
                    break;

                case '}':
                case ']':

                    if (!inQuotes) {
                        result.append("\n");
                        indent--;
                        appendIndent(result, indent);
                    }

                    result.append(c);
                    break;

                case ',':

                    result.append(c);

                    if (!inQuotes) {
                        result.append("\n");
                        appendIndent(result, indent);
                    }
                    break;

                case ':':

                    result.append(c);

                    if (!inQuotes) {
                        result.append(" ");
                    }
                    break;

                default:
                    result.append(c);
            }
        }

        return result.toString();
    }

    private static void appendIndent(StringBuilder sb, int indent) {

        for (int i = 0; i < indent; i++) {
            sb.append("    ");
        }
    }
}