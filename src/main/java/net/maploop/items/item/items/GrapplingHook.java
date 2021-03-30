package net.maploop.items.item.items;

import net.maploop.items.Items;
import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemAbility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.util.IUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class GrapplingHook extends CustomItem {
    public GrapplingHook(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, boolean glowing) {
        super(id, rarity, name, material, durability, stackable, oneTimeUse, hasActive, abilities, manaCost, reforgeable, itemType, glowing);
    }

    @Override
    public void onItemStackCreate(ItemStack paramItemStack) {

    }

    @Override
    public void getSpecificLorePrefix(List<String> paramList, ItemStack paramItemStack) {
        String lore = IUtil.colorize("&7Travel around in style using\n&7this Grappling Hook.\n&82 Second Cooldown");
        String[] lore1 = lore.split("\n");
        paramList.addAll(Arrays.asList(lore1));
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
    public void rightClickBlockAction(Player paramPlayer, PlayerInteractEvent paramPlayerInteractEvent, Block paramBlock, ItemStack paramItemStack) {

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

    @Override
    public void playerFishAction(Player player, PlayerFishEvent event, ItemStack item) {
        if(event.getState().equals(PlayerFishEvent.State.FAILED_ATTEMPT) || event.getState().equals(PlayerFishEvent.State.IN_GROUND)) {
            if(player.getInventory().contains(CustomItem.fromString(Items.getInstance(), "magical_clock", 1))) {
                Location loc1 = player.getLocation();
                Location loc2 = event.getHook().getLocation();

                Vector v = new Vector(loc2.getX() - loc1.getX(), 1, loc2.getZ() - loc1.getZ());

                player.setVelocity(v);

                return;
            }

            if(!(ItemUtilities.enforceCooldown(player, "grapple", 2D, item, false))) {
                player.sendMessage("§cWhow! Slow down there!");
                return;
            }
            Location loc1 = player.getLocation();
            Location loc2 = event.getHook().getLocation();

            Vector v = new Vector(loc2.getX() - loc1.getX(), 1, loc2.getZ() - loc1.getZ());

            player.setVelocity(v);
        }
    }
}
