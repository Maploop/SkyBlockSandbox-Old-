package net.maploop.items.items;

import org.bukkit.inventory.ItemStack;

public class OVERFLUX_POWER_ORB {
    static ItemStack item;

    public static void load() {
        item = ItemMaker.makeCustomSkullItem("http://textures.minecraft.net/texture/84859d0adfc93be19bb441e6edfd43f6bfe6912723033f963d009a11c4824510", "§5Overflux Power Orb", 1,
                "§6Item Ability: Deploy",
                "§7Place an orb for §a60s §7buffing",
                "§7up to §b5§7 players within §a18",
                "§7blocks.",
                "§8Costs 50% of max mana.",
                "§8Only one orb applies per player.",
                "",
                "§5Orb Buff: Overflux",
                "§7Grants §b+100% §7base mana regen.",
                "§7Heals §c2.5% §7of max §c❤ §7per second.",
                "§7Grants §c+25 Strength§7.",
                "",
                "§5§lEPIC");
    }

    public static ItemStack get() { return item; }
}
