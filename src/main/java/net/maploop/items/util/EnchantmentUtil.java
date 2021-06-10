package net.maploop.items.util;

import net.maploop.items.enums.Enchant;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemUtilities;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentUtil {
    private final ItemStack item;

    public EnchantmentUtil(ItemStack itemStack) {
        this.item = itemStack;

    }

    /**
     * Adds an enchantment to your selected item.
     *
     * @param e The enchantment being added.
     * @param lvl Level of your enchant.
     * @return An ItemStack that has the enchantment.
     */
    public ItemStack addEnchant(Enchant e, int lvl) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
        NBTTagCompound defaultNBT = (nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
        NBTTagCompound data = defaultNBT.getCompound("ExtraAttributes");

        NBTTagCompound enchants = (data.getCompound("enchantments") != null ? data.getCompound("enchantments") : new NBTTagCompound());

        if (lvl > e.getMax() && !e.isBypassable())
            throw new IllegalArgumentException("Cannot put this enchantment level on an item!");

        // Ignored cause sometimes caused issues.
        /*
        if (ItemUtilities.isSBItem(item)) {
            CustomItem ci = ItemUtilities.getSBItem(item);
            if (!e.getType().isSimilar(ci.getType())) {
                throw new IllegalArgumentException("Cannot add this enchantment to this item!");
            }
        }
         */

        enchants.setInt(e.toString(), lvl);
        enchants.setInt(Enchant.ANY.toString(), -1);

        data.set("enchantments", enchants);
        defaultNBT.set("ExtraAttributes", data);

        nmsItem.setTag(defaultNBT);

        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    /**
     * Removes an enchant from your selected item.
     *
     * @param e The enchantment being removed.
     * @return An ItemStack which does not have the removed enchantment anymore.
     */
    public ItemStack removeEnchant(Enchant e) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
        NBTTagCompound defaultNBT = (nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
        NBTTagCompound data = defaultNBT.getCompound("ExtraAttributes");

        NBTTagCompound enchants = (data.getCompound("enchantments") != null ? data.getCompound("enchantments") : new NBTTagCompound());

        enchants.remove(e.toString());
        enchants.setInt(Enchant.ANY.toString(), -1);

        data.set("enchantments", enchants);
        defaultNBT.set("ExtraAttributes", data);

        nmsItem.setTag(defaultNBT);

        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    /**
     * Check to see if an item has any enchantments or is it empty?
     *
     * @return If item's NBT has key "ANY"
     */
    public boolean hasAnyEnchant() {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
        NBTTagCompound defaultNBT = (nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
        NBTTagCompound data = defaultNBT.getCompound("ExtraAttributes");

        NBTTagCompound enchants = (data.getCompound("enchantments") != null ? data.getCompound("enchantments") : new NBTTagCompound());

        return enchants.hasKey("ANY");
    }

    /**
     * Check if your item has a specific enchantment.
     *
     * @param e The enchantment that you want to know if it exists or not.
     * @return If the enchantment data of item contains your enchantment's name.
     */
    public boolean hasEnchant(Enchant e) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
        NBTTagCompound defaultNBT = (nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
        NBTTagCompound data = defaultNBT.getCompound("ExtraAttributes");

        NBTTagCompound enchants = (data.getCompound("enchantments") != null ? data.getCompound("enchantments") : new NBTTagCompound());

        return enchants.hasKey(e.toString());
    }

    /**
     * Get the level of the enchantment your item has.
     *
     * @param e The enchantment you want to get the level of.
     * @return If the item has your enchantment of choice, it returns it's level, if not it returns -1.
     */
    public int getEnchant(Enchant e) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
        NBTTagCompound defaultNBT = (nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
        NBTTagCompound data = defaultNBT.getCompound("ExtraAttributes");

        NBTTagCompound enchants = (data.getCompound("enchantments") != null ? data.getCompound("enchantments") : new NBTTagCompound());

        if (hasEnchant(e))
            return enchants.getInt(e.toString());

        return -1;
    }

    /**
     * Get a list of all enchantment keys in your item.
     *
     * @return A list of enchantment keys,
     */
    public List<Enchant> getAllEnchantments() {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.item);
        NBTTagCompound defaultNBT = (nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
        NBTTagCompound data = defaultNBT.getCompound("ExtraAttributes");

        NBTTagCompound enchants = (data.getCompound("enchantments") != null ? data.getCompound("enchantments") : new NBTTagCompound());

        if (hasAnyEnchant()) {
            List<Enchant> enchants1 = new ArrayList<>();
            for (String s : enchants.c()) {
                enchants1.add(Enchant.valueOf(s));
            }

            return enchants1;
        }

        return null;
    }
}
