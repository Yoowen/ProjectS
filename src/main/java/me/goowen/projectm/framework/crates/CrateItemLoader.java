package me.goowen.projectm.framework.crates;

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

public class CrateItemLoader {
    private ProjectM projectM = ProjectM.getInstance();

    /**
     * saves a single CrateItem object to the database.
     * @param crateItem object being saved.
     * @return result of the completableFuture.
     */
    public CompletableFuture<Void> saveCrateItem(CrateItem crateItem){
        return CompletableFuture.runAsync(() -> {
            MongoCollection<Document> crateItemCollection = MongodbModule.getMongoClient().getDatabase("crates").getCollection("crateItems");
            Document document = Document.parse(MongodbModule.getGson().toJson(crateItem, CrateItem.class));
            crateItemCollection.replaceOne(new BasicDBObject().append("_id", crateItem.getName()), document, new UpdateOptions().upsert(true));
        }).exceptionally(throwable -> {
            projectM.getLog().info(ChatColor.DARK_AQUA + "[CrateItemLoader] has failed to save a new crate item by the name: " + crateItem.getName());
            throwable.printStackTrace();
            return null;
        });
    }

    /**
     * Returns a list of all the CrateItem objects in the database.
     * @return list of all CrateItem objects.
     */
    public CompletableFuture<List<CrateItem>> getCrateItems() {
        return CompletableFuture.supplyAsync(() -> {
            MongoCollection<Document> crateItemCollection = MongodbModule.getMongoClient().getDatabase("crates").getCollection("crateItems");
            List<CrateItem> crateItemsList = new ArrayList<>();
            for (Document crateItemObject : crateItemCollection.find()) {
                CrateItem crateItem =  MongodbModule.getGson().fromJson(crateItemObject.toJson(),CrateItem.class);
                crateItemsList.add(crateItem);
            }
            return crateItemsList;
        });
    }

    /**
     * removes a single CrateItem object from the database.
     * @param crateItem object that needs to be removed.
     * @return result of the completableFuture.
     */
    public CompletableFuture<Void> deleteCrateItem(CrateItem crateItem) {
        return CompletableFuture.runAsync(() -> {
            MongoCollection<Document> crateItemsCollection = MongodbModule.getMongoClient().getDatabase("crates").getCollection("crateItems");
            crateItemsCollection.deleteOne(new BasicDBObject("_id", crateItem.getName()));
        });
    }
}
