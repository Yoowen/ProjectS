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

public class ShopItemLoader {
    private ProjectM projectM = ProjectM.getInstance();
    private final Gson gson = MongodbModule.getGson();

    public CompletableFuture<Void> saveShopItem(ShopItem shopItem){
        return CompletableFuture.runAsync(() -> {
            MongoCollection<Document> shopsItemCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("shopItems");
            Document document = Document.parse(gson.toJson(shopItem, ShopItem.class));
            shopsItemCollection.replaceOne(new BasicDBObject().append("_id", shopItem.getTagg()), document, new UpdateOptions().upsert(true));
        }).exceptionally(throwable -> {
            projectM.getLog().info(ChatColor.DARK_AQUA + "[ShopLoader] has failed to save a new shopItem by the name: " + shopItem.getTagg());
            throwable.printStackTrace();
            return null;
        });
    }

    public CompletableFuture<List<ShopItem>> getShopItems() {
        return CompletableFuture.supplyAsync(() -> {
            MongoCollection<Document> shopsItemCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("shopItems");
            List<ShopItem> shopsItemsList = new ArrayList<>();
            for (Document shopItemObject : shopsItemCollection.find()) {
                ShopItem shopItem =  gson.fromJson(shopItemObject.toJson(),ShopItem.class);
                shopsItemsList.add(shopItem);
            }
            return shopsItemsList;
        });
    }
}
