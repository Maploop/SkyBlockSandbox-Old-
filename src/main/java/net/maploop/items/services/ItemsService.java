package net.maploop.items.services;

import net.maploop.items.user.User;

public abstract class ItemsService {
    private final User user;
    public ItemsService(User user) {
        this.user = user;
    }

    public abstract String getServiceName();
}
