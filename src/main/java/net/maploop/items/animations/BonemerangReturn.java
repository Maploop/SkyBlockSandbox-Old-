package net.maploop.items.animations;

import net.maploop.items.Items;
import net.maploop.items.helpers.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

public class BonemerangReturn implements Runnable {
    private ArmorStand bone;
    private Player player;

    public BonemerangReturn(ArmorStand bone, Player player) {
        this.bone = bone;
        this.player = player;
    }

    @Override
    public void run() {
        for (Entity e : bone.getNearbyEntities(1, 1, 1)) {
            LivingEntity e1 = null;
            if (!(e instanceof Item)) e1 = (LivingEntity) e;
            
            if (!(e instanceof Player)) {
                double dmg = Utilities.getRandomInteger(20000);
                if (e1 != null) {
                    e1.damage(dmg);
                }
            }
        }
        Location loc = bone.getLocation();
        Vector direction = player.getLocation().getDirection();

        loc.setYaw(bone.getLocation().getYaw() + 60);
        loc.subtract(direction);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Items.getInstance(), new Runnable() {
            @Override
            public void run() {
                bone.teleport(loc);

                loc.setYaw(bone.getLocation().getYaw() + 60);
                loc.subtract(direction);
            }
        }, 2L);
    }
}
