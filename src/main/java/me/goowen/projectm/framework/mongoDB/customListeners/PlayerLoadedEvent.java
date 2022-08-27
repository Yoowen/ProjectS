package me.goowen.projectm.framework.mongoDB.customListeners;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.player.repositories.ProjectMPlayer;
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
        return ProjectM.getPlayerModule().getPlayerDB(player);
    }
}
