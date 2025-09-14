package me.goowen.projects.modules.mongoDB;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.LongSerializationPolicy;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import lombok.Getter;
import me.goowen.projects.ProjectS;
import me.goowen.projects.framework.mongoDB.adapters.ItemstackAdapter;
import me.goowen.projects.framework.mongoDB.adapters.LocationAdapter;
import me.goowen.projects.modules.config.ConfigModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.time.ZoneId;
import java.util.TimeZone;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MongodbModule {
    public static @Getter Gson gson;
    public static @Getter MongoClient mongoClient;

    private static TaskChainFactory taskChainFactory;
    private ProjectS projectS = ProjectS.getInstance();
    private ConfigModule configModule = projectS.getConfigModule();

    /**
     * Maakt de instances aan voor de databasemodule en databasemanager en zet taskchainfactory op!
     */
    public MongodbModule()
    {
        mongoConnect();
        gson = new GsonBuilder().setLongSerializationPolicy( LongSerializationPolicy.STRING )
                .registerTypeAdapter(World.class, (JsonDeserializer<World>) (element, type, context) -> Bukkit.getWorld(UUID.fromString(element.getAsString())))
                .registerTypeAdapter(ChatColor.class, (JsonDeserializer<ChatColor>) (element, type, context) -> ChatColor.getByChar(element.getAsCharacter()))
                .registerTypeAdapter(TimeZone.class, (JsonDeserializer<TimeZone>) (element, type, context) -> TimeZone.getTimeZone(ZoneId.of(element.getAsString())))
                .registerTypeHierarchyAdapter(ItemStack.class, new ItemstackAdapter())
                .registerTypeHierarchyAdapter(Location.class, new LocationAdapter())
                .disableHtmlEscaping().serializeSpecialFloatingPointValues().create();
        taskChainFactory = BukkitTaskChainFactory.create(projectS);
        projectS.getLog().info(ChatColor.DARK_AQUA + "[DatabaseModule] De module is succesvol geladen!");
    }

    /**
     * Opent de MongoDB connection en maakt een instance van de playerDB Collection.
     */
    public void mongoConnect()
    {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.WARNING);

        try
        {
            String uri = configModule.getConfig().getConfigConfiguration().getString("mongoURI");
            MongoClientURI clientURI = new MongoClientURI(uri);
            mongoClient = new MongoClient(clientURI);
            projectS.getLog().info(ChatColor.DARK_PURPLE + "[Database] Database has been connected!");
        }
        catch (MongoException expetion)
        {
            projectS.getLog().warning(ChatColor.RED + "Something went wrong with connecting to the database please try again!");
            expetion.printStackTrace();
            projectS.getServer().shutdown();
        }
    }

    public static <T> TaskChain<T> newChain()
    {
        return taskChainFactory.newChain();
    }
}
