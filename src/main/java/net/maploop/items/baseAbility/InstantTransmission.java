package net.maploop.items.baseAbility;

import net.maploop.items.item.ItemUtilities;
import net.maploop.items.user.User;
import net.maploop.items.util.IUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;

public class InstantTransmission extends BaseAbility {
    private final Map<UUID, Long> cooldownMap = new HashMap<>();

    public InstantTransmission(Player player, int damage, int cooldown, int manaCost) {
        super(player, damage, cooldown, manaCost);
    }

    @Override
    public void run() {
        if(cooldownMap.containsKey(player.getUniqueId())) {
            if(cooldownMap.get(player.getUniqueId()) > System.currentTimeMillis()) {
                player.sendMessage("§cYou are on cooldown!");
                return;
            }
        }
        cooldownMap.put(player.getUniqueId(), System.currentTimeMillis() + (this.cooldown * 1000L));

        if(new User(player).getIntelligence() < manaCost) {
            player.sendMessage("§cYou do not have enough mana to do that.");
            return;
        }
        new User(player).setIntelligence(new User(player).getIntelligence() - manaCost);

        Location l = player.getLocation().clone();
        Set<Material> TRANSPARENT = new HashSet<Material>();
        TRANSPARENT.add(Material.AIR);
        TRANSPARENT.add(Material.STATIONARY_WATER);
        Location looking = player.getTargetBlock(TRANSPARENT, 120).getLocation();
        if (looking.distance(l) < 8) {
            switch ((int) looking.distance(l)) {
                case 8:
                    l.add(player.getEyeLocation().getDirection().multiply(7));
                    player.sendMessage(ChatColor.RED + "There are blocks in the way!");
                    break;
                case 7:
                    l.add(player.getEyeLocation().getDirection().multiply(6));
                    player.sendMessage(ChatColor.RED + "There are blocks in the way!");
                    break;
                case 6:
                    l.add(player.getEyeLocation().getDirection().multiply(5));
                    player.sendMessage(ChatColor.RED + "There are blocks in the way!");
                    break;
                case 5:
                    l.add(player.getEyeLocation().getDirection().multiply(4));
                    player.sendMessage(ChatColor.RED + "There are Blocks in the way!");
                    break;
                case 4:
                    l.add(player.getEyeLocation().getDirection().multiply(3));
                    player.sendMessage(ChatColor.RED + "There are blocks in the way!");
                    break;
                case 3:
                    l.add(player.getEyeLocation().getDirection().multiply(2));
                    player.sendMessage(ChatColor.RED + "There are blocks in the way!");
                    break;
                case 2:
                    l.add(player.getEyeLocation().getDirection().multiply(1));
                    player.sendMessage(ChatColor.RED + "There are blocks in the way!");
                    break;
                case 1:
                case 0:
                    player.sendMessage(ChatColor.RED + "There are blocks in the way!");
                    return;

            }
        } else
            l.add(player.getEyeLocation().getDirection().multiply(8));

        IUtil.sendActionText(player, "§b-" + manaCost + " Mana (§6Instant Transmission§b)");
        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        if (l.getPitch() < 0) {
            player.teleport(new Location(l.getWorld(), l.getX(), l.getY() - 1, l.getZ(), l.getYaw(), l.getPitch()));
        } else {
            player.teleport(new Location(l.getWorld(), l.getX(), l.getY() + 1.5F, l.getZ(), l.getYaw(), l.getPitch()));
        }
        if (ItemUtilities.getIntFromItem(player.getItemInHand(), "has_teleported") == 0) {
            player.setWalkSpeed(player.getWalkSpeed() + 0.05F);
            ItemUtilities.storeIntInItem(player.getItemInHand(), 1, "has_teleported");
            ItemUtilities.scheduleTask(new Runnable() {
                public void run() {
                    player.setWalkSpeed(player.getWalkSpeed() - 0.05F);
                    ItemUtilities.storeIntInItem(player.getItemInHand(), 0, "has_teleported");
                }
            }, 60);
        }
    }
}
