package net.maploop.items.listeners;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.maploop.items.Items;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.user.User;
import net.maploop.items.util.IUtil;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.Random;

public class EntityDamageByEntityListener implements Listener {
    public static boolean activatedcrithit = false;
    @EventHandler
    public void onDMG(EntityDamageByEntityEvent event) {
        double damage = 0;
        if (event.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getDamager();
            if (arrow.getShooter() instanceof Player) {

                Player player = (Player) arrow.getShooter();
                User user = new User(player);
                try {
                    double d = 0;
                    d += (5 + ItemUtilities.getIntFromItem(player.getItemInHand(), "DAMAGE") + (user.getTotalStrength() / 5)) * (1 + user.getTotalStrength() / 100);
                    d += 1 + (1 * 0.04);
                    Random rnd = new Random();
                    int critchancernd = rnd.nextInt(100);
                    if (critchancernd <= user.getTotalCritChance()) {
                        d *= (1 + user.getTotalCritDamage() / 100);
                        activatedcrithit = true;
                    }
                    damage = d;
                    event.setDamage(d);
                } catch (NullPointerException e) {
                    double d = 0;
                    d += (5 + (user.getTotalStrength() / 5)) * (1 + user.getTotalStrength() / 100);
                    d += 1 + (1 * 0.04);
                    Random rnd = new Random();
                    int critchancernd = rnd.nextInt(100);
                    if (critchancernd <= user.getCrit_chance()) {
                        d *= (1 + user.getCrit_damage() / 100);
                        activatedcrithit = true;
                    }
                    damage = d;
                    event.setDamage(d);
                }
            }
        }
        if (event.getDamager() instanceof Player) {
            if(event.getEntity() instanceof Player) return;
            Player player = (Player) event.getDamager();
            User user = new User(player);
            // Calculations
            try {
                double d = 0;
                d += (5 + ItemUtilities.getIntFromItem(player.getItemInHand(), "DAMAGE") + (user.getTotalStrength() / 5)) * (1 + user.getTotalStrength() / 100);
                d += 1 + (1 * 0.04);
                Random rnd = new Random();
                int critchancernd = rnd.nextInt(100);
                if (critchancernd <= user.getTotalCritChance()) {
                    d *= (1 + user.getTotalCritDamage() / 100);
                    activatedcrithit = true;
                }
                damage = d;
                event.setDamage(d);
            } catch (NullPointerException e) {
                double d = 0;
                d += (5 + (user.getTotalStrength() / 5)) * (1 + user.getTotalStrength() / 100);
                d += 1 + (1 * 0.04);
                Random rnd = new Random();
                int critchancernd = rnd.nextInt(100);
                if (critchancernd <= user.getCrit_chance()) {
                    d *= (1 + user.getCrit_damage() / 100);
                    activatedcrithit = true;
                }
                damage = d;
                event.setDamage(d);
            }
        } else if (event.getEntity() instanceof Player) {
            double d = event.getDamage();
            Player player = (Player) event.getEntity();
            User user = new User(player);
            double reduction = user.getTotalDefense() / (user.getTotalDefense() + 100);
            double realDmg = d - (d * reduction);
            user.setHealth(user.getHealth() - realDmg);
            event.setDamage(0.1);
        }

        double finalDamage = damage;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (finalDamage > 0) {
                    addIndicator(finalDamage, IUtil.getRandomLocation(event.getEntity().getLocation(), 2), event.getCause());
                } else {
                    addIndicator(finalDamage, IUtil.getRandomLocation(event.getEntity().getLocation(), 2), event.getCause());
                }
            }
        }.runTaskLater(Items.getInstance(), 1L);
    }

    public String addCritTexture(String str) {
        String new_string = null;
        if (str.length() == 1)
            new_string = "§f✧§f" + str + "§e✧";
        if (str.length() == 2)
            new_string = "§f✧§f" + (str.charAt(0)) + "§e" + String.valueOf(str.charAt(1)) + "§c✧".replaceAll(",", ChatColor.DARK_PURPLE + ",");
        if (str.length() == 3)
            new_string = "§f✧§f" + (str.charAt(0)) + "§e" + String.valueOf(str.charAt(1)) + "§6" + String.valueOf(str.charAt(2)) + "§c✧".replaceAll(",", ChatColor.DARK_PURPLE + ",");
        if (str.length() == 4)
            new_string = "§f✧§f" + (str.charAt(0)) + "§e" + String.valueOf(str.charAt(1)) + "§6" + String.valueOf(str.charAt(2)) + "§c" + String.valueOf(str.charAt(3)) + "§c✧".replaceAll(",", ChatColor.DARK_PURPLE + ",");
        if (str.length() == 5)
            new_string = "§f✧§f" +(str.charAt(0)) + "§e" + String.valueOf(str.charAt(1)) + "§6" + String.valueOf(str.charAt(2)) + "§c" + String.valueOf(str.charAt(3)) + str.charAt(4) + "§e✧".replaceAll(",", ChatColor.DARK_PURPLE + ",");
        if (str.length() == 6)
            new_string = "§f✧§f" + (str.charAt(0)) + "§e" + String.valueOf(str.charAt(1)) + "§6" + String.valueOf(str.charAt(2)) + "§c" + String.valueOf(str.charAt(3)) + str.charAt(4) + str.charAt(5) + "§e✧".replaceAll(",", ChatColor.DARK_PURPLE + ",");
        if (str.length() == 7)
            new_string = "§f✧§f" + (str.charAt(0)) + "§e" + (str.charAt(1)) + "§6" + String.valueOf(str.charAt(2)) + "§c" + String.valueOf(str.charAt(3)) + str.charAt(4) + str.charAt(5) + str.charAt(6) + "§e✧".replaceAll(",", ChatColor.DARK_PURPLE + ",");
        if(str.length() == 8)
            new_string = "§f✧§f" + (str.charAt(0)) + "§e" + (str.charAt(1)) + "§6" + String.valueOf(str.charAt(2)) + "§c" + String.valueOf(str.charAt(3)) + str.charAt(4) + str.charAt(5) + str.charAt(6) + str.charAt(7) + "§e✧".replaceAll(",", ChatColor.DARK_PURPLE + ",");
        if(str.length() == 9)
            new_string = "§f✧§f" + (str.charAt(0)) + "§e" + (str.charAt(1)) + "§6" + String.valueOf(str.charAt(2)) + "§c" + String.valueOf(str.charAt(3)) + str.charAt(4) + str.charAt(5) + str.charAt(6) + str.charAt(7) + str.charAt(8) + "§e✧".replaceAll(",", ChatColor.DARK_PURPLE + ",");
        if(str.length() == 10)
            new_string = "§f✧§f" + (str.charAt(0)) + "§e" + (str.charAt(1)) + "§6" + String.valueOf(str.charAt(2)) + "§c" + String.valueOf(str.charAt(3)) + str.charAt(4) + str.charAt(5) + str.charAt(6) + str.charAt(7) + str.charAt(8) + "§6" + str.charAt(9) + "§e✧".replaceAll(",", ChatColor.DARK_PURPLE + ",");
        if(str.length() == 11)
            new_string = "§f✧§f" + (str.charAt(0)) + "§e" + (str.charAt(1)) + "§6" + String.valueOf(str.charAt(2)) + "§c" + String.valueOf(str.charAt(3)) + str.charAt(4) + str.charAt(5) + str.charAt(6) + str.charAt(7) + str.charAt(8) + str.charAt(9) + "§e" + str.charAt(10) + "§e✧".replaceAll(",", ChatColor.DARK_PURPLE + ",");
        if(str.length() == 12)
            new_string = "§f✧§f" + (str.charAt(0)) + "§e" + (str.charAt(1)) + "§6" + String.valueOf(str.charAt(2)) + "§c" + String.valueOf(str.charAt(3)) + str.charAt(4) + str.charAt(5) + str.charAt(6) + str.charAt(7) + str.charAt(8) + str.charAt(9) + str.charAt(10) + "§f" + str.charAt(11) + "§e✧".replaceAll(",", ChatColor.DARK_PURPLE + ",");

        return new_string;
    }

    public void addIndicator(double damage, Location loc, EntityDamageEvent.DamageCause cause) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formatted = formatter.format(damage);

        EntityArmorStand Indicator = new EntityArmorStand(((CraftWorld) loc.getWorld()).getHandle(), loc.getX(), loc.getY(), loc.getZ());
        Indicator.setCustomNameVisible(true);
        Indicator.setGravity(false);
        Indicator.setInvisible(true);
        Indicator.setSmall(true);
        Indicator.setSize(1, 1);
        Indicator.setAirTicks(20);

        if (cause == EntityDamageEvent.DamageCause.FIRE_TICK || cause == EntityDamageEvent.DamageCause.FIRE || cause == EntityDamageEvent.DamageCause.LAVA) {
            Indicator.setCustomName("§6" + Math.round(damage));
        } else if(cause == EntityDamageEvent.DamageCause.DROWNING) {
            Indicator.setCustomName("§3" + Math.round(damage));
        } else if(cause == EntityDamageEvent.DamageCause.POISON) {
            Indicator.setCustomName("§2" + Math.round(damage));
        } else {
            if (activatedcrithit) {
                Indicator.setCustomName(addCritTexture(formatted));
                activatedcrithit = false;
            } else {
                Indicator.setCustomName("§7" + Math.round(damage));
            }
        }

        PacketPlayOutSpawnEntityLiving pakcet = new PacketPlayOutSpawnEntityLiving(Indicator);
        PacketPlayOutEntityDestroy removePacket = new PacketPlayOutEntityDestroy(Indicator.getId());
        for (Player players : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) players).getHandle().playerConnection.sendPacket(pakcet);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    ((CraftPlayer) players).getHandle().playerConnection.sendPacket(removePacket);
                }
            }
        }.runTaskLater(Items.getInstance(), 10);
    }
}
