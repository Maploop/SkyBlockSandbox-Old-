package net.maploop.items.animations;

import net.maploop.items.helpers.Utilities;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;

public class GreenParticle extends BukkitRunnable {
    private final ArmorStand stand;

    public GreenParticle(ArmorStand stand) {
        this.stand = stand;
    }

    Location loc, first, second;
    double var = 0;
    @Override
    public void run() {
        loc = stand.getLocation();

        var += Math.PI / 16;
        loc = stand.getLocation();
        first = loc.clone().add(Math.cos(var), Math.cos(var) + 2, Math.sin(var));
        second = loc.clone().add(Math.cos(var + Math.PI), Math.cos(var) + 2, Math.sin(var + Math.PI)); // Math.sin(var)

        stand.getWorld().playEffect(first, Effect.HAPPY_VILLAGER, 20);
        stand.getWorld().playEffect(second, Effect.HAPPY_VILLAGER, 20);
    }
}
