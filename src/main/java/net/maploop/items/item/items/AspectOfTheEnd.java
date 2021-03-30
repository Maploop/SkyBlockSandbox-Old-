package net.maploop.items.item.items;

import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemAbility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.util.IUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AspectOfTheEnd extends CustomItem {
    public AspectOfTheEnd(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, boolean glowing) {
        super(id, rarity, name, material, durability, stackable, oneTimeUse, hasActive, abilities, manaCost, reforgeable, itemType, glowing);
    }
    @Override
    public void onItemStackCreate(ItemStack paramItemStack) {

    }

    @Override    public void getSpecificLorePrefix(List<String> lore, ItemStack paramItemStack) {
    }

    @Override
    public void getSpecificLoreSuffix(List<String> paramList, ItemStack paramItemStack) {

    }

    @Override
    public void leftClickAirAction(Player paramPlayer, ItemStack paramItemStack) {

    }

    @Override
    public void leftClickBlockAction(Player paramPlayer, PlayerInteractEvent paramPlayerInteractEvent, Block paramBlock, ItemStack paramItemStack) {

    }

    @Override
    public void rightClickAirAction(Player player, PlayerInteractEvent event, ItemStack item) {
        Location l = player.getLocation().clone();
        Set<Material> TRANSPARENT = new HashSet<Material>();
        TRANSPARENT.add(Material.AIR);
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

        IUtil.sendActionText(player, "§b-50 Mana (§6Instant Transmission§b)");
        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        if (l.getPitch() < 0) {
            player.teleport(new Location(l.getWorld(), l.getX(), l.getY() - 1, l.getZ(), l.getYaw(), l.getPitch()));
            System.out.println("Winkel1");
        } else {
            player.teleport(new Location(l.getWorld(), l.getX(), l.getY() + 1.5F, l.getZ(), l.getYaw(), l.getPitch()));
        }
        if (ItemUtilities.getIntFromItem(item, "has_teleported") == 0) {
            player.setWalkSpeed(player.getWalkSpeed() + 0.05F);
            ItemUtilities.storeIntInItem(item, 1, "has_teleported");
            ItemUtilities.scheduleTask(new Runnable() {
                public void run() {
                    player.setWalkSpeed(player.getWalkSpeed() - 0.05F);
                    ItemUtilities.storeIntInItem(item, 0, "has_teleported");
                }
            }, 60);
        }
        onItemUse(player, item);
    }

    @Override
    public void rightClickBlockAction(Player paramPlayer, PlayerInteractEvent paramPlayerInteractEvent, Block paramBlock, ItemStack paramItemStack) {
        rightClickAirAction(paramPlayer, paramPlayerInteractEvent, paramItemStack);
    }

    @Override
    public void shiftLeftClickAirAction(Player paramPlayer, ItemStack paramItemStack) {

    }

    @Override
    public void shiftLeftClickBlockAction(Player paramPlayer, PlayerInteractEvent paramPlayerInteractEvent, Block paramBlock, ItemStack paramItemStack) {

    }

    @Override
    public void shiftRightClickAirAction(Player paramPlayer, PlayerInteractEvent event, ItemStack paramItemStack) {

    }

    @Override
    public void shiftRightClickBlockAction(Player paramPlayer, PlayerInteractEvent paramPlayerInteractEvent, Block paramBlock, ItemStack paramItemStack) {

    }

    @Override
    public void middleClickAction(Player paramPlayer, ItemStack paramItemStack) {

    }

    @Override
    public void hitEntityAction(Player paramPlayer, EntityDamageByEntityEvent event, Entity paramEntity, ItemStack paramItemStack) { }

    @Override
    public void breakBlockAction(Player paramPlayer, BlockBreakEvent paramBlockBreakEvent, Block paramBlock, ItemStack paramItemStack) {

    }

    @Override
    public void clickedInInventoryAction(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent) {

    }

    @Override
    public void activeEffect(Player player, ItemStack paramItemStack) { }
}
