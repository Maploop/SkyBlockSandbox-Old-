package net.maploop.items.runnables;

import net.maploop.items.commands.ReforgingCommand;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;


public class ReforgingCheck extends BukkitRunnable {
    private Inventory inventory;
    public ReforgingCheck(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void run() {

    }
}
