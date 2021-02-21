package net.maploop.items.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class FallDamageListener implements Listener {
    @EventHandler
    private void onFall(EntityDamageEvent event){
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();
        if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
            if(PlayerFishListener.inAir.containsKey(player.getName())){
                if(PlayerFishListener.inAir.get(player.getName())){
                    event.setCancelled(true);
                    PlayerFishListener.inAir.remove(player.getName());
                }
            }
        }
    }
}
