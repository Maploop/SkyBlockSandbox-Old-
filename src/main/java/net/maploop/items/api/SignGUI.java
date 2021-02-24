package net.maploop.items.api;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenSignEditor;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class SignGUI {
    private Player player;

    public Class<?> getNMSClass(String clazz) {
        try {
            return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void start() {
        BlockPosition bp = new BlockPosition(((CraftPlayer)player).getHandle());
        PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor(bp);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public SignGUI(Player player) {
        this.player = player;
    }
}
