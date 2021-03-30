package net.maploop.items.item.items;

import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemAbility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.util.IUtil;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Treecapitator extends CustomItem {
    public Treecapitator(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, boolean glowing) {
        super(id, rarity, name, material, durability, stackable, oneTimeUse, hasActive, abilities, manaCost, reforgeable, itemType, glowing);
    }

    @Override
    public void onItemStackCreate(ItemStack paramItemStack) {

    }

    @Override
    public void getSpecificLorePrefix(List<String> lore, ItemStack paramItemStack) {
        lore.add("§7A forceful Gold Axe which can");
        lore.add("§7break a larg amount of logs in");
        lore.add("§7a single hit!");
        lore.add("§8Cooldown: §a2s");
    }

    @Override
    public void getSpecificLoreSuffix(List<String> paramList, ItemStack paramItemStack) {

    }

    @Override
    public void leftClickAirAction(Player player, ItemStack item) {

    }

    @Override
    public void leftClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item) {

    }

    @Override
    public void rightClickAirAction(Player player, PlayerInteractEvent event, ItemStack item) {

    }

    @Override
    public void rightClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item) {

    }

    @Override
    public void shiftLeftClickAirAction(Player player, ItemStack item) {

    }

    @Override
    public void shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item) {

    }

    @Override
    public void shiftRightClickAirAction(Player player, PlayerInteractEvent event, ItemStack item) {

    }

    @Override
    public void shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item) {

    }

    @Override
    public void middleClickAction(Player player, ItemStack item) {

    }

    @Override
    public void hitEntityAction(Player player, EntityDamageByEntityEvent event, Entity paramEntity, ItemStack item) {

    }

    @Override
    public void breakBlockAction(Player player, BlockBreakEvent event, Block block, ItemStack item) {
        if(!(ItemUtilities.enforceCooldown(player, "treecapitator_break", 2d, item, false))) {
            return;
        }
        if (block.getType().name().contains("LOG") || block.getType().name().contains("LEAVES"))
            doBlockEater(player, block, 15);
    }

    @Override
    public void clickedInInventoryAction(Player player, InventoryClickEvent event) {

    }

    @Override
    public void activeEffect(Player player, ItemStack item) {

    }

    public void doBlockEater(Player player, Block startingBlock, int amount) {
        if (startingBlock.getType() == Material.AIR)
            return;
        Material targetMaterial = startingBlock.getType();
        ArrayList<Block> blocksToCheck = new ArrayList<>();
        blocksToCheck.add(startingBlock);
        for (int i = 0; i <= amount; ++i) {
            IUtil.scheduleTask(() -> {
                ArrayList<Block> preClonedList = new ArrayList<>(blocksToCheck);
                for (Block block : preClonedList) {
                    blocksToCheck.remove(block);
                    Block upperBlock = block.getRelative(BlockFace.UP);
                    Block lowerBlock = block.getRelative(BlockFace.DOWN);
                    Block northBlock = block.getRelative(BlockFace.NORTH);
                    Block eastBlock = block.getRelative(BlockFace.EAST);
                    Block southBlock = block.getRelative(BlockFace.SOUTH);
                    Block westBlock = block.getRelative(BlockFace.WEST);
                    for (Block nearbyBlock : (new Block[] { upperBlock, lowerBlock, northBlock, eastBlock, southBlock, westBlock })) {
                        if (nearbyBlock.getType() == targetMaterial) {
                            if (nearbyBlock.getType().name().contains("LOG")) {
                                player.getInventory().addItem(new ItemStack[] { new ItemStack(targetMaterial, 1) });
                                nearbyBlock.getWorld().playSound(nearbyBlock.getLocation(), Sound.DIG_WOOD, 0.3F, 2.0F);
                            }
                            if (nearbyBlock.getType().name().contains("LEAVES")) {
                                Random rand = new Random();
                                int n = rand.nextInt(200);
                                if (n > 189)
                                    player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.SAPLING, 1) });
                                if (n < 6)
                                    player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.APPLE, 1) });
                                nearbyBlock.getWorld().playSound(nearbyBlock.getLocation(), Sound.DIG_GRASS, 0.3F, 2.0F);
                            }
                            nearbyBlock.setType(Material.AIR);
                            blocksToCheck.add(nearbyBlock);
                        }
                    }
                }
            }, i * 2);
        }
    }
}
