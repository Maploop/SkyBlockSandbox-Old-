package net.maploop.items;

import net.maploop.items.commands.DebugCommand;
import net.maploop.items.commands.ItemsCommand;
import net.maploop.items.commands.MysearchCommand;
import net.maploop.items.commands.PetsCommand;
import net.maploop.items.items.*;
import net.maploop.items.items.pets.bee.BEE_PET_COMMON;
import net.maploop.items.listeners.*;
import net.maploop.items.menus.PlayerMenuUtility;
import org.bukkit.entity.ArmorStand;
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
        removeStands();
    }

    private void registerItems() {
        HYPERION.load();
        RADIANT_POWER_ORB.load();
        MANAFLUX_POWER_ORB.load();
        OVERFLUX_POWER_ORB.load();
        PLASMAFLUX_POWER_ORB.load();
        GRAPPLING_HOOK.load();
        BEE_PET_COMMON.load();


    }

    private void registerCommands() {
        this.getCommand("items").setExecutor(new ItemsCommand());
        this.getCommand("pets").setExecutor(new PetsCommand());
        this.getCommand("debug").setExecutor(new DebugCommand());
        this.getCommand("mysearch").setExecutor(new MysearchCommand());
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractAtEntityListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerFishListener(), this);
        this.getServer().getPluginManager().registerEvents(new FallDamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
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

    public void removeStands() {
        if (BlockPlaceListener.isPlaced.keySet() == null) return;
        for (ArmorStand stands : BlockPlaceListener.isPlaced.values()) {
            stands.remove();
        }
    }
}
