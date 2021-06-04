package net.maploop.items.util;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.maploop.items.Items;
import net.maploop.items.user.User;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PAPI extends PlaceholderExpansion {
    public boolean persist() {
        return true;
    }

    public @NotNull String getIdentifier() {
        return "items";
    }

    public @NotNull String getAuthor() {
        return "Ender/Maploop";
    }

    public @NotNull String getVersion() {
        return Items.getInstance().getDescription().getVersion();
    }

    public boolean canRegister() {
        return true;
    }

    public String onPlaceholderRequest(final Player player, final String identifier) {
        User user = new User(player);
        if (player == null) {
            return "";
        }
        switch (identifier.toLowerCase()){
            case "stats_health": {
                return "" + Math.round(user.getTotalHealth());
            }
            case "stats_defense": {
                return "" + Math.round(user.getTotalDefense());
            }
            case "stats_intelligence": {
                return "" + Math.round(user.getTotalIntelligence());
            }
            case "stats_strength": {
                return "" + Math.round(user.getTotalStrength());
            }
            case "stats_crit_chance": {
                return "" + Math.round(user.getTotalCritChance());
            }
            case "stats_crit_damage": {
                return "" + Math.round(user.getTotalCritDamage());
            }
            default: {
                return null;
            }
        }
    }
}
