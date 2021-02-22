package net.maploop.items.items;

import org.bukkit.inventory.ItemStack;

public class MANAFLUX_POWER_ORB {
    static ItemStack item;

    public static void load() {
        item = ItemMaker.makeCustomSkullItem("http://textures.minecraft.net/texture/82ada1c7fcc8cf35defeb944a4f8ffa9a9d260560fc7f5f5826de8085435967c", "§9Mana Flux Power Orb", 1,
                "§6Item Ability: Deploy",
                "§7Place an orb for §a30s §7buffing",
                "§7up to §b5§7 players within §a18",
                "§7blocks.",
                "§8Costs 50% of max mana.",
                "§8Only one orb applies per player.",
                "",
                "§9Orb Buff: Mana Flux",
                "§7Grants §b+50% §7base mana regen.",
                "§7Heals §c2% §7of max §c❤ §7per second.",
                "§7Grants §c+10 Strength§7.",
                "",
                "§9§lRARE");
    }

    public static ItemStack get() { return item; }
}
