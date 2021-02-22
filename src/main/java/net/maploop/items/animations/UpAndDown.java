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
    int limit = 40;
    @Override
    public void run() {
        theta += Math.PI / 4;
        loc = stand.getLocation();
        newloc = loc.clone().add(0, Math.sin(theta), 0);

        stand.teleport(newloc);
    }
    /*
        for (int i = 0; i > limit; i++) {
            if (i < 20) {
                stand.teleport(stand.getLocation().clone().add(0, 0.2, 0));
                return;
            }
            stand.teleport(stand.getLocation().clone().add(0, -0.2, 0));

            if (i == 40) {
                i = 0;
            }
        }
     */
}
