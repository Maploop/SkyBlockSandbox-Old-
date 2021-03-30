package net.maploop.items.data;

import net.maploop.items.Items;
import net.maploop.items.util.IUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataHandler {
    public Map<Player, Double> mana = new HashMap<>();
    public Map<Player, Double> defense = new HashMap<>();
    public Map<Player, Double> health = new HashMap<>();

    public Map<Player, Double> maxHealth = new HashMap<>();
    public Map<Player, Double> maxMana = new HashMap<>();

    public Items plugin;
    private final Player player;

    public DataHandler(Player player) {
        plugin = Items.getInstance();
        this.player = player;
    }

    public void inject() {
        int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(plugin.getConfig().getBoolean("use-actionbar")) {
                    if (getHealth() > getMaxHealth()) {
                        IUtil.sendActionText(player, "§6" + Math.round(getHealth()) + "/" + Math.round(getMaxHealth()) + "❤§a    " + Math.round(getDefense()) + "❈§b    " + Math.round(getMana()) + "/" + Math.round(getMaxMana()) + "✎");
                    } else {
                        IUtil.sendActionText(player, "§c" + Math.round(getHealth()) + "/" + Math.round(getMaxHealth()) + "❤§a    " + Math.round(getDefense()) + "❈§b    " + Math.round(getMana()) + "/" + Math.round(getMaxMana()) + "✎");
                    }
                }

                if(getMana() < getMaxMana()) {
                    addMana(getMaxMana() / 10);
                }

                if(getHealth() < getMaxHealth()) {
                    addHealth(getMaxHealth() / 50);
                }

            }
        }, 0, 20L);
    }

    public void saveData() {
        if (exits()) {
            TextComponent component = new TextComponent(ChatColor.DARK_GRAY + getUniqueId().toString());
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to type the UUID in your chatbox!").color(ChatColor.AQUA).create()));
            component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, getUniqueId().toString()));

            player.sendMessage(IUtil.colorize("&eWelcome back &a" + player.getName() + "&e!"));
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendMessage(IUtil.colorize("&7You are playing on profile:"));
                    player.spigot().sendMessage(component);
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1.5f);
                }
            }.runTaskLater(plugin, 10);

            maxHealth.put(player, plugin.getData().getDouble("data.players." + player.getUniqueId().toString() + ".max-health"));
            defense.put(player, plugin.getData().getDouble("data.players." + player.getUniqueId().toString() + ".defense"));
            maxMana.put(player, plugin.getData().getDouble("data.players." + player.getUniqueId().toString() + ".max-mana"));

            mana.put(player, maxMana.get(player));
            health.put(player, maxHealth.get(player));

            return;
        }
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".dungeon-class", "NONE");
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".catacombs-level", 0);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".mage-class-level", 0);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".berserk-class-level", 0);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".healer-class-level", 0);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".archer-class-level", 0);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".tank-class-level", 0);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".exp-amount", 0);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".exp-required-amount", 500);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".defense", 50);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".max-health", 100);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".health", 100);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".max-mana", 100);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".mana", 100);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".crit-chance", 20);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".strength", 50);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".crit-damage", 50);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".coins", 100);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".profile-viewer", true);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".profile-uuid", UUID.randomUUID().toString());

        TextComponent component = new TextComponent(ChatColor.DARK_GRAY + getUniqueId().toString());
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to type the UUID in your chatbox!").color(ChatColor.AQUA).create()));
        component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, getUniqueId().toString()));

        player.sendMessage(IUtil.colorize("&eWelcome &a" + player.getName() + "&e!"));
        player.sendMessage(IUtil.colorize("&7A new profile was created for you!"));
        new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage(IUtil.colorize("&7You are playing on profile:"));
                player.spigot().sendMessage(component);
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1.5f);
            }
        }.runTaskLater(plugin, 10);

        mana.put(player, 100d);
        defense.put(player, 50d);
        health.put(player, 100d);
        maxHealth.put(player, 100d);
        maxMana.put(player, 100d);

        plugin.saveData();
    }

    public UUID getUniqueId() {
        return UUID.fromString(plugin.getData().getString("data.players." + player.getUniqueId().toString() + ".profile-uuid"));
    }

    public double getStrength() {
        return plugin.getData().getDouble("data.players." + player.getUniqueId().toString() + ".strength");
    }

    public void setStrength(double d) {
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".strength", d);
    }

    public boolean getProfileViewer() {
        return plugin.getData().getBoolean("data.players." + player.getUniqueId().toString() + ".profile-viewer");
    }

    public void setProfileViewer(boolean b) {
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".profile-viewer", b);
        plugin.saveData();
    }

    public void setRequiredExp(double exp) {
        if (exits()) {
            plugin.getData().set("data.players." + player.getUniqueId().toString() + ".exp-required-amount", exp);
            plugin.saveData();
        }
    }

    public double getRequiredExp() {
        return plugin.getData().getDouble("data.players." + player.getUniqueId().toString() + ".exp-required-amount");
    }

    public void setExp(double exp) {
        if (exits()) {
            plugin.getData().set("data.players." + player.getUniqueId().toString() + ".exp-amount", exp);
            plugin.saveData();
        }
    }

    public double getExp() {
        return plugin.getData().getDouble("data.players." + player.getUniqueId().toString() + ".exp-amount");
    }

    private boolean exits() {
        return (plugin.getData().getString("data.players." + player.getUniqueId()) != null);
    }

    public void setHealth(double d) {
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".health", d);

        plugin.saveData();
    }

    public void setMaxHealth(double d) {
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".max-health", d);

        plugin.saveData();
    }

    public double getHealth() {
        return plugin.getData().getDouble("data.players." + player.getUniqueId().toString() + ".health");
    }

    public double getMaxHealth() {
        return plugin.getData().getDouble("data.players." + player.getUniqueId().toString() + ".max-health");
    }

    public void addHealth(double d) {
        double newD = plugin.getData().getDouble("data.players." + player.getUniqueId().toString() + ".health") + d;
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".health", newD);

        plugin.saveData();
    }

    public void removeHealth(double d) {
        double newD = plugin.getData().getDouble("data.players." + player.getUniqueId().toString() + ".health") - d;
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".health", newD);

        plugin.saveData();
    }

    public void addMana(double d) {
        double newD = plugin.getData().getDouble("data.players." + player.getUniqueId().toString() + ".mana") + d;
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".mana", newD);

        plugin.saveData();
    }

    public void setMana(double d) {
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".mana", d);

        plugin.saveData();
    }

    public void setMaxMana(double mana) {
        this.maxMana.put(player, mana);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".max-mana", mana);

        plugin.saveData();
    }

    public double getMana() {
        return plugin.getData().getDouble("data.players." + player.getUniqueId().toString() + ".mana");
    }

    public double getMaxMana() {
        return plugin.getData().getDouble("data.players." + player.getUniqueId().toString() + ".max-mana");
    }

    public void setDefense(double d) {
        defense.put(player, d);
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".defense", d);

        plugin.saveData();
    }

    public double getDefense() {
        return plugin.getData().getDouble("data.players." + player.getUniqueId().toString() + ".defense");
    }

    public void setCritDamage(double d) {
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".crit-damage", d);

        plugin.saveData();
    }

    public void addCritDamage(double d) {
        double newD = plugin.getData().getDouble("data.players." + player.getUniqueId().toString() + ".crit-damage") + d;

        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".crit-damage", newD);

        plugin.saveData();
    }

    public double getCritDamage() {
        return plugin.getData().getDouble("data.players." + player.getUniqueId().toString() + ".crit-damage");
    }

    public void setCritChance(double d) {
        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".crit-chance", d);

        plugin.saveData();
    }

    public void addCritChance(double d) {
        double newD = plugin.getData().getDouble("data.players." + player.getUniqueId().toString() + ".crit-chance") + d;

        plugin.getData().set("data.players." + player.getUniqueId().toString() + ".crit-chance", newD);

        plugin.saveData();
    }

    public double getCritChance() {
        return plugin.getData().getDouble("data.players." + player.getUniqueId().toString() + ".crit-chance");
    }
}
