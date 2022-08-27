package me.goowen.projectm.framework.player.prefix;

public enum PrefixType {
    PLAYER("player", "(Guest)"),
    BUILDER("builder", "ꑥ"),
    MOD("mod", "ꑥ"),
    PROJECT_LEAD("project-lead","ꐁ");

    private String name;
    private String prefix;

    PrefixType(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    public String getPrefix(){
        return this.prefix;
    }

    public String getName(){
        return this.name;
    }
}
