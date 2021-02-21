package net.maploop.items.listeners;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class PlayerFishListener implements Listener {
    private HashMap<String, Long> cooldown = new HashMap<>();
    public static HashMap<String, Boolean> inAir = new HashMap<>();

    @EventHandler
    private void onFish(PlayerFishEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();
        if (event.getState() == PlayerFishEvent.State.FAILED_ATTEMPT || event.getState() == PlayerFishEvent.State.IN_GROUND) {
            if (!(item.hasItemMeta())) return;
            if (!(item.getItemMeta().hasDisplayName())) return;
            if (item.getItemMeta().getDisplayName().contains("§aGrappling Hook")) {
                if(cooldown.containsKey(player.getName())){
                    if(cooldown.get(player.getName()) > System.currentTimeMillis()){
                        player.sendMessage("§cPlease wait before doing that!");
                        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 2F, 9);
                        return;
                    }
                }
                cooldown.put(player.getName(), System.currentTimeMillis() + (2 * 1000));
                inAir.put(player.getName(), true);
                Location loc1 = player.getLocation();
                Location loc2 = event.getHook().getLocation();

                Vector v = new Vector(loc2.getX() - loc1.getX(), 1, loc2.getZ() - loc1.getZ());
                try{
                    player.setVelocity(v);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
