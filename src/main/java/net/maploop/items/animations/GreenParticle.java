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
    float r = 1;
    float g = 10;
    float b = 1;
    @Override
    public void run() {
        var += Math.PI / 16;
        loc = stand.getLocation();
        first = loc.clone().add(Math.cos(var), +1, Math.sin(var));
        second = loc.clone().add(Math.cos(var + Math.PI), +1, Math.sin(var + Math.PI)); // Math.sin(var)

        stand.getWorld().spigot().playEffect(first, Effect.HAPPY_VILLAGER, 0, 0, r, g, b, 1, 0, 100);
        stand.getWorld().spigot().playEffect(second, Effect.HAPPY_VILLAGER, 0, 0, r, g, b, 1, 0, 100);
    }
}
