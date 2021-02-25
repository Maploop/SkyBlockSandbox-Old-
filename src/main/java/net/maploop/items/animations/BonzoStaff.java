package net.maploop.items.animations;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.*;
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
        for (Entity e : stand.getNearbyEntities(1, 1, 1)) {
            if (e != null) {
                LivingEntity e1 = null;
                if (!(e instanceof Item)) e1 = (LivingEntity) e;

                if (!(e instanceof Player)) {
                    if (!e.isDead()) {
                        if (!(e1 == null)) {
                            e1.damage(5000);
                            stand.remove();
                            player.getWorld().playSound(e1.getLocation(), Sound.FIREWORK_BLAST, 1f, 1f);
                            player.getWorld().playEffect(e1.getLocation(), Effect.FIREWORKS_SPARK, 10);
                        }
                    }
                }
            }
        }

        Vector vecTo = player.getLocation().getDirection().normalize().multiply(1);

        stand.teleport(stand.getLocation().clone().add(vecTo));
    }
}
