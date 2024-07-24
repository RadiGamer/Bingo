package org.imradigamer.bingo;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemStackUtil {
    public static List<Map<String, Object>> serializeItemStacks(List<ItemStack> items) {
        List<Map<String, Object>> serializedItems = new ArrayList<>();
        for (ItemStack item : items) {
            serializedItems.add(item.serialize());
        }
        return serializedItems;
    }

    public static List<ItemStack> deserializeItemStacks(List<Map<String, Object>> serializedItems) {
        List<ItemStack> items = new ArrayList<>();
        for (Map<String, Object> serializedItem : serializedItems) {
            items.add(ItemStack.deserialize(serializedItem));
        }
        return items;
    }

    public static void saveItemsToConfig(FileConfiguration config, String path, List<ItemStack> items) {
        List<Map<String, Object>> serializedItems = serializeItemStacks(items);
        config.set(path, serializedItems);
    }

    public static List<ItemStack> loadItemsFromConfig(FileConfiguration config, String path) {
        List<Map<String, Object>> serializedItems = (List<Map<String, Object>>) config.getList(path, new ArrayList<>());
        return deserializeItemStacks(serializedItems);
    }
}
