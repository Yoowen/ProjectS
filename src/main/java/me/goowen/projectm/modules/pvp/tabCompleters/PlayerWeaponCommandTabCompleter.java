package me.goowen.projectm.modules.pvp.tabCompleters;

import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.pvp.GunWeapon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlayerWeaponCommandTabCompleter implements TabCompleter {

    @Override
    public @Nullable
    List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> possibleArgs = new ArrayList<>();

        if (args.length == 1) {
            possibleArgs.add("create");
            possibleArgs.add("get");
            possibleArgs.add("reload");

            possibleArgs = changeByUseInput(possibleArgs, args[0]);
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("get")) {
            for (GunWeapon gun : ProjectM.getPvpModule().getGunList()) {
                possibleArgs.add(gun.getTagg());
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
