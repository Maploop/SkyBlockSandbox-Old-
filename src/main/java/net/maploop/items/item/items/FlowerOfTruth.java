package net.maploop.items.item.items;

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
        if(!(ItemUtilities.enforceCooldown(paramPlayer, "flower_of_truth", 1d, paramItemStack, false))) {  // 1d = (cooldown time in seconds)      item = (item cooldown must be on)     boolean false = if throw error in console (KEEP ON FALSE)
            ItemUtilities.warnPlayer(paramPlayer, Collections.singletonList(ChatColor.RED + "This ability is currently on cooldown for 1 more second."));
            return;
        }

        ArmorStand stand = (ArmorStand) paramPlayer.getWorld().spawnEntity(paramPlayer.getEyeLocation(), EntityType.ARMOR_STAND);
        Vector direction = paramPlayer.getLocation().getDirection();

        stand.setVisible(false);
        stand.setGravity(false);

        stand.setItemInHand(paramItemStack);

        paramPlayer.playSound(paramPlayer.getLocation(), Sound.EAT, 1, 1);

        int taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Runnable() {
            @Override
            public void run() {
                Location loc = stand.getLocation();

                loc.add(direction.multiply(1.2));

                stand.teleport(loc);

                for (Entity entity : stand.getNearbyEntities(1, 1, 1)) {
                    if (entity instanceof LivingEntity) {
                        if (entity instanceof Player) {
                            if (!entity.getName().equalsIgnoreCase(paramPlayer.getName())) {
                                if (entity.hasMetadata("NPC")) {
                                    hitOne = true;
                                    targetEntity.add(entity);
                                    for (Entity e2 : stand.getNearbyEntities(15, 15, 15)) {
                                        if (e2 instanceof LivingEntity) {
                                            loc.subtract(e2.getLocation().getDirection());

                                            ((LivingEntity) e2).damage(130349);
                                            targetEntity.add(e2);

                                            for (Entity e3 : stand.getNearbyEntities(15, 15, 15)) {
                                                if (e3 instanceof LivingEntity) {
                                                    loc.subtract(e3.getLocation().getDirection());

                                                    ((LivingEntity) e3).damage(140063);
                                                    targetEntity.add(e3);
                                                    targetEntity.clear();

                                                    EntityDamageListener listener = new EntityDamageListener();
                                                    if(!alreadyDone) {
                                                        alreadyDone = true;
                                                        listener.addIndicator(140063.0, IUtil.getRandomLocation(e3.getLocation(), 2), EntityDamageEvent.DamageCause.ENTITY_ATTACK);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (event.getPlayer().hasMetadata("NPC")) {
                                        hitOne = true;
                                        targetEntity.add(entity);
                                        for (Entity e2 : stand.getNearbyEntities(15, 15, 15)) {
                                            if (e2 instanceof LivingEntity) {
                                                loc.subtract(e2.getLocation().getDirection());

                                                ((LivingEntity) e2).damage(130349);
                                                targetEntity.add(e2);

                                                for (Entity e3 : stand.getNearbyEntities(15, 15, 15)) {
                                                    if (e3 instanceof LivingEntity) {
                                                        loc.subtract(e3.getLocation().getDirection());

                                                        ((LivingEntity) e3).damage(140063);
                                                        targetEntity.add(e3);
                                                        targetEntity.clear();

                                                        EntityDamageListener listener = new EntityDamageListener();
                                                        if(!alreadyDone) {
                                                            alreadyDone = true;
                                                            listener.addIndicator(140063.0, IUtil.getRandomLocation(e3.getLocation(), 2), EntityDamageEvent.DamageCause.ENTITY_ATTACK);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            hitOne = true;
                            targetEntity.add(entity);
                            for (Entity e2 : stand.getNearbyEntities(15, 15, 15)) {
                                if (e2 instanceof LivingEntity) {
                                    loc.subtract(e2.getLocation().getDirection());

                                    ((LivingEntity) e2).damage(130349);
                                    targetEntity.add(e2);

                                    for (Entity e3 : stand.getNearbyEntities(15, 15, 15)) {
                                        if (e3 instanceof LivingEntity) {
                                            loc.subtract(e3.getLocation().getDirection());

                                            ((LivingEntity) e3).damage(140063);
                                            targetEntity.add(e3);
                                            targetEntity.clear();

                                            EntityDamageListener listener = new EntityDamageListener();
                                            if(!alreadyDone) {
                                                alreadyDone = true;
                                                listener.addIndicator(140063.0, IUtil.getRandomLocation(e3.getLocation(), 2), EntityDamageEvent.DamageCause.ENTITY_ATTACK);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, 0, 1);

        Bukkit.getScheduler().runTaskLater(Items.getInstance(), new Runnable() {
            @Override
            public void run() {
                Bukkit.getScheduler().cancelTask(taskid);
                if (!hitOne && targetEntity.isEmpty()) {
                    stand.remove();
                    paramPlayer.getWorld().playEffect(stand.getLocation(), Effect.SNOWBALL_BREAK, null);
                }
            }
        }, 10);
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
