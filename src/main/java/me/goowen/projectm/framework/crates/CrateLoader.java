package me.goowen.projectm.framework.crates;

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

public class CrateLoader {
    private ProjectM projectM = ProjectM.getInstance();
    private final Gson gson = MongodbModule.getGson();

    public CompletableFuture<Void> saveCrate(Crate crate){
        return CompletableFuture.runAsync(() -> {
            MongoCollection<Document> cratesCollection = MongodbModule.getMongoClient().getDatabase("crates").getCollection("crateLocations");
            Document document = Document.parse(gson.toJson(crate, Crate.class));
            cratesCollection.replaceOne(new BasicDBObject().append("_id", crate.getTagg()), document, new UpdateOptions().upsert(true));
        }).exceptionally(throwable -> {
            projectM.getLog().info(ChatColor.DARK_AQUA + "[CrateLoader] has failed to save a new crate by the name: " + crate.getTagg());
            throwable.printStackTrace();
            return null;
        });
    }

    public CompletableFuture<List<Crate>> getCrates() {
        return CompletableFuture.supplyAsync(() -> {
            MongoCollection<Document> cratesCollection = MongodbModule.getMongoClient().getDatabase("crates").getCollection("crateLocations");
            List<Crate> crateList = new ArrayList<>();
            for (Document crateObject : cratesCollection.find()) {
                Crate crate =  gson.fromJson(crateObject.toJson(),Crate.class);
                crateList.add(crate);
            }
            return crateList;
        });
    }

    public CompletableFuture<Void> deleteCrate(Crate crate) {
        return CompletableFuture.runAsync(() -> {
            MongoCollection<Document> cratesCollection = MongodbModule.getMongoClient().getDatabase("crates").getCollection("crateLocations");
            cratesCollection.deleteOne(new BasicDBObject("_id", crate.getTagg()));
        });
    }
}
