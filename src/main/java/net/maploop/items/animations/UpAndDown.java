package net.maploop.items.animations;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class UpAndDown extends BukkitRunnable {
    private final ArmorStand stand;
    public UpAndDown(ArmorStand stand) {
        this.stand = stand;
    }

    double theta = 0;
    Location loc, newloc;
    @Override
    public void run() {
        theta += Math.PI / 4;
        loc = stand.getLocation();
        newloc = loc.clone().add(0, Math.sin(theta), 0);

        stand.teleport(newloc);
    }
}
