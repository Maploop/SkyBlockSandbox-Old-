package net.maploop.items.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class HYPERION {
    static ItemStack item;

    public static void load() {
        item = Item.makeItem(Material.IRON_SWORD, "§6Hyperion", 1, 0,
                "§7Gear Score: §d673",
                "§7Damage: §c+293",
                "§7Strength: §c+150",
                "",
                "§7Intelligence: §a400",
                "§7Ferocity: §a+30",
                "",
                "§7Deals +§a50% §7damage to",
                "§7Withers. Grants §c+1 ❁ Damage",
                "§7and §a+2 §b✎ Intelligence",
                "§7per §cCatacombs §7level.",
                "",
                "§7Your Catacombs level: §4N/A",
                "",
                "§eRight-Click to use your class ability!",
                "",
                "§8This item can be reforged!",
                "§6§lLEGENDARY DUNGEON SWORD");
    }

    public static ItemStack get() {
        return item;
    }
}
