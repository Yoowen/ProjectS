package me.goowen.projects.framework.mongoDB.customListeners;

import me.goowen.projects.ProjectS;
import me.goowen.projects.framework.player.repositories.ProjectMPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerLoadedEvent extends PlayerEvent {
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public PlayerLoadedEvent(Player player)
    {
        super(player);
        this.player = player;
    }

    public static HandlerList getHandlerList()
    {
        return HANDLERS_LIST;
    }

    @Override
    public HandlerList getHandlers()
    {
        return HANDLERS_LIST;
    }

    public ProjectMPlayer getPlayerDB()
    {
        return ProjectS.getPlayerModule().getPlayerDB(player);
    }
}
