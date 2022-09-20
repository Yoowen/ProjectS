package me.goowen.projectm;

import lombok.Getter;
import me.goowen.projectm.modules.config.ConfigModule;
import me.goowen.projectm.modules.crates.CratesModule;
import me.goowen.projectm.modules.essentials.EssentialsModule;
import me.goowen.projectm.modules.lore.LoreModule;
import me.goowen.projectm.modules.mongoDB.MongodbModule;
import me.goowen.projectm.modules.player.PlayerModule;
import me.goowen.projectm.modules.pvp.PvpModule;
import me.goowen.projectm.modules.time.TimeModule;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ProjectM extends JavaPlugin {

    public static @Getter ProjectM instance;
    public static @Getter ConfigModule configModule;
    public static @Getter MongodbModule mongodbModule;
    public static @Getter PlayerModule playerModule;
    public static @Getter TimeModule timeModule;
    public static @Getter EssentialsModule essentialsModule;
    public static @Getter CratesModule cratesModule;
    public static @Getter PvpModule pvpModule;
    public static @Getter LoreModule loreModule;

    public final @Getter Logger log = this.getLogger();
    public final @Getter String prefix = ChatColor.DARK_AQUA + "[Project M]";
    private long loadMS;

    @Override
    public void onEnable()
    {
        this.loadMS = System.currentTimeMillis();
        System.out.println(prefix + "Gestart met het laden van ProjectM V" + this.getDescription().getVersion() + " by " + this.getDescription().getAuthors().toString().replace("[", "").replace("]", ""));
        instance = this;
        configModule = new ConfigModule();
        mongodbModule = new MongodbModule();
        playerModule = new PlayerModule();
        timeModule = new TimeModule();
        essentialsModule = new EssentialsModule();
        cratesModule = new CratesModule();
        pvpModule = new PvpModule();
        loreModule = new LoreModule();

        System.out.println(prefix + "Project M succesvol geladen! Dit prosess duurde " + (System.currentTimeMillis() - loadMS) + " ms!");
    }

    @Override
    public void onDisable()
    {
        System.out.println(prefix + "Plugin succesvol uitgezet, good bye!");
    }

}