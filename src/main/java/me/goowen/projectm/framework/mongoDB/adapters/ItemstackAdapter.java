package me.goowen.projectm.framework.mongoDB.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import me.goowen.projectm.utilities.itemstacks.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.Objects;

/* *
 * Created by Joshua Bell (RingOfStorms).
 *
 * Edited for 1.20.4 and onwards by Owen (goowen).
 *
 * Post explaining here: [URL]http://bukkit.org/threads/gsonfactory-gson-that-works-on-itemstack-potioneffect-location-objects.331161/[/URL]
 * */

public class ItemstackAdapter extends TypeAdapter<ItemStack> {

    /**
     * Writes the itemstack to a gson file.
     * @param jsonWriter writer on which the adapter will work.
     * @param itemStack that wil be written into json.
     * @throws IOException is the exception thrown when the try of the class has failed.
     */
    @Override
    public void write(JsonWriter jsonWriter, ItemStack itemStack) throws IOException {
        try {
            //Checks if itemstack isn't null else write null and return.
            if (itemStack == null) {
                jsonWriter.nullValue();
                return;
            }

            jsonWriter.beginObject();

            jsonWriter.name("type");
            jsonWriter.value(itemStack.getType().toString()); //I hate using this - but

            jsonWriter.name("amount");
            jsonWriter.value(itemStack.getAmount());

            jsonWriter.name("durability");
            jsonWriter.value(itemStack.getDurability());

            if (itemStack.hasItemMeta()) {
                jsonWriter.name("customName");
                jsonWriter.value(itemStack.getItemMeta().getDisplayName());

                if (itemStack.getItemMeta().hasLore()) {
                    StringBuilder lores = new StringBuilder();
                    for (String lore: Objects.requireNonNull(itemStack.getItemMeta().getLore())) {
                        lores.append(lore).append(",");
                    }
                    jsonWriter.name("lore");
                    jsonWriter.value(String.valueOf(lores));
                }

                if (itemStack.getItemMeta().hasCustomModelData()) {
                    jsonWriter.name("customModelData");
                    jsonWriter.value(itemStack.getItemMeta().getCustomModelData());
                }

                if (itemStack.getItemMeta().hasItemModel()) {
                    jsonWriter.name("itemModel");
                    jsonWriter.value(itemStack.getItemMeta().getItemModel().toString());
                }
            }

            jsonWriter.endObject();

        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    /**
     * Reads an itemstack from a json file and returns it as a minecraft itemstack object.
     * @param jsonReader reader with which the adapter will work.
     * @return returns an itemstack.
     * @throws IOException is the exception thrown when the try of the class has failed.
     */
    @Override
    public ItemStack read(JsonReader jsonReader) throws IOException {
        try {
            if (jsonReader.peek() == JsonToken.NULL) {
                return null;
            }
            //Basic values
            Material type = Material.STONE;
            int amount = 1;
            int durability = 1;
            String customName = null;
            String[] lore = null;
            int customModelData = 0;
            String itemModel = null;

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                switch (name) {
                    case "type":
                        type = Material.getMaterial(jsonReader.nextString());
                        break;
                    case "amount":
                        amount = jsonReader.nextInt();
                        break;
                    case "durability":
                        durability = jsonReader.nextInt();
                        break;
                    case "customName":
                        customName = jsonReader.nextString();
                        break;
                    case "lore":
                        lore = jsonReader.nextString().split(",");
                        break;
                    case "customModelData":
                        customModelData = jsonReader.nextInt();
                        break;
                    case "itemModel":
                        itemModel = jsonReader.nextString();
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }
            jsonReader.endObject();
            return new ItemBuilder(type).setAmouth(amount).setName(customName).setDurability((short) durability).setLore(lore).setCustomModelData(customModelData).setItemModel(itemModel).toItemStack();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
