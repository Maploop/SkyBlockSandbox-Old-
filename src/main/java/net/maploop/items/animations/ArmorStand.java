package net.maploop.items.animations;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;

public class ArmorStand extends EntityArmorStand {
    private final World world;
    private final Location loc;
    public ArmorStand(World world, World world1, Location loc) {
        super(world);
        this.world = world1;
        this.loc = loc;
    }
}
