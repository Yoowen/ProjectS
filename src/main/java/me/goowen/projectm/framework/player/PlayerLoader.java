package me.goowen.projectm.framework.player;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.mongoDB.callbacks.LoadingPlayer;
import me.goowen.projectm.framework.player.repositories.ProjectMPlayer;
import me.goowen.projectm.modules.mongoDB.MongodbModule;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

import static com.mongodb.client.model.Filters.eq;

public class PlayerLoader {
    private final ProjectM projectM = ProjectM.getInstance();
    private final Gson gson = MongodbModule.getGson();

    public CompletableFuture<Void> savePlayer(ProjectMPlayer projectMPlayer) {
        return CompletableFuture.runAsync(() -> {
            MongoCollection mongoCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("players");
            Document document = Document.parse(MongodbModule.getGson().toJson(projectMPlayer, ProjectMPlayer.class));

            mongoCollection.replaceOne(new BasicDBObject().append("_id", projectMPlayer.getUuid().toString()), document, new UpdateOptions().upsert(true));
            projectM.getLog().info(ChatColor.LIGHT_PURPLE + projectMPlayer.getName() + " saved to the database!");
        });
    }

    public CompletableFuture<ProjectMPlayer> load(Player player, LoadingPlayer loadingPlayer) {
        return CompletableFuture.supplyAsync(() -> {

            return MongodbModule.getMongoClient().getDatabase("projectM").getCollection("players").find(eq("_id", player.getUniqueId().toString()));
        }).thenApply(documentIterator -> {
            Document document = (Document) documentIterator.first();
            ProjectMPlayer projectMPlayer;
            if (document != null && !document.isEmpty()) {
                projectMPlayer = gson.fromJson(document.toJson(), ProjectMPlayer.class);
                projectMPlayer.setName(player.getName());
                ProjectM.getPlayerModule().getPlayersList().add(projectMPlayer);
                projectM.getLog().info(ChatColor.LIGHT_PURPLE + player.getName() + " found in Database and loaded...Welkom!");
            } else {
                projectMPlayer = new ProjectMPlayer(player.getUniqueId());
                projectMPlayer.setName(player.getName());
                savePlayer(projectMPlayer);
                ProjectM.getPlayerModule().getPlayersList().add(projectMPlayer);
                projectM.getLog().info(ChatColor.LIGHT_PURPLE + "" + player + " is new here added them in Database and loaded...Welkom!");
            }
            loadingPlayer.done(ProjectM.getPlayerModule().getPlayerDB(player));
            return projectMPlayer;
        });
    }

    public CompletableFuture<ProjectMPlayer> load(OfflinePlayer player) {
        return CompletableFuture.supplyAsync(() -> {
            return MongodbModule.getMongoClient().getDatabase("projectM").getCollection("players").find(eq("_id", player.getUniqueId().toString()));
        }).thenApply(documentIterator -> {
            Document document = (Document) documentIterator.first();
            ProjectMPlayer projectMPlayer;
            if (document == null || document.isEmpty()) {
                projectM.getLog().warning(ChatColor.RED+ "" + player + " Error Player was unable to load because the player does not exist!");
                return null;
            }
            projectMPlayer = gson.fromJson(document.toJson(), ProjectMPlayer.class);
            projectMPlayer.setName(player.getName());
            ProjectM.getPlayerModule().getPlayersList().add(projectMPlayer);
            projectM.getLog().info(ChatColor.LIGHT_PURPLE + player.getName() + " found in Database and loaded...");
            return projectMPlayer;
        });
    }
}