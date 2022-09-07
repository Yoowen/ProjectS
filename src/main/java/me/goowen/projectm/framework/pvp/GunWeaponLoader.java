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

public class GunWeaponLoader {
    private ProjectM projectM = ProjectM.getInstance();
    private final Gson gson = MongodbModule.getGson();

    public CompletableFuture<Void> saveGun(GunWeapon gunWeapon){
        return CompletableFuture.runAsync(() -> {
            MongoCollection<Document> gunsCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("guns");
            Document document = Document.parse(gson.toJson(gunWeapon, GunWeapon.class));
            gunsCollection.replaceOne(new BasicDBObject().append("_id", gunWeapon.getTagg()), document, new UpdateOptions().upsert(true));
        }).exceptionally(throwable -> {
            projectM.getLog().info(ChatColor.DARK_AQUA + "[GunLoader] has failed to save a new gun by the name: " + gunWeapon.getTagg());
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