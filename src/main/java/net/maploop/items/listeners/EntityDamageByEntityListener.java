package net.maploop.items.listeners;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.user.User;
import net.maploop.items.util.IUtil;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

public class EntityDamageByEntityListener implements Listener {
    public static boolean activatedcrithit = false;
    @EventHandler
    public void onDMG(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if(event.getEntity() instanceof Player) return;
            Player player = (Player) event.getDamager();
            User user = new User(player);
            // Calculations
            try {
                double d = 0;
                d += (5 + ItemUtilities.getIntFromItem(player.getItemInHand(), "DAMAGE") + (user.getTotalStrength() / 5)) * (1 + user.getTotalStrength() / 100);
                d += 1 + (1 * 0.04);
                Random rnd = new Random();
                int critchancernd = rnd.nextInt(100);
                if (critchancernd <= user.getTotalCritChance()) {
                    d *= (1 + user.getTotalCritDamage() / 100);
                    activatedcrithit = true;
                }
                event.setDamage(d);
                EntityDamageListener l = new EntityDamageListener();
                l.damage = d;
            } catch (NullPointerException e) {
                double d = 0;
                d += (5 + (user.getTotalStrength() / 5)) * (1 + user.getTotalStrength() / 100);
                d += 1 + (1 * 0.04);
                Random rnd = new Random();
                int critchancernd = rnd.nextInt(100);
                if (critchancernd <= user.getCrit_chance()) {
                    d *= (1 + user.getCrit_damage() / 100);
                    activatedcrithit = true;
                }
                event.setDamage(d);
                EntityDamageListener l = new EntityDamageListener();
                l.damage = d;
            }
        } else if (event.getEntity() instanceof Player) {
            double d = event.getDamage();
            Player player = (Player) event.getEntity();
            User user = new User(player);
            double reduction = user.getTotalDefense() / (user.getTotalDefense() + 100);
            double realDmg = d - (d * reduction);
            user.setHealth(user.getHealth() - realDmg);
            event.setDamage(0.1);
        } else {
            event.setDamage(event.getDamage());
        }
    }
}
