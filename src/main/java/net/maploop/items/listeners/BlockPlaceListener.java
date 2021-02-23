package net.maploop.items.listeners;

import net.maploop.items.Items;
import net.maploop.items.animations.*;
import net.maploop.items.items.MANAFLUX_POWER_ORB;
import net.maploop.items.items.OVERFLUX_POWER_ORB;
import net.maploop.items.items.PLASMAFLUX_POWER_ORB;
import net.maploop.items.items.RADIANT_POWER_ORB;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class BlockPlaceListener implements Listener {
    public static HashMap<UUID, ArmorStand> isPlaced = new HashMap<>();
    public HashMap<ArmorStand, Long> timer = new HashMap<>();

    @SuppressWarnings("unchecked")
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();
        Location loc = event.getBlock().getLocation().add(0, 1, 0);

        if (!(item.hasItemMeta())) return;
        if (!(item.getItemMeta().hasDisplayName())) return;
        if (item.getItemMeta().getDisplayName().contains("§aRadiant Power Orb")) {
            if (isPlaced.containsKey(player.getUniqueId())) {
                event.setCancelled(true);
                player.sendMessage("§cYou already have one placed!");
                return;
            }

            if (!(isPlaced.containsKey(player.getUniqueId()))) {
                event.setCancelled(true);
                player.sendMessage("§eYou placed your §aRadiant Power Orb§e.");
                player.playSound(player.getLocation(), Sound.CLICK, 10F, 0);

                ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
                stand.getEquipment().setHelmet(RADIANT_POWER_ORB.get());
                stand.setVisible(false);
                stand.setGravity(false);
                stand.setCustomNameVisible(true);
                stand.setCanPickupItems(false);
                stand.setCustomName("§aRadiant Power Orb");
                isPlaced.put(player.getUniqueId(), stand);
                timer.put(stand, (System.currentTimeMillis()) + 30 * 1000);

                int animate = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Spin(stand), 0L, 1);
                int particle = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Items.getInstance(), new GreenParticle(stand), 0L, 1);
                int animate1 = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Items.getInstance(), new UpAndDown(stand), 0L, 10);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (timer.get(stand) > System.currentTimeMillis()) {
                            long time = (timer.get(stand) - System.currentTimeMillis()) / 1000;
                            stand.setCustomName("§aRadiant §e" + time + "s");

                            // Particle
                        }
                    }
                }.runTaskTimer(Items.getInstance(), 0, 1);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.getScheduler().cancelTask(animate);
                        Bukkit.getScheduler().cancelTask(animate1);
                        Bukkit.getScheduler().cancelTask(particle);
                        isPlaced.get(player.getUniqueId()).remove();
                        isPlaced.remove(player.getUniqueId());
                        stand.getWorld().playEffect(stand.getLocation(), Effect.LARGE_SMOKE, Effect.LARGE_SMOKE.getData());
                        player.sendMessage(ChatColor.RED + "Your previous orb was removed.");
                    }
                }.runTaskLater(Items.getInstance(), 600);
            }
        }

        if (item.getItemMeta().getDisplayName().contains("§9Mana Flux Power Orb")) {
            if (isPlaced.containsKey(player.getUniqueId())) {
                event.setCancelled(true);
                player.sendMessage("§cYou already have one placed!");
                return;
            }

            event.setCancelled(true);
            player.sendMessage("§eYou placed your §9Mana Flux Power Orb§e.");
            player.playSound(player.getLocation(), Sound.CLICK, 10F, 0);

            ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
            stand.getEquipment().setHelmet(MANAFLUX_POWER_ORB.get());
            stand.setVisible(false);
            stand.setGravity(false);
            stand.setCustomNameVisible(true);
            stand.setCanPickupItems(false);
            stand.setCustomName("§aRadiant Power Orb");
            isPlaced.put(player.getUniqueId(), stand);
            timer.put(stand, (System.currentTimeMillis()) + 30 * 1000);

            int animate = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Spin(stand), 0L, 1);
            int particle = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Items.getInstance(), new BlueParticle(stand), 0L, 1);
            int animate1 = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Items.getInstance(), new UpAndDown(stand), 0L, 10);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (timer.get(stand) > System.currentTimeMillis()) {
                        long time = (timer.get(stand) - System.currentTimeMillis()) / 1000;
                        stand.setCustomName("§9Mana Flux §e" + time + "s");
                    }
                }
            }.runTaskTimer(Items.getInstance(), 0, 1);

            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getScheduler().cancelTask(animate);
                    Bukkit.getScheduler().cancelTask(animate1);
                    Bukkit.getScheduler().cancelTask(particle);
                    isPlaced.get(player.getUniqueId()).remove();
                    isPlaced.remove(player.getUniqueId());
                    stand.getWorld().playEffect(stand.getLocation(), Effect.LARGE_SMOKE, Effect.LARGE_SMOKE.getData());
                    player.sendMessage(ChatColor.RED + "Your previous orb was removed.");
                }
            }.runTaskLater(Items.getInstance(), 600);
        }

        if (item.getItemMeta().getDisplayName().contains("§5Overflux Power Orb")) {
            if (isPlaced.containsKey(player.getUniqueId())) {
                event.setCancelled(true);
                player.sendMessage("§cYou already have one placed!");
                return;
            }

            event.setCancelled(true);
            player.sendMessage("§eYou placed your §5Overflux Power Orb§e.");
            player.playSound(player.getLocation(), Sound.CLICK, 10F, 0);

            ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
            stand.getEquipment().setHelmet(OVERFLUX_POWER_ORB.get());
            stand.setVisible(false);
            stand.setGravity(false);
            stand.setCustomNameVisible(true);
            stand.setCanPickupItems(false);
            stand.setCustomName("§aRadiant Power Orb");
            isPlaced.put(player.getUniqueId(), stand);
            timer.put(stand, (System.currentTimeMillis()) + 60 * 1000);

            int animate = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Spin(stand), 0L, 1);
            int animate1 = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Items.getInstance(), new UpAndDown(stand), 0L, 10);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (timer.get(stand) > System.currentTimeMillis()) {
                        long time = (timer.get(stand) - System.currentTimeMillis()) / 1000;
                        stand.setCustomName("§5Overflux §e" + time + "s");
                    }
                }
            }.runTaskTimer(Items.getInstance(), 0, 1);

            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getScheduler().cancelTask(animate);
                    Bukkit.getScheduler().cancelTask(animate1);
                    isPlaced.get(player.getUniqueId()).remove();
                    isPlaced.remove(player.getUniqueId());
                    stand.getWorld().playEffect(stand.getLocation(), Effect.LARGE_SMOKE, Effect.LARGE_SMOKE.getData());
                    player.sendMessage(ChatColor.RED + "Your previous orb was removed.");
                }
            }.runTaskLater(Items.getInstance(), 1200);
        }

        if (item.getItemMeta().getDisplayName().contains("§6Plasmaflux Power Orb")) {
            if (isPlaced.containsKey(player.getUniqueId())) {
                event.setCancelled(true);
                player.sendMessage("§cYou already have one placed!");
                return;
            }

            event.setCancelled(true);
            player.sendMessage("§eYou placed your §6Plasmaflux Power Orb§e.");
            player.playSound(player.getLocation(), Sound.CLICK, 10F, 0);

            ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
            stand.getEquipment().setHelmet(PLASMAFLUX_POWER_ORB.get());
            stand.setVisible(false);
            stand.setGravity(false);
            stand.setCustomNameVisible(true);
            stand.setCanPickupItems(false);
            stand.setCustomName("§aRadiant Power Orb");
            isPlaced.put(player.getUniqueId(), stand);
            timer.put(stand, (System.currentTimeMillis()) + 60 * 1000);

            int animate = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Spin(stand), 0L, 1);
            int animate1 = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Items.getInstance(), new UpAndDown(stand), 0L, 10);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (timer.get(stand) > System.currentTimeMillis()) {
                        long time = (timer.get(stand) - System.currentTimeMillis()) / 1000;
                        stand.setCustomName("§d§lPlasmaflux §e" + time + "s");
                    }
                }
            }.runTaskTimer(Items.getInstance(), 0, 1);

            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getScheduler().cancelTask(animate);
                    Bukkit.getScheduler().cancelTask(animate1);
                    isPlaced.get(player.getUniqueId()).remove();
                    isPlaced.remove(player.getUniqueId());
                    stand.getWorld().playEffect(stand.getLocation(), Effect.LARGE_SMOKE, Effect.LARGE_SMOKE.getData());
                    player.sendMessage(ChatColor.RED + "Your previous orb was removed.");
                }
            }.runTaskLater(Items.getInstance(), 1200);
        }
    }
}


