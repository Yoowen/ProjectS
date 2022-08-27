package me.goowen.projectm.framework.mongoDB.callbacks;

import me.goowen.projectm.framework.player.repositories.ProjectMPlayer;

public interface LoadingPlayer {

    void waiting();

    void fetching();

    void done(ProjectMPlayer projectMPlayer);

    void error(String err);

    void welcome();
}