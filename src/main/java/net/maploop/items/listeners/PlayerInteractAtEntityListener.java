package net.maploop.items.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractAtEntityListener implements Listener {
    @EventHandler
    public void onInteractWithEntity(PlayerArmorStandManipulateEvent event) {
        if (event.getRightClicked().getEquipment().getItemInHand().getType().equals(Material.BONE)) {
            event.setCancelled(true);
            return;
        }
        if (!(event.getRightClicked().isCustomNameVisible())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void armorStandBonemerangBypass(PlayerInteractAtEntityEvent e) {
        if (!e.getRightClicked().getType().equals(EntityType.ARMOR_STAND)) return;
        ArmorStand stand = (ArmorStand) e.getRightClicked();
        if (!stand.getEquipment().getItemInHand().getType().equals(Material.BONE)) return;

        Bukkit.getServer().getPluginManager().callEvent(new PlayerInteractEvent(e.getPlayer(), Action.RIGHT_CLICK_AIR, e.getPlayer().getItemInHand(), null, BlockFace.DOWN));
    }
}
