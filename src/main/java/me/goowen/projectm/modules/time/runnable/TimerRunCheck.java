package me.goowen.projectm.modules.time.runnable;

import me.goowen.projectm.ProjectM;

public class TimerRunCheck implements Runnable {

    @Override
    public void run() {
        if (ProjectM.getTimeModule().getBossBarMap().isEmpty()) return;
        String timer = ProjectM.getTimeModule().calculateTime();
        ProjectM.getTimeModule().getBossBarMap().forEach((player, bossBar) -> ProjectM.getTimeModule().recalculateTimer(player.getPlayer(), timer));
    }
}
