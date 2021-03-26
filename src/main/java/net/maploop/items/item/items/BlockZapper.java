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
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
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

import java.util.*;

public class BlockZapper extends CustomItem {
    public BlockZapper(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, boolean glowing) {
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
        event.setCancelled(true);
        doBlockEater(event.getClickedBlock().getType(), event.getClickedBlock(), 30, player);

        TextComponent component = new TextComponent("§eZapped §a" + blockAmount.get(player) + "§e blocks! ");
        TextComponent comp = DUtil.makeClickableText("§a§lUNDO", "§eClick to undo the latest zap!", ClickEvent.Action.RUN_COMMAND, "/undolatestzap");

        player.spigot().sendMessage(component, comp);
        for(int i = 0; i < i1; ++i) {
            player.getInventory().addItem(new ItemStack(event.getClickedBlock().getType()));
        }

        player.playSound(player.getLocation(), Sound.SLIME_WALK2, 1f, 1.5f);
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
        rightClickBlockAction(player, event, paramBlock, item);
    }

    @Override
    public void middleClickAction(Player player, ItemStack item) {

    }

    @Override
    public void hitEntityAction(Player player, EntityDamageByEntityEvent event, Entity paramEntity, ItemStack item) {

    }

    @Override
    public void breakBlockAction(Player player, BlockBreakEvent event, Block paramBlock, ItemStack item) {

    }

    @Override
    public void clickedInInventoryAction(Player player, InventoryClickEvent event) {

    }

    @Override
    public void activeEffect(Player player, ItemStack item) {

    }

    public static Map<Player, List<Block>> blocks = new HashMap<>();
    public static Map<Player, Integer> blockAmount = new HashMap<>();
    public static Map<Player, Material> material = new HashMap<>();

    public static int i1 = 0;
    public static List<Block> zapped = new ArrayList<>();

    public void doBlockEater(Material type, Block origin, int amount, Player player) {
        zapped.clear();
        blocks.remove(player);
        i1 = 0;
        List<Block> blockList = new ArrayList<>();
        material.put(player, type);
        blockList.add(origin);
        for(int i = 0; i <= amount; ++i) {
            List<Block> preClonedBlocks = new ArrayList<>(blockList);
            for(Block b : preClonedBlocks) {
                blockList.remove(b);
                Block up = b.getRelative(BlockFace.UP);
                Block down = b.getRelative(BlockFace.DOWN);
                Block north = b.getRelative(BlockFace.NORTH);
                Block east = b.getRelative(BlockFace.EAST);
                Block west = b.getRelative(BlockFace.WEST);
                Block south = b.getRelative(BlockFace.SOUTH);
                for(Block nearby : Arrays.asList(up, down, north, east, south, west)) {
                    if(nearby.getType().equals(type)) {
                        nearby.setType(Material.AIR);
                        blockList.add(nearby);
                        zapped.add(nearby);
                        blocks.put(player, zapped);
                        ++i1;
                        blockAmount.put(player, i1);
                    }
                }
            }
        }
    }

    public static void undoZap(Player player) {
        try {
            for(Block block : blocks.get(player)) {
                block.setType(material.get(player));
            }
        } catch (Exception e) {
            player.sendMessage("§cAn error occurred while trying to undo your zap. Please contact an administrator to get this issue possibly fixed.");
        }
    }
}
