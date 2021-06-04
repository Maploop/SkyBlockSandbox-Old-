package net.maploop.items.listeners;

import net.maploop.items.Items;
import net.maploop.items.data.DataHandler;
import net.maploop.items.item.CustomItem;
import net.maploop.items.sql.SQLGetter;
import net.maploop.items.user.User;
import net.maploop.items.user.UserInjector;
import net.maploop.items.util.IUtil;
import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerJoinListener implements Listener {
    public static Map<Player, EntityWither> bossBar = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        User user = new User(player);
        UserInjector injector = new UserInjector(user);
        user.setHealth(user.getTotalHealth());
        user.setIntelligence(user.getTotalIntelligence());
        injector.inject();

        final Player p = event.getPlayer();
        File playerData = new File("plugins/Items/playerData/" + p.getUniqueId().toString() + "/data.yml");
        FileConfiguration pD = new YamlConfiguration();
        if (!new File("plugins/Items/playerData/" + p.getUniqueId().toString()).exists()) {
            new File("plugins/Items/playerData/" + p.getUniqueId().toString()).mkdirs();
        }
        if (!playerData.exists()) {
            try {
                playerData.createNewFile();
                pD.load(playerData);
                pD.set("stats.extra_mana", 0);
                pD.set("stats.extra_defense", 0);
                pD.set("stats.extra_health", 0);
                pD.set("stats.extra_strength", 0);
                pD.set("stats.extra_crit_chance", 0);
                pD.set("stats.extra_crit_damage", 0);
                pD.set("skill.mining.level", 0);
                pD.set("skill.farming.level", 0);
                pD.set("skill.foraging.level", 0);
                pD.set("skill.combat.level", 0);
                pD.save(playerData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Items plugin = Items.getInstance();
        double x = plugin.getConfig().getDouble("locations.lobbyspawn.x");
        double y = plugin.getConfig().getDouble("locations.lobbyspawn.y");
        double z = plugin.getConfig().getDouble("locations.lobbyspawn.z");
        String world = plugin.getConfig().getString("locations.lobbyspawn.world");
        float yaw = plugin.getConfig().getInt("locations.lobbyspawn.yaw");
        float pitch = plugin.getConfig().getInt("locations.lobbyspawn.pitch");

        Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
        player.teleport(loc);
        player.setMaxHealth(20);
        player.setHealthScale(20);
        player.setHealth(20);
        player.setWalkSpeed(0.2f);
    }

}
