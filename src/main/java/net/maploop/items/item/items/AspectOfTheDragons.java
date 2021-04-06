package net.maploop.items.item.items;

import net.maploop.items.Items;
import net.maploop.items.data.DataHandler;
import net.maploop.items.enums.ItemStats;
import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemAbility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.listeners.EntityDamageListener;
import net.maploop.items.sql.SQLGetter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class AspectOfTheDragons extends CustomItem {
    public AspectOfTheDragons(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, boolean glowing) {
        super(id, rarity, name, material, durability, stackable, oneTimeUse, hasActive, abilities, manaCost, reforgeable, itemType, glowing);
    }

    @Override
    public void onItemStackCreate(ItemStack paramItemStack) {

    }

    @Override    public void getSpecificLorePrefix(List<String> lore, ItemStack paramItemStack) {
        lore.add(ItemStats.DAMAGE.getDisplayname() + "290");
        lore.add(ItemStats.STRENGTH.getDisplayname() + "250");
        lore.add(ItemStats.CRIT_DAMAGE.getDisplayname() + "150");
        lore.add(ItemStats.CRIT_CHANCE.getDisplayname() + "50");
    }

    @Override
    public void getSpecificLoreSuffix(List<String> paramList, ItemStack paramItemStack) {

    }

    @Override
    public void leftClickAirAction(Player paramPlayer, ItemStack paramItemStack) {

    }

    @Override
    public void leftClickBlockAction(Player paramPlayer, PlayerInteractEvent paramPlayerInteractEvent, Block paramBlock, ItemStack paramItemStack) {

    }

    @Override
    public void rightClickAirAction(Player paramPlayer, PlayerInteractEvent event, ItemStack paramItemStack) {
        DataHandler handler = new DataHandler(paramPlayer);
        if(handler.getMana() < 100) {
            ItemUtilities.warnPlayer(paramPlayer, Collections.singletonList("You do not have enough mana!"));
            return;
        }
        paramPlayer.sendMessage(ChatColor.GREEN + "UwU Mana");
        handler.setMana(handler.getMana() - 100);
    }

    @Override
    public void rightClickBlockAction(Player paramPlayer, PlayerInteractEvent paramPlayerInteractEvent, Block paramBlock, ItemStack paramItemStack) {

    }

    @Override
    public void shiftLeftClickAirAction(Player paramPlayer, ItemStack paramItemStack) {

    }

    @Override
    public void shiftLeftClickBlockAction(Player paramPlayer, PlayerInteractEvent paramPlayerInteractEvent, Block paramBlock, ItemStack paramItemStack) {

    }

    @Override
    public void shiftRightClickAirAction(Player paramPlayer, PlayerInteractEvent event, ItemStack paramItemStack) {

    }

    @Override
    public void shiftRightClickBlockAction(Player paramPlayer, PlayerInteractEvent paramPlayerInteractEvent, Block paramBlock, ItemStack paramItemStack) {

    }

    @Override
    public void middleClickAction(Player paramPlayer, ItemStack paramItemStack) {

    }

    @Override
    public void hitEntityAction(Player paramPlayer, EntityDamageByEntityEvent event, Entity paramEntity, ItemStack paramItemStack) {

    }

    @Override
    public void breakBlockAction(Player paramPlayer, BlockBreakEvent paramBlockBreakEvent, Block paramBlock, ItemStack paramItemStack) {

    }

    @Override
    public void clickedInInventoryAction(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent) {

    }

    @Override
    public void activeEffect(Player player, ItemStack paramItemStack) {
        DataHandler handler = new DataHandler(player);
        // if(handler.getStrength() > int i = handler.setStrength(handler.getStrength() + 250) return;

    }

    public static int addedStr = 250;
    public static int addedCD = 150;

    @Override
    public void onSwapAction(Player player, PlayerItemHeldEvent event, ItemStack item) {
        SQLGetter getter = new SQLGetter(player, Items.getInstance());
        getter.setCritDamage(getter.getCritDamage() + 150);
        getter.setStrength(getter.getStrength() + 250);
    }
}
