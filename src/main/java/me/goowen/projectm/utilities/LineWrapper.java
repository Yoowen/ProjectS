package me.goowen.projectm.utilities;

import java.util.ArrayList;

public class LineWrapper {
    public static ArrayList<String> wrap(String string, int wrapLength) {
        if (string == null) {
            return null;
        }

        if (wrapLength < 1) {
            wrapLength = 1;
        }
        int inputLineLength = string.length();
        int offset = 0;

        ArrayList<String> strings = new ArrayList<String>();

        while ((inputLineLength - offset) > wrapLength) {
            if (string.charAt(offset) == ' ') {
                offset++;
                continue;
            }
            int spaceToWrapAt = string.lastIndexOf(' ', wrapLength + offset);

            if (spaceToWrapAt >= offset) {
                // normal case
                strings.add(string.substring(offset, spaceToWrapAt));
                offset = spaceToWrapAt + 1;

            } else {
                // do not wrap really long word, just extend beyond limit
                spaceToWrapAt = string.indexOf(' ', wrapLength + offset);
                if (spaceToWrapAt >= 0) {
                    strings.add(string.substring(offset, spaceToWrapAt));
                    offset = spaceToWrapAt + 1;
                } else {
                    strings.add(string.substring(offset));
                    offset = inputLineLength;
                }
            }
        }

        // Whatever is left in line is short enough to just pass through
        strings.add(string.substring(offset));

        return strings;
    }
}
