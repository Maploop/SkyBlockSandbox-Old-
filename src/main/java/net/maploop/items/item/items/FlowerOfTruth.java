package net.maploop.items.item.items;

import net.maploop.items.Items;
import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemAbility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.listeners.EntityDamageByEntityListener;
import net.maploop.items.user.User;
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

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class FlowerOfTruth extends CustomItem {

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
        AtomicBoolean alreadyDone = new AtomicBoolean(false);
        AtomicBoolean stopRunnable = new AtomicBoolean(false);
        if(!(ItemUtilities.enforceCooldown(paramPlayer, "Flower_of_truth", 1d, paramItemStack, false))) {
            ItemUtilities.warnPlayer(paramPlayer, Collections.singletonList(ChatColor.RED + "This ability is currently on cooldown for 1 more second."));
            return;
        }

        User user = new User(paramPlayer);
        if(user.getIntelligence() > (user.getTotalIntelligence()/10)) {
            user.setIntelligence(user.getIntelligence() - (user.getTotalIntelligence() / 10));
        } else{
            ItemUtilities.warnPlayer(paramPlayer, Collections.singletonList(ChatColor.RED + "You do not have enough mana."));
            return;
        }
        IUtil.sendActionText(paramPlayer, "§c" + Math.round(user.getHealth()) + "/" + Math.round(user.getTotalHealth()) + "❤§b    " + Math.round(user.getTotalIntelligence()/10) + " Mana (§6Heat-Seeking Rose§b)    " + Math.round(user.getIntelligence()) + "/" + Math.round(user.getTotalIntelligence()) + "✎ Mana");
        IUtil.abilityUsed.put(paramPlayer,true);
        ArmorStand stand = (ArmorStand) paramPlayer.getWorld().spawnEntity(paramPlayer.getEyeLocation(), EntityType.ARMOR_STAND);
        Vector direction = paramPlayer.getLocation().getDirection();
        Vector facing = stand.getLocation().getDirection();

        stand.setVisible(false);
        stand.setGravity(false);

        stand.setItemInHand(paramItemStack);

        paramPlayer.playSound(paramPlayer.getLocation(), Sound.EAT, 1, 1);

        int taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Runnable() {
            @Override
            public void run() {
                Location loc = stand.getLocation();

                loc.add(direction);

                stand.teleport(loc);

                EntityDamageByEntityListener listener = new EntityDamageByEntityListener();
                List<Entity> entites = stand.getNearbyEntities(5, 3, 5);
                if (entites.size() >= 4) {
                    if (validEntity(entites.get(0)) && validEntity(entites.get(1)) && validEntity(entites.get(2)) && validEntity(entites.get(3))) {
                        IUtil.scheduleTask(() -> {
                            stand.teleport(entites.get(0).getLocation().setDirection(direction));
                            ((LivingEntity) entites.get(0)).damage(40129);
                            listener.addIndicator(40129, IUtil.getRandomLocation(entites.get(0).getLocation(), 2), EntityDamageEvent.DamageCause.ENTITY_ATTACK);
                        }, 0);
                        IUtil.scheduleTask(() -> {
                            stand.teleport(entites.get(1).getLocation().setDirection(direction));
                            ((LivingEntity) entites.get(1)).damage(40129);
                            listener.addIndicator(40129, IUtil.getRandomLocation(entites.get(1).getLocation(), 2), EntityDamageEvent.DamageCause.ENTITY_ATTACK);
                        }, 2);
                        IUtil.scheduleTask(() -> {
                            stand.teleport(entites.get(2).getLocation().setDirection(direction));
                            ((LivingEntity) entites.get(2)).damage(40129);
                            listener.addIndicator(40129, IUtil.getRandomLocation(entites.get(2).getLocation(), 2), EntityDamageEvent.DamageCause.ENTITY_ATTACK);
                        }, 4);
                        IUtil.scheduleTask(() -> {
                            stand.teleport(entites.get(3).getLocation().setDirection(direction));
                            ((LivingEntity) entites.get(3)).damage(40129);
                            listener.addIndicator(40129, IUtil.getRandomLocation(entites.get(3).getLocation(), 2), EntityDamageEvent.DamageCause.ENTITY_ATTACK);
                        }, 6);
                        IUtil.scheduleTask(() -> {
                            alreadyDone.set(true);
                        },8);
                        stopRunnable.set(true);
                    }
                } else {
                    int i = 0;
                    for (Entity e2 : stand.getNearbyEntities(5, 3, 5)) {
                        if (validEntity(e2)) {
                            if (!e2.isDead()) {
                                LivingEntity entity = (LivingEntity) e2;
                                stand.teleport(e2.getLocation().setDirection(direction));
                                entity.damage(40129);
                                listener.addIndicator(40129, IUtil.getRandomLocation(e2.getLocation(), 2), EntityDamageEvent.DamageCause.ENTITY_ATTACK);
                                i++;
                                if(i >= 3){
                                    stopRunnable.set(true);
                                    IUtil.scheduleTask(() -> {
                                        alreadyDone.set(true);
                                    },4);
                                }
                            }
                        }
                    }
                }
            }
        }, 0L, 1L);
        int taskid3 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(alreadyDone.get()){
                    stand.remove();
                    Bukkit.getScheduler().cancelTask(taskid);
                    alreadyDone.set(false);
                }
                if(stopRunnable.get()){
                    Bukkit.getScheduler().cancelTask(taskid);
                    stopRunnable.set(false);
                }
            }
        }, 0L, 1L);
        IUtil.scheduleTask(() ->{
            stand.remove();
            Bukkit.getScheduler().cancelTask(taskid);
            Bukkit.getScheduler().cancelTask(taskid3);
        },100);
    }

    private boolean validEntity(Entity entity) {
        if (entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.VILLAGER && entity.getType() != EntityType.SLIME) {
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
        rightClickAirAction(paramPlayer,event,paramItemStack);
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
