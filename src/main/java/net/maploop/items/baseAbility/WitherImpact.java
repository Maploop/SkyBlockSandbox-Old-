package net.maploop.items.baseAbility;

import org.bukkit.entity.Player;

public class WitherImpact extends BaseAbility {
    public WitherImpact(Player player, int damage, int cooldown, int manaCost) {
        super(player, damage, cooldown, manaCost);
    }

    @Override
    public void run() {
        player.sendMessage("RUN!");
    }
}
