package net.maploop.items.baseAbility;

import org.bukkit.entity.Player;

public abstract class BaseAbility implements Runnable {
    protected final Player player;
    protected final int damage;
    protected final int cooldown;
    protected final int manaCost;

    public BaseAbility(Player player, int damage, int cooldown, int manaCost) {
        this.player = player;
        this.damage = damage;
        this.cooldown = cooldown;
        this.manaCost = manaCost;
    }

    public abstract void run();
}
