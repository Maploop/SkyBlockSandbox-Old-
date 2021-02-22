package net.maploop.items.items;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.bukkit.ChatColor;

public enum Rarity {
    COMMON(ChatColor.WHITE),
    UNCOMMON(ChatColor.GREEN),
    RARE(ChatColor.BLUE),
    EPIC(ChatColor.DARK_PURPLE),
    LEGENDARY(ChatColor.GOLD),
    MYTHIC(ChatColor.LIGHT_PURPLE),
    SPECIAL(ChatColor.RED),
    VERY_SPECIAL(ChatColor.RED),
    SUPREME(ChatColor.DARK_RED);

    private final ChatColor color;

    Rarity(ChatColor color) {
        this.color = color;
    }

    public String getName() {
        return "" + color + ChatColor.BOLD + name();
    }
}
