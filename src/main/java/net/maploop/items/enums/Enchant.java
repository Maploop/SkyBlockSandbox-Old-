package net.maploop.items.enums;

import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.util.IUtil;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public enum Enchant {
    ANY("ANY", -1, false, true, EnchantType.ITEM),

    SHARPNESS("Sharpness", 7, true, false, EnchantType.SWORD, "§7Increases melee damage by", "&a5%&7 per level."),
    TELEKINESIS("Telekinesis", 1, false, false, EnchantType.ITEM, "&7Blocks and mob drops go directly", "&7into your inventory."),
    SMITE("Smite", 7, true, false, EnchantType.SWORD, "&7Increases damage dealt to", "&7Zombies, Zombie Pigmen,", "&7Withers, Wither Skeletons, and", "&7Skeletons by &a8%&7 per level."),
    BANE_OF_ARTHROPODS("Bane of Arthropods", 7, true, false, EnchantType.SWORD, "&7Increases damage dealt to Cave", "&7Spiders, Spiders, and Silverfish", "&7by &a8%&7 per level."),
    ENDER_SLAYER("Ender Slayer", 7, true, false, EnchantType.SWORD, "&7Increases damage dealt to Ender", "&7Dragons and Enderman by &a12%&7", "&7per level."),
    CUBSIM("Cubsim", 6, true, false, EnchantType.SWORD, "&7Increases damage dealt to", "&7Creepers, Magma Cubes, and", "&7Slimes by &a10%&7 per level."),
    CRITICAL("Critical", 6, true, false, EnchantType.SWORD, "&7Increases critical damage by &a10%", "&7per level."),
    // Titan killer is a working in progress!
    TITAN_KILLER("Titan Killer", 7, true, false, EnchantType.SWORD, "&7Increases damage dealt by &a2%&7 per", "&7level for each 100 defense your", "&7target has up to &a50%&7.");

    private final String friendlyName;
    private final EnchantType type;
    private final int max;
    private final boolean bypassable;
    private final boolean isUltimate;
    private final String[] description;

    Enchant(String friendlyName, int max, boolean bypassable, boolean isUltimate, EnchantType type, String... description) {
        this.friendlyName = friendlyName;
        this.type = type;
        this.bypassable = bypassable;
        this.max = max;
        this.isUltimate = isUltimate;
        this.description = description;
    }

    public EnchantType getType() {
        return this.type;
    }

    public String[] getDescription() {
        List<String> desc = new ArrayList<>();
        for (String s : description) {
            desc.add(IUtil.colorize(s));
        }

        return desc.toArray(new String[0]);
    }

    public int getMax() {
        return this.max;
    }

    public boolean isBypassable() {
        return this.bypassable;
    }

    public boolean isUltimate() {
        return this.isUltimate;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public static void refreshLore(ItemStack stack) {
        if (ItemUtilities.isSBItem(stack)) {
            CustomItem item = ItemUtilities.getSBItem(stack);
            assert item != null;
            item.updateLore(stack);
        }
    }

    public enum EnchantType {
        WEAPON,
        BOW,
        SWORD,

        ARMOR,
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS,

        TOOL,
        PICKAXE,
        AXE,
        SHEARS,
        SHOVEL,
        HOE,

        ITEM;

        public boolean isSimilar(ItemType type) {
            return type.toString().equals(this.toString());
        }
    }
}
