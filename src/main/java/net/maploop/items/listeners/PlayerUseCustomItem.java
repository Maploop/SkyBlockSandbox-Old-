package net.maploop.items.listeners;

import net.maploop.items.Items;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.gui.SkyblockMenuGUI;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemUtilities;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerUseCustomItem implements Listener {
    Items main;

    public PlayerUseCustomItem(Items main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEvent event) {
        if (event.getItem() != null) {
            ItemStack item = event.getPlayer().getItemInHand();
            if(event.getPlayer().getItemInHand() == null) return;
            if(event.getPlayer().getItemInHand().getType() == Material.AIR) return;

            net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
            NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
            NBTTagCompound data = compound.getCompound("ExtraAttributes");
            if(!(data.hasKey("is-SB"))) return;
            if(!(ItemUtilities.getStringFromItem(item, "is-SB").equals("true"))) return;

            if (event.getItem().hasItemMeta()) {
                if (ItemUtilities.isSBItem(event.getPlayer().getInventory().getItemInHand())) {
                    useSBItem(event, event.getPlayer().getInventory().getItemInHand());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onClick(InventoryClickEvent event) {
        if(true) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack used = event.getCurrentItem();

        if(used == null) return;
        if(!(used.hasItemMeta())) return;
        if(!(used.getItemMeta().hasDisplayName())) return;

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(used);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagCompound data = (compound.getCompound("ExtraAttributes") != null ? compound.getCompound("ExtraAttributes") : new NBTTagCompound());
        if(!(data.hasKey("is-SB"))) return;
        if(!(ItemUtilities.getStringFromItem(used, "is-SB").equals("true"))) return;

        ItemUtilities.getSBItem(used).clickedInInventoryAction(player, event);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        ItemStack used = player.getInventory().getItemInHand();

        if (event.getDamager().getType() != EntityType.PLAYER) return;
        if(used == null) return;
        if(!(used.hasItemMeta())) return;
        if(!(used.getItemMeta().hasDisplayName())) return;
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(used);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagCompound data = compound.getCompound("ExtraAttributes");
        if(!(data.hasKey("is-SB"))) return;
        if(!(ItemUtilities.getStringFromItem(used, "is-SB").equals("true"))) return;
        if(ItemUtilities.getIntFromItem(used,"SB-ID") == null) return;


        if (used != null) {
            if (used.getType() != Material.AIR) {
                if (ItemUtilities.isSBItem(used))
                    ItemUtilities.getSBItem(used).hitEntityAction(player, event, event.getEntity(), used);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(player.getItemInHand() == null) return;
        ItemStack item = player.getInventory().getItemInHand();

        if(item == null) return;
        if(!(item.hasItemMeta())) return;
        if(!(item.getItemMeta().hasDisplayName())) return;
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagCompound data = compound.getCompound("ExtraAttributes");
        if(!(data.hasKey("is-SB"))) return;
        if(!(ItemUtilities.getStringFromItem(item, "is-SB").equals("true"))) return;

        if (item == null || item.getType() == Material.AIR) return;
        if (ItemUtilities.isSBItem(item))
            ItemUtilities.getSBItem(item).breakBlockAction(player, event, event.getBlock(), item);
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        if(player.getItemInHand() == null) return;
        ItemStack item = player.getInventory().getItemInHand();

        if(item == null) return;
        if(!(item.hasItemMeta())) return;
        if(!(item.getItemMeta().hasDisplayName())) return;
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagCompound data = compound.getCompound("ExtraAttributes");
        if(!(data.hasKey("is-SB"))) return;
        if(!(ItemUtilities.getStringFromItem(item, "is-SB").equals("true"))) return;

        if (item == null || item.getType() == Material.AIR) return;
        if (ItemUtilities.isSBItem(item))
            ItemUtilities.getSBItem(item).playerFishAction(player, event, item);
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if(player.getItemInHand() == null) return;
        ItemStack item = player.getInventory().getItemInHand();

        if(item == null) return;
        if(!(item.hasItemMeta())) return;
        if(!(item.getItemMeta().hasDisplayName())) return;
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagCompound data = compound.getCompound("ExtraAttributes");
        if(!(data.hasKey("is-SB"))) return;
        if(!(ItemUtilities.getStringFromItem(item, "is-SB").equals("true"))) return;

        if (item == null || item.getType() == Material.AIR) return;
        if (ItemUtilities.isSBItem(item))
            ItemUtilities.getSBItem(item).playerShootAction(player, event, item);
    }

/*
    private void undoChanges(Player player, PlayerItemHeldEvent event) {
        SQLGetter getter = new SQLGetter(player, main);
        if(player.getInventory().getItem(event.getPreviousSlot()).getItemMeta().getDisplayName().contains("§6Aspect of the Dragons")) {
            getter.setStrength(getter.getStrength() - 250);
            getter.setCritDamage(getter.getCritDamage() - 150);
        }
    }
 */

/*
    @EventHandler
    public void onSwap(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getPlayer().getInventory().getItem(event.getNewSlot());
        SQLGetter getter = new SQLGetter(player, main);

        if(item == null) {
            undoChanges(player, event);
            return;
        }
        if(!(item.hasItemMeta())) return;
        if(!(item.getItemMeta().hasDisplayName())) return;
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        if(!(compound.hasKey("is-SB"))) {
            undoChanges(player, event);
            return;
        }
        if(!(ItemUtilities.getStringFromItem(item, "is-SB").equals("true"))) return;

        if (item == null || item.getType() == Material.AIR) return;
        if (ItemUtilities.isSBItem(item))
            ItemUtilities.getSBItem(item).onSwapAction(player, event, item);
        else {

        }
    }
 */

    private void useSBItem(PlayerInteractEvent event, ItemStack item) {
        Player player = event.getPlayer();
        if(item == null) return;
        if(!(item.hasItemMeta())) return;
        if(!(item.getItemMeta().hasDisplayName())) return;

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagCompound data = compound.getCompound("ExtraAttributes");
        if(!(data.hasKey("is-SB"))) return;
        if(!(ItemUtilities.getStringFromItem(item, "is-SB").equals("true"))) return;

        CustomItem sbitem = ItemUtilities.getSBItem(item);
        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            if (!player.isSneaking()) {
                sbitem.leftClickAirAction(player, item);
            } else {
                sbitem.shiftLeftClickAirAction(player, item);
            }
        } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!player.isSneaking()) {
                sbitem.leftClickBlockAction(player, event, event.getClickedBlock(), item);
            } else {
                sbitem.shiftLeftClickBlockAction(player, event, event.getClickedBlock(), item);
            }
        } else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (!player.isSneaking()) {
                sbitem.rightClickAirAction(player, event, item);
            } else {
                sbitem.shiftRightClickAirAction(player, event, item);
            }
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (!player.isSneaking()) {
                sbitem.rightClickBlockAction(player, event, event.getClickedBlock(), item);
            } else {
                sbitem.shiftRightClickBlockAction(player, event, event.getClickedBlock(), item);
            }
        }
    }
}
