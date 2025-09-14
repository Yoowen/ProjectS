package me.goowen.projects.framework.mongoDB.callbacks;

import me.goowen.projects.framework.player.repositories.ProjectMPlayer;

public interface LoadingPlayer {

    void waiting();

    void fetching();

    void done(ProjectMPlayer projectMPlayer);

    void error(String err);

    void welcome();
}