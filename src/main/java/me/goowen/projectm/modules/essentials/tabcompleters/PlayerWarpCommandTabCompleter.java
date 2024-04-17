package me.goowen.projectm.modules.essentials.tabcompleters;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.essentials.WarpLocation;
import me.goowen.projectm.framework.plot.enums.PlotStatus;
import me.goowen.projectm.modules.essentials.EssentialsModule;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlayerWarpCommandTabCompleter implements TabCompleter {

    @Override
    public @Nullable
    List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> possibleArgs = new ArrayList<>();

        if (args.length == 1) {
            possibleArgs.add("teleport");
            possibleArgs.add("create");
            possibleArgs.add("delete");
            possibleArgs.add("setpermission");
            possibleArgs.add("deletepermission");

            possibleArgs = changeByUseInput(possibleArgs, args[0]);
        }

        if (args.length == 2 && !args[0].equalsIgnoreCase("create")) {
            for (WarpLocation warpLocation : ProjectM.getEssentialsModule().getWarpLocations()) {
                possibleArgs.add(warpLocation.getTagg());
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