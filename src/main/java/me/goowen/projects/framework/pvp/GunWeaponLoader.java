package me.goowen.projects.framework.pvp;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import me.goowen.projects.ProjectS;
import me.goowen.projects.modules.mongoDB.MongodbModule;
import org.bson.Document;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GunWeaponLoader {
    private ProjectS projectS = ProjectS.getInstance();
    private final Gson gson = MongodbModule.getGson();

    public CompletableFuture<Void> saveGun(GunWeapon gunWeapon){
        return CompletableFuture.runAsync(() -> {
            MongoCollection<Document> gunsCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("guns");
            Document document = Document.parse(gson.toJson(gunWeapon, GunWeapon.class));
            gunsCollection.replaceOne(new BasicDBObject().append("_id", gunWeapon.getTagg()), document, new UpdateOptions().upsert(true));
        }).exceptionally(throwable -> {
            projectS.getLog().info(ChatColor.DARK_AQUA + "[GunLoader] has failed to save a new gun by the name: " + gunWeapon.getTagg());
            throwable.printStackTrace();
            return null;
        });
    }

    public CompletableFuture<List<GunWeapon>> getGuns(){
        return CompletableFuture.supplyAsync(() -> {
            MongoCollection<Document> gunsCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("guns");
            List<GunWeapon> gunsList = new ArrayList<>();
            for (Document gunObject : gunsCollection.find()) {
                GunWeapon gun =  gson.fromJson(gunObject.toJson(),GunWeapon.class);
                gunsList.add(gun);
            }
            return gunsList;
        });
    }
}