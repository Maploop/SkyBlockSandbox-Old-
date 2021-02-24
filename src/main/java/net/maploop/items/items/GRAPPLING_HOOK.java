package net.maploop.items.items;

import net.maploop.items.helpers.Utilities;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
