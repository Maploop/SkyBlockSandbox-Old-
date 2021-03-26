package net.maploop.items.commands;

import net.maploop.items.item.items.BlockZapper;
import net.maploop.items.item.items.BuildersWand;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UndolatestbuildCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command command, String cmd, String[] args) {
        if(s instanceof Player) {
            Player player = (Player) s;
            player.sendMessage("§eUnzapped §c" + BuildersWand.blockAmount.get(player) + "§e blocks away!");
            BuildersWand.undoBuild(player);
            player.playSound(player.getLocation(), Sound.SLIME_WALK, 1f, 2f);
        } else {
            s.sendMessage("No!!?!?!?!?!");
        }
        return false;
    }
}
