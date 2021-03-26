package net.maploop.items.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class PlayerArmorStandManipulateListener implements Listener {
    @EventHandler
    public void onManipulate(PlayerArmorStandManipulateEvent event) {
        event.setCancelled(true);
    }
}
