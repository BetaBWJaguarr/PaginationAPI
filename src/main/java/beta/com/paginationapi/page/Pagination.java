package beta.com.paginationapi.page;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Pagination {

    private final int pageSize;
    private List<ItemStack> items;
    private int currentPage;

    public Pagination(int pageSize, List<ItemStack> items) {
        this.pageSize = pageSize;
        this.items = new ArrayList<>(items);
        this.currentPage = 0;
    }

    public List<ItemStack> getCurrentPageItems() {
        int start = currentPage * pageSize;
        int end = Math.min((currentPage + 1) * pageSize, items.size());
        return this.items.subList(start, end);
    }

    public boolean hasNextPage() {
        return (currentPage + 1) * pageSize < items.size();
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

    public void setItems(List<ItemStack> items) {
        this.items = new ArrayList<>(items);
    }
}