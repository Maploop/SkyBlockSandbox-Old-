package net.maploop.items.gui;

import net.maploop.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ShutUpGUI extends GUI {
    public ShutUpGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "Shut someone up!";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        switch (event.getSlot()) {
            case 31:
                player.closeInventory();
                break;
            case 11: {
                AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                    @Override
                    public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                        if(event.getSlot().equals(AnvilGUI.AnvilSlot.OUTPUT)) {
                            event.setWillClose(true);
                            event.setWillDestroy(true);
                            
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if(Bukkit.getPlayer(event.getName()) != null)
                                        new ShutUpPersonGUI(new PlayerMenuUtility(player), Bukkit.getPlayer(event.getName()).getUniqueId()).open();
                                    else
                                        player.sendMessage("§cPlayer not found.");
                                }
                            }.runTaskLater(Items.getInstance(), 1);
                        }
                    }
                });

                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.PAPER, "Enter player name", 1, 0, "§a^^^^^\n§7Enter a player name in the\n§7text box!"));
                gui.open();
                break;
            }
            case 15: {
                player.sendMessage("You shut up everyone!");
                break;
            }
        }
    }

    @Override
    public void setItems() {
        setFilter();

        inventory.setItem(31, makeItem(Material.BARRIER, "§Close", 1, 0));
        inventory.setItem(11, makeSkullItem("§aShut up a specific player", "people", 1, "§7Shut up a specific player!\n§7This will make them\n§7not be able to talk\n§7until you un-shutup them.\n\n§eClick to shut up!"));
        inventory.setItem(15, makeSkullItem("§aShut up all players", "people", 64, "§7Shut up all the players!\n§7This will make everyone\n§7not be able to talk\n§7until you un-shutup them.\n\n§eClick to shut up!"));
    }
}
