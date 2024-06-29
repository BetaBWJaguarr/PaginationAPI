package beta.com.paginationapi.itemmanager.service.impl;

import beta.com.paginationapi.errorevents.HandleExceptions;
import beta.com.paginationapi.itemmanager.ItemManager;
import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ItemManagerServiceImpl implements ItemManagerService {
    private final ConcurrentMap<UUID, ItemManager> itemManagers = new ConcurrentHashMap<>();
    private final HandleExceptions handleExceptions = new HandleExceptions();

    @Override
    public synchronized CompletableFuture<ItemManager> createItemManager(UUID managerId) {
        CompletableFuture<ItemManager> future = new CompletableFuture<>();
        if (managerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Manager ID cannot be null"), this.getClass().getSimpleName(), "createItemManager");
            future.complete(null);
            return future;
        }

        try {
            ItemManager itemManager = new ItemManager(managerId);
            itemManagers.put(managerId, itemManager);
            future.complete(itemManager);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "createItemManager");
            future.completeExceptionally(e);
        }
        return future;
    }

    @Override
    public synchronized CompletableFuture<ItemManager> getItemManager(UUID managerId) {
        CompletableFuture<ItemManager> future = new CompletableFuture<>();
        if (managerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Manager ID cannot be null"), this.getClass().getSimpleName(), "getItemManager");
            future.complete(null);
            return future;
        }

        try {
            ItemManager itemManager = itemManagers.get(managerId);
            if (itemManager != null) {
                future.complete(itemManager);
            } else {
                future.completeExceptionally(new IllegalArgumentException("ItemManager not found for ID: " + managerId));
                handleExceptions.handle(new IllegalArgumentException("ItemManager not found for ID: " + managerId), this.getClass().getSimpleName(), "getItemManager");
            }
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "getItemManager");
            future.completeExceptionally(e);
        }
        return future;
    }

    @Override
    public synchronized CompletableFuture<Void> addItem(UUID managerId, ItemStack item) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        if (managerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Manager ID cannot be null"), this.getClass().getSimpleName(), "addItem");
            future.completeExceptionally(new IllegalArgumentException("Manager ID cannot be null"));
            return future;
        }

        if (item == null) {
            handleExceptions.handle(new IllegalArgumentException("Item cannot be null"), this.getClass().getSimpleName(), "addItem");
            future.completeExceptionally(new IllegalArgumentException("Item cannot be null"));
            return future;
        }

        try {
            ItemManager itemManager = itemManagers.get(managerId);
            if (itemManager != null) {
                itemManager.addItem(item);
                future.complete(null);
            } else {
                future.completeExceptionally(new IllegalArgumentException("ItemManager not found for ID: " + managerId));
                handleExceptions.handle(new IllegalArgumentException("ItemManager not found for ID: " + managerId), this.getClass().getSimpleName(), "addItem");
            }
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "addItem");
            future.completeExceptionally(e);
        }
        return future;
    }

    @Override
    public synchronized CompletableFuture<Void> removeItem(UUID managerId, ItemStack item) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        if (managerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Manager ID cannot be null"), this.getClass().getSimpleName(), "removeItem");
            future.completeExceptionally(new IllegalArgumentException("Manager ID cannot be null"));
            return future;
        }

        if (item == null) {
            handleExceptions.handle(new IllegalArgumentException("Item cannot be null"), this.getClass().getSimpleName(), "removeItem");
            future.completeExceptionally(new IllegalArgumentException("Item cannot be null"));
            return future;
        }

        try {
            ItemManager itemManager = itemManagers.get(managerId);
            if (itemManager != null) {
                itemManager.removeItem(item);
                future.complete(null);
            } else {
                future.completeExceptionally(new IllegalArgumentException("ItemManager not found for ID: " + managerId));
                handleExceptions.handle(new IllegalArgumentException("ItemManager not found for ID: " + managerId), this.getClass().getSimpleName(), "removeItem");
            }
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "removeItem");
            future.completeExceptionally(e);
        }
        return future;
    }

    @Override
    public synchronized CompletableFuture<Void> clearItems(UUID managerId) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        if (managerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Manager ID cannot be null"), this.getClass().getSimpleName(), "clearItems");
            future.completeExceptionally(new IllegalArgumentException("Manager ID cannot be null"));
            return future;
        }

        try {
            ItemManager itemManager = itemManagers.get(managerId);
            if (itemManager != null) {
                itemManager.clearItems();
                future.complete(null);
            } else {
                future.completeExceptionally(new IllegalArgumentException("ItemManager not found for ID: " + managerId));
                handleExceptions.handle(new IllegalArgumentException("ItemManager not found for ID: " + managerId), this.getClass().getSimpleName(), "clearItems");
            }
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "clearItems");
            future.completeExceptionally(e);
        }
        return future;
    }

    @Override
    public synchronized CompletableFuture<List<ItemStack>> getItems(UUID managerId) {
        CompletableFuture<List<ItemStack>> future = new CompletableFuture<>();
        if (managerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Manager ID cannot be null"), this.getClass().getSimpleName(), "getItems");
            future.completeExceptionally(new IllegalArgumentException("Manager ID cannot be null"));
            return future;
        }

        try {
            ItemManager itemManager = itemManagers.get(managerId);
            if (itemManager != null) {
                List<ItemStack> itemList = itemManager.getItems();
                future.complete(itemList);
            } else {
                future.completeExceptionally(new IllegalArgumentException("ItemManager not found for ID: " + managerId));
                handleExceptions.handle(new IllegalArgumentException("ItemManager not found for ID: " + managerId), this.getClass().getSimpleName(), "getItems");
            }
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "getItems");
            future.completeExceptionally(e);
        }
        return future;
    }
}
