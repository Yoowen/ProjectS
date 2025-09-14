package me.goowen.projects.modules.time.runnable;

import me.goowen.projects.ProjectS;

public class TimerRunCheck implements Runnable {

    @Override
    public void run() {
        if (ProjectS.getTimeModule().getBossBarMap().isEmpty()) return;
        String timer = ProjectS.getTimeModule().calculateTime();
        ProjectS.getTimeModule().getBossBarMap().forEach((player, bossBar) -> ProjectS.getTimeModule().recalculateTimer(player.getPlayer(), timer));
    }
}
