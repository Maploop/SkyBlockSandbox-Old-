package net.maploop.items.gui;

import net.maploop.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShutUpPersonGUI extends GUI{
    private final UUID id;

    public ShutUpPersonGUI(PlayerMenuUtility playerMenuUtility, UUID uuid) {
        super(playerMenuUtility);
        this.id = uuid;
    }

    @Override
    public String getTitle() {
        return "Confirm shut up: " + Bukkit.getPlayer(id).getName();
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        Player target = Bukkit.getPlayer(id);

        switch (event.getSlot()) {
            case 10: {
                if(target.hasPermission("items.bypass.shutup")) {
                    player.sendMessage("§cYou cannot shut-uo that player because they bypass it!");
                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                    return;
                }

                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
                player.sendMessage("§aSuccess!");
                if(Items.getInstance().getShuts().getStringList("list").isEmpty()) {
                    Items.getInstance().getShuts().set("list", id.toString());
                    Items.getInstance().saveShuts();
                    return;
                } else {
                    List<String> list = new ArrayList<>(Items.getInstance().getShuts().getStringList("list"));
                    list.add(id.toString());

                    Items.getInstance().getShuts().set("list", list);
                    Items.getInstance().saveShuts();
                }
            }
            player.closeInventory();
            case 16: {
                event.getWhoClicked().closeInventory();
                break;
            }
        }
    }

    @Override
    public void setItems() {
        inventory.setItem(10, makeItem(Material.STAINED_CLAY, "§aConfirm", 1, 5, "§7If you confirm this\n§a" + Bukkit.getPlayer(id).getName() + "§7 will be\n§7permanently shut up."));
        inventory.setItem(16, makeItem(Material.STAINED_CLAY, "§aConfirm", 1, 14));
    }
}
