package net.maploop.items.listeners;

import net.maploop.items.Items;
import net.maploop.items.data.DataHandler;
import net.maploop.items.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {
    @EventHandler
    public void onDMG(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            if(event.getEntity().hasMetadata("NPC")) return;
            if(Items.getInstance().getConfig().getBoolean("mysql.use-mysql")) {
                Player player = (Player) event.getEntity();
                player.setHealth(player.getMaxHealth());
                SQLGetter handler = new SQLGetter(player, Items.getInstance());
                handler.setHealth(handler.getHealth() - event.getDamage());
            } else {
                Player player = (Player) event.getEntity();
                player.setHealth(player.getMaxHealth());
                DataHandler handler = new DataHandler(player);
                handler.removeHealth(event.getDamage());

                if(handler.getHealth() <= 0) {
                    player.setHealth(0);
                }
            }
        }
    }
}
