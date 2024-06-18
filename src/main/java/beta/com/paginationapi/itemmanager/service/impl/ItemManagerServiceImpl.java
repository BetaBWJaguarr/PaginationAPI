package beta.com.paginationapi.itemmanager.service.impl;

import beta.com.paginationapi.errorevents.HandleExceptions;
import beta.com.paginationapi.itemmanager.ItemManager;
import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemManagerServiceImpl implements ItemManagerService {

    private ItemManager itemManager;
    private HandleExceptions handleExceptions = new HandleExceptions();


    @Override
    public ItemManager createItemManager() {
        try {
            if (itemManager == null) {
                itemManager = new ItemManager();
            }
            return itemManager;
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "createItemManager");
            return null;
        }
    }

    @Override
    public ItemManager getItemManager() {
        return itemManager;
    }

    @Override
    public void addItem(ItemStack item) {
        if (item == null) {
            handleExceptions.handle(new IllegalArgumentException("Item cannot be null"), this.getClass().getSimpleName(), "addItem");
            return;
        }
        try {
            itemManager.addItem(item);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "addItem");
        }
    }

    @Override
    public void removeItem(ItemStack item) {
        if (item == null) {
            handleExceptions.handle(new IllegalArgumentException("Item cannot be null"), this.getClass().getSimpleName(), "removeItem");
            return;
        }
        try {
            itemManager.removeItem(item);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "removeItem");
        }
    }

    @Override
    public void clearItems() {
        try {
            itemManager.clearItems();
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "clearItems");
        }
    }

    @Override
    public List<ItemStack> getItems() {
        try {
            return itemManager.getItems();
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "getItems");
            return null;
        }
    }
}
