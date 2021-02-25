package net.maploop.items.items;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BONZO_STAFF {
    static ItemStack item;

    public static void load(){
        item = ItemMaker.makeItem(Material.BLAZE_ROD, "§9Bonzo's Staff", 1, 0,
                "§7Gear Score: §b285",
                "§7Damage: §c+160",
                "",
                "§7Intelligence: §a+250",
                "",
                "§6Item Ability: Showtime §e§lRIGHT CLICK",
                "§7Shoots balloons that create a ",
                "§7large explosion on impact,",
                "§7dealing up to §c1,000 §7damage.",
                "§8Mana Cost: §3100",
                "",
                "§8This item can be reforged!",
                "§4❣ §cRequires §aCatacombs floor 1",
                "§aCompeletion",
                "§9§lRARE DUNGEON SWORD");

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString("ItemData", "bonzo_staff?starred=false");


    }

    public static ItemStack get() { return item; }
}
