package net.maploop.items.item.items;

import net.maploop.items.Items;
import net.maploop.items.enums.ItemStats;
import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemAbility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.listeners.EntityDamageListener;
import net.maploop.items.sql.SQLGetter;
import net.maploop.items.util.IUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class ShadowFury extends CustomItem {
    public ShadowFury(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, boolean glowing) {
        super(id, rarity, name, material, durability, stackable, oneTimeUse, hasActive, abilities, manaCost, reforgeable, itemType, glowing);
    }

    @Override
    public void onItemStackCreate(ItemStack paramItemStack) {

    }

    @Override
    public void getSpecificLorePrefix(List<String> lore, ItemStack paramItemStack) {
        lore.add(ItemStats.GEAR_SCORE.getDisplayname() + "∞");
        lore.add(ItemStats.DAMAGE.getDisplayname() + "300");
        lore.add(ItemStats.STRENGTH.getDisplayname() + "125");
        lore.add("");
        lore.add(ItemStats.SPEED.getDisplayname() + "30");
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
        if(!(ItemUtilities.enforceCooldown(player, "shadow_fury", 15, item, false))) {
            ItemUtilities.warnPlayer(player, Collections.singletonList("§cYou are on cooldown!"));
            return;
        }
        doShadowFuryAbility(player);
    }

    @Override
    public void rightClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item) {
        rightClickAirAction(player, event, item);
    }

    @Override
    public void shiftLeftClickAirAction(Player player, ItemStack item) {

    }

    @Override
    public void shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item) {

    }

    @Override
    public void shiftRightClickAirAction(Player player, PlayerInteractEvent event, ItemStack item) {
        rightClickAirAction(player, event, item);
    }

    @Override
    public void shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item) {
        rightClickAirAction(player, event, item);
    }

    @Override
    public void middleClickAction(Player player, ItemStack item) {

    }

    @Override
    public void hitEntityAction(Player player, EntityDamageByEntityEvent event, Entity paramEntity, ItemStack item) {
        SQLGetter handler = new SQLGetter(player, Items.getInstance());
        double d = IUtil.calculateDamage(player, 300, (int) (handler.getStrength() + 125));
        event.setDamage(d);

        EntityDamageListener listener = new EntityDamageListener();
        listener.damage = d;
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

    private void doShadowFuryAbility(Player player) {
        for(int i = 0; i < 5; ++i) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Items.getInstance(), new Runnable() {
                @Override
                public void run() {
                    List<Entity> entityList = player.getNearbyEntities(6, 6, 6);
                    int i = IUtil.getRandomInteger(entityList.size());
                    player.teleport(entityList.get(i).getLocation());
                }
            },  i * 15);
        }
    }
}
