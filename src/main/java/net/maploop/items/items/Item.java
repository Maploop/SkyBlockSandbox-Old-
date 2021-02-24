package net.maploop.items.items;

import net.maploop.items.Items;
import net.maploop.items.helpers.Utilities;
import net.maploop.items.items.pets.bee.BEE_PET_COMMON;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class Item {
    private static Items plugin = Items.getInstance();

    private ItemStack item;
    protected ItemMeta meta;

    private List<String> lore;
    private String name;
    private boolean enchantable;
    private Rarity rarity;
    private ChatColor enchatmentColor;

    public Item(String displayname, Material type, Rarity rarity) {
        this.item = new ItemStack(type);
        this.name = displayname;
        this.meta = item.getItemMeta();
        this.lore = new ArrayList<>();
        this.enchantable = false;
        this.setName(displayname);
        this.meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        this.meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        lore.add(rarity.getName());
        this.applyMetaToStack();
    }

    public void setName(String name) {
        meta.setDisplayName(name);
    }

    public void applyMetaToStack() {
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public String getName() {
        return meta.getDisplayName();
    }

    public void enchantable() {
        this.enchantable = true;
        lore.remove(rarity.getName());
        lore.add(enchatmentColor + "No Enchantments");
        lore.add(rarity.getName());
    }

    public void addLoreLine(String s) {
        lore.remove(enchatmentColor + "No Enchantments");
        lore.remove(rarity.getName());
        lore.add(Utilities.colorize(s));
        if (enchantable)
            lore.add(enchatmentColor + "No Enchantments");
        lore.add(rarity.getName());
    }

    public void onBlockInteract(PlayerInteractEvent e) {}
    public void onItemInteract(PlayerInteractEvent e) {}
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {}

    public static List<ItemStack> getItems() {
        List<ItemStack> items = new ArrayList<>();
        items.add(BEE_PET_COMMON.get());
        items.add(GRAPPLING_HOOK.get());
        items.add(HYPERION.get());
        items.add(MANAFLUX_POWER_ORB.get());
        items.add(OVERFLUX_POWER_ORB.get());
        items.add(RADIANT_POWER_ORB.get());
        items.add(PLASMAFLUX_POWER_ORB.get());

        return items;
    }


}
