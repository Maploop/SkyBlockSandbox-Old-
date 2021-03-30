package net.maploop.items.command;

import net.maploop.items.user.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUser {
    private CommandSender base;

    public User getUser() {
        return new User((Player) base);
    }

    public CommandUser(CommandSender base) {
        this.base = base;
    }

    public boolean isPlayer() {
        return base instanceof Player;
    }

    public Player getPlayer() {
        return (Player) base;
    }

    public void sendMessage(String s) {
        base.sendMessage(s);
    }
}
