package me.goowen.projectm.modules.essentials.tabcompleters;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlayerGamemodeCommandTabCompleter implements TabCompleter {

    @Override
    public @Nullable
    List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> possibleArgs = new ArrayList<>();

        if (args.length == 1) {
            possibleArgs.add("CREATIVE");
            possibleArgs.add("SURVIVAL");
            possibleArgs.add("SPECTATOR");
            possibleArgs.add("ADVENTURE");

            possibleArgs = changeByUseInput(possibleArgs, args[0]);
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