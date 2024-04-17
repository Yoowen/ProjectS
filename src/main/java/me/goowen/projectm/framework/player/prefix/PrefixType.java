package me.goowen.projectm.framework.player.prefix;

public enum PrefixType {
    PLAYER("player", "(Guest)"),
    BUILDER("builder", "ꐃ"),
    MOD("mod", "ꑥ"),
    PROJECT_LEAD("project-lead","ꐁ");

    private String name;
    private String prefix;

    PrefixType(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    /**
     * @return the icon of the prefix.
     */
    public String getPrefix(){
        return this.prefix;
    }

    /**
     * @return the name of the prefix.
     */
    public String getName(){
        return this.name;
    }
}
