package net.maploop.items.listeners;

import net.maploop.items.Items;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class EntitySpawnListener implements Listener {
    private static final Map<Entity, ArmorStand> tag = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSpawn(EntitySpawnEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                ArmorStand name = (ArmorStand) event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.ARMOR_STAND);
                name.setVisible(false);
                name.setGravity(false);
                name.setCustomNameVisible(true);
                name.setCustomName("§aEntity");
                name.setSmall(true);
                name.setMetadata("Invulnerable", new FixedMetadataValue(Items.getInstance(), "true"));
                tag.put(event.getEntity(), name);


                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(tag.containsKey(event.getEntity())) {
                            if(event.getEntity().isDead()) {
                                name.remove();

                                tag.remove(event.getEntity());
                                return;
                            }
                            LivingEntity decoy = (LivingEntity) event.getEntity();
                            name.teleport(decoy.getLocation().add(0, 1, 0));
                            if(decoy.getHealth() < 50) {
                                name.setCustomName("§8[§7Lv25§8] §a" + decoy.getName() +" §e" + Math.round(decoy.getHealth()) + "§f/§a" + Math.round(decoy.getMaxHealth()) + "§c❤");
                                return;
                            }
                            name.setCustomName("§8[§7Lv25§8] §a" + decoy.getName() +" §a" + Math.round(decoy.getHealth()) + "§f/§a" + Math.round(decoy.getMaxHealth()) + "§c❤");
                        }
                    }
                }.runTaskTimer(Items.getInstance(), 0, 1);
            }
        }.runTaskLater(Items.getInstance(), 5);
    }
}
