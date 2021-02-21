package net.maploop.items;

import net.maploop.items.commands.ItemsCommand;
import net.maploop.items.items.*;
import net.maploop.items.listeners.BlockPlaceListener;
import net.maploop.items.listeners.InventoryClickListener;
import net.maploop.items.listeners.PlayerInteractAtEntityListener;
import net.maploop.items.listeners.PlayerInteractListener;
import net.maploop.items.menus.PlayerMenuUtility;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Items extends JavaPlugin {
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    public static Items instance;

    @Override
    public void onEnable() {
        instance = this;
        registerItems();
        registerCommands();
        registerListeners();

        getLogger().info("ENABLED");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("DIS ABLED");
    }

    private void registerItems() {
        HYPERION.load();
        ASPECT_OF_THE_END.load();
        RADIANT_POWER_ORB.load();
        MANAFLUX_POWER_ORB.load();
        OVERFLUX_POWER_ORB.load();
    }

    private void registerCommands() {
        this.getCommand("items").setExecutor(new ItemsCommand());
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractAtEntityListener(), this);
    }

    private static PlayerMenuUtility getPlayerMenuUtility(Player player) {
        PlayerMenuUtility playerMenuUtility;
        if (!(playerMenuUtilityMap.containsKey(player))) {

            playerMenuUtility = new PlayerMenuUtility(player);
            playerMenuUtilityMap.put(player, playerMenuUtility);

            return playerMenuUtility;
        } else {
            return playerMenuUtilityMap.get(player);
        }
    }

    public static Items getInstance() {
        return instance;
    }
}
