package me.goowen.projectm.modules.mongoDB;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import lombok.Getter;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.modules.config.ConfigModule;
import org.bukkit.ChatColor;

public class MongodbModule {
    public static @Getter Gson gson;
    public static @Getter MongoClient mongoClient;

    private static TaskChainFactory taskChainFactory;
    private ProjectM projectM = ProjectM.getInstance();
    private ConfigModule configModule = projectM.getConfigModule();

    /**
     * Maakt de instances aan voor de databasemodule en databasemanager en zet taskchainfactory op!
     */
    public MongodbModule()
    {
        mongoConnect();
        gson = new GsonBuilder().setLongSerializationPolicy( LongSerializationPolicy.STRING ).disableHtmlEscaping().serializeSpecialFloatingPointValues().create();
        taskChainFactory = BukkitTaskChainFactory.create(projectM);
        projectM.getLog().info(ChatColor.DARK_AQUA + "[DatabaseModule] De module is succesvol geladen!");
    }

    /**
     * Opent de MongoDB connection en maakt een instance van de playerDB Collection.
     */
    public void mongoConnect()
    {
        try
        {
            String uri = configModule.getConfig().getConfigConfiguration().getString("mongoURI");
            MongoClientURI clientURI = new MongoClientURI(uri);
            mongoClient = new MongoClient(clientURI);
            projectM.getLog().info(ChatColor.DARK_PURPLE + "[Database] Database has been connected!");
        }
        catch (MongoException expetion)
        {
            projectM.getLog().warning(ChatColor.RED + "Something went wrong with connecting to the database please try again!");
            expetion.printStackTrace();
            projectM.getServer().shutdown();
        }
    }

    public static <T> TaskChain<T> newChain()
    {
        return taskChainFactory.newChain();
    }
}
