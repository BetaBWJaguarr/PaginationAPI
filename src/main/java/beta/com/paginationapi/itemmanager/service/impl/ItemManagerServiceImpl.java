package beta.com.paginationapi.itemmanager.service.impl;

import beta.com.paginationapi.errorevents.HandleExceptions;
import beta.com.paginationapi.itemmanager.ItemManager;
import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The ItemManagerServiceImpl class is an implementation of the ItemManagerService interface.
 * It provides methods for managing items in the application, each returning a CompletableFuture for asynchronous operation.
 *
 * The class maintains a ConcurrentMap of ItemManagers, each identified by a UUID.
 * It also uses an instance of HandleExceptions to handle any exceptions that occur during the execution of its methods.
 *
 * The createItemManager method creates a new ItemManager and adds it to the map.
 * The getItemManager method retrieves an existing ItemManager from the map.
 * The addItem method adds an item to a specified ItemManager.
 * The removeItem method removes an item from a specified ItemManager.
 * The clearItems method clears all items from a specified ItemManager.
 * The getItems method retrieves all items from a specified ItemManager.
 *
 * Each method checks for null parameters and handles exceptions by logging them and completing the returned CompletableFuture exceptionally.
 *
 * Usage:
 * ItemManagerService itemManagerService = new ItemManagerServiceImpl();
 * UUID managerId = ... // get a managerId
 * ItemStack item = ... // get an ItemStack
 * itemManagerService.addItem(managerId, item).thenAccept(void -> {
 *     // item has been added
 * });
 */

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
