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
import net.maploop.items.util.DUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Decoy extends CustomItem {
    private Map<Zombie, ArmorStand> tag = new HashMap<>();

    public Decoy(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, boolean glowing) {
        super(id, rarity, name, material, durability, stackable, oneTimeUse, hasActive, abilities, manaCost, reforgeable, itemType, glowing);
    }

    @Override
    public void onItemStackCreate(ItemStack paramItemStack) {

    }

    @Override
    public void getSpecificLorePrefix(List<String> lore, ItemStack paramItemStack) {
        lore.add("§7Runs around and draws");
        lore.add("§7attention from nearby mobs,");
        lore.add("§7very handy for creating a");
        lore.add("§7distraction!");
        lore.add("§7");
        lore.add("§cDungeons only!");
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

    }

    @Override
    public void rightClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item) {
        event.setCancelled(true);
        if(player.getGameMode() != GameMode.CREATIVE) {
            ItemStack stack = CustomItem.fromString(Items.getInstance(), "decoy", 1);
            player.getInventory().removeItem(stack);
        }

        Zombie decoy = (Zombie) event.getClickedBlock().getLocation().getWorld().spawnEntity(event.getClickedBlock().getLocation().add(0, 1, 0), EntityType.ZOMBIE);
        decoy.setMaxHealth(100);
        decoy.setHealth(100);
        decoy.setBaby(false);
        decoy.setVillager(false);

        ArmorStand name = (ArmorStand) decoy.getWorld().spawnEntity(decoy.getLocation(), EntityType.ARMOR_STAND);
        name.setVisible(false);
        name.setGravity(false);
        name.setCustomNameVisible(true);
        name.setSmall(true);

        tag.put(decoy, name);

        new BukkitRunnable() {
            @Override
            public void run() {
                if(tag.containsKey(decoy)) {
                    if(decoy.isDead()) {
                        name.remove();

                        tag.remove(decoy);
                        return;
                    }
                    name.teleport(decoy.getLocation().add(0, 1, 0));
                    if(decoy.getHealth() < 50) {
                        name.setCustomName("§8[§7Lv25§8] §aDecoy §e" + Math.round(decoy.getHealth()) + "§f/§a" + Math.round(decoy.getMaxHealth()) + "§c❤");
                        return;
                    }
                    name.setCustomName("§8[§7Lv25§8] §aDecoy §a" + Math.round(decoy.getHealth()) + "§f/§a" + Math.round(decoy.getMaxHealth()) + "§c❤");
                }
            }
        }.runTaskTimer(Items.getInstance(), 0, 1);
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
    public void hitEntityAction(Player paramPlayer, EntityDamageByEntityEvent paramEntityDamageByEntityEvent, Entity paramEntity, ItemStack paramItemStack) {

    }

    @Override
    public void breakBlockAction(Player paramPlayer, BlockBreakEvent paramBlockBreakEvent, Block paramBlock, ItemStack paramItemStack) {

    }

    @Override
    public void clickedInInventoryAction(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent) {

    }

    @Override
    public void activeEffect(Player paramPlayer, ItemStack paramItemStack) {

    }
}
