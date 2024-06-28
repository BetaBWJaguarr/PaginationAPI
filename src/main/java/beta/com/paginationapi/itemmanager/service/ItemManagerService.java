package beta.com.paginationapi.itemmanager.service;

import beta.com.paginationapi.itemmanager.ItemManager;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.UUID;

public interface ItemManagerService {
    ItemManager createItemManager(UUID managerId);
    ItemManager getItemManager(UUID managerId);
    void addItem(UUID managerId, ItemStack item);
    void removeItem(UUID managerId, ItemStack item);
    void clearItems(UUID managerId);
    List<ItemStack> getItems(UUID managerId);
}