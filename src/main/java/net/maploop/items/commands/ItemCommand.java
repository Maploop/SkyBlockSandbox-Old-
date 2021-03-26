package net.maploop.items.commands;

import net.maploop.items.enums.Rarity;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.item.SBItems;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if(player.hasPermission("dungeons.admin")) {
                if(args.length == 0) {
                    player.sendMessage(ChatColor.RED + "Missing arguments!");
                } else if(args.length == 1) {

                    if(Arrays.asList(Material.values()).toString().contains(args[0].toUpperCase())) {
                        player.sendMessage(ChatColor.GREEN + "Gave " + player.getName() + " item " + ChatColor.YELLOW + args[0].toUpperCase() + ChatColor.GREEN + ".");
                        ItemStack step1 = new ItemStack(Material.valueOf(args[0].toUpperCase()), 1);
                        ItemStack step2 = ItemUtilities.storeStringInItem(step1, Rarity.COMMON.toString(), "Rarity");
                        ItemMeta meta = step2.getItemMeta();
                        meta.spigot().setUnbreakable(true);
                        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                        List<String> lore = new ArrayList<>();
                        lore.add("§f§lCOMMON");
                        meta.setLore(lore);
                        step2.setItemMeta(meta);

                        player.getInventory().addItem(step2);
                        return true;
                    }

                    for(ItemStack item : SBItems.getItems()) {
                        if(ItemUtilities.getStringFromItem(item, "SB-name").toLowerCase().equals(args[0].toLowerCase())) {
                            player.sendMessage(ChatColor.GREEN + "Gave " + player.getName() + " item " + ChatColor.YELLOW + args[0].toUpperCase() + ChatColor.GREEN + ".");
                            player.getInventory().addItem(item);
                        }
                    }

                } else if(args.length == 2) {

                    int size = Integer.parseInt(args[1]);

                    if(Arrays.asList(Material.values()).toString().contains(args[0].toUpperCase())) {
                        player.sendMessage(ChatColor.GREEN + "Gave " + player.getName() + " item " + ChatColor.YELLOW + args[0].toUpperCase() + ChatColor.GREEN + ".");
                        ItemStack step1 = new ItemStack(Material.valueOf(args[0].toUpperCase()), size);
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
                        return true;
                    }

                    for(ItemStack item : SBItems.getItems()) {
                        if(ItemUtilities.getStringFromItem(item, "SB-name").toLowerCase().equals(args[0].toLowerCase())) {
                            player.sendMessage(ChatColor.GREEN + "Gave " + player.getName() + " item " + ChatColor.YELLOW + args[0].toUpperCase() + ChatColor.GREEN + ".");
                            for(int i = 0; i < size; ++i) {
                                player.getInventory().addItem(item);
                            }
                        }
                    }
                }

                return true;
            } else {
                player.sendMessage(ChatColor.RED + "You must be admin or higher to use this command.");
            }
        } else {
            commandSender.sendMessage(ChatColor.RED + "You cannot use this command from console!");
        }
        return false;
    }
}
