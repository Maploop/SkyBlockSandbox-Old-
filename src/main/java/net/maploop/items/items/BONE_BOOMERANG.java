package net.maploop.items.items;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

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

        item.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 1);
        net.minecraft.server.v1_8_R3.ItemStack stack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = stack.getTag() != null ? stack.getTag() : new NBTTagCompound();
        tag.setString("uuid", UUID.randomUUID().toString());
        stack.setTag(tag);
    }

    public static ItemStack get() {
        return item;
    }
}
