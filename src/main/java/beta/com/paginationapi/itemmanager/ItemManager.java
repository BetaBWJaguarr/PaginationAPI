package beta.com.paginationapi.itemmanager;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The ItemManager class serves as a unique container for items within the application, identified by a UUID.
 * It is designed to manage a collection of ItemStacks, providing a way to manipulate this collection in a thread-safe manner.
 *
 * The class encapsulates the concept of an item manager in the application, providing a higher level of abstraction for item management.
 * It hides the complexity of item management, such as maintaining the list of items and providing thread-safe operations, from the rest of the application.
 *
 * The ItemManager class is a key part of the application's item management system, working in conjunction with other classes such as ItemManagerService and ItemManagerServiceImpl.
 * It plays a crucial role in ensuring that items are managed correctly and efficiently in the application.
 */

public class ItemManager {
    private final UUID id;
    private final List<ItemStack> items;

    public ItemManager(UUID id) {
        this.id = id;
        this.items = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void addItem(ItemStack item) {
        this.items.add(item);
    }

    public void removeItem(ItemStack item) {
        this.items.remove(item);
    }

    public void clearItems() {
        this.items.clear();
    }

    public List<ItemStack> getItems() {
        return this.items;
    }
}
