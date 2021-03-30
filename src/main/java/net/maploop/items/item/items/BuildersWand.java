package net.maploop.items.item.items;

import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemAbility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.util.IUtil;
import net.md_5.bungee.api.chat.ClickEvent;
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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
    public void leftClickAirAction(Player player, ItemStack paramItemStack) {
        player.sendMessage("§cThis ability is currently disabled!");
        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0f);
        return;

/*
        builder_wand.put(player, player.getItemInHand());
        String uuid = ItemUtilities.getStringFromItem(item, "UUID");

        Inventory inv = Bukkit.createInventory(null, 6 * 9, "Builder's Wand");
        if(builders_wand_storage.containsKey(uuid)) {
            inv.setContents(builders_wand_storage.get(uuid));
            player.openInventory(inv);
        } else {
            player.openInventory(inv);
        }
 */
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

    public static HashMap<String, ItemStack[]> builders_wand_storage = new HashMap<>();
    public static Map<Player, ItemStack> builder_wand = new HashMap<>();
    public static Map<Player, List<Block>> blocksMap = new HashMap<>();
    public static Map<Player, Integer> blocksAmount = new HashMap<>();

    public static void undoWand(Player player) {
        for(Block b : blocksMap.get(player)) {
            b.setType(Material.AIR);
        }
        ItemStack step1 = new ItemStack(blocksMap.get(player).get(0).getType(), blocksAmount.get(player));
        ItemStack step2 = ItemUtilities.storeStringInItem(step1, Rarity.COMMON.toString(), "Rarity");
        ItemMeta meta = step2.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add("§f§lCOMMON");
        meta.spigot().setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(lore);
        step2.setItemMeta(meta);

        player.getInventory().addItem(step2);
    }

    public void fillConnectedFaces(Player player, Block origin, BlockFace face, ItemStack item) {
        Material fillMaterial = origin.getType();
        int blocksInInventory = countBlocks(player.getInventory(), origin.getType(), item);
        int blockLimit = 164;
        if (blocksInInventory < blockLimit)
            blockLimit = blocksInInventory;
        List<Block> blocks = new ArrayList<>();
        List<Block> blocksForUndo = new ArrayList<>();
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
                blocksForUndo.add(w.getBlockAt(l.clone().add(vector)));
            }
            Block fillBlock = w.getBlockAt(l.clone().add(translate));
            if (canPlaceBlock(player, fillBlock.getLocation())) {
                blocks.removeIf(blocks.get(0)::equals);
                if (fillBlock.getType() != fillMaterial) {
                    fillBlock.setType(fillMaterial);
                    blockLimit--;
                    blocksPlaced++;
                }
                if (blocksPlaced == blockLimit)
                    break;
                continue;
            }
            blocks.removeIf(blocks.get(0)::equals);
            blockLimit--;
        }
        if(blocksPlaced == 0) {
            player.playSound(player.getLocation(), Sound.WOOD_CLICK, 1f, 1f);
            player.sendMessage("§cYou cannot place any blocks! You do not have enough blocks to place with your Builder's wand!");
        }
        if (blocksPlaced != 0) {
            blocksMap.put(player, blocksForUndo);
            blocksAmount.put(player, blocksPlaced);
            player.getInventory().removeItem(new ItemStack(origin.getType(), blocksPlaced));

            TextComponent component = new TextComponent("§eYou built §a" + blocksPlaced + "§e blocks! ");
            TextComponent comp = IUtil.makeClickableText("§a§lUNDO", "§eClick to undo latest creation!", ClickEvent.Action.RUN_COMMAND, "/undograndarchitect");

            player.spigot().sendMessage(component, comp);
            player.playSound(player.getLocation(), Sound.EXPLODE, 1.0F, 2.0F);

            removeItems(player.getInventory(), origin.getType(), blocksPlaced);
        }
    }

    public int countBlocks(Inventory inv, Material m, ItemStack stack) {
        int blockAmount = 0;
        String uuid = ItemUtilities.getStringFromItem(stack, "UUID");
        for (ListIterator<ItemStack> listIterator = inv.iterator(); listIterator.hasNext(); ) {
            ItemStack item = listIterator.next();
            if (item != null &&
                    item.getType() == m)
                blockAmount += item.getAmount();
        }
        if(builders_wand_storage.containsKey(uuid)) {
            for(ItemStack i : builders_wand_storage.get(uuid)) {
                if(i != null && i.getType() == m)
                    blockAmount += i.getAmount();
            }
        }
        return blockAmount;
    }

    public void removeBlocks(Player player, Material m, int blockAmount) {
        ItemStack mats = new ItemStack(m, blockAmount);
        player.getInventory().removeItem(mats);
    }

    public boolean canPlaceBlock(Player player, Location l) {
        return true;
    }

    /**
     * Removes the items of type from an inventory.
     * @param inventory Inventory to modify
     * @param type The type of Material to remove
     * @param amount The amount to remove, or MAX_VALUE to remove all
     * @return 0 for success, otherwise -1
     */
    public static int removeItems(Inventory inventory, Material type, int amount) {

        if(type == null || inventory == null)
            return -1;
        if (amount <= 0)
            return -1;

        if (amount == Integer.MAX_VALUE) {
            inventory.remove(type);
            return 0;
        }
        ItemStack step1 = new ItemStack(type, amount);
        ItemStack step2 = ItemUtilities.storeStringInItem(step1, Rarity.COMMON.toString(), "Rarity");
        ItemMeta meta = step2.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add("§f§lCOMMON");
        meta.spigot().setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(lore);
        step2.setItemMeta(meta);

        inventory.removeItem(step2);
        return 0;
    }

    private boolean a(ItemStack item) {
        String uuid = ItemUtilities.getStringFromItem(item, "UUID");
        return true;
    }
}
