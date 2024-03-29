package net.maploop.items.util;

import net.maploop.items.Items;
import net.maploop.items.data.DataHandler;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.*;

public class IUtil {
    public static Map<Player,Boolean> abilityUsed = new HashMap<>();
    public static String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

//
    public static void sendAbilityActionText(Player player, String ability){
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText("NO"), (byte)2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
    public static void sendActionText(Player player, String message){
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message), (byte)2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static int getRandomInteger(int max) {
        Random ran = new Random();
        return ran.nextInt(max);
    }

    public static Boolean isTransparentBlock(Material material) {
        List<Material> valid = new ArrayList<>();
        valid.addAll(Arrays.asList(Material.AIR, Material.WATER, Material.SIGN, Material.FENCE_GATE));

        if (valid.contains(material)) {
            return true;
        } else {
            return false;
        }
    }

    public static String convertToRomanNumeral(int number) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4,1 };
        String[] romanLiterals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder roman = new StringBuilder();

        for(int i = 0; i < values.length; i++) {
            while(number >= values[i]) {
                i -= values[i];
                roman.append(romanLiterals[i]);
            }
        }

        return roman.toString();
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

    public static double calculateDamage(Player player, int damage, int stregth) {
        DataHandler handler = new DataHandler(player);
        double damage1 = (5 + damage + (Math.round(stregth) / 5)) * (1 + stregth/100);
        double damage2 = (damage1 + (damage1 * 0.25) + (damage1 * 0.56));
        double finalDmg = damage2 * (1 + handler.getCritDamage()/100);

        return finalDmg;
    }

    public static void scheduleTask(Runnable run, int i) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Items.getInstance(), run, i);
    }

    public static void scheduleRepeatingTask(Runnable run, int i, int i1) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), run, i, i1);
    }

    public static ArrayList<Block> getBlocks(Block start, int radius){
        ArrayList<Block> blocks = new ArrayList<>();
        for(double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++){
            for(double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++){
                for(double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++){
                    Location loc = new Location(start.getWorld(), x, y, z);
                    blocks.add(loc.getBlock());
                }
            }
        }
        return blocks;
    }

    public static TextComponent makeClickableText(String text, String hoverText, @Nullable ClickEvent.Action actiom, @Nullable String actionText) {
        TextComponent component = new TextComponent(text);
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverText).create()));
        component.setClickEvent(new ClickEvent(actiom, actionText));

        return component;
    }

    public static List<String> getPlayerList() {
        List<String> list = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            list.add(player.getName());
        }
        return list;
    }

    public static String integerToRomanNumeral(int input) {
        if (input < 1 || input > 3999)
            return "§cNAN";
        String s = "";
        while (input >= 1000) {
            s += "M";
            input -= 1000;        }
        while (input >= 900) {
            s += "CM";
            input -= 900;
        }
        while (input >= 500) {
            s += "D";
            input -= 500;
        }
        while (input >= 400) {
            s += "CD";
            input -= 400;
        }
        while (input >= 100) {
            s += "C";
            input -= 100;
        }
        while (input >= 90) {
            s += "XC";
            input -= 90;
        }
        while (input >= 50) {
            s += "L";
            input -= 50;
        }
        while (input >= 40) {
            s += "XL";
            input -= 40;
        }
        while (input >= 10) {
            s += "X";
            input -= 10;
        }
        while (input >= 9) {
            s += "IX";
            input -= 9;
        }
        while (input >= 5) {
            s += "V";
            input -= 5;
        }
        while (input >= 4) {
            s += "IV";
            input -= 4;
        }
        while (input >= 1) {
            s += "I";
            input -= 1;
        }
        return s;
    }
}
