package net.maploop.items.listeners;

import de.tr7zw.nbtapi.NBTItem;
import net.maploop.items.Items;
import net.maploop.items.animations.Bonemerang;
import net.maploop.items.animations.BonemerangReturn;
import net.maploop.items.animations.BonzoStaff;
import net.maploop.items.helpers.Utilities;
import net.maploop.items.items.BONE_BOOMERANG;
import net.maploop.items.items.Item;
import net.maploop.items.items.ItemMaker;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import sun.nio.ch.Util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        if (!(item.hasItemMeta())) return;
        if (!(item.getItemMeta().hasDisplayName())) return;
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (item.getItemMeta().getDisplayName().contains("§6Hyperion")) {
                Player p = event.getPlayer();
                Location l = p.getEyeLocation();

                for (int i = 0; i < 9; i++) {
                    Block b = Utilities.getBlockOfSight(p, i);
                    if (Utilities.getBlockOfSight(p, 2).getType().equals(Material.AIR) && b.getType().equals(Material.AIR)) {
                        p.teleport(p.getLocation().add(p.getLocation().getDirection()));
                    }
                }

                p.playSound(l, Sound.EXPLODE, 1, 1);

                List<Entity> entities = p.getNearbyEntities(7, 4, 7);

                List<Double> possibleDMG = new ArrayList<>();

                possibleDMG.add(826965.96);
                possibleDMG.add(846963.34);
                possibleDMG.add(857465.78);
                possibleDMG.add(799695.96);
                possibleDMG.add(826965.96);
                possibleDMG.add(803704.64);
                possibleDMG.add(802896.63);
                possibleDMG.add(842351.72);
                possibleDMG.add(793745.75);
                possibleDMG.add(829194.25);
                possibleDMG.add(811169.42);

                int rnd = new Random().nextInt(possibleDMG.size());

                int entcount = 0;

                for (Entity en : entities) {
                    if (en instanceof Damageable && (!(en instanceof Player)) && (!(en instanceof ArmorStand)) && (!(en instanceof WitherSkull))) {
                        entcount++;
                        Damageable len = (LivingEntity) en;

                        len.damage(possibleDMG.get(rnd));
                    } else if (en instanceof Player) {
                        if (en.hasMetadata("NPC")) {
                            entcount++;
                            Damageable len = (LivingEntity) en;

                            len.damage(possibleDMG.get(rnd));
                        }
                    }
                }

                NumberFormat myFormat = NumberFormat.getNumberInstance(Locale.US);

                String dfs = myFormat.format(entcount * 5 * possibleDMG.get(rnd)).replace(" ", ",");

                if (entcount > 0) {
                    p.playSound(p.getLocation(), Sound.ZOMBIE_UNFECT, 1, 1);
                    p.sendMessage("§7Your Implosion hit §c" + entcount + "§7 enemies for §c" + dfs + " §7damage.");
                }

                if (p.getHealth() + 3 < p.getMaxHealth()) {
                    p.setHealth(p.getHealth() + 3);
                } else {
                    p.setHealth(p.getMaxHealth());
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

                int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Bonemerang(stand, player, player.getLocation()), 0L, 1);

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
                net.minecraft.server.v1_8_R3.ItemStack stack = CraftItemStack.asNMSCopy(item);
                NBTTagCompound tag = stack.getTag();

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
