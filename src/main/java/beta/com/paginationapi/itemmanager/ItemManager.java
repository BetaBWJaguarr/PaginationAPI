package beta.com.paginationapi.itemmanager;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    private List<ItemStack> items;

    public ItemManager() {
        this.items = new ArrayList<>();
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