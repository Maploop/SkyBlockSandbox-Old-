package net.maploop.items.listeners;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {
    @EventHandler
    public void onDMG(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            User user = new User(player);
            // Calculations
            double d = 0;
            d += (5 + ItemUtilities.getIntFromItem(player.getItemInHand(), "DAMAGE") + (user.getTotalStrength() / 5)) * (1 + user.getTotalStrength() / 100);
            d += 1 + (1 * 0.04);
            d += (d * 1 * (1 + user.getCrit_damage() / 100));
            event.setDamage(d);
            EntityDamageListener l = new EntityDamageListener();
            l.damage = d;
        }
    }
}
