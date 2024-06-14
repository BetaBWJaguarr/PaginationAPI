package beta.com.paginationapi.itemmanager.service;

import beta.com.paginationapi.itemmanager.ItemManager;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public interface ItemManagerService {
    ItemManager createItemManager();
    ItemManager getItemManager();
    void addItem(ItemStack item);
    void removeItem(ItemStack item);
    void clearItems();
    List<ItemStack> getItems();
}