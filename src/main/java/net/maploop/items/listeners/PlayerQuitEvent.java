package net.maploop.items.listeners;

import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitEvent implements Listener {
    @EventHandler
    public void onQuit(org.bukkit.event.player.PlayerQuitEvent event) {
        Player player = event.getPlayer();

        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(PlayerJoinListener.bossBar.get(player).getId());
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);

        PlayerJoinListener.bossBar.remove(player);
    }
}
