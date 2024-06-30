package beta.com.paginationapi.itemmanager.service;

import beta.com.paginationapi.itemmanager.ItemManager;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * The ItemManagerService interface defines the contract for managing items in the application.
 * It provides methods for creating an ItemManager, getting an ItemManager, adding an item, removing an item, clearing items, and getting items.
 *
 * Each method returns a CompletableFuture, allowing the operations to be performed asynchronously and their results to be obtained when ready.
 *
 * The createItemManager method takes a managerId and returns a CompletableFuture that completes with the created ItemManager.
 *
 * The getItemManager method takes a managerId and returns a CompletableFuture that completes with the retrieved ItemManager.
 *
 * The addItem method takes a managerId and an ItemStack to be added. It returns a CompletableFuture that completes when the item has been added.
 *
 * The removeItem method takes a managerId and an ItemStack to be removed. It returns a CompletableFuture that completes when the item has been removed.
 *
 * The clearItems method takes a managerId and returns a CompletableFuture that completes when all items have been cleared.
 *
 * The getItems method takes a managerId and returns a CompletableFuture that completes with a list of ItemStacks.
 *
 * Usage:
 * ItemManagerService itemManagerService = ... // get an instance of ItemManagerService
 * UUID managerId = ... // get a managerId
 * ItemStack item = ... // get an ItemStack
 * itemManagerService.addItem(managerId, item).thenAccept(void -> {
 *     // item has been added
 * });
 */

public interface ItemManagerService {
    CompletableFuture<ItemManager> createItemManager(UUID managerId);
    CompletableFuture<ItemManager> getItemManager(UUID managerId);
    CompletableFuture<Void> addItem(UUID managerId, ItemStack item);
    CompletableFuture<Void> removeItem(UUID managerId, ItemStack item);
    CompletableFuture<Void> clearItems(UUID managerId);
    CompletableFuture<List<ItemStack>> getItems(UUID managerId);
}