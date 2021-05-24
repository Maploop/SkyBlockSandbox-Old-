package net.maploop.items.data;

import net.maploop.items.user.User;
import net.maploop.items.util.IUtil;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

public enum BaseAbility {
    WITHER_IMPACT("Wither Impact", () -> {

    }),

    INSTANT_TRANSMISSION("Instant Transmission", () -> {

    });

    private final String s;
    private final Runnable run;

    private int damageValue;
    private Player player;

    public String getDisplayname() {
        return this.s;
    }

    public void run(int damageValue, Player player) {
        this.damageValue = damageValue;
        this.player = player;

        this.run.run();
    }

    BaseAbility(String s, Runnable run) {
        this.s = s;
        this.run = run;
    }
}
