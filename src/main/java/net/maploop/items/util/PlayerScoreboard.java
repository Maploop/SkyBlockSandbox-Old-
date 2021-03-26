package net.maploop.items.util;

import me.qKing12.RoyaleEconomy.RoyaleEconomy;
import net.maploop.items.Items;
import net.maploop.items.data.DataHandler;
import net.maploop.items.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerScoreboard {
    public static void setPlayerScoreboard(Player player) {
        DecimalFormat formatter = new DecimalFormat("#,###.0");
        DataHandler handler = new DataHandler(player);
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective(player.getName(), player.getName() + "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "SKYBLOCK SANDBOX");

        objective.getScore(ChatColor.GRAY + new SimpleDateFormat("MM/dd/yy").format(new Date()) + ChatColor.DARK_GRAY + " mega1d").setScore(6);
        objective.getScore(ChatColor.GREEN + " ").setScore(5);
        objective.getScore(ChatColor.AQUA + "§7⏣§b Dungeon Hub").setScore(4);
        objective.getScore("   ").setScore(3);

        objective.getScore("     ").setScore(1);
        objective.getScore(ChatColor.YELLOW + "mc.skybloocksandbox.net").setScore(0);
        player.setScoreboard(scoreboard);
    }

    public static void setPlayerSQLScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective(player.getName(), player.getName() + "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "SKYBLOCK SANDBOX");

        objective.getScore(ChatColor.GRAY + new SimpleDateFormat("MM/dd/yy").format(new Date()) + ChatColor.DARK_GRAY + " mega1A").setScore(6);
        objective.getScore(ChatColor.GREEN + " ").setScore(5);
        objective.getScore(ChatColor.AQUA + "Coming Soon").setScore(4);
        objective.getScore("   ").setScore(3);

        double playerBalance = RoyaleEconomy.apiHandler.balance.getBalance(player.getUniqueId().toString());

        objective.getScore(ChatColor.WHITE + "Purse: " + ChatColor.GOLD + playerBalance).setScore(2);

        objective.getScore("     ").setScore(1);
        objective.getScore(ChatColor.YELLOW + "mc.skybloocksandbox.net").setScore(0);
        player.setScoreboard(scoreboard);
    }
}
