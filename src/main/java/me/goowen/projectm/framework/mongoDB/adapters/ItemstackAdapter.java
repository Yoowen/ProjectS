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
 * Edited for 1.16 and onwards by Owen (goowen).
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
                    jsonWriter.name("lore");
                    StringBuilder lores = new StringBuilder();
                    for (String lore: Objects.requireNonNull(itemStack.getItemMeta().getLore())) {
                        lores.append(lore).append(",");
                    }
                    jsonWriter.value(String.valueOf(lores));
                }

                if (itemStack.getItemMeta().hasCustomModelData()) {
                    jsonWriter.name("customModelData");
                    jsonWriter.value(itemStack.getItemMeta().getCustomModelData());
                }
            }

            jsonWriter.endObject();

        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    @Override
    public ItemStack read(JsonReader jsonReader) throws IOException {
        try {
            if (jsonReader.peek() == JsonToken.NULL) {
                return null;
            }

            jsonReader.beginObject();

            jsonReader.nextName();
            Material type = Material.getMaterial(jsonReader.nextString());

            jsonReader.nextName();
            int amount = jsonReader.nextInt();

            jsonReader.nextName();
            Integer durability = jsonReader.nextInt();

            String customName = null;
            if (jsonReader.hasNext()) {
                jsonReader.nextName();
                customName = jsonReader.nextString();
            }


            String[] lore = null;
            if (jsonReader.hasNext()) {
                jsonReader.nextName();
                lore = jsonReader.nextString().split(",");

            }

            int customModelData = 0;
            if (jsonReader.hasNext()) {
                jsonReader.nextName();
                customModelData = jsonReader.nextInt();
            }

            jsonReader.endObject();
            return new ItemBuilder(type).setAmouth(amount).setName(customName).setDurability(durability.shortValue()).setLore(lore).setCustomModelData(customModelData).toItemStack();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
