package net.maploop.items.command.commands;

import net.maploop.items.Items;
import net.maploop.items.enums.Enchant;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.util.EnchantmentUtil;
import net.maploop.items.util.IUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_addenchant implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("items.admin.enchant")) {
                switch (args.length) {
                    case 0:
                    case 1:
                        send(sender, "&cPlease specify an enchantment and a level!");
                        break;
                    case 2:
                        Player player = (Player) sender;
                        EnchantmentUtil eutil = new EnchantmentUtil(player.getItemInHand());

                        if (ItemUtilities.isInteger(args[1])) {
                            try {
                                try {
                                    player.setItemInHand(eutil.addEnchant(Enchant.valueOf(args[0].toUpperCase()), Integer.parseInt(args[1])));

                                    Bukkit.getScheduler().runTaskLater(Items.getInstance(), () -> Enchant.refreshLore(player.getItemInHand()), 2);
                                    send(sender, "&aEnchantment added!");
                                } catch (IllegalArgumentException ex) {
                                    send(sender, "&cAn error occurred while trying to add the enchantment to your item! &7(LEVEL_TOO_HIGH)");
                                }
                            } catch (Exception ex) {
                                player.setItemInHand(eutil.addEnchant(Enchant.valueOf(args[0].toUpperCase()), Integer.parseInt(args[1])));
                                send(sender, "&aEnchantment added! &7(Lore failed!)");
                            }
                        } else {
                            send(sender, "&c" + args[1] + " is not a valid number!");
                        }
                }
            } else {
                sender.sendMessage(IUtil.colorize("&cYou must be admin or higher to use this command!"));
            }
        }

        return true;
    }

    public void send(CommandSender a, String b) {
        a.sendMessage(IUtil.colorize(b));
    }
}
