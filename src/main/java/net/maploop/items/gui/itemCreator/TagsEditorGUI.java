package net.maploop.items.gui.itemCreator;

import net.maploop.items.Items;
import net.maploop.items.gui.AnvilGUI;
import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.util.Attribute;
import net.maploop.items.util.IUtil;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TagsEditorGUI extends GUI {

    public TagsEditorGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "Edit item tags";
    }

    @Override
    public int getSize() {
        return 9 * 6;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        ItemStack item = player.getItemInHand();

        switch (event.getCurrentItem().getType()) {
            case ARROW: {
                new ItemCreatorGUI(playerMenuUtility).open();
                break;
            }
            case IRON_BLOCK: {
                if(event.getClick().isRightClick()){
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.removeItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    item.setDurability((short) 0);
                    itemMeta.spigot().setUnbreakable(false);
                    item.setItemMeta(itemMeta);
                }
                if(event.getClick().isLeftClick()){
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    item.setDurability((short) 0);
                    itemMeta.spigot().setUnbreakable(true);
                    item.setItemMeta(itemMeta);
                }
                break;
            }
            case ENCHANTED_BOOK: {
                if(event.getClick().isRightClick()){
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
                    item.setItemMeta(itemMeta);
                }
                if(event.getClick().isLeftClick()){
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    item.setItemMeta(itemMeta);
                }
                break;
            }
            case GLOWSTONE_DUST: {
                if(event.getClick().isRightClick()){
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.removeEnchant(Enchantment.LUCK);
                    itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
                    item.setItemMeta(itemMeta);
                }
                if(event.getClick().isLeftClick()){
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.addEnchant(Enchantment.LUCK, 1, false);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    item.setItemMeta(itemMeta);
                }
                break;
            }
            case GOLDEN_APPLE: {
                if(event.getClick().isRightClick()){
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    item.setItemMeta(itemMeta);
                }
                if(event.getClick().isLeftClick()){
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    item.setItemMeta(itemMeta);
                }
                break;
            }
        }
    }

    @Override
    public void setItems() {
        setFilter();
        inventory.setItem(49, makeItem(Material.ARROW, "§aGo Back", 1, 0, "§7To Create an Item"));

        inventory.setItem(13, makeItem(Material.IRON_BLOCK, "§aToggle Unbreakable", 1, 0, IUtil.colorize("&7Toggle the unbreakable tag\n\n&eLeft click to Enable\n&eRight click to Disable!")));
        inventory.setItem(15, makeItem(Material.ENCHANTED_BOOK, "§aToggle Enchant Tag", 1, 0, IUtil.colorize("&7Toggle the enchant tag\n\n&eLeft click to Enable\n&eRight click to Disable!")));
        inventory.setItem(31, makeItem(Material.GLOWSTONE_DUST, "§aToggle Glowing", 1, 0, IUtil.colorize("&7Toggle Item Glow\n\n&eLeft click to Enable\n&eRight click to Disable!")));
        inventory.setItem(11, makeItem(Material.GOLDEN_APPLE, "§aToggle Damage Tag", 1, 0, IUtil.colorize("&7Toggle the damage tag\n\n&eLeft click to Enable\n&eRight click to Disable!")));
    }
}
