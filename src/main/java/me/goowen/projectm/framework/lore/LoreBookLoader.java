package me.goowen.projectm.framework.lore;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.crates.CrateItem;
import me.goowen.projectm.modules.mongoDB.MongodbModule;
import org.bson.Document;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LoreBookLoader {
    private ProjectM projectM = ProjectM.getInstance();

    public CompletableFuture<Void> saveLoreBook(LoreBook loreBook){
        return CompletableFuture.runAsync(() -> {
            MongoCollection<Document> loreBookCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("lorebook");
            Document document = Document.parse(MongodbModule.getGson().toJson(loreBook, LoreBook.class));
            loreBookCollection.replaceOne(new BasicDBObject().append("_id", loreBook.getTagg()), document, new UpdateOptions().upsert(true));
        }).exceptionally(throwable -> {
            projectM.getLog().info(ChatColor.DARK_AQUA + "[LoreBookLoader] has failed to save a new lore item by the name: " + loreBook.getTagg());
            throwable.printStackTrace();
            return null;
        });
    }

    public CompletableFuture<List<LoreBook>> getLoreBooks() {
        return CompletableFuture.supplyAsync(() -> {
            MongoCollection<Document> loreBookCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("lorebook");
            List<LoreBook> loreBookList = new ArrayList<>();
            for (Document loreBookObject : loreBookCollection.find()) {
                LoreBook loreBook =  MongodbModule.getGson().fromJson(loreBookObject.toJson(),LoreBook.class);
                loreBookList.add(loreBook);
            }
            return loreBookList;
        });
    }
}
