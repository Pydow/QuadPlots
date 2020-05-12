package net.lldv.pydow.quadplots.components.tools;

import java.util.List;

public class TextTools {

    public static String stringifyList(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list.size() == 0) return "";

        for (String str : list) {
            sb.append(str).append(", ");
        }

        return sb.toString().substring(0, sb.toString().length() - 2);
    }

}
