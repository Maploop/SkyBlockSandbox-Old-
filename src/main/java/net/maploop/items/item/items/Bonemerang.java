package net.maploop.items.item.items;

import net.maploop.items.Items;
import net.maploop.items.animations.BonemerangReturn;
import net.maploop.items.animations.BonemrangForward;
import net.maploop.items.data.DataHandler;
import net.maploop.items.enums.ItemStats;
import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemAbility;
import net.maploop.items.listeners.EntityDamageListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.List;

public class Bonemerang extends CustomItem {
    public Bonemerang(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, boolean glowing) {
        super(id, rarity, name, material, durability, stackable, oneTimeUse, hasActive, abilities, manaCost, reforgeable, itemType, glowing);
    }

    @Override
    public void onItemStackCreate(ItemStack paramItemStack) {

    }

    @Override
    public void getSpecificLorePrefix(List<String> lore, ItemStack paramItemStack) {
        lore.add(ItemStats.GEAR_SCORE.getDisplayname() + "530");
        lore.add(ItemStats.DAMAGE.getDisplayname() + "320");
        lore.add(ItemStats.STRENGTH.getDisplayname() + "130");
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
        if (paramPlayer.getItemInHand().getType().equals(Material.GHAST_TEAR)) return;
        ArmorStand stand = (ArmorStand) paramPlayer.getWorld().spawnEntity(paramPlayer.getLocation().add(0, 0.8f, 0), EntityType.ARMOR_STAND);
        int slot = paramPlayer.getInventory().getHeldItemSlot();
        ItemStack bone = paramPlayer.getItemInHand().clone();
        stand.getEquipment().setItemInHand(paramPlayer.getItemInHand());
        stand.setArms(true);
        stand.setGravity(false);
        stand.setVisible(false);
        stand.setRightArmPose(new EulerAngle(270f, 0f, 0f));

        int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new BonemrangForward(stand, paramPlayer), 0L, 1);

        paramPlayer.getItemInHand().setType(Material.GHAST_TEAR);

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getScheduler().cancelTask(i);
                int i1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new BonemerangReturn(stand, paramPlayer), 0L, 1);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.getScheduler().cancelTask(i1);
                        stand.remove();
                    }
                }.runTaskLater(Items.getInstance(), 45);
            }
        }.runTaskLater(Items.getInstance(), 45);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Items.getInstance(), new Runnable() {
            @Override
            public void run() {
                paramPlayer.getInventory().setItem(slot, bone);
            }
        }, 45);
    }

    @Override
    public void rightClickBlockAction(Player paramPlayer, PlayerInteractEvent paramPlayerInteractEvent, Block paramBlock, ItemStack paramItemStack) {
        paramPlayerInteractEvent.setCancelled(true);
        if (paramPlayer.getItemInHand().getType().equals(Material.GHAST_TEAR)) return;
        ArmorStand stand = (ArmorStand) paramPlayer.getWorld().spawnEntity(paramPlayer.getLocation().add(0, 0.8f, 0), EntityType.ARMOR_STAND);
        int slot = paramPlayer.getInventory().getHeldItemSlot();
        ItemStack bone = paramPlayer.getItemInHand().clone();
        stand.getEquipment().setItemInHand(paramPlayer.getItemInHand());
        stand.setArms(true);
        stand.setGravity(false);
        stand.setVisible(false);
        stand.setRightArmPose(new EulerAngle(270f, 0f, 0f));

        int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new BonemrangForward(stand, paramPlayer), 0L, 1);

        paramPlayer.getItemInHand().setType(Material.GHAST_TEAR);

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getScheduler().cancelTask(i);
                int i1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new BonemerangReturn(stand, paramPlayer), 0L, 1);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.getScheduler().cancelTask(i1);
                        stand.remove();
                    }
                }.runTaskLater(Items.getInstance(), 45);
            }
        }.runTaskLater(Items.getInstance(), 45);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Items.getInstance(), new Runnable() {
            @Override
            public void run() {
                paramPlayer.getInventory().setItem(slot, bone);
            }
        }, 45);
    }

    @Override
    public void shiftLeftClickAirAction(Player paramPlayer, ItemStack paramItemStack) {

    }

    @Override
    public void shiftLeftClickBlockAction(Player paramPlayer, PlayerInteractEvent paramPlayerInteractEvent, Block paramBlock, ItemStack paramItemStack) {

    }

    @Override
    public void shiftRightClickAirAction(Player paramPlayer, PlayerInteractEvent event, ItemStack paramItemStack) {
        rightClickAirAction(paramPlayer, event, paramItemStack);
    }

    @Override
    public void shiftRightClickBlockAction(Player paramPlayer, PlayerInteractEvent paramPlayerInteractEvent, Block paramBlock, ItemStack paramItemStack) {
        rightClickAirAction(paramPlayer, paramPlayerInteractEvent, paramItemStack);
    }

    @Override
    public void middleClickAction(Player paramPlayer, ItemStack paramItemStack) {

    }

    @Override
    public void hitEntityAction(Player paramPlayer, EntityDamageByEntityEvent event, Entity paramEntity, ItemStack paramItemStack) { }

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
