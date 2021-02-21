package net.maploop.items.animations;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.fusesource.jansi.Ansi;

public class BlueParticle extends BukkitRunnable {
    private final ArmorStand stand;

    public BlueParticle(ArmorStand stand) {
        this.stand = stand;
    }

    Location loc, first, second;
    double var = 0;
    float red = 0;
    float green = 1;
    float blue = 3;
    @Override
    public void run() {
        loc = stand.getLocation();

        var += Math.PI / 16;
        loc = stand.getLocation();
        first = loc.clone().add(Math.cos(var), Math.cos(var) + 2, Math.sin(var));
        second = loc.clone().add(Math.cos(var + Math.PI), Math.cos(var) + 2, Math.sin(var + Math.PI)); // Math.sin(var)

        PacketPlayOutWorldParticles packet1 = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, false, (float) first.getX(),  (float) first.getY(), (float) first.getZ(), red, green, blue, 2, 2);
        PacketPlayOutWorldParticles packet2 = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, false, (float) second.getX(),  (float) second.getY(), (float) second.getZ(), red, green, blue, 2, 2);
        for (Player players : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) players).getHandle().playerConnection.sendPacket(packet1);
            ((CraftPlayer) players).getHandle().playerConnection.sendPacket(packet2);
        }
    }
}
