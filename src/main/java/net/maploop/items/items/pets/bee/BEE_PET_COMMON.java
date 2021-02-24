package net.maploop.items.items.pets.bee;

import net.maploop.items.items.ItemMaker;
import org.bukkit.inventory.ItemStack;

public class BEE_PET_COMMON {
    static ItemStack item;

    public static void load() {
        item = ItemMaker.makeCustomSkullItem("http://textures.minecraft.net/texture/12724a9a4cdd68ba49415560e5be40b4a1c47cb5be1d66aedb52a30e62ef2d47", "§8[§7Lvl. 1§8] §fBee", 1,
                "§7this is a bee pet");
    }

    public static ItemStack get() {
        return item;
    }
}
