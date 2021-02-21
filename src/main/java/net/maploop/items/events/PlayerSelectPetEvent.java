package net.maploop.items.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSelectPetEvent extends Event implements Cancellable {
    private Class<?> pet;
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private boolean isCanelled;
    private final Player player;

    public PlayerSelectPetEvent(Class<?> pet, Player player) {
        this.pet = pet;
        this.player = player;
        this.isCanelled = false;
    }

    @Override
    public boolean isCancelled() {
        return isCanelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCanelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public Class<?> getPet() {
        return this.pet;
    }

    public void setPet(Class<?> pet) {
        this.pet = pet;
    }

    public Player getPlayer() {
        return this.player;
    }
}
