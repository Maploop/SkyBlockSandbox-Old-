package net.maploop.items.item.items;

import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.gui.SkyblockMenuGUI;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemAbility;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SkyblockMenu extends CustomItem {
    public SkyblockMenu(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, boolean glowing) {
        super(id, rarity, name, material, durability, stackable, oneTimeUse, hasActive, abilities, manaCost, reforgeable, itemType, glowing);
    }

    @Override
    public void onItemStackCreate(ItemStack paramItemStack) {

    }

    @Override
    public void getSpecificLorePrefix(List<String> lore, ItemStack paramItemStack) {
        lore.add("§7View all your Skyblock");
        lore.add("§7progress, including your Skills,");
        lore.add("§7Collections, Recipes, and more!");
    }

    @Override
    public void getSpecificLoreSuffix(List<String> paramList, ItemStack paramItemStack) {

    }

    @Override
    public void leftClickAirAction(Player paramPlayer, ItemStack paramItemStack) {
        new SkyblockMenuGUI(new PlayerMenuUtility(paramPlayer)).open();
    }

    @Override
    public void leftClickBlockAction(Player paramPlayer, PlayerInteractEvent event, Block paramBlock, ItemStack paramItemStack) {
        event.setCancelled(true);
        new SkyblockMenuGUI(new PlayerMenuUtility(paramPlayer)).open();
    }

    @Override
    public void rightClickAirAction(Player paramPlayer, PlayerInteractEvent event, ItemStack paramItemStack) {
        event.setCancelled(true);
        new SkyblockMenuGUI(new PlayerMenuUtility(paramPlayer)).open();
    }

    @Override
    public void rightClickBlockAction(Player paramPlayer, PlayerInteractEvent event, Block paramBlock, ItemStack paramItemStack) {
        event.setCancelled(true);
        new SkyblockMenuGUI(new PlayerMenuUtility(paramPlayer)).open();
    }

    @Override
    public void shiftLeftClickAirAction(Player paramPlayer, ItemStack paramItemStack) {
        new SkyblockMenuGUI(new PlayerMenuUtility(paramPlayer)).open();
    }

    @Override
    public void shiftLeftClickBlockAction(Player paramPlayer, PlayerInteractEvent event, Block paramBlock, ItemStack paramItemStack) {
        event.setCancelled(true);
        new SkyblockMenuGUI(new PlayerMenuUtility(paramPlayer)).open();
    }

    @Override
    public void shiftRightClickAirAction(Player paramPlayer, PlayerInteractEvent event, ItemStack paramItemStack) {
        event.setCancelled(true);
        new SkyblockMenuGUI(new PlayerMenuUtility(paramPlayer)).open();
    }

    @Override
    public void shiftRightClickBlockAction(Player paramPlayer, PlayerInteractEvent event, Block paramBlock, ItemStack paramItemStack) {
        event.setCancelled(true);
        new SkyblockMenuGUI(new PlayerMenuUtility(paramPlayer)).open();
    }

    @Override
    public void middleClickAction(Player paramPlayer, ItemStack paramItemStack) {

    }

    @Override
    public void hitEntityAction(Player paramPlayer, EntityDamageByEntityEvent event, Entity paramEntity, ItemStack paramItemStack) {
        event.setCancelled(true);
        new SkyblockMenuGUI(new PlayerMenuUtility(paramPlayer)).open();
    }

    @Override
    public void breakBlockAction(Player paramPlayer, BlockBreakEvent event, Block paramBlock, ItemStack paramItemStack) {
        event.setCancelled(true);
        new SkyblockMenuGUI(new PlayerMenuUtility(paramPlayer)).open();
    }

    @Override
    public void clickedInInventoryAction(Player paramPlayer, InventoryClickEvent event) {
        event.setCancelled(true);
        new SkyblockMenuGUI(new PlayerMenuUtility(paramPlayer)).open();
    }

    @Override
    public void activeEffect(Player paramPlayer, ItemStack paramItemStack) {

    }
}
