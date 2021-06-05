package net.maploop.items.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
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
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

import static net.maploop.items.listeners.EntityDamageByEntityListener.activatedcrithit;

public class EntityDamageListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            User user = new User(player);
            if(event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)){return;}
            user.setHealth(user.getHealth() - event.getDamage());
            event.setDamage(0.1);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onExplode(EntityExplodeEvent event) {
        if(event.getEntity() instanceof Fireball) {
            event.setYield(0.0F);
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPrimeExplode(ExplosionPrimeEvent event) {
        if(event.getEntity() instanceof Fireball) {
            event.setFire(false);
            event.setRadius(0.0F);
            event.setCancelled(true);
        }
    }
}
