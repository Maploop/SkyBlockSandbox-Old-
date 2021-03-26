package net.maploop.items.listeners;

import net.maploop.items.Items;
import net.maploop.items.data.DataHandler;
import net.maploop.items.item.CustomItem;
import net.maploop.items.sql.SQLGetter;
import net.maploop.items.util.DUtil;
import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PlayerJoinListener implements Listener {
    public static Map<Player, EntityWither> bossBar = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(bossBar(player));
        bossBar.put(player, bossBar(player));
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);

        if(Items.getInstance().getConfig().getBoolean("mysql.use-mysql")) {
            SQLGetter getter = new SQLGetter(player, Items.getInstance());
            getter.inject();
            System.out.println("Data sync for " + player.getName() + "complete!");
        } else {
            DataHandler data = new DataHandler(player);
            data.saveData();
            data.inject();
        }

        ItemStack skyblock_menu = CustomItem.fromString(Items.getInstance(), "skyblock_menu", 1);
        player.getInventory().setItem(8, skyblock_menu);

    }

    private EntityWither bossBar(Player player) {
        EntityWither wither = new EntityWither(((CraftWorld)player.getWorld()).getHandle());
        wither.setInvisible(true);
        wither.setCustomName(DUtil.colorize("&e&lYOU ARE PLAYING ON &aMC.SKYBLOCKSANDBOX.NET"));

        return wither;
    }
}
