package net.maploop.items.listeners;

import net.maploop.items.Items;
import net.maploop.items.api.SignGUIUpdateEvent;
import net.maploop.items.gui.ItemsGUI;
import net.maploop.items.gui.PlayerMenuUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class SignGUIUpdateListener implements Listener {
    @EventHandler
    public void onUpdate(SignGUIUpdateEvent event) {
        Player player = event.getPlayer();
        if(ItemsGUI.searching.contains(player)) {
            ItemsGUI.search.put(player, event.getSignText()[0]);

            new BukkitRunnable() {
                @Override
                public void run() {
                    new ItemsGUI(new PlayerMenuUtility(player)).open();
                    ItemsGUI.searching.remove(player);
                }
            }.runTaskLater(Items.getInstance(), 2);
        }
    }
}
