package net.maploop.items.listeners;

import net.maploop.items.helpers.Utilities;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;


public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        if (!(item.hasItemMeta())) return;
        if (!(item.getItemMeta().hasDisplayName())) return;
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (item.getItemMeta().getDisplayName().contains("§6Hyperion")) {
                if (Utilities.getBlockOfSight(player, 8).getType() == Material.AIR) {
                    Location loc = player.getLocation();
                    Vector direction = player.getLocation().getDirection();
                    direction.normalize();
                    direction.multiply(8);
                    loc.add(direction);

                    player.teleport(loc);
                    player.getWorld().playEffect(player.getLocation(), Effect.EXPLOSION_LARGE, 10);
                    player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 10F, 2);
                    Utilities.sendActionbar(player, "§b-200 Mana (§6Hyperion Ability§b)");
                } else {
                    Location loc = Utilities.getBlockOfSight(player, 8).getLocation();
                    Location finalloc = loc.add(0, 1, 0);
                    player.teleport(new Location(player.getWorld(), finalloc.getX(), finalloc.getY(), finalloc.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
                    player.getWorld().playEffect(player.getLocation(), Effect.EXPLOSION_LARGE, 10);
                    player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 10F, 2);
                    Utilities.sendActionbar(player, "§b-200 Mana (§6Hyperion Ability§b)");
                }
            } else {
                return;
            }
        }
    }
}
