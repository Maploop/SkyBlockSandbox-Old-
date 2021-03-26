package net.maploop.items.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerLoseHungerListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onLoseHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
