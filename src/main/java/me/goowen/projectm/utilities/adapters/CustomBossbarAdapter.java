package me.goowen.projectm.utilities.adapters;

public class CustomBossbarAdapter {

    public String getBarLength(String string) {
        return "ꈁ\uF811" + addaptForBossbarLetters(string) + "ꈁ\uF811" +  addaptForBossbarSpaces(string);
    }

    public String addaptForBossbarLetters(String string) {
        String bossbarString = string;
        //numbers
        bossbarString = bossbarString.replace("0", "ꈅ\uF811");
        bossbarString = bossbarString.replace("1", "ꈅ\uF811");
        bossbarString = bossbarString.replace("2", "ꈅ\uF811");
        bossbarString = bossbarString.replace("3", "ꈅ\uF811");
        bossbarString = bossbarString.replace("4", "ꈅ\uF811");
        bossbarString = bossbarString.replace("5", "ꈅ\uF811");
        bossbarString = bossbarString.replace("6", "ꈅ\uF811");
        bossbarString = bossbarString.replace("7", "ꈅ\uF811");
        bossbarString = bossbarString.replace("8", "ꈅ\uF811");
        bossbarString = bossbarString.replace("9", "ꈅ\uF811");

        //Special Signs
        bossbarString = bossbarString.replace(":", "ꈄ\uF811");
        bossbarString = bossbarString.replace("'", "ꈄ\uF811");
        bossbarString = bossbarString.replace(",", "ꈄ\uF811");
        bossbarString = bossbarString.replace(".", "ꈄ\uF811");
        bossbarString = bossbarString.replace("/", "ꈅ\uF811");
        bossbarString = bossbarString.replace(" ", "ꈄ\uF811");

        bossbarString = bossbarString.replace("鄟", "ꈅ\uF811ꈄ\uF811");
        bossbarString = bossbarString.replace("숡", "ꈅ\uF811ꈄ\uF811");
        bossbarString = bossbarString.replace("뀁", "ꈅ\uF811ꈄ\uF811");


        //Capitol Letters

        bossbarString = bossbarString.replace("A", "ꈅ\uF811");
        bossbarString = bossbarString.replace("B", "ꈅ\uF811");
        bossbarString = bossbarString.replace("C", "ꈅ\uF811");
        bossbarString = bossbarString.replace("D", "ꈅ\uF811");
        bossbarString = bossbarString.replace("E", "ꈅ\uF811");
        bossbarString = bossbarString.replace("F", "ꈅ\uF811");
        bossbarString = bossbarString.replace("G", "ꈅ\uF811");
        bossbarString = bossbarString.replace("H", "ꈅ\uF811");
        bossbarString = bossbarString.replace("I", "ꈄ\uF811ꈃ\uF811");
        bossbarString = bossbarString.replace("J", "ꈅ\uF811");
        bossbarString = bossbarString.replace("K", "ꈅ\uF811");
        bossbarString = bossbarString.replace("L", "ꈅ\uF811");
        bossbarString = bossbarString.replace("M", "ꈅ\uF811");
        bossbarString = bossbarString.replace("N", "ꈅ\uF811");
        bossbarString = bossbarString.replace("O", "ꈅ\uF811");
        bossbarString = bossbarString.replace("P", "ꈅ\uF811");
        bossbarString = bossbarString.replace("Q", "ꈅ\uF811");
        bossbarString = bossbarString.replace("R", "ꈅ\uF811");
        bossbarString = bossbarString.replace("S", "ꈅ\uF811");
        bossbarString = bossbarString.replace("T", "ꈅ\uF811");
        bossbarString = bossbarString.replace("U", "ꈅ\uF811");
        bossbarString = bossbarString.replace("V", "ꈅ\uF811");
        bossbarString = bossbarString.replace("W", "ꈅ\uF811");
        bossbarString = bossbarString.replace("X", "ꈅ\uF811");
        bossbarString = bossbarString.replace("Y", "ꈅ\uF811");
        bossbarString = bossbarString.replace("Z", "ꈅ\uF811");


        //Standard Letters.
        bossbarString = bossbarString.replace("a", "ꈅ\uF811");
        bossbarString = bossbarString.replace("b", "ꈅ\uF811");
        bossbarString = bossbarString.replace("c", "ꈅ\uF811");
        bossbarString = bossbarString.replace("d", "ꈅ\uF811");
        bossbarString = bossbarString.replace("e", "ꈅ\uF811");
        bossbarString = bossbarString.replace("f", "ꈄ\uF811ꈃ\uF811ꈂ\uF811");
        bossbarString = bossbarString.replace("g", "ꈅ\uF811");
        bossbarString = bossbarString.replace("h", "ꈅ\uF811");
        bossbarString = bossbarString.replace("i", "ꈄ\uF811");
        bossbarString = bossbarString.replace("j", "ꈅ\uF811");
        bossbarString = bossbarString.replace("k", "ꈄ\uF811ꈃ\uF811ꈂ\uF811");
        bossbarString = bossbarString.replace("l", "ꈄ\uF811ꈂ\uF811");
        bossbarString = bossbarString.replace("m", "ꈅ\uF811");
        bossbarString = bossbarString.replace("n", "ꈅ\uF811");
        bossbarString = bossbarString.replace("o", "ꈅ\uF811");
        bossbarString = bossbarString.replace("p", "ꈅ\uF811");
        bossbarString = bossbarString.replace("q", "ꈅ\uF811");
        bossbarString = bossbarString.replace("r", "ꈅ\uF811");
        bossbarString = bossbarString.replace("s", "ꈅ\uF811");
        bossbarString = bossbarString.replace("t", "ꈄ\uF811ꈃ\uF811");
        bossbarString = bossbarString.replace("u", "ꈅ\uF811");
        bossbarString = bossbarString.replace("v", "ꈅ\uF811");
        bossbarString = bossbarString.replace("w", "ꈅ\uF811");
        bossbarString = bossbarString.replace("x", "ꈅ\uF811");
        bossbarString = bossbarString.replace("y", "ꈅ\uF811");
        bossbarString = bossbarString.replace("z", "ꈅ\uF811");

        return bossbarString;
    }

    public String addaptForBossbarSpaces(String string) {
        String bossbarString = string;
        //numbers
        bossbarString = bossbarString.replace("0", "\uF817");
        bossbarString = bossbarString.replace("1", "\uF817");
        bossbarString = bossbarString.replace("2", "\uF817");
        bossbarString = bossbarString.replace("3", "\uF817");
        bossbarString = bossbarString.replace("4", "\uF817");
        bossbarString = bossbarString.replace("5", "\uF817");
        bossbarString = bossbarString.replace("6", "\uF817");
        bossbarString = bossbarString.replace("7", "\uF817");
        bossbarString = bossbarString.replace("8", "\uF817");
        bossbarString = bossbarString.replace("9", "\uF817");

        //Special Signs
        bossbarString = bossbarString.replace(":", "\uF813");
        bossbarString = bossbarString.replace("'", "\uF813");
        bossbarString = bossbarString.replace(",", "\uF813");
        bossbarString = bossbarString.replace(".", "\uF813");
        bossbarString = bossbarString.replace("/", "\uF817");
        bossbarString = bossbarString.replace(" ", "\uF813");

        bossbarString = bossbarString.replace("鄟", "\uF818\uF814");
        bossbarString = bossbarString.replace("숡", "\uF818\uF814");
        bossbarString = bossbarString.replace("뀁", "\uF818\uF814");

        //Capitol Letters

        bossbarString = bossbarString.replace("A", "\uF817");
        bossbarString = bossbarString.replace("B", "\uF817");
        bossbarString = bossbarString.replace("C", "\uF817");
        bossbarString = bossbarString.replace("D", "\uF817");
        bossbarString = bossbarString.replace("E", "\uF817");
        bossbarString = bossbarString.replace("F", "\uF817");
        bossbarString = bossbarString.replace("G", "\uF817");
        bossbarString = bossbarString.replace("H", "\uF817");
        bossbarString = bossbarString.replace("I", "\uF815");
        bossbarString = bossbarString.replace("J", "\uF817");
        bossbarString = bossbarString.replace("K", "\uF817");
        bossbarString = bossbarString.replace("L", "\uF817");
        bossbarString = bossbarString.replace("M", "\uF817");
        bossbarString = bossbarString.replace("N", "\uF817");
        bossbarString = bossbarString.replace("O", "\uF817");
        bossbarString = bossbarString.replace("P", "\uF817");
        bossbarString = bossbarString.replace("Q", "\uF817");
        bossbarString = bossbarString.replace("R", "\uF817");
        bossbarString = bossbarString.replace("S", "\uF817");
        bossbarString = bossbarString.replace("T", "\uF817");
        bossbarString = bossbarString.replace("U", "\uF817");
        bossbarString = bossbarString.replace("V", "\uF817");
        bossbarString = bossbarString.replace("W", "\uF817");
        bossbarString = bossbarString.replace("X", "\uF817");
        bossbarString = bossbarString.replace("Y", "\uF817");
        bossbarString = bossbarString.replace("Z", "\uF817");


        //Standard Letters.
        bossbarString = bossbarString.replace("a", "\uF817");
        bossbarString = bossbarString.replace("b", "\uF817");
        bossbarString = bossbarString.replace("c", "\uF817");
        bossbarString = bossbarString.replace("d", "\uF817");
        bossbarString = bossbarString.replace("e", "\uF817");
        bossbarString = bossbarString.replace("f", "\uF816");
        bossbarString = bossbarString.replace("g", "\uF817");
        bossbarString = bossbarString.replace("h", "\uF817");
        bossbarString = bossbarString.replace("i", "\uF813");
        bossbarString = bossbarString.replace("j", "\uF817");
        bossbarString = bossbarString.replace("k", "\uF816");
        bossbarString = bossbarString.replace("l", "\uF814");
        bossbarString = bossbarString.replace("m", "\uF817");
        bossbarString = bossbarString.replace("n", "\uF817");
        bossbarString = bossbarString.replace("o", "\uF817");
        bossbarString = bossbarString.replace("p", "\uF817");
        bossbarString = bossbarString.replace("q", "\uF817");
        bossbarString = bossbarString.replace("r", "\uF817");
        bossbarString = bossbarString.replace("s", "\uF817");
        bossbarString = bossbarString.replace("t", "\uF815");
        bossbarString = bossbarString.replace("u", "\uF817");
        bossbarString = bossbarString.replace("v", "\uF817");
        bossbarString = bossbarString.replace("w", "\uF817");
        bossbarString = bossbarString.replace("x", "\uF817");
        bossbarString = bossbarString.replace("y", "\uF817");
        bossbarString = bossbarString.replace("z", "\uF817");

        return bossbarString;
    }
}
