package net.maploop.items.data;

import net.maploop.items.Items;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BackpackData {
    private static final Map<String, ItemStack[]> backpackData = new HashMap<>();
    public static Map<UUID, ItemStack> inv = new HashMap<>();

    public static Map<String, ItemStack[]> getData() {
        return backpackData;
    }

    public static void save() {
        Items main = Items.getInstance();
        if(backpackData.isEmpty()) return;
        for(Map.Entry<String, ItemStack[]> entry : backpackData.entrySet()) {
            main.getBackpackData().set("backpacks." + entry.getKey(), entry.getValue());
        }
        main.saveBackbackData();
    }

    public static void restore() {
        Items main = Items.getInstance();
        if(main.getBackpackData().get("backpacks") == null) return;
        main.getBackpackData().getConfigurationSection("backpacks").getKeys(false).forEach(key -> {
            @SuppressWarnings("unchecked")
            ItemStack[] content =((List<ItemStack>)main.getBackpackData().get("backpacks." + key)).toArray(new ItemStack[0]);
            backpackData.put(key, content);
        });
        main.getBackpackData().set("backpacks", null);
        main.saveBackbackData();
    }
}
