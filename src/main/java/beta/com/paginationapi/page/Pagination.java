package beta.com.paginationapi.page;

import beta.com.paginationapi.itemmanager.ItemManager;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Pagination {

    private final int pageSize;
    private ItemManager itemManager;
    private int currentPage;

    public Pagination(int pageSize, ItemManager itemManager) {
        this.pageSize = pageSize;
        this.itemManager = itemManager;
        this.currentPage = 0;
    }

    public List<ItemStack> getCurrentPageItems() {
        int start = currentPage * pageSize;
        int end = Math.min((currentPage + 1) * pageSize, itemManager.getItems().size());
        return this.itemManager.getItems().subList(start, end);
    }

    public boolean hasNextPage() {
        return (currentPage + 1) * pageSize < itemManager.getItems().size();
    }

    public boolean hasPreviousPage() {
        return currentPage > 0;
    }

    public void nextPage() {
        if (hasNextPage()) {
            currentPage++;
        }
    }

    public void previousPage() {
        if (hasPreviousPage()) {
            currentPage--;
        }
    }

    public boolean isPageEmpty() {
        int start = currentPage * pageSize;
        int end = Math.min((currentPage + 1) * pageSize, itemManager.getItems().size());
        return this.itemManager.getItems().subList(start, end).isEmpty();
    }

    public boolean isPageFull() {
        int start = currentPage * pageSize;
        int end = Math.min((currentPage + 1) * pageSize, itemManager.getItems().size());
        return this.itemManager.getItems().subList(start, end).size() == pageSize;
    }
}