package net.maploop.items.data;

import net.maploop.items.baseAbility.InstantTransmission;
import net.maploop.items.baseAbility.WitherImpact;
import net.maploop.items.item.ItemUtilities;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class AbilityHandler implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        if(true) return;

        if(event.getPlayer().getItemInHand() == null) return;
        if(event.getItem().getType().equals(Material.AIR)) return;
        if(!event.getItem().hasItemMeta()) return;
        if(!AbilityData.hasAbility(event.getItem())) return;
        ItemStack item = event.getItem();
        Player player = event.getPlayer();

        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            for(int i = 0; i < 6; ++i) {
                if(AbilityData.retrieveData(EnumAbilityData.FUNCTION, item, i).equals("RIGHT_CLICK")) {
                    switch (String.valueOf(AbilityData.retrieveData(EnumAbilityData.BASE_ABILITY, event.getItem(), i))) {
                        case "INSTANT_TRANSMISSION":
                            new InstantTransmission(player,
                                    (ItemUtilities.isInteger(AbilityData.retrieveData(EnumAbilityData.DAMAGE, item, i).toString()) ? Integer.parseInt(AbilityData.retrieveData(EnumAbilityData.DAMAGE, item, i).toString()) : 0),
                                    (ItemUtilities.isInteger(AbilityData.retrieveData(EnumAbilityData.COOLDOWN, item, i).toString()) ? Integer.parseInt(AbilityData.retrieveData(EnumAbilityData.COOLDOWN, item, i).toString()) : 0),
                                    (ItemUtilities.isInteger(AbilityData.retrieveData(EnumAbilityData.MANA_COST, item, i).toString()) ? Integer.parseInt(AbilityData.retrieveData(EnumAbilityData.MANA_COST, item, i).toString()) : 0)).run();
                            break;
                        case "WITHER_IMPACT":
                            new WitherImpact(player,
                                    (ItemUtilities.isInteger(AbilityData.retrieveData(EnumAbilityData.DAMAGE, item, i).toString()) ? Integer.parseInt(AbilityData.retrieveData(EnumAbilityData.DAMAGE, item, i).toString()) : 0),
                                    (ItemUtilities.isInteger(AbilityData.retrieveData(EnumAbilityData.COOLDOWN, item, i).toString()) ? Integer.parseInt(AbilityData.retrieveData(EnumAbilityData.COOLDOWN, item, i).toString()) : 0),
                                    (ItemUtilities.isInteger(AbilityData.retrieveData(EnumAbilityData.MANA_COST, item, i).toString()) ? Integer.parseInt(AbilityData.retrieveData(EnumAbilityData.MANA_COST, item, i).toString()) : 0)).run();
                            break;
                    }
                }
            }
            return;
        }
        if(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            for(int i = 0; i < 6; ++i) {
                if(AbilityData.retrieveData(EnumAbilityData.FUNCTION, item, i).equals("LEFT_CLICK")) {

                }
            }
        }
    }
}
