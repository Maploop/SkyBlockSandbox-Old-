package net.maploop.items.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ASPECT_OF_THE_END {
    static ItemStack item;

    public static void load() {
        item = Item.makeItem(Material.DIAMOND_SWORD, "§9Aspect of the End", 1, 0, "");
    }

    public static ItemStack get() { return item; }
}
