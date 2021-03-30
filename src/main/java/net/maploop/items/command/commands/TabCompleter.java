package net.maploop.items.command.commands;

import net.maploop.items.item.SBItems;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        final List<String> completions = new ArrayList<>();
        completions.addAll(SBItems.items.keySet());
        completions.addAll(Collections.singletonList(Arrays.toString(Material.values())));
        return completions;
    }
}
