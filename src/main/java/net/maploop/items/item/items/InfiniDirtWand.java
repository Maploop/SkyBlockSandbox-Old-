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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public class InfiniDirtWand extends CustomItem {
    public InfiniDirtWand(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, boolean glowing) {
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
    public void rightClickAirAction(Player player, PlayerInteractEvent event, ItemStack paramItemStack) {
        Block block = player.getTargetBlock((Set<Material>) null, 30);
        if(block.getType() == Material.AIR) return;
        Location loc = null;
        switch (getBlockFace(player)) {
            case UP:
                loc = block.getLocation().add(0, 1, 0);
                break;
            case DOWN:
                loc = block.getLocation().add(0, -1, 0);
                break;
            case NORTH:
                loc = block.getLocation().add(0, 0, -1);
                break;
            case SOUTH:
                loc = block.getLocation().add(0, 0, 1);
                break;
            case EAST:
                loc = block.getLocation().add(1, 0, 0);
                break;
            case WEST:
                loc = block.getLocation().add(-1, 0, 0);
                break;
        }
        assert loc != null;
        Block newBlock = loc.getBlock();

        newBlock.setType(Material.DIRT);
        player.playSound(player.getLocation(), Sound.DIG_GRASS, 1, 1f);
    }

    @Override
    public void rightClickBlockAction(Player player, PlayerInteractEvent paramPlayerInteractEvent, Block paramBlock, ItemStack paramItemStack) {
        Block block = player.getTargetBlock((Set<Material>) null, 30);
        if(block.getType() == Material.AIR) return;
        Location loc = null;
        switch (getBlockFace(player)) {
            case UP:
                loc = block.getLocation().add(0, 1, 0);
                break;
            case DOWN:
                loc = block.getLocation().add(0, -1, 0);
                break;
            case NORTH:
                loc = block.getLocation().add(0, 0, -1);
                break;
            case SOUTH:
                loc = block.getLocation().add(0, 0, 1);
                break;
            case EAST:
                loc = block.getLocation().add(1, 0, 0);
                break;
            case WEST:
                loc = block.getLocation().add(-1, 0, 0);
                break;
        }
        assert loc != null;
        Block newBlock = loc.getBlock();

        newBlock.setType(Material.DIRT);
        player.playSound(player.getLocation(), Sound.DIG_GRASS, 1, 1f);
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

    private BlockFace getBlockFace(Player player) {
        List<Block> lastTwoTargetBlocks = player.getLastTwoTargetBlocks((Set<Material>) null, 100);
        if (lastTwoTargetBlocks.size() != 2 || !lastTwoTargetBlocks.get(1).getType().isOccluding()) return null;
        Block targetBlock = lastTwoTargetBlocks.get(1);
        Block adjacentBlock = lastTwoTargetBlocks.get(0);
        return targetBlock.getFace(adjacentBlock);
    }
}
