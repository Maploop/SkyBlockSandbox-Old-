package net.maploop.items.extras;

import net.maploop.items.Items;
import net.maploop.items.enums.AbilityScroll;
import net.maploop.items.enums.EnchantmentType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.enums.Reforge;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.item.items.Scylla;
import net.maploop.items.util.IUtil;
import net.minecraft.server.v1_8_R3.NBTTagByte;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtraApplier {
    private ItemStack stack;

    public ExtraApplier(ItemStack stack) {
        this.stack = stack;
    }

    public void addEnchant(EnchantmentType enchant) {

    }

    public void setReforge(Reforge reforge) {
        CustomItem item = ItemUtilities.getSBItem(stack);
        ReforgeStatsObject stats = reforge.getByRarity(item.getRarity());
        ItemUtilities.nameItem(stack, reforge.getPrefix() + " " + item.getName());

        ItemUtilities.storeIntInItem(stack, stats.getCrit_chance(), "reforge-cc");
        ItemUtilities.storeIntInItem(stack, stats.getCrit_damage(), "reforge-cd");
        ItemUtilities.storeIntInItem(stack, stats.getStrength(), "reforge-strength");
        ItemUtilities.storeIntInItem(stack, stats.getDefense(), "reforge-defense");
        ItemUtilities.storeIntInItem(stack, stats.getFerocity(), "reforge-ferocity");
        ItemUtilities.storeIntInItem(stack, stats.getHealth(), "reforge-hp");
        ItemUtilities.storeIntInItem(stack, stats.getIntelligence(), "reforge-intel");

        buildLore(item, stack);
        item.setReforge(reforge);
    }

    public static void buildLore(CustomItem item, ItemStack stack) {
        List<String> lore = new ArrayList<>();

        if (item.getStrength() != 0) {
            lore.add("§7Strength: §c+" + item.getStrength());
        }

        lore.add(" ");
        item.getSpecificLorePrefix(lore, stack);

        lore.add(" ");

        if (!item.getEnchantments().isEmpty()) {
            for (Enchantment ench : item.getEnchantments()) {
                lore.add("§9" + ench.getType().getFriendlyName() + " " + IUtil.convertToRomanNumeral(ench.getLvl()));
            }
            lore.add("");
        }

        if (!item.getAbilities().isEmpty()) {
            lore.addAll(item.getAbilities().get(0).toLore());
            lore.add(" ");
        }

        lore.add(item.getRarity().getColor() + item.getRarity().toString().toUpperCase() + " " + item.getRarity().getColor() + item.getType().getValue().toUpperCase());
    }

    public void applyScroll(AbilityScroll scroll) {
        CustomItem item = ItemUtilities.getSBItem(stack);

        if (item instanceof Scylla) {
            net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(stack);
            NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
            NBTTagList list = (NBTTagList) tag.get("ability_scroll");

            if (list == null) {
                list = new NBTTagList();

                list.add(new NBTTagString(scroll.name().toLowerCase()));
            }
        }
    }
}
