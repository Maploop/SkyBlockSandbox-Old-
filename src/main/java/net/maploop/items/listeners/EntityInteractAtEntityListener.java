package net.maploop.items.listeners;

import net.maploop.items.item.ItemUtilities;
import net.maploop.items.util.DUtil;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EntityInteractAtEntityListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        Bukkit.getServer().getPluginManager().callEvent(new PlayerInteractEvent(event.getPlayer(), Action.RIGHT_CLICK_AIR, event.getPlayer().getItemInHand(), ItemUtilities.getBlockLookingAt(event.getPlayer()), BlockFace.UP));
    }
}
