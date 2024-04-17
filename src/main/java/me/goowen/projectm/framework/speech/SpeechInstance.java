package me.goowen.projectm.framework.speech;

import lombok.Data;

@Data
public class SpeechInstance {
    Speech speech;
    Long timestamp;
    Integer index;
    String npc;

    public SpeechInstance(Speech speech, Long timestamp, Integer index, String npc) {
        this.speech = speech;
        this.timestamp = timestamp;
        this.index = index;
        this.npc = npc;
    }
}
