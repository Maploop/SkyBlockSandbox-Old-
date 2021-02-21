package net.maploop.items.helpers;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import java.util.Random;

public class Utilities {
    public static String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static Block getBlockOfSight(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }

    public static void sendActionbar(Player player, String message) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" +
                ChatColor.translateAlternateColorCodes('&', message) + "\"}");
        PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte)2);
        (((CraftPlayer) player).getHandle()).playerConnection.sendPacket((Packet)bar);
    }

    public static int getRandomInteger(int max) {
        Random ran = new Random();
        return ran.nextInt(max);
    }

    public static Location getRandomLocation(Location origin, int radius) {
        int which = getRandomInteger(3);
        Location newLoc = origin;

        switch (which) {
            case 0:
                int x = getRandomInteger(radius);
                newLoc = origin.add(x, 0 ,0);
                break;
            case 1:
                int z = getRandomInteger(radius);
                newLoc = origin.add(0, 0, z);
                break;
            case 2:
                int minusX = getRandomInteger(radius);
                newLoc = origin.add(-minusX, 0, 0);
                break;
            case 3:
                int minusZ = getRandomInteger(radius);
                newLoc = origin.add(0, 0, -minusZ);
                break;
        }

        return newLoc;
    }
}
