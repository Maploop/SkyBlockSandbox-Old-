package net.maploop.items.listeners;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.maploop.items.Items;
import net.maploop.items.event.PlayerCustomDeathEvent;
import net.maploop.items.user.User;
import net.maploop.items.util.IUtil;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

public class EntityDamageListener implements Listener {
    public double damage = 0;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event) {
        double d = event.getDamage();
        if (event.getEntity().hasMetadata("NPC")) {
            NPC npc = CitizensAPI.getNPCRegistry().getNPC(event.getEntity());

            if (npc.getName().contains("Lost Adventurer")) {
                LivingEntity livingEntity = (LivingEntity) event.getEntity();
                String health = "";

                if (livingEntity.getHealth() < livingEntity.getMaxHealth() / 2) {
                    health += "§e";
                } else {
                    health += "§a";
                }

                if (livingEntity.getHealth() < 10000000 && livingEntity.getHealth() > 1000000) {
                    if (String.valueOf(livingEntity.getHealth()).substring(1, 2) != "0") {
                        health += String.valueOf(livingEntity.getHealth()).substring(0, 1) + "." + String.valueOf(livingEntity.getHealth()).substring(1, 2) + "M";
                    } else {
                        health += String.valueOf(livingEntity.getHealth()).substring(0, 1) + "M";
                    }
                } else if (livingEntity.getHealth() < 1000000 && livingEntity.getHealth() > 100000) {
                    health += String.valueOf(livingEntity.getHealth()).substring(0, 3) + "k";
                } else {
                    health += (int) livingEntity.getHealth();
                }

                npc.setName("§d§lLost Adventurer " + health + "§c❤");
            } else if (npc.getName().contains("Terracotta")) {
                LivingEntity livingEntity = (LivingEntity) event.getEntity();
                String health = "";

                if (livingEntity.getHealth() < livingEntity.getMaxHealth() / 2) {
                    health += "§e";
                } else {
                    health += "§a";
                }

                if (livingEntity.getHealth() < 10000000 && livingEntity.getHealth() > 1000000) {
                    if (String.valueOf(livingEntity.getHealth()).substring(1, 2) != "0") {
                        health += String.valueOf(livingEntity.getHealth()).substring(0, 1) + "." + String.valueOf(livingEntity.getHealth()).substring(1, 2) + "M";
                    } else {
                        health += String.valueOf(livingEntity.getHealth()).substring(0, 1) + "M";
                    }
                } else if (livingEntity.getHealth() < 1000000 && livingEntity.getHealth() > 100000) {
                    health += String.valueOf(livingEntity.getHealth()).substring(0, 3) + "k";
                } else if (livingEntity.getHealth() > 10000000) {
                    health += String.valueOf(livingEntity.getHealth()).substring(0, 1) + "M";
                } else {
                    health += (int) livingEntity.getHealth();
                }

                npc.setName("§d§lLost Adventurer " + health + "§c❤");
            } else if (npc.getName().contains("§6﴾§cEnder§6﴿")){
                LivingEntity livingEntity = (LivingEntity) event.getEntity();
                String health = "";
                event.setDamage(event.getDamage());
            }
            return;
        }
        if(event.getEntity() instanceof Player) {
            event.setDamage(0);
            if(event.getEntity().hasMetadata("NPC")) return;
            Player player = (Player) event.getEntity();
            User user = new User(player);
            double reduction = user.getTotalDefense() / (user.getTotalDefense() + 100);
            double realDmg = d - (d * reduction);
            user.setHealth(user.getHealth() - realDmg);
            System.out.println(realDmg);
            if(user.getHealth() <= 0) {
                Bukkit.getPluginManager().callEvent(new PlayerCustomDeathEvent(player, user, event.getCause()));
            }
        }
        if (event.getEntity().getType() == EntityType.ARMOR_STAND) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (damage > 0) {
                    addIndicator(damage, IUtil.getRandomLocation(event.getEntity().getLocation(), 2), event.getCause());
                } else {
                    addIndicator(d, IUtil.getRandomLocation(event.getEntity().getLocation(), 2), event.getCause());
                }
            }
        }.runTaskLater(Items.getInstance(), 1L);
    }


    public String addCritTexture(String str) {
        String new_string = null;
        if (str.length() == 1)
            new_string = "§f✧§f" + str + "§f✧";
        if (str.length() == 2)
            new_string = "§f✧" + (str.charAt(0)) + "§e" + String.valueOf(str.charAt(1)) + "§c✧".replaceAll(",", ChatColor.DARK_PURPLE + ",");
        if (str.length() == 3)
            new_string = "§f✧" + (str.charAt(0)) + "§e" + String.valueOf(str.charAt(1)) + "§6" + String.valueOf(str.charAt(2)) + "§c✧".replaceAll(",", ChatColor.DARK_PURPLE + ",");
        if (str.length() == 4)
            new_string = "§f✧" + (str.charAt(0)) + "§e" + String.valueOf(str.charAt(1)) + "§6" + String.valueOf(str.charAt(2)) + "§c" + String.valueOf(str.charAt(3)) + "§c✧".replaceAll(",", ChatColor.DARK_PURPLE + ",");
        if (str.length() == 5)
            new_string = "§f✧" +(str.charAt(0)) + "§e" + String.valueOf(str.charAt(1)) + "§6" + String.valueOf(str.charAt(2)) + "§c" + String.valueOf(str.charAt(3)) + str.charAt(4) + "§f✧".replaceAll(",", ChatColor.DARK_PURPLE + ",");
        if (str.length() == 6)
            new_string = "§f✧" + (str.charAt(0)) + "§e" + String.valueOf(str.charAt(1)) + "§6" + String.valueOf(str.charAt(2)) + "§c" + String.valueOf(str.charAt(3)) + str.charAt(4) + str.charAt(5) + "§f✧".replaceAll(",", ChatColor.DARK_PURPLE + ",");
        if (str.length() == 7)
            new_string = "§f✧" + (str.charAt(0)) + "§e" + (str.charAt(1)) + "§6" + String.valueOf(str.charAt(2)) + "§c" + String.valueOf(str.charAt(3)) + str.charAt(4) + str.charAt(5) + str.charAt(6) + "§f✧".replaceAll(",", ChatColor.DARK_PURPLE + ",");
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
            if (damage < 600) {
                Indicator.setCustomName("§7" + Math.round(damage));
            } else if (damage > 600) {
                Indicator.setCustomName(addCritTexture(formatted));
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
