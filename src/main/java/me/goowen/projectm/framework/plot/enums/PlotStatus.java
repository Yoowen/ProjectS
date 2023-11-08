package me.goowen.projectm.framework.plot.enums;

public enum PlotStatus {
    OCCUPIED("TEMP(Occupied)"),
    FREE("TEMP(Free)"),
    UNAVAILABLE("TEMP(Unavailable)");

    private final String prefix;

    PlotStatus(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix(){
        return this.prefix;
    }
}
