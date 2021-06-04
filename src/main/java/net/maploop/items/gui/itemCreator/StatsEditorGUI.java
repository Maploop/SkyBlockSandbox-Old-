package net.maploop.items.gui.itemCreator;

import net.maploop.items.Items;
import net.maploop.items.api.SignGUI;
import net.maploop.items.api.SignGUIUpdateEvent;
import net.maploop.items.gui.AnvilGUI;
import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.util.Attribute;
import net.maploop.items.util.IUtil;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class StatsEditorGUI extends GUI {
    /*
    TODO
      - Add damage, critchance and critdamage editing to the GUI.
     */

    public StatsEditorGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public static Set<Player> healthChange = new HashSet<>();
    public static Set<Player> intelligenceChange = new HashSet<>();
    public static Set<Player> defenseChange = new HashSet<>();
    public static Set<Player> strengthChange = new HashSet<>();
    public static Set<Player> damageChange = new HashSet<>();
    public static Set<Player> critChanceChange = new HashSet<>();
    public static Set<Player> critDamageChange = new HashSet<>();

    @Override
    public String getTitle() {
        return "Edit item stats";
    }

    @Override
    public int getSize() {
        return 9 * 4;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        switch (event.getCurrentItem().getType()) {
            case ARROW: {
                new ItemCreatorGUI(playerMenuUtility).open();
                break;
            }
            case GOLDEN_APPLE: {
                AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                    @Override
                    public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                        if(!ItemUtilities.isInteger(event.getName())) {
                            player.sendMessage("§cThat's not a valid number!");
                            player.closeInventory();
                            return;
                        }
                        if(Integer.parseInt(event.getName()) > 100000) {
                            player.sendMessage("§cThe Health amount can't be more than 100,000!");
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                            player.closeInventory();
                            return;
                        }
                        if(Integer.parseInt(event.getName()) <= 0) {
                            player.sendMessage("§cThe Health amount can't be less than 1!");
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                            player.closeInventory();
                            return;
                        }
                        if (ItemUtilities.isInteger(event.getName()) && event.getName() != null) {
                            String s = event.getName();
                            int strength = Integer.parseInt(s);

                            ItemStack is = player.getItemInHand();
                            ItemMeta im = is.getItemMeta();
                            ArrayList new_lore;
                            if (player.getItemInHand().getItemMeta().getLore() == null) {
                                new_lore = new ArrayList();
                                new_lore.add("§7" + ChatColor.translateAlternateColorCodes('&', "&7Health: §a+" + strength));
                                im.setLore(new_lore);
                                is.setItemMeta(im);
                                ItemStack i = ItemUtilities.storeIntInItem(is, strength, Attribute.HEALTH.toString());
                                player.setItemInHand(i);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAdded [strength] Health to the item in your hand!".replace("[strength]", event.getName())));
                            } else {
                                new_lore = new ArrayList();
                                Iterator var12 = player.getItemInHand().getItemMeta().getLore().iterator();

                                String str;
                                while (var12.hasNext()) {
                                    str = (String) var12.next();
                                    if (!str.contains("§7" + ChatColor.translateAlternateColorCodes('&', "Health: §a+"))) {
                                        new_lore.add(str);
                                    } else {
                                        new_lore.add("§7" + ChatColor.translateAlternateColorCodes('&', "Health: §a+" + strength));
                                    }
                                }

                                str = "§7" + ChatColor.translateAlternateColorCodes('&', "Health: §a+" + strength);
                                if (!new_lore.contains(str)) {
                                    new_lore.add(str);
                                }

                                im.setLore(new_lore);
                                is.setItemMeta(im);
                                ItemStack i = ItemUtilities.storeIntInItem(is, strength, Attribute.HEALTH.toString());
                                player.setItemInHand(i);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAdded [strength] Health to the item in your hand!".replace("[strength]", event.getName())));

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        new StatsEditorGUI(new PlayerMenuUtility(player)).open();
                                    }
                                }.runTaskLater(Items.getInstance(), 2);
                            }
                        } else {
                            invalidNumberError(event, player);
                        }
                    }
                });

                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.PAPER, "Enter value", 1, 0));
                gui.open();
                break;
            }
            case EYE_OF_ENDER: {
                AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                    @Override
                    public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                        if(!ItemUtilities.isInteger(event.getName())) {
                            player.sendMessage("§cThat's not a valid number!");
                            player.closeInventory();
                            return;
                        }
                        if(Integer.parseInt(event.getName()) > 100000) {
                            player.sendMessage("§cThe Intelligence amount can't be more than 100,000!");
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                            player.closeInventory();
                            return;
                        }
                        if(Integer.parseInt(event.getName()) <= 0) {
                            player.sendMessage("§cThe Intelligence amount can't be less than 1!");
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                            player.closeInventory();
                            return;
                        }
                        if (ItemUtilities.isInteger(event.getName()) && event.getName() != null) {
                            String s = event.getName();
                            int strength = Integer.parseInt(s);

                            ItemStack is = player.getItemInHand();
                            ItemMeta im = is.getItemMeta();
                            ArrayList new_lore;
                            if (player.getItemInHand().getItemMeta().getLore() == null) {
                                new_lore = new ArrayList();
                                new_lore.add("§7" + ChatColor.translateAlternateColorCodes('&', "&7Intelligence: §a+" + strength));
                                im.setLore(new_lore);
                                is.setItemMeta(im);
                                ItemStack i = ItemUtilities.storeIntInItem(is, strength, Attribute.INTELLIGENCE.toString());
                                player.setItemInHand(i);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAdded [strength] Intelligence to the item in your hand!".replace("[strength]", event.getName())));
                            } else {
                                new_lore = new ArrayList();
                                Iterator var12 = player.getItemInHand().getItemMeta().getLore().iterator();

                                String str;
                                while (var12.hasNext()) {
                                    str = (String) var12.next();
                                    if (!str.contains("§7" + ChatColor.translateAlternateColorCodes('&', "Intelligence: §a+"))) {
                                        new_lore.add(str);
                                    } else {
                                        new_lore.add("§7" + ChatColor.translateAlternateColorCodes('&', "Intelligence: §a+" + strength));
                                    }
                                }

                                str = "§7" + ChatColor.translateAlternateColorCodes('&', "Intelligence: §a+" + strength);
                                if (!new_lore.contains(str)) {
                                    new_lore.add(str);
                                }

                                im.setLore(new_lore);
                                is.setItemMeta(im);
                                ItemStack i = ItemUtilities.storeIntInItem(is, strength, Attribute.INTELLIGENCE.toString());
                                player.setItemInHand(i);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAdded [strength] Intelligence to the item in your hand!".replace("[strength]", event.getName())));

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        new StatsEditorGUI(new PlayerMenuUtility(player)).open();
                                    }
                                }.runTaskLater(Items.getInstance(), 2);
                            }
                        } else {
                            invalidNumberError(event, player);
                        }
                    }
                });

                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.PAPER, "Enter value", 1, 0));
                gui.open();

                break;
            }
            case IRON_CHESTPLATE: {

                AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                    @Override
                    public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                        if(!ItemUtilities.isInteger(event.getName())) {
                            player.sendMessage("§cThat's not a valid number!");
                            player.closeInventory();
                            return;
                        }
                        if(Integer.parseInt(event.getName()) > 100000) {
                            player.sendMessage("§cThe Defense amount can't be more than 100,000!");
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                            player.closeInventory();
                            return;
                        }
                        if(Integer.parseInt(event.getName()) <= 0) {
                            player.sendMessage("§cThe Defense amount can't be less than 1!");
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                            player.closeInventory();
                            return;
                        }
                        if (ItemUtilities.isInteger(event.getName()) && event.getName() != null) {
                            String s = event.getName();
                            int strength = Integer.parseInt(s);

                            ItemStack is = player.getItemInHand();
                            ItemMeta im = is.getItemMeta();
                            ArrayList new_lore;
                            if (player.getItemInHand().getItemMeta().getLore() == null) {
                                new_lore = new ArrayList();
                                new_lore.add("§7" + ChatColor.translateAlternateColorCodes('&', "&7Defense: §a+" + strength));
                                im.setLore(new_lore);
                                is.setItemMeta(im);
                                ItemStack i = ItemUtilities.storeIntInItem(is, strength, Attribute.DEFENSE.toString());
                                player.setItemInHand(i);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAdded [strength] Defense to the item in your hand!".replace("[strength]", event.getName())));
                            } else {
                                new_lore = new ArrayList();
                                Iterator var12 = player.getItemInHand().getItemMeta().getLore().iterator();

                                String str;
                                while (var12.hasNext()) {
                                    str = (String) var12.next();
                                    if (!str.contains("§7" + ChatColor.translateAlternateColorCodes('&', "Defense: §a+"))) {
                                        new_lore.add(str);
                                    } else {
                                        new_lore.add("§7" + ChatColor.translateAlternateColorCodes('&', "Defense: §a+" + strength));
                                    }
                                }

                                str = "§7" + ChatColor.translateAlternateColorCodes('&', "Defense: §a+" + strength);
                                if (!new_lore.contains(str)) {
                                    new_lore.add(str);
                                }

                                im.setLore(new_lore);
                                is.setItemMeta(im);
                                ItemStack i = ItemUtilities.storeIntInItem(is, strength, Attribute.DEFENSE.toString());
                                player.setItemInHand(i);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAdded [strength] Defense to the item in your hand!".replace("[strength]", event.getName())));

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        new StatsEditorGUI(new PlayerMenuUtility(player)).open();
                                    }
                                }.runTaskLater(Items.getInstance(), 2);
                            }
                        }else {
                            invalidNumberError(event, player);
                        }
                    }
                });

                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.PAPER, "Enter value", 1, 0));
                gui.open();

                break;
            }
            case IRON_SWORD: {

                AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                    @Override
                    public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                        if(!ItemUtilities.isInteger(event.getName())) {
                            player.sendMessage("§cThat's not a valid number!");
                            player.closeInventory();
                            return;
                        }
                        if(Integer.parseInt(event.getName()) > 100000) {
                            player.sendMessage("§cThe Strength amount can't be more than 100,000!");
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                            player.closeInventory();
                            return;
                        }
                        if(Integer.parseInt(event.getName()) <= 0) {
                            player.sendMessage("§cThe Strength amount can't be less than 1!");
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                            player.closeInventory();
                            return;
                        }
                        if (ItemUtilities.isInteger(event.getName()) && event.getName() != null) {
                            String s = event.getName();
                            int strength = Integer.parseInt(s);

                            ItemStack is = player.getItemInHand();
                            ItemMeta im = is.getItemMeta();
                            ArrayList new_lore;
                            if (player.getItemInHand().getItemMeta().getLore() == null) {
                                new_lore = new ArrayList();
                                new_lore.add("§7" + ChatColor.translateAlternateColorCodes('&', "&7Strength: §c+" + strength));
                                im.setLore(new_lore);
                                is.setItemMeta(im);
                                ItemStack i = ItemUtilities.storeIntInItem(is, strength, Attribute.STRENGTH.toString());
                                player.setItemInHand(i);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAdded [strength] Strength to the item in your hand!".replace("[strength]", event.getName())));
                            } else {
                                new_lore = new ArrayList();
                                Iterator var12 = player.getItemInHand().getItemMeta().getLore().iterator();

                                String str;
                                while (var12.hasNext()) {
                                    str = (String) var12.next();
                                    if (!str.contains("§7" + ChatColor.translateAlternateColorCodes('&', "Strength: §c+"))) {
                                        new_lore.add(str);
                                    } else {
                                        new_lore.add("§7" + ChatColor.translateAlternateColorCodes('&', "Strength: §c+" + strength));
                                    }
                                }

                                str = "§7" + ChatColor.translateAlternateColorCodes('&', "Strength: §c+" + strength);
                                if (!new_lore.contains(str)) {
                                    new_lore.add(str);
                                }

                                im.setLore(new_lore);
                                is.setItemMeta(im);
                                ItemStack i = ItemUtilities.storeIntInItem(is, strength, Attribute.STRENGTH.toString());
                                player.setItemInHand(i);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAdded [strength] Strength to the item in your hand!".replace("[strength]", event.getName())));
                            }

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    new StatsEditorGUI(new PlayerMenuUtility(player)).open();
                                }
                            }.runTaskLater(Items.getInstance(), 2);
                        } else {
                            invalidNumberError(event, player);
                        }
                    }
                });

                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.PAPER, "Enter value", 1, 0));
                gui.open();

                break;
            }
            case BLAZE_POWDER: {

                AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                    @Override
                    public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                        if(!ItemUtilities.isInteger(event.getName())) {
                            player.sendMessage("§cThat's not a valid number!");
                            player.closeInventory();
                            return;
                        }
                        if(Integer.parseInt(event.getName()) > 100000) {
                            player.sendMessage("§cThe Damage amount can't be more than 100,000!");
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                            player.closeInventory();
                            return;
                        }
                        if(Integer.parseInt(event.getName()) <= 0) {
                            player.sendMessage("§cThe Damage amount can't be less than 1!");
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                            player.closeInventory();
                            return;
                        }
                        if (ItemUtilities.isInteger(event.getName()) && event.getName() != null) {
                            String s = event.getName();
                            int strength = Integer.parseInt(s);

                            ItemStack is = player.getItemInHand();
                            ItemMeta im = is.getItemMeta();
                            ArrayList new_lore;
                            if (player.getItemInHand().getItemMeta().getLore() == null) {
                                new_lore = new ArrayList();
                                new_lore.add("§7" + ChatColor.translateAlternateColorCodes('&', "&7Damage: §c+" + strength));
                                im.setLore(new_lore);
                                is.setItemMeta(im);
                                ItemStack i = ItemUtilities.storeIntInItem(is, strength, Attribute.DAMAGE.toString());
                                player.setItemInHand(i);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAdded [strength] Damage to the item in your hand!".replace("[strength]", event.getName())));
                            } else {
                                new_lore = new ArrayList();
                                Iterator var12 = player.getItemInHand().getItemMeta().getLore().iterator();

                                String str;
                                while (var12.hasNext()) {
                                    str = (String) var12.next();
                                    if (!str.contains("§7" + ChatColor.translateAlternateColorCodes('&', "Damage: §c+"))) {
                                        new_lore.add(str);
                                    } else {
                                        new_lore.add("§7" + ChatColor.translateAlternateColorCodes('&', "Damage: §c+" + strength));
                                    }
                                }

                                str = "§7" + ChatColor.translateAlternateColorCodes('&', "Damage: §c+" + strength);
                                if (!new_lore.contains(str)) {
                                    new_lore.add(str);
                                }

                                im.setLore(new_lore);
                                is.setItemMeta(im);
                                ItemStack i = ItemUtilities.storeIntInItem(is, strength, Attribute.DAMAGE.toString());
                                player.setItemInHand(i);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAdded [strength] Damage to the item in your hand!".replace("[strength]", event.getName())));
                            }

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    new StatsEditorGUI(new PlayerMenuUtility(player)).open();
                                }
                            }.runTaskLater(Items.getInstance(), 2);
                        } else {
                            invalidNumberError(event, player);
                        }
                    }
                });

                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.PAPER, "Enter value", 1, 0));
                gui.open();

                break;
            }
        }
        switch (event.getSlot()){
            case 15: {
                AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                    @Override
                    public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                        if(!ItemUtilities.isInteger(event.getName())) {
                            player.sendMessage("§cThat's not a valid number!");
                            player.closeInventory();
                            return;
                        }
                        if(Integer.parseInt(event.getName()) > 100) {
                            player.sendMessage("§cThe Crit Chance amount can't be more than 100!");
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                            player.closeInventory();
                            return;
                        }
                        if(Integer.parseInt(event.getName()) <= 0) {
                            player.sendMessage("§cThe Crit Chance amount can't be less than 1!");
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                            player.closeInventory();
                            return;
                        }
                        if (ItemUtilities.isInteger(event.getName()) && event.getName() != null) {
                            String s = event.getName();
                            int strength = Integer.parseInt(s);

                            ItemStack is = player.getItemInHand();
                            ItemMeta im = is.getItemMeta();
                            ArrayList new_lore;
                            if (player.getItemInHand().getItemMeta().getLore() == null) {
                                new_lore = new ArrayList();
                                new_lore.add("§7" + ChatColor.translateAlternateColorCodes('&', "&7Crit Chance: §c+" + strength + "%"));
                                im.setLore(new_lore);
                                is.setItemMeta(im);
                                ItemStack i = ItemUtilities.storeIntInItem(is, strength, Attribute.CRIT_CHANCE.toString());
                                player.setItemInHand(i);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAdded [strength] Crit Chance to the item in your hand!".replace("[strength]", event.getName())));
                            } else {
                                new_lore = new ArrayList();
                                Iterator var12 = player.getItemInHand().getItemMeta().getLore().iterator();

                                String str;
                                while (var12.hasNext()) {
                                    str = (String) var12.next();
                                    if (!str.contains("§7" + ChatColor.translateAlternateColorCodes('&', "Crit Chance: §c+"))) {
                                        new_lore.add(str);
                                    } else {
                                        new_lore.add("§7" + ChatColor.translateAlternateColorCodes('&', "Crit Chance: §c+" + strength + "%"));
                                    }
                                }

                                str = "§7" + ChatColor.translateAlternateColorCodes('&', "Crit Chance: §c+" + strength + "%");
                                if (!new_lore.contains(str)) {
                                    new_lore.add(str);
                                }

                                im.setLore(new_lore);
                                is.setItemMeta(im);
                                ItemStack i = ItemUtilities.storeIntInItem(is, strength, Attribute.CRIT_CHANCE.toString());
                                player.setItemInHand(i);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAdded [strength] Crit Chance to the item in your hand!".replace("[strength]", event.getName())));
                            }

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    new StatsEditorGUI(new PlayerMenuUtility(player)).open();
                                }
                            }.runTaskLater(Items.getInstance(), 2);
                        } else {
                            invalidNumberError(event, player);
                        }
                    }
                });

                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.PAPER, "Enter value", 1, 0));
                gui.open();

                break;
            }
            case 16: {
                AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                    @Override
                    public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                        if(!ItemUtilities.isInteger(event.getName())) {
                            player.sendMessage("§cThat's not a valid number!");
                            player.closeInventory();
                            return;
                        }
                        if(Integer.parseInt(event.getName()) > 100000) {
                            player.sendMessage("§cThe Crit Damage amount can't be more than 100,000!");
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                            player.closeInventory();
                            return;
                        }
                        if(Integer.parseInt(event.getName()) <= 0) {
                            player.sendMessage("§cThe Crit Damage amount can't be less than 1!");
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                            player.closeInventory();
                            return;
                        }
                        if (ItemUtilities.isInteger(event.getName()) && event.getName() != null) {
                            String s = event.getName();
                            int strength = Integer.parseInt(s);

                            ItemStack is = player.getItemInHand();
                            ItemMeta im = is.getItemMeta();
                            ArrayList new_lore;
                            if (player.getItemInHand().getItemMeta().getLore() == null) {
                                new_lore = new ArrayList();
                                new_lore.add("§7" + ChatColor.translateAlternateColorCodes('&', "&7Crit Damage: §c+" + strength + "%"));
                                im.setLore(new_lore);
                                is.setItemMeta(im);
                                ItemStack i = ItemUtilities.storeIntInItem(is, strength, Attribute.CRIT_DAMAGE.toString());
                                player.setItemInHand(i);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAdded [strength] Crit Damage to the item in your hand!".replace("[strength]", event.getName())));
                            } else {
                                new_lore = new ArrayList();
                                Iterator var12 = player.getItemInHand().getItemMeta().getLore().iterator();

                                String str;
                                while (var12.hasNext()) {
                                    str = (String) var12.next();
                                    if (!str.contains("§7" + ChatColor.translateAlternateColorCodes('&', "Crit Damage: §c+"))) {
                                        new_lore.add(str);
                                    } else {
                                        new_lore.add("§7" + ChatColor.translateAlternateColorCodes('&', "Crit Damage: §c+" + strength + "%"));
                                    }
                                }

                                str = "§7" + ChatColor.translateAlternateColorCodes('&', "Crit Damage: §c+" + strength + "%");
                                if (!new_lore.contains(str)) {
                                    new_lore.add(str);
                                }

                                im.setLore(new_lore);
                                is.setItemMeta(im);
                                ItemStack i = ItemUtilities.storeIntInItem(is, strength, Attribute.CRIT_DAMAGE.toString());
                                player.setItemInHand(i);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAdded [strength] Crit Damage to the item in your hand!".replace("[strength]", event.getName())));
                            }

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    new StatsEditorGUI(new PlayerMenuUtility(player)).open();
                                }
                            }.runTaskLater(Items.getInstance(), 2);
                        } else {
                            invalidNumberError(event, player);
                        }
                    }
                });

                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.PAPER, "Enter value", 1, 0));
                gui.open();

                break;
            }
        }
    }

    @Override
    public void setItems() {
        setFilter();
        inventory.setItem(31, makeItem(Material.ARROW, "§aGo Back", 1, 0, "§7To Create an Item"));

        inventory.setItem(11, makeItem(Material.EYE_OF_ENDER, "§aSet Intelligence", 1, 0, IUtil.colorize("&7Edit the amount of &b✎ Intelligence\n&7your item has!\n\n&eClick to set!")));
        inventory.setItem(12, makeItem(Material.IRON_CHESTPLATE, "§aSet Defense", 1, 0, IUtil.colorize("&7Edit the amount of &a❈ Defense\n&7your item has!\n\n&eClick to set!")));
        inventory.setItem(13, makeItem(Material.IRON_SWORD, "§aSet Strength", 1, 0, IUtil.colorize("&7Edit the amount of &c❁ Strength\n&7your item has!\n\n&eClick to set!")));
        inventory.setItem(10, makeItem(Material.GOLDEN_APPLE, "§aSet Health", 1, 0, IUtil.colorize("&7Edit the amount of &c❤ Health\n&7your item has!\n\n&eClick to set!")));
        inventory.setItem(14, makeItem(Material.BLAZE_POWDER,"§aSet Damage",1,0,IUtil.colorize("&7Edit the amount of &c❁ Damage\n&7your item has!\n\n&eClick to set!")));
        inventory.setItem(15, makeCustomSkullItem("http://textures.minecraft.net/texture/3e4f49535a276aacc4dc84133bfe81be5f2a4799a4c04d9a4ddb72d819ec2b2b", "§aSet Crit Chance", 1, IUtil.colorize("&7Edit the amount of §9☣ Crit Chance\n&7your item has!\n\n&eClick to set!")));
        inventory.setItem(16, makeCustomSkullItem("http://textures.minecraft.net/texture/ddafb23efc57f251878e5328d11cb0eef87b79c87b254a7ec72296f9363ef7c", "§aSet Crit Damage", 1, IUtil.colorize("&7Edit the amount of §9☠ Crit Damage\n&7your item has!\n\n&eClick to set!")));
    }

    private void invalidNumberError(AnvilGUI.AnvilClickEvent event, Player player) {
        IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a("{\"text\":\"§cThat's not a valid number!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§fYour input: §e" + event.getName() + "\"}}");
        PacketPlayOutChat c = new PacketPlayOutChat(comp);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(c);

        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0f);
        player.closeInventory();
    }
}
