package net.maploop.items.animations;

import net.maploop.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

public class Effects {
    private int taskId;
    private final ArmorStand stand;

    public Effects(ArmorStand stand) {
        this.stand = stand;
    }

    public void startRadiant() {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Runnable() {
            double var = 0;
            Location loc, first, second;
            ParticleData partile = new ParticleData(stand);

            @Override
            public void run() {
                if (!(partile.hasId())) {
                    partile.setId(taskId);
                }

                var += Math.PI / 16;
                loc = stand.getLocation();
                first = loc.clone().add(Math.cos(var), +2, Math.sin(var));
                second = loc.clone().add(Math.cos(var + Math.PI), +2, Math.sin(var + Math.PI)); // Math.sin(var)

                stand.getWorld().playEffect(first, Effect.HAPPY_VILLAGER, 20);
                stand.getWorld().playEffect(second, Effect.HAPPY_VILLAGER, 20);
            }
        }, 0L, 1L);
    }
}
