package net.maploop.items.listeners;

import net.maploop.items.Items;
import net.maploop.items.event.PlayerCustomDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerCustomDeathListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerCustomDeathEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("§c☠ §7You died!");
        event.getUser().playSound("random.anvil.land", 1, 2);
        event.getUser().setHealth(event.getUser().getTotalHealth());

        Items plugin = Items.getInstance();
        double x = plugin.getData().getDouble("data.locations.lobbyspawn.x");
        double y = plugin.getData().getDouble("data.locations.lobbyspawn.y");
        double z = plugin.getData().getDouble("data.locations.lobbyspawn.z");
        String world = plugin.getData().getString("data.locations.lobbyspawn.world");
        float yaw = plugin.getData().getInt("data.locations.lobbyspawn.yaw");
        float pitch = plugin.getData().getInt("data.locations.lobbyspawn.pitch");

        Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
        player.teleport(loc);
    }

}
