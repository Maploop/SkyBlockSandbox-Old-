package net.maploop.items.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GRAPPLING_HOOK {
    static ItemStack item;

    public static void load(){
        item = ItemMaker.makeItem(Material.FISHING_ROD, "§aGrappling Hook", 1, 0,
                "§7Travel around in style using",
                "§7this Grappling Hook.",
                "§82 Second Cooldown",
                "§7",
                "§a§lUNCOMMON");
    }

    public static ItemStack get() { return item; }
}
