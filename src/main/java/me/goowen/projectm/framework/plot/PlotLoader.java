package me.goowen.projectm.framework.plot;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import me.goowen.projectm.ProjectM;
import me.goowen.projectm.framework.pvp.Ammo;
import me.goowen.projectm.modules.mongoDB.MongodbModule;
import org.bson.Document;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PlotLoader {
    private ProjectM projectM = ProjectM.getInstance();
    private final Gson gson = MongodbModule.getGson();

    public CompletableFuture<Void> savePlot(Plot plot){
        return CompletableFuture.runAsync(() -> {
            MongoCollection<Document> plotCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("plots");
            Document document = Document.parse(gson.toJson(plot, Plot.class));
            plotCollection.replaceOne(new BasicDBObject().append("_id", plot.getTagg()), document, new UpdateOptions().upsert(true));
        }).exceptionally(throwable -> {
            projectM.getLog().info(ChatColor.DARK_AQUA + "[AmmoLoader] has failed to save a new plot by the name: " + plot.getTagg());
            throwable.printStackTrace();
            return null;
        });
    }

    public CompletableFuture<List<Plot>> getPlot() {
        return CompletableFuture.supplyAsync(() -> {
            MongoCollection<Document> plotCollection = MongodbModule.getMongoClient().getDatabase("projectM").getCollection("plots");
            List<Plot> plotList = new ArrayList<>();
            for (Document plotObject : plotCollection.find()) {
                Plot plot = gson.fromJson(plotObject.toJson(),Plot.class);
                plotList.add(plot);
            }
            return plotList;
        });
    }
}