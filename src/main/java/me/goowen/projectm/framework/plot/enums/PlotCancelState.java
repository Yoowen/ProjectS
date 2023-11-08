package me.goowen.projectm.framework.plot.enums;

public enum PlotCancelState {
    CANCELED( "ꑘ"),
    RENTED("ꑡ");

    private String prefix;

    PlotCancelState(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix(){
        return this.prefix;
    }
}
