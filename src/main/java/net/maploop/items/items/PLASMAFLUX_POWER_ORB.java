package net.maploop.items.items;

import org.bukkit.inventory.ItemStack;

public class PLASMAFLUX_POWER_ORB {
    static ItemStack item;

    public static void load() {
        item = ItemMaker.makeCustomSkullItem("http://textures.minecraft.net/texture/83ed4ce23933e66e04df16070644f7599eeb55307f7eafe8d92f40fb3520863c", "§6Plasmaflux Power Orb", 1,
                "§6Item Ability: Deploy",
                "§7Place an orb for §a60s §7buffing",
                "§7up to §b5§7 players within §a18",
                "§7blocks.",
                "§8Costs 50% of max mana.",
                "§8Only one orb applies per player.",
                "",
                "§6Orb Buff: Plasmaflux",
                "§7Grants §b+125% §7base mana regen.",
                "§7Heals §c3% §7of max §c❤ §7per second.",
                "§7Increases all heals by §a+7.5%§7.",
                "§7Grants §c+25❁ Strength§7.",
                "",
                "§6§lLEGENDARY");
    }

    public static ItemStack get() { return item; }
}
