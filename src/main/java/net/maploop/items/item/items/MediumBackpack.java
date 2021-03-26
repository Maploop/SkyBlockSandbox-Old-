package net.maploop.items.item.items;

import net.maploop.items.Items;
import net.maploop.items.data.BackpackData;
import net.maploop.items.data.DataHandler;
import net.maploop.items.enums.ItemStats;
import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemAbility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.listeners.EntityDamageListener;
import net.maploop.items.sql.SQLGetter;
import net.maploop.items.util.DUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class MediumBackpack extends CustomItem {
    public MediumBackpack(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, String url, boolean glowing) {
        super(id, rarity, name, material, durability, stackable, oneTimeUse, hasActive, abilities, manaCost, reforgeable, itemType, url, glowing);
    }

    @Override
    public void onItemStackCreate(ItemStack paramItemStack) {
        applyTexture(paramItemStack);
    }

    @Override
    public void getSpecificLorePrefix(List<String> paramList, ItemStack paramItemStack) {
        String lore = DUtil.colorize("&7A special portable bag with\n&a18 &7slots which can hold any\n&7item within.");
        String[] lore1 = lore.split("\n");
        paramList.addAll(Arrays.asList(lore1));
    }

    @Override
    public void getSpecificLoreSuffix(List<String> paramList, ItemStack paramItemStack) {

    }

    @Override
    public void leftClickAirAction(Player player, ItemStack item) {

    }

    @Override
    public void leftClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item) {

    }

    @Override
    public void rightClickAirAction(Player player, PlayerInteractEvent event, ItemStack item) {
        event.setCancelled(true);
        String uuid = ItemUtilities.getStringFromItem(item, "UUID");
        player.playSound(player.getLocation(), Sound.HORSE_ARMOR, 1f, 1f);

        Inventory backPackinv = Bukkit.createInventory(player, 9 * 2, "Medium Backpack");
        if(BackpackData.getData().containsKey(uuid)) {
            backPackinv.setContents(BackpackData.getData().get(uuid));
            player.openInventory(backPackinv);
            BackpackData.inv.put(player.getUniqueId(), item);
            return;
        }
        BackpackData.inv.put(player.getUniqueId(), item);
        player.openInventory(backPackinv);
    }

    @Override
    public void rightClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item) {
        rightClickAirAction(player, event, item);
    }

    @Override
    public void shiftLeftClickAirAction(Player player, ItemStack item) {

    }

    @Override
    public void shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item) {

    }

    @Override
    public void shiftRightClickAirAction(Player player, PlayerInteractEvent event, ItemStack item) {
        rightClickAirAction(player, event, item);
    }

    @Override
    public void shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item) {
        rightClickAirAction(player, event, item);
    }

    @Override
    public void middleClickAction(Player player, ItemStack item) {

    }

    @Override
    public void hitEntityAction(Player player, EntityDamageByEntityEvent event, Entity paramEntity, ItemStack item) {

    }

    @Override
    public void breakBlockAction(Player player, BlockBreakEvent event, Block paramBlock, ItemStack item) {

    }

    @Override
    public void clickedInInventoryAction(Player player, InventoryClickEvent event) {
        if(event.getAction().equals(InventoryAction.PICKUP_HALF)) {
            event.setCancelled(true);
            String uuid = ItemUtilities.getStringFromItem(event.getCurrentItem(), "UUID");
            player.playSound(player.getLocation(), Sound.HORSE_ARMOR, 1f, 1f);

            Inventory backPackinv = Bukkit.createInventory(player, 9 * 2, "Medium Backpack");
            if(BackpackData.getData().containsKey(uuid)) {
                backPackinv.setContents(BackpackData.getData().get(uuid));
                player.openInventory(backPackinv);
                BackpackData.inv.put(player.getUniqueId(), event.getCurrentItem());
                return;
            }
            BackpackData.inv.put(player.getUniqueId(), event.getCurrentItem());
            player.openInventory(backPackinv);
        }
        if(event.getInventory().getTitle().contains("Backpack") || event.getAction().equals(InventoryAction.HOTBAR_MOVE_AND_READD) || event.getAction().equals(InventoryAction.HOTBAR_SWAP)) {
            event.setCancelled(true);
        }
    }

    @Override
    public void activeEffect(Player player, ItemStack item) {

    }
}
