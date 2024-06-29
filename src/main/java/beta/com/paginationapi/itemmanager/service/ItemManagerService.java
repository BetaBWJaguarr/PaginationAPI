package beta.com.paginationapi.itemmanager.service;

import beta.com.paginationapi.itemmanager.ItemManager;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ItemManagerService {
    CompletableFuture<ItemManager> createItemManager(UUID managerId);
    CompletableFuture<ItemManager> getItemManager(UUID managerId);
    CompletableFuture<Void> addItem(UUID managerId, ItemStack item);
    CompletableFuture<Void> removeItem(UUID managerId, ItemStack item);
    CompletableFuture<Void> clearItems(UUID managerId);
    CompletableFuture<List<ItemStack>> getItems(UUID managerId);
}