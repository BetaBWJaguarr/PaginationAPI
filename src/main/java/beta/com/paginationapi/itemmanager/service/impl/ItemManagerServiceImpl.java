package beta.com.paginationapi.itemmanager.service.impl;

import beta.com.paginationapi.itemmanager.ItemManager;
import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public class ItemManagerServiceImpl implements ItemManagerService {
    private ItemManager itemManager;


    @Override
    public ItemManager createItemManager() {
        if (itemManager == null) {
            itemManager = new ItemManager();
        }
        return itemManager;
    }

    @Override
    public ItemManager getItemManager() {
        return itemManager;
    }

    @Override
    public void addItem(ItemStack item) {
        itemManager.addItem(item);
    }

    @Override
    public void removeItem(ItemStack item) {
        itemManager.removeItem(item);
    }

    @Override
    public void clearItems() {
        itemManager.clearItems();
    }

    @Override
    public List<ItemStack> getItems() {
        return itemManager.getItems();
    }
}