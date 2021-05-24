package net.maploop.items.item.items;

import me.clip.placeholderapi.PlaceholderAPI;
import net.maploop.items.enums.AbilityType;
import net.maploop.items.enums.ItemStats;
import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemAbility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.user.User;
import net.maploop.items.util.Attribute;
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

import java.text.DecimalFormat;
import java.util.*;

public class Hyperion extends CustomItem {
    public Hyperion(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, boolean enchantable, ItemType itemType, boolean glowing, int damage, int strength, int crit_damage, int intelligence, int health, int defense) {
        super(id, rarity, name, material, durability, stackable, oneTimeUse, hasActive, abilities, manaCost, reforgeable, enchantable, itemType, glowing, damage, strength, crit_damage, intelligence, health, defense);
    }

    @Override
    public void onItemStackCreate(ItemStack paramItemStack) {

    }

    @Override
    public void getSpecificLorePrefix(List<String> lore, ItemStack paramItemStack) {
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
        if(new User(player).getIntelligence() <= 250) {
            player.sendMessage("§cYou do not have enough mana to do that.");
            return;
        }
        new User(player).setIntelligence(new User(player).getIntelligence() - 250);

        Location l = player.getLocation().clone();
        Set<Material> TRANSPARENT = new HashSet<Material>();
        TRANSPARENT.add(Material.AIR);
        TRANSPARENT.add(Material.STATIONARY_WATER);
        TRANSPARENT.add(Material.VINE);
        Location looking = player.getTargetBlock(TRANSPARENT, 120).getLocation();
        if (looking.distance(l) < 8) {
            switch ((int) looking.distance(l)) {
                case 8:
                    l.add(player.getEyeLocation().getDirection().multiply(7));
                    player.sendMessage(ChatColor.RED + "There are blocks in the way!");
                    break;
                case 7:
                    l.add(player.getEyeLocation().getDirection().multiply(6));
                    player.sendMessage(ChatColor.RED + "There are blocks in the way!");
                    break;
                case 6:
                    l.add(player.getEyeLocation().getDirection().multiply(5));
                    player.sendMessage(ChatColor.RED + "There are blocks in the way!");
                    break;
                case 5:
                    l.add(player.getEyeLocation().getDirection().multiply(4));
                    player.sendMessage(ChatColor.RED + "There are Blocks in the way!");
                    break;
                case 4:
                    l.add(player.getEyeLocation().getDirection().multiply(3));
                    player.sendMessage(ChatColor.RED + "There are blocks in the way!");
                    break;
                case 3:
                    l.add(player.getEyeLocation().getDirection().multiply(2));
                    player.sendMessage(ChatColor.RED + "There are blocks in the way!");
                    break;
                case 2:
                    l.add(player.getEyeLocation().getDirection().multiply(1));
                    player.sendMessage(ChatColor.RED + "There are blocks in the way!");
                    break;
                case 1:
                case 0:
                    player.sendMessage(ChatColor.RED + "There are blocks in the way!");
                    IUtil.sendActionText(player, "§b-250 Mana (§6Wither Impact§b)");
                    player.playSound(player.getLocation(), Sound.EXPLODE, 1.0F, 2.0F);

                    onItemUse(player, item);

                    int i = 0;
                    for (Entity e : player.getWorld().getNearbyEntities(player.getLocation(), 5, 5, 5)) {
                        if (e instanceof LivingEntity) {
                            if (!(e instanceof Player || e instanceof ArmorStand || e instanceof NPC)) {
                                ++i;
                                if(e.isDead()) {
                                    ((LivingEntity) e).damage(0);
                                } else {
                                    ((LivingEntity) e).damage(190000);
                                }
                            }/* else {
                        if (e instanceof Player) {
                            if (!((Player) e).getPlayer().equals(player)) {
                                String text = "%worldguard_region_name%";
                                String regionid = PlaceholderAPI.setPlaceholders(((Player) e).getPlayer(), text);
                                if (regionid.equals("colosseum")) {
                                    ++i;
                                    User user = new User(((Player) e).getPlayer());
                                    int damage = (int) 1900;
                                    double health = user.getHealth()-damage;
                                    user.setHealth(health);
                                }
                                */
                        }
                    }

                    if(i >= 1) {
                        DecimalFormat format = new DecimalFormat("#,###");
                        int damage = 190000 * i;
                        player.sendMessage(IUtil.colorize("&7Your Wither Impact hit &c" + i + "&7 enemies dealing &c" + format.format(damage) + " damage&7."));
                    }
                    return;

            }
        } else
            l.add(player.getEyeLocation().getDirection().multiply(8));

        IUtil.sendActionText(player, "§b-250 Mana (§6Wither Impact§b)");
        player.playSound(player.getLocation(), Sound.EXPLODE, 1.0F, 2.0F);

        if (l.getPitch() <= 0) {
            player.teleport(new Location(l.getWorld(), l.getX(), l.getY() - 1, l.getZ(), l.getYaw(), l.getPitch()));
        } else {
            player.teleport(new Location(l.getWorld(), l.getX(), l.getY() + 1.5F, l.getZ(), l.getYaw(), l.getPitch()));
        }

        onItemUse(player, item);

        int i = 0;
        for (Entity e : player.getWorld().getNearbyEntities(player.getLocation(), 5, 5, 5)) {
            if (e instanceof LivingEntity) {
                if (!(e instanceof Player || e instanceof ArmorStand || e instanceof NPC)) {
                    ++i;
                    if(e.isDead()) {
                        ((LivingEntity) e).damage(0);
                    } else {
                        ((LivingEntity) e).damage(190000);
                    }
                }/* else {
                        if (e instanceof Player) {
                            if (!((Player) e).getPlayer().equals(player)) {
                                String text = "%worldguard_region_name%";
                                String regionid = PlaceholderAPI.setPlaceholders(((Player) e).getPlayer(), text);
                                if (regionid.equals("colosseum")) {
                                    ++i;
                                    User user = new User(((Player) e).getPlayer());
                                    int damage = (int) 1900;
                                    double health = user.getHealth()-damage;
                                    user.setHealth(health);
                                }
                                */
            }
        }

        if(i >= 1) {
            DecimalFormat format = new DecimalFormat("#,###");
            int damage = 190000 * i;
            player.sendMessage(IUtil.colorize("&7Your Wither Impact hit &c" + i + "&7 enemies dealing &c" + format.format(damage) + " damage&7."));
        }
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
}
