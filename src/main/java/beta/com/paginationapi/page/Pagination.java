package beta.com.paginationapi.page;

import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Pagination {

    private final int pageSize;
    private ItemManagerService itemManager;
    private int currentPage;
    private Map<UUID, Integer> playerPages;

    public Pagination(int pageSize, ItemManagerService itemManager) {
        this.pageSize = pageSize;
        this.itemManager = itemManager;
        this.playerPages = new HashMap<>();
        this.currentPage = 0;
    }

    public List<ItemStack> getCurrentPageItems(UUID playerId) {
        int currentPage = getCurrentPageForPlayer(playerId);
        int start = currentPage * pageSize;
        int end = Math.min((currentPage + 1) * pageSize, itemManager.getItems().size());
        return this.itemManager.getItems().subList(start, end);
    }

    public void rememberPages(UUID playerId, boolean remember) {
        if (!remember) {
            this.playerPages.put(playerId, 0);
        }
    }

    public void setPageForPlayer(UUID playerId, int page) {
        this.playerPages.put(playerId, page);
    }

    public int getPageForPlayer(UUID playerId) {
        return this.playerPages.getOrDefault(playerId, 0);
    }

    public int getCurrentPageForPlayer(UUID playerId) {
        return this.playerPages.getOrDefault(playerId, 0);
    }

    public void nextPage(UUID playerId) {
        int currentPage = this.playerPages.getOrDefault(playerId, 0);
        if (hasNextPage(playerId)) {
            this.playerPages.put(playerId, currentPage + 1);
        }
    }

    public void previousPage(UUID playerId) {
        int currentPage = this.playerPages.getOrDefault(playerId, 0);
        if (hasPreviousPage(playerId)) {
            this.playerPages.put(playerId, currentPage - 1);
        }
    }

    public boolean hasNextPage(UUID playerId) {
        int currentPage = this.playerPages.getOrDefault(playerId, 0);
        return (currentPage + 1) * pageSize < itemManager.getItems().size();
    }

    public boolean hasPreviousPage(UUID playerId) {
        int currentPage = this.playerPages.getOrDefault(playerId, 0);
        return currentPage > 0;
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

    public void openPageForPlayer(UUID playerId, int pageNumber) {
        if (pageNumber < 0 || pageNumber * pageSize >= itemManager.getItems().size()) {
            return;
        }
        setPageForPlayer(playerId, pageNumber);
    }

    public int getPageSize() {
        return pageSize;
    }
}