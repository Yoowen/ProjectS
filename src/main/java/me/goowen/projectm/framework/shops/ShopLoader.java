package me.goowen.projectm.framework.shops;

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

public class ShopLoader {
    private ProjectM projectM = ProjectM.getInstance();
    private final Gson gson = MongodbModule.getGson();

    public CompletableFuture<Void> saveShop(Shop shop){
        return CompletableFuture.runAsync(() -> {
            MongoCollection<Document> shopsCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("shops");
            Document document = Document.parse(gson.toJson(shop, Shop.class));
            shopsCollection.replaceOne(new BasicDBObject().append("_id", shop.getTagg()), document, new UpdateOptions().upsert(true));
        }).exceptionally(throwable -> {
            projectM.getLog().info(ChatColor.DARK_AQUA + "[ShopLoader] has failed to save a new shop by the name: " + shop.getTagg());
            throwable.printStackTrace();
            return null;
        });
    }

    public CompletableFuture<List<Shop>> getShops() {
        return CompletableFuture.supplyAsync(() -> {
            MongoCollection<Document> shopsCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("shops");
            List<Shop> shopsList = new ArrayList<>();
            for (Document shopObject : shopsCollection.find()) {
                Shop shop =  gson.fromJson(shopObject.toJson(),Shop.class);
                shopsList.add(shop);
            }
            return shopsList;
        });
    }
}
