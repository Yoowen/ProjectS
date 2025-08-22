package me.goowen.projectm;

import lombok.Getter;
import me.goowen.projectm.modules.config.ConfigModule;
import me.goowen.projectm.modules.crates.CratesModule;
import me.goowen.projectm.modules.currency.CurrencyModule;
import me.goowen.projectm.modules.essentials.EssentialsModule;
import me.goowen.projectm.modules.lore.LoreModule;
import me.goowen.projectm.modules.misc.MiscModule;
import me.goowen.projectm.modules.mongoDB.MongodbModule;
import me.goowen.projectm.modules.player.PlayerModule;
import me.goowen.projectm.modules.playerInventory.PlayerInventoryModule;
import me.goowen.projectm.modules.plot.PlotModule;
import me.goowen.projectm.modules.pvp.PvpModule;
import me.goowen.projectm.modules.shops.ShopsModule;
import me.goowen.projectm.modules.speech.SpeechModule;
import me.goowen.projectm.modules.time.TimeModule;
import me.goowen.projectm.utilities.UtilitiesModule;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ProjectM extends JavaPlugin {

    public static @Getter ProjectM instance;
    public static @Getter ConfigModule configModule;
    public static @Getter MongodbModule mongodbModule;
    public static @Getter UtilitiesModule utilitiesModule;
    public static @Getter PlayerModule playerModule;
    public static @Getter TimeModule timeModule;
    public static @Getter EssentialsModule essentialsModule;
    public static @Getter CratesModule cratesModule;
    public static @Getter PvpModule pvpModule;
    public static @Getter LoreModule loreModule;
    public static @Getter MiscModule miscModule;
    public static @Getter PlayerInventoryModule playerInventoryModule;
    public static @Getter CurrencyModule currencyModule;
    public static @Getter SpeechModule speechModule;
    public static @Getter ShopsModule shopsModule;
    public static @Getter PlotModule plotModule;

    public final @Getter Logger log = this.getLogger();
    public final @Getter String prefix = ChatColor.DARK_AQUA + "[Project M]";
    private long loadMS;

    @Override
    public void onEnable()
    {
        this.loadMS = System.currentTimeMillis();
        log.info(prefix + "Gestart met het laden van ProjectM V" + this.getDescription().getVersion() + " by " + this.getDescription().getAuthors().toString().replace("[", "").replace("]", ""));

        instance = this;
        configModule = new ConfigModule();
        mongodbModule = new MongodbModule();
        utilitiesModule = new UtilitiesModule();
        playerModule = new PlayerModule();
        timeModule = new TimeModule();
        essentialsModule = new EssentialsModule();
        cratesModule = new CratesModule();
        pvpModule = new PvpModule();
        loreModule = new LoreModule();
        miscModule = new MiscModule();
        playerInventoryModule = new PlayerInventoryModule();
        plotModule = new PlotModule();
        currencyModule = new CurrencyModule();
        speechModule = new SpeechModule();
        shopsModule = new ShopsModule();

        log.info(prefix + "Project M succesvol geladen! Dit prosess duurde " + (System.currentTimeMillis() - loadMS) + " ms!");
    }

    @Override
    public void onDisable()
    {
        System.out.println(prefix + "Plugin succesvol uitgezet, good bye!");
    }
}