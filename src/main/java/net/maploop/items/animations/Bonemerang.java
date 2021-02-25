package net.maploop.items.animations;

import net.maploop.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Bonemerang implements Runnable {
    private ArmorStand bone;
    private Player player;
    public Bonemerang(ArmorStand bone, Player player) {
        this.bone = bone;
        this.player = player;
    }
    Location loc, newloc;
    @Override
    public void run() {
        Location loc = bone.getLocation();
        Vector direction = player.getLocation().getDirection();

        loc.setYaw(bone.getLocation().getYaw() + 60);
        loc.add(direction);

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
