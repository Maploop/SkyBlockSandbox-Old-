package net.maploop.items.item.items;

import net.citizensnpcs.api.CitizensAPI;
import net.maploop.items.Items;
import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemAbility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.listeners.EntityDamageListener;
import net.maploop.items.util.IUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.mcmonkey.sentinel.SentinelTrait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlowerOfTruth extends CustomItem {
    List<Entity> targetEntity = new ArrayList<>();
    Boolean hitOne = false;
    boolean alreadyDone = false;

    public FlowerOfTruth(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, boolean glowing) {
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
        if (!(ItemUtilities.enforceCooldown(paramPlayer, "flower_of_truth", 1d, paramItemStack, false))) {  // 1d = (cooldown time in seconds)      item = (item cooldown must be on)     boolean false = if throw error in console (KEEP ON FALSE)
            ItemUtilities.warnPlayer(paramPlayer, Collections.singletonList(ChatColor.RED + "This ability is currently on cooldown for 1 more second."));
            return;
        }

        ArmorStand stand = (ArmorStand) paramPlayer.getWorld().spawnEntity(paramPlayer.getEyeLocation(), EntityType.ARMOR_STAND);
        Vector direction = paramPlayer.getLocation().getDirection();
        Vector facing = stand.getLocation().getDirection();

        stand.setVisible(false);
        stand.setGravity(false);

        stand.setItemInHand(paramItemStack);

        paramPlayer.playSound(paramPlayer.getLocation(), Sound.EAT, 1, 1);

        int target = 0;
        Entity targetE = paramPlayer.getWorld().getEntities().get(target);

        Location l = stand.getLocation();
        l.setDirection(targetE.getLocation().getDirection().multiply(-1));

        targetE.teleport(l);

        for (Entity e : stand.getNearbyEntities(15, 15, 15)) {
            if (validEntity(e)) {
                target = e.getEntityId();
            }
        }

        int target1 = 0;
        Entity targetE1 = paramPlayer.getWorld().getEntities().get(target1);

        Location l1 = stand.getLocation();
        l1.setDirection(targetE.getLocation().getDirection().multiply(-1));

        targetE1.teleport(l1);

        for (Entity e : stand.getNearbyEntities(15, 15, 15)) {
            if (validEntity(e)) {
                target1 = e.getEntityId();
            }
        }

        int target2 = 0;
        Entity targetE2 = paramPlayer.getWorld().getEntities().get(target2);

        Location l2 = stand.getLocation();
        l2.setDirection(targetE2.getLocation().getDirection().multiply(-1));

        targetE1.teleport(l2);

        for (Entity e : stand.getNearbyEntities(15, 15, 15)) {
            if (validEntity(e)) {
                target2 = e.getEntityId();
            }
        }

        int taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Runnable() {
            @Override
            public void run() {
                Location loc = stand.getLocation();

                loc.add(direction);

                stand.teleport(loc);

                for (Entity e : stand.getNearbyEntities(2, 1, 2)) {
                    if (validEntity(e)) {
                        ((LivingEntity) e).damage(40129);
                        EntityDamageListener listener = new EntityDamageListener();
                        if(!alreadyDone) {
                            alreadyDone = true;
                            listener.addIndicator(40129, IUtil.getRandomLocation(e.getLocation(), 2), EntityDamageEvent.DamageCause.ENTITY_ATTACK);
                        }
                    }
                }
            }
        }, 0L, 1L);
    }

    private boolean validEntity(Entity entity) {
        if (entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.VILLAGER) {
            return true;
        }
        return false;
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
}
