package net.maploop.items.item.items;

import net.maploop.items.Items;
import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemAbility;
import net.maploop.items.listeners.EntityDamageListener;
import net.maploop.items.user.User;
import net.maploop.items.util.IUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class JerrychineGun extends CustomItem {
    public JerrychineGun(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, boolean enchantable, ItemType itemType, boolean glowing, int damage, int strength, int crit_damage, int intelligence, int health, int defense) {
        super(id, rarity, name, material, durability, stackable, oneTimeUse, hasActive, abilities, manaCost, reforgeable, enchantable, itemType, glowing, damage, strength, crit_damage, intelligence, health, defense);
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
        player.sendMessage("§cThis item is disabled!");

        /*
        if(new User(player).getIntelligence() < 10) {
            player.sendMessage("§cYou do not have enough mana to use this!");
            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0f);
            return;
        }
        new User(player).setIntelligence(new User(player).getIntelligence() - 10);
        player.playSound(player.getLocation(), Sound.VILLAGER_YES, 0.1f, 2f);
        IUtil.sendActionText(player, "§b-10 Mana (§6Rapid-fire§b)");


        ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(player.getEyeLocation().add(player.getLocation().getDirection().multiply(0.3)), EntityType.ARMOR_STAND);
        stand.setGravity(false);
        stand.setVisible(false);
        stand.setMetadata("BonzoStaffStand", new FixedMetadataValue(Items.getInstance(), 1));
        stand.getEquipment().setHelmet(CustomItem.fromString(Items.getInstance(), "jerry_head", 1));

        Location l1 = stand.getLocation();
        Location l2 = player.getLocation();
        player.setVelocity(new Vector(l2.getX() - l1.getX(), 0.5, l2.getZ() - l1.getZ()));

        int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Runnable() {
            Location loc;
            boolean alreadyDone = false;
            int dmg = 0;
            @Override
            public void run() {
                loc = stand.getLocation().add(stand.getLocation().getDirection().multiply(0.6554));
                stand.teleport(loc);

                if(loc == null)return;
                for(Entity e : stand.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5)) {
                    if(e instanceof LivingEntity && e.getType() != EntityType.DROPPED_ITEM && e.getType() != EntityType.ARMOR_STAND && e.getType() != EntityType.PLAYER) {
                        if(e instanceof Player) return;
                        LivingEntity f = (LivingEntity) e;
                        f.damage(5000.0);
                        stand.remove();
                        loc = null;
                        dmg = 5000;
                        EntityDamageListener listener = new EntityDamageListener();
                        if(!alreadyDone) {
                            DecimalFormat format = new DecimalFormat("#,###.0");
                            alreadyDone = true;
                            f.getWorld().playSound(f.getLocation(), Sound.FIREWORK_BLAST, 1f, 1f);
                            listener.addIndicator(dmg, IUtil.getRandomLocation(f.getLocation(), 2), EntityDamageEvent.DamageCause.ENTITY_ATTACK);
                        }
                    }
                }
                for(Block b : getBlocks(stand.getLocation().getBlock(), 1)) {
                    if(b.getType() != Material.AIR) {

                    }
                }
            }
        }, 0, 1);

        new BukkitRunnable() {
            @Override
            public void run() {
                stand.remove();
            }
        }.runTaskLater(Items.getInstance(), 100);
         */
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
    public void breakBlockAction(Player player, BlockBreakEvent event, Block paramBlock, ItemStack item) {

    }

    @Override
    public void clickedInInventoryAction(Player player, InventoryClickEvent event) {

    }

    @Override
    public void activeEffect(Player player, ItemStack item) {

    }

    public ArrayList<Block> getBlocks(Block start, int radius){
        ArrayList<Block> blocks = new ArrayList<Block>();
        for(double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++){
            for(double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++){
                for(double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++){
                    Location loc = new Location(start.getWorld(), x, y, z);
                    blocks.add(loc.getBlock());
                }
            }
        }
        return blocks;
    }
}
