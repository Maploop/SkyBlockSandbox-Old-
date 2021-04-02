package net.maploop.items.listeners;

import net.maploop.items.Items;
import net.maploop.items.data.DataHandler;
import net.maploop.items.event.PlayerCustomDeathEvent;
import net.maploop.items.sql.SQLGetter;
import net.maploop.items.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {
    @EventHandler
    public void onDMG(EntityDamageByEntityEvent event) {
    }
}
