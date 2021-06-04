package net.maploop.items.command.commands;

import net.maploop.items.Items;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.gui.itemCreator.DyeGUI;
import net.maploop.items.gui.wardobes.WardobeGUI;
import net.maploop.items.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_wardrobe implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("You have to be a player to execute this command!");
            return true;
        }
        Player player = (Player) sender;
        if(args.length <= 1){
            new WardobeGUI(new PlayerMenuUtility(player)).open();
            return true;
        }
        switch (args[0].toLowerCase()){
            case "reset": {
                SQLGetter targetGetter = new SQLGetter(Bukkit.getPlayer(args[1]), Items.getInstance());
                targetGetter.setWardobe("NONE");
            }
        }

        return true;
    }
}
