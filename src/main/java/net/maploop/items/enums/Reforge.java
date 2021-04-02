package net.maploop.items.enums;

import net.maploop.items.extras.ReforgeStatsObject;

import java.util.HashMap;
import java.util.Map;

public enum Reforge {
    SPICY("Spicy", (Map<Rarity, ReforgeStatsObject>) new HashMap<>().put(Rarity.COMMON, new ReforgeStatsObject(0, 101, 2, 2, 14, 2,2)));

    private Map<Rarity, ReforgeStatsObject> stats;
    private String prefix;

    Reforge(String prefix, Map<Rarity, ReforgeStatsObject> stats) {
        stats = stats;
        this.prefix = prefix;
    }

    public Map<Rarity, ReforgeStatsObject> getStats() {
        return stats;
    }

    public ReforgeStatsObject getByRarity(Rarity r) {
        return stats.get(r);
    }

    public String getPrefix() {
        return prefix;
    }
}
