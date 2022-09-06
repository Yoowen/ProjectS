package me.goowen.projectm.framework.pvp;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.crates.Crate;
import me.goowen.projectm.modules.mongoDB.MongodbModule;
import org.bson.Document;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AmmoLoader {
    private ProjectM projectM = ProjectM.getInstance();
    private final Gson gson = MongodbModule.getGson();

    public CompletableFuture<Void> saveAmmo(Ammo ammo){
        return CompletableFuture.runAsync(() -> {
            MongoCollection<Document> ammoCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("ammo");
            Document document = Document.parse(gson.toJson(ammo, Ammo.class));
            ammoCollection.replaceOne(new BasicDBObject().append("_id", ammo.getTagg()), document, new UpdateOptions().upsert(true));
        }).exceptionally(throwable -> {
            projectM.getLog().info(ChatColor.DARK_AQUA + "[AmmoLoader] has failed to save a new ammo by the name: " + ammo.getTagg());
            throwable.printStackTrace();
            return null;
        });
    }

    public CompletableFuture<List<Ammo>> getAmmo() {
        return CompletableFuture.supplyAsync(() -> {
            MongoCollection<Document> ammoCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("ammo");
            List<Ammo> ammoList = new ArrayList<>();
            for (Document ammoObject : ammoCollection.find()) {
                Ammo ammo =  gson.fromJson(ammoObject.toJson(),Ammo.class);
                ammoList.add(ammo);
            }
            return ammoList;
        });
    }
}
