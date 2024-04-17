package me.goowen.projectm.framework.speech;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.shops.Shop;
import me.goowen.projectm.modules.mongoDB.MongodbModule;
import org.bson.Document;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SpeechLoader {
    private ProjectM projectM = ProjectM.getInstance();
    private final Gson gson = MongodbModule.getGson();

    public CompletableFuture<Void> saveSpeech(Speech speech){
        return CompletableFuture.runAsync(() -> {
            MongoCollection<Document> speechCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("speech");
            Document document = Document.parse(gson.toJson(speech, Speech.class));
            speechCollection.replaceOne(new BasicDBObject().append("_id", speech.getTagg()), document, new UpdateOptions().upsert(true));
        }).exceptionally(throwable -> {
            projectM.getLog().info(ChatColor.DARK_AQUA + "[SpeechLoader] has failed to save a new shop by the name: " + speech.getTagg());
            throwable.printStackTrace();
            return null;
        });
    }

    public CompletableFuture<List<Speech>> getSpeech() {
        return CompletableFuture.supplyAsync(() -> {
            MongoCollection<Document> speechCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("speech");
            List<Speech> speechList = new ArrayList<>();
            for (Document shopObject : speechCollection.find()) {
                Speech speech =  gson.fromJson(shopObject.toJson(),Speech.class);
                speechList.add(speech);
            }
            return speechList;
        });
    }
}
