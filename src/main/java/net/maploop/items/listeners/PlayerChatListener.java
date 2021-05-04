package net.maploop.items.listeners;

import net.maploop.items.Items;
import net.maploop.items.gui.ItemsGUI;
import net.maploop.items.gui.MinecraftItemsGUI;
import net.maploop.items.gui.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChatListener implements Listener {
    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        if(Items.getInstance().getShuts().getStringList("list").contains(player.getUniqueId().toString())) {
            event.setCancelled(true);
            player.sendMessage("§cYou cannot talk because you are currently shut-upped!");
        }
    }
}
