package net.maploop.items.handlers;

import net.maploop.items.Items;
import net.maploop.items.animations.UpAndDown;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PetsHandler {
    private final Player player;
    public Map<UUID, ArmorStand> equippedPet = new HashMap<>();

    public void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Runnable() {
            double theta = 0;
            ArmorStand stand;
            Location loc, newloc;
            int animate;
            @Override
            public void run() {
                if (equippedPet.containsKey(player.getUniqueId())) {
                    theta += Math.PI / 4;
                    stand = equippedPet.get(player.getUniqueId());

                    stand.teleport(player.getLocation().add(1, 0, 1));
                    animate = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new UpAndDown(stand), 0L, 10L);
                } else {
                    Bukkit.getScheduler().cancelTask(animate);
                }
            }
        }, 0L, 1L);
    }

    public PetsHandler(Player player) {
        this.player = player;
    }
}
