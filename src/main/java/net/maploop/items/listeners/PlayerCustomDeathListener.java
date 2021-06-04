package net.maploop.items.listeners;

import net.maploop.items.Items;
import net.maploop.items.event.PlayerCustomDeathEvent;
import net.maploop.items.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerCustomDeathListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerCustomDeathEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("§c☠ §7You died!");
        player.playSound(player.getLocation(), Sound.ANVIL_LAND,2,1);
        event.getUser().setHealth(event.getUser().getTotalHealth());

        Items plugin = Items.getInstance();
        double x = plugin.getConfig().getDouble("locations.lobbyspawn.x");
        double y = plugin.getConfig().getDouble("locations.lobbyspawn.y");
        double z = plugin.getConfig().getDouble("locations.lobbyspawn.z");
        String world = plugin.getConfig().getString("locations.lobbyspawn.world");
        float yaw = plugin.getConfig().getInt("locations.lobbyspawn.yaw");
        float pitch = plugin.getConfig().getInt("locations.lobbyspawn.pitch");

        Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
        player.teleport(loc);
        User user = new User(player);
        user.setHealth(user.getTotalHealth());
    }

}
