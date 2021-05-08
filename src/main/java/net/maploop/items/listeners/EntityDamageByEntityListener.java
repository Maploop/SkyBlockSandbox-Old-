package net.maploop.items.listeners;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.ender.sbx.Elections.data.DataHandler;
import net.maploop.items.Items;
import net.maploop.items.event.PlayerCustomDeathEvent;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.sql.SQLGetter;
import net.maploop.items.user.User;
import net.maploop.items.util.Attribute;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
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
            if (event.getEntity().hasMetadata("NPC")) {
                NPC npc = CitizensAPI.getNPCRegistry().getNPC(event.getEntity());
                if(npc.getName().equals("§6﴾§cEnder§6﴿")){
                    DataHandler playerGetter = new DataHandler(player);
                    event.setDamage(playerGetter.getVoteCount() * 1000);
                } else {
                    // Calculations
                    double d = 0;
                    d += (5 + 50 + (user.getTotalStrength() / 5)) * (1 + user.getTotalStrength() / 100);
                    d += 1 + (1 * 0.04);
                    d += (d * 1 * (1 + user.getCrit_damage() / 100));
                    event.setDamage(d);
                    EntityDamageListener l = new EntityDamageListener();
                    l.damage = d;
                }
            } else {
                // Calculations
                double d = 0;
                d += (5 + 50 + (user.getTotalStrength() / 5)) * (1 + user.getTotalStrength() / 100);
                d += 1 + (1 * 0.04);
                d += (d * 1 * (1 + user.getCrit_damage() / 100));
                event.setDamage(d);
                EntityDamageListener l = new EntityDamageListener();
                l.damage = d;
            }



        }
    }
}
