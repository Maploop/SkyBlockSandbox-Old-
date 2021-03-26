package net.maploop.items.animations;

import net.maploop.items.Items;
import net.maploop.items.listeners.EntityDamageListener;
import net.maploop.items.util.DUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class BonemerangReturn implements Runnable {
    private ArmorStand bone;
    private Player player;
    private boolean alreadyDone = false;

    public BonemerangReturn(ArmorStand bone, Player player) {
        this.bone = bone;
        this.player = player;
    }

    @Override
    public void run() {
        for (Entity e : bone.getNearbyEntities(0.1, 0.1, 0.1)) {
            LivingEntity e1 = null;
            if(!(e.isDead())) {
                if (!(e instanceof Item)) e1 = (LivingEntity) e;

                if (!(e instanceof Player)) {
                    double dmg = DUtil.calculateDamage(player, 320, 130) * 2;
                    if (e1 != null) {
                        e1.damage(dmg);

                        EntityDamageListener listener = new EntityDamageListener();
                        if(!alreadyDone) {
                            alreadyDone = true;
                            listener.addIndicator(dmg, DUtil.getRandomLocation(e1.getLocation(), 2), EntityDamageEvent.DamageCause.ENTITY_ATTACK);
                        }
                    };
                }
            }
        }
        Location loc = bone.getLocation();
        Vector direction = player.getLocation().getDirection();

        loc.setYaw(bone.getLocation().getYaw() + 73);
        loc.subtract(direction);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Items.getInstance(), new Runnable() {
            @Override
            public void run() {
                bone.teleport(loc);

                loc.setYaw(bone.getLocation().getYaw() + 73);
                loc.subtract(direction);
            }
        }, 2L);
    }
}
