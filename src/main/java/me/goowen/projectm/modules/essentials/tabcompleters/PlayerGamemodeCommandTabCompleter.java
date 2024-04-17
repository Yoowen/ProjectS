package me.goowen.projectm.modules.essentials.tabcompleters;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlayerGamemodeCommandTabCompleter implements TabCompleter {

    @Override
    public @Nullable
    List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player player = (Player) commandSender;
        List<String> possibleArgs = new ArrayList<>();

        if (args.length == 1) {
            if (player.hasPermission("projectM.command.gamemode.creative")) possibleArgs.add("CREATIVE");
            if (player.hasPermission("projectM.command.gamemode.survival")) possibleArgs.add("SURVIVAL");
            if (player.hasPermission("projectM.command.gamemode.spectator")) possibleArgs.add("SPECTATOR");
            if (player.hasPermission("projectM.command.gamemode.adventure")) possibleArgs.add("ADVENTURE");

            possibleArgs = changeByUseInput(possibleArgs, args[0]);
        }

        if (args.length == 2) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.isVisibleByDefault()) {
                    possibleArgs.add(onlinePlayer.getName());
                }
            }
            possibleArgs = changeByUseInput(possibleArgs, args[1]);
        }

        return possibleArgs;
    }

    private List<String> changeByUseInput(List<String> Possibleargs, String args) {

        for (int i = 0; i < Possibleargs.size(); i++) {
            if (!Possibleargs.get(i).toLowerCase().startsWith(args.toLowerCase())) {
                Possibleargs.remove(i);
                i--;
            }
        }
        return Possibleargs;
    }

}