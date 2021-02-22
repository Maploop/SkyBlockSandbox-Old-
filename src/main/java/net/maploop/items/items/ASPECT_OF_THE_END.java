package net.maploop.items.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ASPECT_OF_THE_END extends Item{

    public ASPECT_OF_THE_END(String displayname, Material type, Rarity rarity) {
        super("§9Aspect of the End", Material.DIAMOND_SWORD, Rarity.RARE);
        addLoreLine("§7Line");
    }
}
