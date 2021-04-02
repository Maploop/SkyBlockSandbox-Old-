package net.maploop.items.listeners;

import net.citizensnpcs.api.npc.NPC;
import net.maploop.items.Items;
import net.maploop.items.data.DataHandler;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.gui.PlayerProfileGUI;
import net.maploop.items.gui.SBAnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(event.getClickedBlock().getType().equals(Material.ANVIL)) {
                if(event.getClickedBlock().hasMetadata("reforge_anvil")) {
                    // Do something else
                } else {
                    event.setCancelled(true);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            new SBAnvilGUI(new PlayerMenuUtility(event.getPlayer())).open();
                        }
                    }.runTaskLater(Items.getInstance(), 2);
                }
            }
        }
    }
}
