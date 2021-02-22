package net.maploop.items.animations;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Spin extends BukkitRunnable {
    ArmorStand stand;
    public Spin(ArmorStand stand) {
        this.stand = stand;
    }
    int amount = 40;
    Location location;

    @Override
    public void run() {
        location = stand.getLocation();
        location.setYaw(stand.getLocation().getYaw() + 15.0f);
        stand.teleport(location);
    }
}
