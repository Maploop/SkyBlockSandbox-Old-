package net.maploop.items.listeners;

import net.maploop.items.Items;
import net.maploop.items.animations.Bonemerang;
import net.maploop.items.animations.BonemerangReturn;
import net.maploop.items.animations.BonzoStaff;
import net.maploop.items.helpers.Utilities;
import net.maploop.items.items.BONE_BOOMERANG;
import net.maploop.items.items.Item;
import net.maploop.items.items.ItemMaker;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;


public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        if (!(item.hasItemMeta())) return;
        if (!(item.getItemMeta().hasDisplayName())) return;
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (item.getItemMeta().getDisplayName().contains("§6Hyperion")) {
                if (Utilities.getBlockOfSight(player, 8).getType() == Material.AIR) {
                    Location loc = player.getLocation();
                    Vector direction = player.getLocation().getDirection();
                    direction.normalize();
                    direction.multiply(8);
                    loc.add(direction);

                    player.teleport(loc);
                    player.getWorld().playEffect(player.getLocation(), Effect.EXPLOSION_LARGE, 10);
                    player.playSound(player.getLocation(), Sound.EXPLODE, 2f, 2);
                    Utilities.sendActionbar(player, "§b-200 Mana (§6Hyperion Ability§b)");
                } else {
                    Location loc = Utilities.getBlockOfSight(player, 8).getLocation();
                    Location finalloc = loc.add(0, 1, 0);
                    player.teleport(new Location(player.getWorld(), finalloc.getX(), finalloc.getY(), finalloc.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
                    player.getWorld().playEffect(player.getLocation(), Effect.EXPLOSION_LARGE, 10);
                    player.playSound(player.getLocation(), Sound.EXPLODE, 2f, 2);
                    Utilities.sendActionbar(player, "§b-200 Mana (§6Hyperion Ability§b)");
                }
            }

            if (item.getItemMeta().getDisplayName().equals("§aSkyblock Menu")) {
                player.performCommand("sbmenu");
            }

            if (item.getItemMeta().getDisplayName().contains("§6Bonemerang")) {
                if (player.getItemInHand().getType().equals(Material.GHAST_TEAR)) return;
                ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0, 0.8f, 0), EntityType.ARMOR_STAND);
                int slot = player.getInventory().getHeldItemSlot();
                ItemStack bone = player.getItemInHand().clone();
                stand.getEquipment().setItemInHand(BONE_BOOMERANG.get());
                stand.setArms(true);
                stand.setGravity(false);
                stand.setVisible(false);
                stand.setRightArmPose(new EulerAngle(270f, 0f, 0f));

                int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Bonemerang(stand, player), 0L, 1);

                player.getItemInHand().setType(Material.GHAST_TEAR);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.getScheduler().cancelTask(i);
                        int i1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new BonemerangReturn(stand, player), 0L, 1);

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
                        player.getInventory().setItem(slot, bone);
                    }
                }, 90);
            }

            if (item.getItemMeta().getDisplayName().contains("§9Bonzo's Staff")) {
                ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
                stand.getEquipment().setHelmet(ItemMaker.makeCustomSkullItem("http://textures.minecraft.net/texture/f868e6a5c4a445d60a3050b5bec1d37af1b25943745d2d479800c8436488065a", "§aBalloon", 1));
                stand.setArms(true);
                stand.setGravity(false);
                stand.setVisible(false);
                int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new BonzoStaff(stand, player), 0L, 1);

                new BukkitRunnable() {

                    @Override
                    public void run() {
                        Bukkit.getScheduler().cancelTask(i);
                        stand.remove();
                    }
                }.runTaskLater(Items.getInstance(), 100);
            }
        }
    }
}
