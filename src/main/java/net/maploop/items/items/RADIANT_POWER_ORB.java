package net.maploop.items.items;

import org.bukkit.inventory.ItemStack;

public class RADIANT_POWER_ORB {
    static ItemStack item;

    public static void load() {
        item = ItemMaker.makeCustomSkullItem("http://textures.minecraft.net/texture/7ab4c4d6ee69bc24bba2b8faf67b9f704a06b01aa93f3efa6aef7a9696c4feef", "§aRadiant Power Orb", 1,
                "§6Item Ability: Deploy",
                "§7Place an orb for §a30s §7buffing",
                "§7up to §b5§7 players within §a18",
                "§7blocks.",
                "§8Costs 50% of max mana.",
                "§8Only one orb applies per player.",
                "",
                "§aOrb Buff: Radiant",
                "§7Heals §c1% §7of max §c❤ §7per second.",
                "",
                "§a§lUNCOMMON");
    }

    public static ItemStack get() { return item; }
}
