package net.maploop.items.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerInteractAtEntityListener implements Listener {
    @EventHandler
    public void onInteractWithEntity(PlayerArmorStandManipulateEvent event) {
        if (!(event.getRightClicked().isCustomNameVisible())) return;
        event.setCancelled(true);
    }
}
