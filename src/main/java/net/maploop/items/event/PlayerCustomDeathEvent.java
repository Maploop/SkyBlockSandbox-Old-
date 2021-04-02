package net.maploop.items.event;

import net.maploop.items.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerCustomDeathEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private boolean isCancelled = false;
    private final Player player;
    private final EntityDamageEvent.DamageCause deathCause;
    private final User user;

    public PlayerCustomDeathEvent(Player player, User user, EntityDamageEvent.DamageCause deathCause) {
        this.deathCause = deathCause;
        this.user = user;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public EntityDamageEvent.DamageCause getDeathCause() {
        return deathCause;
    }

    public User getUser() {
        return user;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        isCancelled = b;
    }
}
