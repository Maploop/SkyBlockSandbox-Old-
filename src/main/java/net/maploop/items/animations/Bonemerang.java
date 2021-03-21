package net.maploop.items.animations;

import net.maploop.items.Items;
import net.maploop.items.helpers.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

public class Bonemerang implements Runnable {
    private ArmorStand bone;
    private Player player;
    private Location loca;
    public Bonemerang(ArmorStand bone, Player player, Location loca) {
        this.bone = bone;
        this.player = player;
        this.loca = loca;
    }
    @Override
    public void run() {
        Location loc = bone.getLocation();
        Vector direction = loca.getDirection();

        loc.setYaw(bone.getLocation().getYaw() + 60);
        loc.add(direction);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Items.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Entity e : bone.getNearbyEntities(1, 1, 1)) {
                    LivingEntity e1 = null;
                    if (!(e instanceof Item)) e1 = (LivingEntity) e;

                    if (!(e instanceof Player)) {
                        double dmg = Utilities.getRandomInteger(20000);
                        if (e1 != null) {
                            e1.damage(dmg);
                        };
                    }
                }

                bone.teleport(loc);

                loc.setYaw(bone.getLocation().getYaw() + 60);
                loc.subtract(direction);
            }
        }, 2L);
    }
}
