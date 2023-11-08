package me.goowen.projectm.modules.plot.tabCompleters;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.plot.Plot;
import me.goowen.projectm.framework.plot.enums.PlotStatus;
import me.goowen.projectm.framework.plot.enums.PlotType;
import me.goowen.projectm.framework.pvp.GunWeapon;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlayerPlotCommandTabCompleter implements TabCompleter {

    @Override
    public @Nullable
    List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> possibleArgs = new ArrayList<>();

        if (args.length == 1) {
            possibleArgs.add("create");
            possibleArgs.add("reload");
            possibleArgs.add("setOwner");
            possibleArgs.add("removeOwner");
            possibleArgs.add("addMember");
            possibleArgs.add("removeMember");
            possibleArgs.add("setStatus");
            possibleArgs.add("setMailbox");
            possibleArgs.add("unavailablePlots");

            possibleArgs = changeByUseInput(possibleArgs, args[0]);
        }

        if (args.length == 4 && args[0].equalsIgnoreCase("create")) {
            for (PlotType plotType : PlotType.values()) {
                possibleArgs.add(plotType.toString());
            }
            possibleArgs = changeByUseInput(possibleArgs, args[3]);
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("setStatus")) {
            for (PlotStatus plotStatus : PlotStatus.values()) {
                possibleArgs.add(plotStatus.toString());
            }
            possibleArgs = changeByUseInput(possibleArgs, args[2]);
        }

        if (args.length == 2 && (args[0].equalsIgnoreCase("setOwner") || args[0].equalsIgnoreCase("removeOwner") || args[0].equalsIgnoreCase("addMember") || args[0].equalsIgnoreCase("removeMember") || args[0].equalsIgnoreCase("setStatus") || args[0].equalsIgnoreCase("setMailbox"))) {
            for (Plot plot : ProjectM.getPlotModule().getPlotList()) {
                possibleArgs.add(plot.getTagg());
            }
            possibleArgs = changeByUseInput(possibleArgs, args[1]);
        }

        if (args.length == 3 && (args[0].equalsIgnoreCase("setOwner") ||  args[0].equalsIgnoreCase("addMember") || args[0].equalsIgnoreCase("removeMember"))) {
            for (Player player: Bukkit.getOnlinePlayers()) {
                Player sender = (Player) commandSender;
                if (sender.canSee(player)) {
                    possibleArgs.add(player.getName());
                }
            }
            possibleArgs = changeByUseInput(possibleArgs, args[2]);
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