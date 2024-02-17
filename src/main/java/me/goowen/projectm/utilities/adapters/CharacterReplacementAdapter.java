package me.goowen.projectm.utilities.adapters;

public class CharacterReplacementAdapter {

    public String addaptForBossbar(String string) {
        String bossbarString = string;
        //numbers
        bossbarString = bossbarString.replace("0", "\uE1E2");
        bossbarString = bossbarString.replace("1", "\uE1E3");
        bossbarString = bossbarString.replace("2", "\uE1E4");
        bossbarString = bossbarString.replace("3", "\uE1E5");
        bossbarString = bossbarString.replace("4", "\uE1E6");
        bossbarString = bossbarString.replace("5", "\uE1E7");
        bossbarString = bossbarString.replace("6", "\uE1E8");
        bossbarString = bossbarString.replace("7", "\uE1E9");
        bossbarString = bossbarString.replace("8", "\uE1EA");
        bossbarString = bossbarString.replace("9", "\uE1EB");

        //Special Signs
        bossbarString = bossbarString.replace(":", "\uE1EC");
        bossbarString = bossbarString.replace("'", "\uE1D9");
        bossbarString = bossbarString.replace(",", "\uE1DE");
        bossbarString = bossbarString.replace(".", "\uE1E0");
        bossbarString = bossbarString.replace("/", "\uE1E1");
        bossbarString = bossbarString.replace("!", "\uE1D3");
        bossbarString = bossbarString.replace("+", "\uE1DD");

        //Capitol Letters

        bossbarString = bossbarString.replace("A", "\uE1F3");
        bossbarString = bossbarString.replace("B", "\uE1F4");
        bossbarString = bossbarString.replace("C", "\uE1F5");
        bossbarString = bossbarString.replace("D", "\uE1F6");
        bossbarString = bossbarString.replace("E", "\uE1F7");
        bossbarString = bossbarString.replace("F", "\uE1F8");
        bossbarString = bossbarString.replace("G", "\uE1F9");
        bossbarString = bossbarString.replace("H", "\uE1FA");
        bossbarString = bossbarString.replace("I", "\uE1FB");
        bossbarString = bossbarString.replace("J", "\uE1FC");
        bossbarString = bossbarString.replace("K", "\uE1FD");
        bossbarString = bossbarString.replace("L", "\uE1FE");
        bossbarString = bossbarString.replace("M", "\uE1FF");
        bossbarString = bossbarString.replace("N", "\uE200");
        bossbarString = bossbarString.replace("O", "\uE201");
        bossbarString = bossbarString.replace("P", "\uE202");
        bossbarString = bossbarString.replace("Q", "\uE203");
        bossbarString = bossbarString.replace("R", "\uE204");
        bossbarString = bossbarString.replace("S", "\uE205");
        bossbarString = bossbarString.replace("T", "\uE206");
        bossbarString = bossbarString.replace("U", "\uE207");
        bossbarString = bossbarString.replace("V", "\uE208");
        bossbarString = bossbarString.replace("W", "\uE209");
        bossbarString = bossbarString.replace("X", "\uE20A");
        bossbarString = bossbarString.replace("Y", "\uE20B");
        bossbarString = bossbarString.replace("Z", "\uE20C");


        //Standard Letters.
        bossbarString = bossbarString.replace("a", "\uE213");
        bossbarString = bossbarString.replace("b", "\uE214");
        bossbarString = bossbarString.replace("c", "\uE215");
        bossbarString = bossbarString.replace("d", "\uE216");
        bossbarString = bossbarString.replace("e", "\uE217");
        bossbarString = bossbarString.replace("f", "\uE218");
        bossbarString = bossbarString.replace("g", "\uE219");
        bossbarString = bossbarString.replace("h", "\uE21A");
        bossbarString = bossbarString.replace("i", "\uE21B");
        bossbarString = bossbarString.replace("j", "\uE21C");
        bossbarString = bossbarString.replace("k", "\uE21D");
        bossbarString = bossbarString.replace("l", "\uE21E");
        bossbarString = bossbarString.replace("m", "\uE21F");
        bossbarString = bossbarString.replace("n", "\uE220");
        bossbarString = bossbarString.replace("o", "\uE221");
        bossbarString = bossbarString.replace("p", "\uE222");
        bossbarString = bossbarString.replace("q", "\uE223");
        bossbarString = bossbarString.replace("r", "\uE224");
        bossbarString = bossbarString.replace("s", "\uE225");
        bossbarString = bossbarString.replace("t", "\uE226");
        bossbarString = bossbarString.replace("u", "\uE227");
        bossbarString = bossbarString.replace("v", "\uE228");
        bossbarString = bossbarString.replace("w", "\uE229");
        bossbarString = bossbarString.replace("x", "\uE22A");
        bossbarString = bossbarString.replace("y", "\uE22B");
        bossbarString = bossbarString.replace("z", "\uE22C");

        return bossbarString;
    }

    public String addaptForActionbar(String string) {
        String actionbarString = string;

        actionbarString = actionbarString.replace("0", "ꢚ");
        actionbarString = actionbarString.replace("1", "ꢛ");
        actionbarString = actionbarString.replace("2", "ꢜ");
        actionbarString = actionbarString.replace("3", "ꢝ");
        actionbarString = actionbarString.replace("4", "ꢞ");
        actionbarString = actionbarString.replace("5", "ꢟ");
        actionbarString = actionbarString.replace("6", "ꢠ");
        actionbarString = actionbarString.replace("7", "ꢡ");
        actionbarString = actionbarString.replace("8", "ꢢ");
        actionbarString = actionbarString.replace("9", "ꢣ");
        actionbarString = actionbarString.replace("", "\uF811");

        return actionbarString;
    }
}
