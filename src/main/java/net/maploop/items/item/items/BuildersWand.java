package net.maploop.items.item.items;

import net.maploop.items.Items;
import net.maploop.items.data.DataHandler;
import net.maploop.items.enums.ItemStats;
import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemAbility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.listeners.EntityDamageListener;
import net.maploop.items.sql.SQLGetter;
import net.maploop.items.util.DUtil;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.*;

public class BuildersWand extends CustomItem {
    public BuildersWand(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, boolean glowing) {
        super(id, rarity, name, material, durability, stackable, oneTimeUse, hasActive, abilities, manaCost, reforgeable, itemType, glowing);
    }

    @Override
    public void onItemStackCreate(ItemStack paramItemStack) {

    }

    @Override
    public void getSpecificLorePrefix(List<String> paramList, ItemStack paramItemStack) {

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
    public void rightClickAirAction(Player paramPlayer, PlayerInteractEvent event, ItemStack paramItemStack) {

    }

    @Override
    public void rightClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack paramItemStack) {
        if(player.hasPermission("dungeons.builderswand.use")) {
            fillConnectedFaces(player, paramBlock, event.getBlockFace(), paramItemStack);
            onItemUse(player, paramItemStack);
        } else {
            player.playSound(player.getLocation(), Sound.WOOD_CLICK, 1f, 1f);
            player.sendMessage(ChatColor.RED + "You do not have the required permission to use this item!");
        }
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
    public void hitEntityAction(Player paramPlayer, EntityDamageByEntityEvent paramEntityDamageByEntityEvent, Entity paramEntity, ItemStack paramItemStack) {

    }

    @Override
    public void breakBlockAction(Player paramPlayer, BlockBreakEvent paramBlockBreakEvent, Block paramBlock, ItemStack paramItemStack) {

    }

    @Override
    public void clickedInInventoryAction(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent) {

    }

    @Override
    public void activeEffect(Player paramPlayer, ItemStack paramItemStack) {

    }

    public void fillConnectedFaces(Player player, Block origin, BlockFace face, ItemStack item) {
        Material fillMaterial = origin.getType();
        int blocksInInventory = countBlocks(player.getInventory(), origin.getType());
        int blockLimit = 164;
        if (blocksInInventory < blockLimit)
            blockLimit = blocksInInventory;
        List<Block> blocks = new ArrayList<>();
        material.put(player, origin.getType());
        blocks.add(origin);
        World w = player.getWorld();
        Vector check[] = null, translate = null;
        int blocksPlaced = 0;
        switch (face) {
            case NORTH:
            case SOUTH:
                check = new Vector[] { new Vector(-1, -1, 0), new Vector(-1, 0, 0), new Vector(-1, 1, 0), new Vector(0, -1, 0), new Vector(0, 1, 0), new Vector(1, -1, 0), new Vector(1, 0, 0), new Vector(1, 1, 0) };
                break;
            case EAST:
            case WEST:
                check = new Vector[] { new Vector(0, -1, -1), new Vector(0, -1, 0), new Vector(0, -1, 1), new Vector(0, 0, -1), new Vector(0, 0, 1), new Vector(0, 1, -1), new Vector(0, 1, 0), new Vector(0, 1, 1) };
                break;
            case UP:
            case DOWN:
                check = new Vector[] { new Vector(-1, 0, -1), new Vector(-1, 0, 0), new Vector(-1, 0, 1), new Vector(0, 0, -1), new Vector(0, 0, 1), new Vector(1, 0, -1), new Vector(1, 0, 0), new Vector(1, 0, 1) };
                break;
        }
        switch (face) {
            case NORTH:
                translate = new Vector(0, 0, -1);
                break;
            case SOUTH:
                translate = new Vector(0, 0, 1);
                break;
            case EAST:
                translate = new Vector(1, 0, 0);
                break;
            case WEST:
                translate = new Vector(-1, 0, 0);
                break;
            case UP:
                translate = new Vector(0, 1, 0);
                break;
            case DOWN:
                translate = new Vector(0, -1, 0);
                break;
        }
        while (blocks.size() > 0 && blockLimit > 0) {
            Location l = (blocks.get(0)).getLocation();
            for (Vector vector : check) {
                if (w.getBlockAt(l.clone().add(vector)).getType() == fillMaterial && w
                        .getBlockAt(l.clone().add(vector).clone().add(translate)).getType() == Material.AIR)
                    blocks.add(w.getBlockAt(l.clone().add(vector)));
            }
            Block fillBlock = w.getBlockAt(l.clone().add(translate));
            if (canPlaceBlock(player, fillBlock.getLocation())) {
                blocks.removeIf(blocks.get(0)::equals);
                if (fillBlock.getType() != fillMaterial) {
                    fillBlock.setType(fillMaterial);
                    blockLimit--;
                    blocksPlaced++;
                    blockAmount.put(player,blocksPlaced);
                }
                if (blocksPlaced == blockLimit)
                    break;
                continue;
            }
            blocks.removeIf(blocks.get(0)::equals);
            blockLimit--;
            blocks1.put(player, blocks);
        }
        if (blocksPlaced != 0) {
            player.getInventory().remove(new ItemStack(origin.getType(), blocksPlaced));

            TextComponent component = new TextComponent("§eYou built §a" + blocksPlaced + "§e blocks! ");
            TextComponent component1 = new TextComponent("§a§lUNDO");
            component1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("This feature is coming soon!").color(net.md_5.bungee.api.ChatColor.RED).create()));

            player.spigot().sendMessage(component, component1);
            player.playSound(player.getLocation(), Sound.EXPLODE, 1.0F, 2.0F);
        }
    }

    public int countBlocks(Inventory inv, Material m) {
        int blockAmount = 0;
        for (ListIterator<ItemStack> listIterator = inv.iterator(); listIterator.hasNext(); ) {
            ItemStack item = listIterator.next();
            if (item != null &&
                    item.getType() == m)
                blockAmount += item.getAmount();
        }
        return blockAmount;
    }

    public boolean canPlaceBlock(Player player, Location l) {
        return true;
    }

    public static Map<Player, Material> material = new HashMap<>();
    public static Map<Player, List<Block>> blocks1 = new HashMap<>();
    public static Map<Player, Integer> blockAmount = new HashMap<>();
    public static int i1 = 0;

    public static void undoBuild(Player player) {
        try {
            for(Block block : blocks1.get(player)) {
                block.setType(Material.AIR);
            }
        } catch (Exception e) {
            player.sendMessage("§cAn error occurred while trying to undo your zap. Please contact an administrator to get this issue possibly fixed.");
        }
    }
}
