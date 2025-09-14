package me.goowen.projects;

import lombok.Getter;
import me.goowen.projects.modules.config.ConfigModule;
import me.goowen.projects.modules.essentials.EssentialsModule;
import me.goowen.projects.modules.mongoDB.MongodbModule;
import me.goowen.projects.modules.player.PlayerModule;
import me.goowen.projects.modules.pvp.PvpModule;
import me.goowen.projects.modules.recipes.RecipeModule;
import me.goowen.projects.modules.time.TimeModule;
import me.goowen.projects.utilities.UtilitiesModule;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ProjectS extends JavaPlugin {

    public static @Getter ProjectS instance;
    public static @Getter ConfigModule configModule;
    public static @Getter MongodbModule mongodbModule;
    public static @Getter UtilitiesModule utilitiesModule;
    public static @Getter PlayerModule playerModule;
    public static @Getter TimeModule timeModule;
    public static @Getter EssentialsModule essentialsModule;
    public static @Getter PvpModule pvpModule;
    public static @Getter RecipeModule recipeModule;

    public final @Getter Logger log = this.getLogger();
    public final @Getter String prefix = ChatColor.DARK_AQUA + "[Project S]";
    private long loadMS;

    @Override
    public void onEnable()
    {
        this.loadMS = System.currentTimeMillis();
        log.info(prefix + "Gestart met het laden van ProjectS V" + this.getDescription().getVersion() + " by " + this.getDescription().getAuthors().toString().replace("[", "").replace("]", ""));

        instance = this;
        configModule = new ConfigModule();
        mongodbModule = new MongodbModule();
        utilitiesModule = new UtilitiesModule();
        playerModule = new PlayerModule();
        timeModule = new TimeModule();
        essentialsModule = new EssentialsModule();
        pvpModule = new PvpModule();
        recipeModule = new RecipeModule();

        log.info(prefix + "Project S succesvol geladen! Dit prosess duurde " + (System.currentTimeMillis() - loadMS) + " ms!");
    }

    @Override
    public void onDisable()
    {
        System.out.println(prefix + "Plugin succesvol uitgezet, good bye!");
    }
}