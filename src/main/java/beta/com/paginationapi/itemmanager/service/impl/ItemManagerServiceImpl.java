package beta.com.paginationapi.itemmanager.service.impl;

import beta.com.paginationapi.errorevents.HandleExceptions;
import beta.com.paginationapi.itemmanager.ItemManager;
import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemManagerServiceImpl implements ItemManagerService {
    private final Map<UUID, ItemManager> itemManagers = new HashMap<>();
    private final HandleExceptions handleExceptions = new HandleExceptions();

    @Override
    public ItemManager createItemManager(UUID managerId) {
        if (managerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Manager ID cannot be null"), this.getClass().getSimpleName(), "createItemManager");
            return null;
        }

        try {
            ItemManager itemManager = new ItemManager(managerId);
            itemManagers.put(managerId, itemManager);
            return itemManager;
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "createItemManager");
            return null;
        }
    }

    @Override
    public ItemManager getItemManager(UUID managerId) {
        if (managerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Manager ID cannot be null"), this.getClass().getSimpleName(), "getItemManager");
            return null;
        }

        try {
            return itemManagers.get(managerId);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "getItemManager");
            return null;
        }
    }

    @Override
    public void addItem(UUID managerId, ItemStack item) {
        if (managerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Manager ID cannot be null"), this.getClass().getSimpleName(), "addItem");
            return;
        }

        if (item == null) {
            handleExceptions.handle(new IllegalArgumentException("Item cannot be null"), this.getClass().getSimpleName(), "addItem");
            return;
        }

        try {
            itemManagers.get(managerId).addItem(item);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "addItem");
        }
    }

    @Override
    public void removeItem(UUID managerId, ItemStack item) {
        if (managerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Manager ID cannot be null"), this.getClass().getSimpleName(), "removeItem");
            return;
        }

        if (item == null) {
            handleExceptions.handle(new IllegalArgumentException("Item cannot be null"), this.getClass().getSimpleName(), "removeItem");
            return;
        }

        try {
            itemManagers.get(managerId).removeItem(item);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "removeItem");
        }
    }

    @Override
    public void clearItems(UUID managerId) {
        if (managerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Manager ID cannot be null"), this.getClass().getSimpleName(), "clearItems");
            return;
        }

        try {
            itemManagers.get(managerId).clearItems();
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "clearItems");
        }
    }

    @Override
    public List<ItemStack> getItems(UUID managerId) {
        if (managerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Manager ID cannot be null"), this.getClass().getSimpleName(), "getItems");
            return null;
        }

        try {
            return itemManagers.get(managerId).getItems();
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "getItems");
            return null;
        }
    }
}
