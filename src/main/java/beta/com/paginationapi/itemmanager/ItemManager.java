package beta.com.paginationapi.itemmanager;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
