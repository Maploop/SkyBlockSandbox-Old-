package net.maploop.items.animations;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;

import java.util.HashMap;
import java.util.UUID;

public class ParticleData {
    public static HashMap<ArmorStand, Integer> particle = new HashMap<>();
    private final ArmorStand stand;

    public ParticleData(ArmorStand stand) {
        this.stand = stand;
    }

    public void setId(int id) {
        particle.put(stand, id);
    }

    public int getId() {
        return 2;
    }

    public boolean hasId() {
        if (particle.containsKey(stand))
            return true;
        return false;
    }

    public void removeId() {
        particle.remove(stand);
    }

    public void endTask() {
        if (getId() == 1) return;
        Bukkit.getScheduler().cancelTask(2);
    }

    public static boolean hasFakeId(ArmorStand stand) {
        if (particle.containsKey(stand)) {
            if (particle.get(stand) == 1) {
                return true;
            }
        }
        return false;
    }
}
