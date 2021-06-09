package net.maploop.items.auction;

import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.util.IUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import javax.annotation.Nullable;
import java.util.UUID;

public class ConfirmGUI extends GUI {
    private final AuctionItem item;
    private final ConfirmReason reason;

    private int price = 0;
    private boolean bin = false;
    private long endTime = 0L;

    public ConfirmGUI(PlayerMenuUtility playerMenuUtility, ConfirmReason reason, @Nullable AuctionItem item) {
        super(playerMenuUtility);
        this.reason = reason;
        this.item = item;
    }

    public ConfirmGUI(PlayerMenuUtility playerMenuUtility, ConfirmReason reason) {
        this(playerMenuUtility, reason, null);
    }

    public ConfirmGUI(PlayerMenuUtility playerMenuUtility, ConfirmReason reason, boolean bin, int price, long endTime) {
        this(playerMenuUtility, reason, null);
        this.bin = bin;
        this.price = price;
        this.endTime = endTime;
    }

    @Override
    public String getTitle() {
        return "Confirm";
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        switch (event.getCurrentItem().getDurability()) {
            case 14:
                player.closeInventory();
                player.sendMessage("§cCancelled!");
                break;
            case 5:

                switch (reason) {
                    case CREATE_AUCTION: {
                        if (this.price != 0) {
                            AuctionBrowserGUI.put(new AuctionItemHandler(UUID.randomUUID(), this.price, player, AuctionCreatorGUI.SELECTED_ITEM.get(player.getUniqueId()), this.bin, this.endTime).save());
                            player.closeInventory();
                            player.sendMessage("§aCreated!");
                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 2);
                            AuctionCreatorGUI.SELECTED_ITEM.remove(player.getUniqueId());

                            break;
                        }

                        break;
                    }
                }
        }
    }

    @Override
    public void setItems() {
        setFilter();

        inventory.setItem(15, makeItem(Material.STAINED_CLAY, "§cCancel", 1, 14, "§8Cancel Action"));

        switch (reason) {
            case CREATE_AUCTION: {
                if (this.price != 0) {
                    inventory.setItem(11, makeItem(Material.STAINED_CLAY, "§aConfirm", 1, 5,
                            IUtil.colorize("&8Create Auction\n\n&7Item: " + AuctionCreatorGUI.SELECTED_ITEM.get(playerMenuUtility.getOwner().getUniqueId()).getItemMeta().getDisplayName() + "\n&7Price: &6" + this.price + " coins\n&7BIN: " + (this.bin ? "&atrue" : "&cfalse") + "\n\n&eClick to create!")));
                    return;
                }

                if (item == null) {
                    inventory.setItem(11, makeItem(Material.STAINED_CLAY, "§aConfirm", 1, 5, "§8Confirm Action"));
                    return;
                }

                break;
            }
        }
    }

    public enum ConfirmReason {
        CREATE_AUCTION;
    }
}
