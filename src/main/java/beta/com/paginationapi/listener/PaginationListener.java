package beta.com.paginationapi.listener;

import beta.com.paginationapi.itemmanager.ItemManager;
import beta.com.paginationapi.page.Pagination;
import org.bukkit.inventory.ItemStack;

public class PaginationListener {

    private final Pagination pagination;
    private final ItemManager itemManager;

    public PaginationListener(Pagination pagination,ItemManager itemManager) {
        this.pagination = pagination;
        this.itemManager = itemManager;
    }

    public void onNextPage() {
        if (pagination.hasNextPage()) {
            pagination.nextPage();
        }
    }

    public void onPreviousPage() {
        if (pagination.hasPreviousPage()) {
            pagination.previousPage();
        }
    }

    public void onAddItem(ItemStack item) {
        itemManager.addItem(item);
    }

    public void onRemoveItem(ItemStack item) {
        itemManager.removeItem(item);
    }
}
