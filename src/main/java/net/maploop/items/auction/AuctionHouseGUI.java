package net.maploop.items.auction;

import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.user.User;
import net.maploop.items.util.IUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AuctionHouseGUI extends GUI {
    public AuctionHouseGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "Auction House";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        switch (event.getCurrentItem().getType()) {
            case BARRIER:
                player.closeInventory();
                break;
            case GOLD_BLOCK:
                new AuctionBrowserGUI(playerMenuUtility, AuctionBrowserGUI.AuctionCategory.WEAPONS).open();
                break;
            case EMPTY_MAP:
                player.closeInventory();
                player.sendMessage("§cRequest failed please try again later.");
                break;
            case GOLD_BARDING:
                if (new User(player).hasAuctions()) {
                    new YourAuctionsGUI(playerMenuUtility).open();
                    return;
                }
                new AuctionCreatorGUI(playerMenuUtility, false, 500).open();
                break;
        }
    }

    @Override
    public void setItems() {
        setFilter();

        inventory.setItem(11, makeItem(Material.GOLD_BLOCK, "§6Auction Browser", 1, 0, IUtil.colorize("&7Find items for sale by players\n&7across Skyblock Sandbox!\n\n&7Items offered here are for\n&6auction&7, " +
                "&7meaning you have to\n&7place the top bid to acquire\n&7them!\n\n&eClick to browse!")));

        inventory.setItem(15, makeItem(Material.GOLD_BARDING, "§aCreate Auction", 1, 0));

        inventory.setItem(31, makeItem(Material.BARRIER, "§cClose", 1, 0));
    }
}
