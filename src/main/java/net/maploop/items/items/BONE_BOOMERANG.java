package net.maploop.items.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BONE_BOOMERANG {
    static ItemStack item;

    public static void load() {
        item = ItemMaker.makeItem(Material.BONE, "§6Bonemerang", 1, 0,
                "§7Gear Score: §d490",
                "§7Damage: §c+320",
                "§7Strength: §c+130",
                "",
                "§6Item Ability: Swing §e§lRIGHT CLICK",
                "§7Throw the bone for a short distance,",
                "§7dealing damage an arrow",
                "§7would.",
                "",
                "§7Deals §cdouble danage §7when",
                "§7coming back. Pierces up to §e10",
                "§7foes.",
                "",
                "§8This item can be reforged!",
                "§6§lLEGENDARY DUNGEON BOW");
    }

    public static ItemStack get() {
        return item;
    }
}
