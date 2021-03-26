package net.maploop.items.listeners;

import net.citizensnpcs.api.npc.NPC;
import net.maploop.items.data.DataHandler;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.gui.PlayerProfileGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        //Player player = event.getPlayer();
        //if(!(event.getRightClicked() instanceof Player)) return;
        //if(!(event.getRightClicked() instanceof NPC)) return;
        //Player clicked = (Player) event.getRightClicked();
        //DataHandler handler = new DataHandler(player);
        //if(!(handler.getProfileViewer())) return;
        //new PlayerProfileGUI(new PlayerMenuUtility(player), clicked).open();
    }
}
