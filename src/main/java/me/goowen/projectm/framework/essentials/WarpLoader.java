package me.goowen.projectm.framework.essentials;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.modules.mongoDB.MongodbModule;
import org.bson.Document;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WarpLoader {
    private ProjectM projectM = ProjectM.getInstance();
    private final Gson gson = MongodbModule.getGson();

    /**
     * Saves a warp object to the database.
     * @param warp object to save.
     * @return result of the completableFuture.
     */
    public CompletableFuture<Void> saveWarp(WarpLocation warp){
        return CompletableFuture.runAsync(() -> {
            MongoCollection<Document> warpCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("warp");
            Document document = Document.parse(gson.toJson(warp, WarpLocation.class));
            warpCollection.replaceOne(new BasicDBObject().append("_id", warp.getTagg()), document, new UpdateOptions().upsert(true));
        }).exceptionally(throwable -> {
            projectM.getLog().info(ChatColor.DARK_AQUA + "[WarpLoader] has failed to save a new warp by the name: " + warp.getTagg());
            throwable.printStackTrace();
            return null;
        });
    }

    /**
     * Returns a list of all the warp location objects in the database.
     * @return a list of all the warp locations.
     */
    public CompletableFuture<List<WarpLocation>> getWarp() {
        return CompletableFuture.supplyAsync(() -> {
            MongoCollection<Document> warpCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("warp");
            List<WarpLocation> warpList = new ArrayList<>();
            for (Document warpObject : warpCollection.find()) {
                WarpLocation warp =  gson.fromJson(warpObject.toJson(),WarpLocation.class);
                warpList.add(warp);
            }
            return warpList;
        });
    }

    /**
     * Removes a single warp location object from the database.
     * @param warpLocation that needs to be removed.
     * @return result of the completableFuture.
     */
    public CompletableFuture<Void> deleteWarp(WarpLocation warpLocation) {
        return CompletableFuture.runAsync(() -> {
            MongoCollection<Document> warpCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("warp");
            warpCollection.deleteOne(new BasicDBObject("_id", warpLocation.getTagg()));
        });
    }
}