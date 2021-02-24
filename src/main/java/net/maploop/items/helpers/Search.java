package net.maploop.items.helpers;

import net.maploop.items.Items;
import net.maploop.items.menus.ItemsMenu;
import net.maploop.items.menus.PlayerMenuUtility;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Search {
    private Player player;

    public void start() {
        new AnvilGUI.Builder()
                .onClose(player -> {                                        //called when the inventory is closing
                    player.sendMessage("§cYou cancelled the search.");
                })
                .onComplete((player, text) -> {                             //called when the inventory output slot is clicked
                    Maps.searching.put(player.getUniqueId(), text);

                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            new ItemsMenu(new PlayerMenuUtility(player)).open();
                        }
                    }.runTaskLater(Items.getInstance(), 5);

                    player.closeInventory();
                    return AnvilGUI.Response.close();
                })
                .text("Input Item Name")                       //sets the text the GUI should start with
                .itemLeft(new ItemStack(Material.PAPER))               //use a custom item for the first slot//use a custom item for the second slot
                .title("Search Items")                                //set the title of the GUI (only works in 1.14+)
                .plugin(Items.getInstance())                                   //set the plugin instance
                .open(player);
    }

    public Search(Player player) {
        this.player = player;
    }
}
