package net.maploop.items.animations;

import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

public class Spin extends BukkitRunnable {
    ArmorStand stand;
    public Spin(ArmorStand stand) {
        this.stand = stand;
    }

    @Override
    public void run() {
        EulerAngle oldRot = stand.getHeadPose();
        EulerAngle newRot = oldRot.add(0, 0.1f, 0);
        stand.setHeadPose(newRot);
    }
}
