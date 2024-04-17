package me.goowen.projectm.modules.crates.tabCompleters;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlayerCrateCommandTabCompleter implements TabCompleter {

    @Override
    public @Nullable
    List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> possibleArgs = new ArrayList<>();

        if (args.length == 1) {
            possibleArgs.add("editor");
            possibleArgs.add("createItem");
            possibleArgs.add("crateLog");
            possibleArgs.add("forceReplaceAll");
            possibleArgs.add("setAmount");
            possibleArgs.add("reloadItems");
            possibleArgs.add("itemList");
            possibleArgs.add("help");

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
