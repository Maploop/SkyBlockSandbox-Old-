package net.maploop.items.item.items;

import net.maploop.items.Items;
import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemAbility;
import net.maploop.items.util.PacketParticle;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class GuidedMissile extends CustomItem {
    public GuidedMissile(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, String url, boolean glowing) {
        super(id, rarity, name, material, durability, stackable, oneTimeUse, hasActive, abilities, manaCost, reforgeable, itemType, url, glowing);
    }

    @Override
    public void onItemStackCreate(ItemStack paramItemStack) {
        applyTexture(paramItemStack);
    }

    @Override
    public void getSpecificLorePrefix(List<String> paramList, ItemStack paramItemStack) {

    }

    @Override
    public void getSpecificLoreSuffix(List<String> paramList, ItemStack paramItemStack) {
        paramList.add("§0");
        paramList.add("§8Hold sneak to launch freely!");
    }

    @Override
    public void leftClickAirAction(Player player, ItemStack item) {

    }

    @Override
    public void leftClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item) {

    }

    @Override
    public void rightClickAirAction(Player player, PlayerInteractEvent event, ItemStack item) {
        if (true) {
            player.sendMessage("§cThis item is disabled!");
            return;
        }

        ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setHelmet(player.getItemInHand());
        stand.setPassenger(player);

        Bukkit.getScheduler().runTaskTimer(Items.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                Location loc = stand.getLocation();
                Vector direction = player.getLocation().getDirection();

                loc.add(direction);

                for (Entity entity : stand.getNearbyEntities(0.3, 0.3, 0.3)) {
                    if (entity instanceof LivingEntity) {
                        stand.remove();
                        new PacketParticle(EnumParticle.EXPLOSION_HUGE, stand.getLocation(), false, 0, 0, 0, 0, 1).sendPlayer(player);
                        player.playSound(player.getLocation(), Sound.EXPLODE, 1, 1);
                        this.cancel();
                        return;
                    }
                }

                stand.teleport(loc);

                loc.subtract(direction);
            }
        }, 0, 1);
    }

    @Override
    public void rightClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item) {

    }

    @Override
    public void shiftLeftClickAirAction(Player player, ItemStack item) {

    }

    @Override
    public void shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item) {

    }

    @Override
    public void shiftRightClickAirAction(Player player, PlayerInteractEvent event, ItemStack item) {

    }

    @Override
    public void shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item) {

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

    }

    @Override
    public void activeEffect(Player player, ItemStack item) {

    }
}
