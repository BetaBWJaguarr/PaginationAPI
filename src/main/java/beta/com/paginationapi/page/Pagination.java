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
    private Map<UUID, Integer> playerPages = new HashMap<>();
    private int currentPage = 0;

    public Pagination(int pageSize, ItemManagerService itemManager) {
        this.pageSize = pageSize;
        this.itemManager = itemManager;
    }

    public List<ItemStack> getCurrentPageItems(UUID playerId) {
        int currentPage = getCurrentPageForPlayer(playerId);
        int start = currentPage * pageSize;
        int end = Math.min((currentPage + 1) * pageSize, itemManager.getItems().size());
        return this.itemManager.getItems().subList(start, end);
    }

    public void rememberPages(UUID playerId, boolean remember) {
        if (!remember) this.playerPages.put(playerId, 0);
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
        if (hasNextPage(playerId)) this.playerPages.put(playerId, getCurrentPageForPlayer(playerId) + 1);
    }

    public void previousPage(UUID playerId) {
        if (hasPreviousPage(playerId)) this.playerPages.put(playerId, getCurrentPageForPlayer(playerId) - 1);
    }

    public boolean hasNextPage(UUID playerId) {
        return (getCurrentPageForPlayer(playerId) + 1) * pageSize < itemManager.getItems().size();
    }

    public boolean hasPreviousPage(UUID playerId) {
        return getCurrentPageForPlayer(playerId) > 0;
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
        if (pageNumber >= 0 && pageNumber * pageSize < itemManager.getItems().size()) setPageForPlayer(playerId, pageNumber);
    }

    public int getPageSize() {
        return pageSize;
    }
}