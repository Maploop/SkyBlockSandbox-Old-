package net.maploop.items.command.commands;

import net.maploop.items.item.items.BlockZapper;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_undozap implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command command, String cmd, String[] args) {
        if(s instanceof Player) {
            Player player = (Player) s;
            player.sendMessage("§eUnzapped §c" + BlockZapper.blockAmount.get(player) + "§e blocks away!");
            BlockZapper.undoZap(player);
            player.playSound(player.getLocation(), Sound.SLIME_ATTACK, 1f, 0f);
            player.closeInventory();
        } else {
            s.sendMessage("No!!?!?!?!?!");
        }
        return false;
    }
}
