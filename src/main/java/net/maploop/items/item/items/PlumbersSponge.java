package net.maploop.items.item.items;

import net.maploop.items.Items;
import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemAbility;
import org.bukkit.Bukkit;
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
import java.util.Arrays;
import java.util.List;

public class PlumbersSponge extends CustomItem {
    public PlumbersSponge(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, boolean glowing) {
        super(id, rarity, name, material, durability, stackable, oneTimeUse, hasActive, abilities, manaCost, reforgeable, itemType, glowing);
    }

    @Override
    public void onItemStackCreate(ItemStack paramItemStack) {

    }

    @Override
    public void getSpecificLorePrefix(List<String> lore, ItemStack paramItemStack) {
        lore.add("§7Instructions:");
        lore.add("§e1. §7Place on water.");
        lore.add("§e2. §7Drains other water.");
        lore.add("§e3. §7Double-bill client.");
        lore.add("§7");
        lore.add("§8Thanks Plumber Joe!");
        lore.add("§7");
        lore.add("§dNew! §eAlso works with lava!");
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
    public void rightClickBlockAction(Player paramPlayer, PlayerInteractEvent event, Block paramBlock, ItemStack paramItemStack) {
        if(!paramPlayer.hasPermission("items.plumberssponge.use")) {
            paramPlayer.sendMessage("§cYou are not allowed to use this item.");
            return;
        }

        event.setCancelled(true);
        eatBlocks(event.getClickedBlock(), 30);
        onItemUse(paramPlayer, paramItemStack);
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
        rightClickBlockAction(paramPlayer, paramPlayerInteractEvent, paramBlock, paramItemStack);
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

    public void eatBlocks(Block start, int amount) {
        List<Block> blockList = new ArrayList<>();
        blockList.add(start);
        for(int i = 0; i <= amount; ++i) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Items.getInstance(), new Runnable() {
                @Override
                public void run() {
                    List<Block> preClonedList = new ArrayList<>(blockList);
                    for(Block b : preClonedList) {
                        blockList.remove(b);
                        Block up = b.getRelative(BlockFace.UP);
                        Block down = b.getRelative(BlockFace.DOWN);
                        Block north = b.getRelative(BlockFace.NORTH);
                        Block east = b.getRelative(BlockFace.EAST);
                        Block west = b.getRelative(BlockFace.WEST);
                        Block south = b.getRelative(BlockFace.SOUTH);
                        for(Block nearbyBlock : Arrays.asList(up, down, north, east, west, south)) {
                            if(nearbyBlock.getType().equals(Material.WATER) || nearbyBlock.getType().equals(Material.STATIONARY_WATER)) {
                                nearbyBlock.setType(Material.AIR);
                                nearbyBlock.getWorld().playSound(nearbyBlock.getLocation(), Sound.DIG_SNOW, 0.4f, 2.0f);
                                blockList.add(nearbyBlock);
                            }
                        }
                    }
                }
            }, i * 2L);
        }
    }
}
