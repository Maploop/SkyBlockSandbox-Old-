package net.maploop.items.animations;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BonzoStaff extends BukkitRunnable {
    private ArmorStand stand;
    private Player player;
    public BonzoStaff(ArmorStand stand, Player player) {
        this.stand = stand;
        this.player = player;
    }
    Location loc, newloc;
    @Override
    public void run() {
        Vector vecTo = player.getLocation().getDirection().normalize().multiply(1);

        stand.teleport(stand.getLocation().clone().add(vecTo));
    }
}
