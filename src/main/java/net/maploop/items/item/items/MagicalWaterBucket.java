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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class MagicalWaterBucket extends CustomItem {
    public MagicalWaterBucket(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, boolean glowing) {
        super(id, rarity, name, material, durability, stackable, oneTimeUse, hasActive, abilities, manaCost, reforgeable, itemType, glowing);
    }

    @Override
    public void onItemStackCreate(ItemStack paramItemStack) {

    }

    @Override
    public void getSpecificLorePrefix(List<String> lore, ItemStack paramItemStack) {
        lore.add("§7This Magical Water Bucket will");
        lore.add("§7never run out of water, no");
        lore.add("§7matter how many times it is");
        lore.add("§7emptied.");
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
    public void rightClickBlockAction(Player paramPlayer, PlayerInteractEvent event, Block paramBlock, ItemStack paramItemStack) {
        paramPlayer.setItemInHand(CustomItem.fromString(Items.getInstance(), "magical_water_bucket", 1));
        paramPlayer.updateInventory();
        switch (event.getBlockFace()) {
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                if(event.getClickedBlock().getType() == Material.PRISMARINE && event.getClickedBlock().hasMetadata("Prismapump")) {
                    switch (event.getBlockFace()) {
                        case NORTH: {
                            Location loc = event.getClickedBlock().getLocation().add(0, 0, -1);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Location finalCheckLoc = loc.add(0, 0, -1);
                                    if(finalCheckLoc.getBlock().getType() == Material.AIR || finalCheckLoc.getBlock().getType() == Material.STATIONARY_WATER || finalCheckLoc.getBlock().getType() == Material.WATER) {
                                        finalCheckLoc.getBlock().setType(Material.WATER);
                                    } else {
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(Items.getInstance(), 1, 3);
                            break;
                        }
                        case EAST: {
                            Location loc = event.getClickedBlock().getLocation().add(+1, 0, 0);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Location finalCheckLoc = loc.add(+1, 0, 0);
                                    if(finalCheckLoc.getBlock().getType() == Material.AIR || finalCheckLoc.getBlock().getType() == Material.STATIONARY_WATER || finalCheckLoc.getBlock().getType() == Material.WATER) {
                                        finalCheckLoc.getBlock().setType(Material.WATER);
                                    } else {
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(Items.getInstance(), 1, 3);
                            break;
                        }
                        case SOUTH: {
                            Location loc = event.getClickedBlock().getLocation().add(0, 0, +1);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Location finalCheckLoc = loc.add(0, 0, +1);
                                    if(finalCheckLoc.getBlock().getType() == Material.AIR || finalCheckLoc.getBlock().getType() == Material.STATIONARY_WATER || finalCheckLoc.getBlock().getType() == Material.WATER) {
                                        finalCheckLoc.getBlock().setType(Material.WATER);
                                    } else {
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(Items.getInstance(), 1, 3);
                            break;
                        }
                        case WEST: {
                            Location loc = event.getClickedBlock().getLocation().add(-1, 0, 0);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Location finalCheckLoc = loc.add(-1, 0, 0);
                                    if(finalCheckLoc.getBlock().getType() == Material.AIR || finalCheckLoc.getBlock().getType() == Material.STATIONARY_WATER || finalCheckLoc.getBlock().getType() == Material.WATER) {
                                        finalCheckLoc.getBlock().setType(Material.WATER);
                                    } else {
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(Items.getInstance(), 1, 3);
                            break;
                        }
                    }
                }
            default: {
                break;
            }
        }
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
        rightClickBlockAction(paramPlayer, paramPlayerInteractEvent, paramBlock, paramItemStack);
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
