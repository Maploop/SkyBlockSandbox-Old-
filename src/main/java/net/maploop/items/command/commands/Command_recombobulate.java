package net.maploop.items.command.commands;

import net.maploop.items.enums.Message;
import net.maploop.items.enums.Rarity;
import net.maploop.items.item.ItemUtilities;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Command_recombobulate implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission("items.admin")) {
                if(player.getItemInHand() == null) return true;
                if(ItemUtilities.hasRarity(player.getItemInHand())) {
                    switch (ItemUtilities.getRarity(player.getItemInHand())) {
                        case COMMON: {
                            ItemStack l = ItemUtilities.storeIntInItem(player.getItemInHand(), 1, "RarityUpgraded");
                            ItemStack a = ItemUtilities.storeStringInItem(l, Rarity.UNCOMMON.toString(), "Rarity");
                            ItemMeta m = a.getItemMeta();
                            m.setDisplayName(Rarity.UNCOMMON.getColor() + ChatColor.stripColor(m.getDisplayName()));
                            List<String> lore;
                            if(m.hasLore()) {
                                lore = new ArrayList<>(m.getLore());
                                lore.set(lore.size() - 1, Rarity.UNCOMMON.getColor() + "" + ChatColor.BOLD + "§kU§r §f" + Rarity.UNCOMMON.getColor() + "" + ChatColor.BOLD + Rarity.UNCOMMON.toString() + " " + ItemUtilities.getSBItem(player.getItemInHand()).getType().getValue() + " §kU");
                            } else {
                                lore = new ArrayList<>();
                                lore.add(Rarity.UNCOMMON.getColor() + "" + ChatColor.BOLD + "§kU§r §f" + Rarity.UNCOMMON.getColor() + "" + ChatColor.BOLD + Rarity.UNCOMMON.toString() + " " +  ItemUtilities.getSBItem(player.getItemInHand()).getType().getValue() + " §kU");
                            }
                            m.setLore(lore);
                            a.setItemMeta(m);
                            player.setItemInHand(a);
                            break;
                        }
                        case UNCOMMON: {
                            ItemStack l = ItemUtilities.storeIntInItem(player.getItemInHand(), 1, "RarityUpgraded");
                            ItemStack a = ItemUtilities.storeStringInItem(l, Rarity.RARE.toString(), "Rarity");
                            ItemMeta m = a.getItemMeta();
                            m.setDisplayName(Rarity.RARE.getColor() + ChatColor.stripColor(m.getDisplayName()));
                            List<String> lore;
                            if(m.hasLore()) {
                                lore = new ArrayList<>(m.getLore());
                                lore.set(lore.size() - 1, Rarity.RARE.getColor() + "" + ChatColor.BOLD + "§kU§r §f" + Rarity.RARE.getColor() + "" + ChatColor.BOLD + Rarity.RARE.toString() + " " +  ItemUtilities.getSBItem(player.getItemInHand()).getType().getValue() + " §kU");
                            } else {
                                lore = new ArrayList<>();
                                lore.add(Rarity.RARE.getColor() + "" + ChatColor.BOLD + "§kU§r §f" + Rarity.RARE.getColor() + "" + ChatColor.BOLD + Rarity.RARE.toString() + " " +  ItemUtilities.getSBItem(player.getItemInHand()).getType().getValue() + " §kU");
                            }
                            m.setLore(lore);
                            a.setItemMeta(m);
                            player.setItemInHand(a);
                            break;
                        }
                        case RARE: {
                            ItemStack l = ItemUtilities.storeIntInItem(player.getItemInHand(), 1, "RarityUpgraded");
                            ItemStack a = ItemUtilities.storeStringInItem(l, Rarity.EPIC.toString(), "Rarity");
                            ItemMeta m = a.getItemMeta();
                            m.setDisplayName(Rarity.EPIC.getColor() + ChatColor.stripColor(m.getDisplayName()));
                            List<String> lore;
                            if(m.hasLore()) {
                                lore = new ArrayList<>(m.getLore());
                                lore.set(lore.size() - 1, Rarity.EPIC.getColor() + "" + ChatColor.BOLD + "§kU§r §f" + Rarity.EPIC.getColor() + "" + ChatColor.BOLD + Rarity.EPIC.toString() + " " +  ItemUtilities.getSBItem(player.getItemInHand()).getType().getValue() + " §kU");
                            } else {
                                lore = new ArrayList<>();
                                lore.add(Rarity.EPIC.getColor() + "" + ChatColor.BOLD + "§kU§r §f" + Rarity.EPIC.getColor() + "" + ChatColor.BOLD + Rarity.EPIC.toString() + " " +  ItemUtilities.getSBItem(player.getItemInHand()).getType().getValue() + " §kU");
                            }
                            m.setLore(lore);
                            a.setItemMeta(m);
                            player.setItemInHand(a);
                            break;
                        }
                        case EPIC: {
                            ItemStack l = ItemUtilities.storeIntInItem(player.getItemInHand(), 1, "RarityUpgraded");
                            ItemStack a = ItemUtilities.storeStringInItem(l, Rarity.LEGENDARY.toString(), "Rarity");
                            ItemMeta m = a.getItemMeta();
                            m.setDisplayName(Rarity.LEGENDARY.getColor() + ChatColor.stripColor(m.getDisplayName()));
                            List<String> lore;
                            if(m.hasLore()) {
                                lore = new ArrayList<>(m.getLore());
                                lore.set(lore.size() - 1, Rarity.LEGENDARY.getColor() + "" + ChatColor.BOLD + "§kU§r §f" + Rarity.LEGENDARY.getColor() + "" + ChatColor.BOLD + Rarity.LEGENDARY.toString() + " " +  ItemUtilities.getSBItem(player.getItemInHand()).getType().getValue() + " §kU");
                            } else {
                                lore = new ArrayList<>();
                                lore.add(Rarity.LEGENDARY.getColor() + "" + ChatColor.BOLD + "§kU§r §f" + Rarity.LEGENDARY.getColor() + "" + ChatColor.BOLD + Rarity.LEGENDARY.toString() + " " +  ItemUtilities.getSBItem(player.getItemInHand()).getType().getValue() + " §kU");
                            }
                            m.setLore(lore);
                            a.setItemMeta(m);
                            player.setItemInHand(a);
                            break;
                        }
                        case LEGENDARY: {
                            ItemStack l = ItemUtilities.storeIntInItem(player.getItemInHand(), 1, "RarityUpgraded");
                            ItemStack a = ItemUtilities.storeStringInItem(l, Rarity.MYTHIC.toString(), "Rarity");
                            ItemMeta m = a.getItemMeta();
                            m.setDisplayName(Rarity.MYTHIC.getColor() + ChatColor.stripColor(m.getDisplayName()));
                            List<String> lore;
                            if(m.hasLore()) {
                                lore = new ArrayList<>(m.getLore());
                                lore.set(lore.size() - 1, Rarity.MYTHIC.getColor() + "" + ChatColor.BOLD + "§kU§r §f" + Rarity.MYTHIC.getColor() + "" + ChatColor.BOLD + Rarity.MYTHIC.toString() + " " +  ItemUtilities.getSBItem(player.getItemInHand()).getType().getValue() + " §kU");
                            } else {
                                lore = new ArrayList<>();
                                lore.add(Rarity.MYTHIC.getColor() + "" + ChatColor.BOLD + "§kU§f §f" + Rarity.MYTHIC.getColor() + "" + ChatColor.BOLD + Rarity.MYTHIC.toString() + " " +  ItemUtilities.getSBItem(player.getItemInHand()).getType().getValue() + " §kU");
                            }
                            m.setLore(lore);
                            a.setItemMeta(m);
                            player.setItemInHand(a);
                            break;
                        }
                        case MYTHIC: {
                            ItemStack l = ItemUtilities.storeIntInItem(player.getItemInHand(), 1, "RarityUpgraded");
                            ItemStack a = ItemUtilities.storeStringInItem(l, Rarity.SPECIAL.toString(), "Rarity");
                            ItemMeta m = a.getItemMeta();
                            m.setDisplayName(Rarity.SPECIAL.getColor() + ChatColor.stripColor(m.getDisplayName()));
                            List<String> lore;
                            if(m.hasLore()) {
                                lore = new ArrayList<>(m.getLore());
                                lore.set(lore.size() - 1, Rarity.SPECIAL.getColor() + "" + ChatColor.BOLD + "§kU§r §f" + Rarity.SPECIAL.getColor() + "" + ChatColor.BOLD + Rarity.SPECIAL.toString() + " " + ItemUtilities.getSBItem(player.getItemInHand()).getType().getValue() + " §kU");
                            } else {
                                lore = new ArrayList<>();
                                lore.add(Rarity.SPECIAL.getColor() + "" + ChatColor.BOLD + "§kU§r §f" + Rarity.SPECIAL.getColor() + "" + ChatColor.BOLD + Rarity.SPECIAL.toString() + " " + ItemUtilities.getSBItem(player.getItemInHand()).getType().getValue() + " §kU");
                            }
                            m.setLore(lore);
                            a.setItemMeta(m);
                            player.setItemInHand(a);
                            break;
                        }
                    }
                }
            } else {
                player.sendMessage(Message.NO_PERMISSION.getMessage());
            }
        }
        return false;
    }
}
