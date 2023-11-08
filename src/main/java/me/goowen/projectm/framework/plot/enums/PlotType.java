package me.goowen.projectm.framework.plot.enums;

public enum PlotType {
    SINGLE(0, "셝"),
    SMALL(2, "셞"),
    SHOP(4, "ꑛ"),
    MEDIUM(4, "셟"),
    LARGE(6,"셡");

    private Integer size;
    private String prefix;

    PlotType(Integer size, String prefix) {
        this.size = size;
        this.prefix = prefix;
    }

    public Integer getPlayerSize(){
        return this.size;
    }

    public String getPrefix(){
        return this.prefix;
    }
}
