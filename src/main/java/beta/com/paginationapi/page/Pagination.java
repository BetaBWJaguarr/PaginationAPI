package beta.com.paginationapi.page;

import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Pagination {
    private final int pageSize;
    private final ItemManagerService itemManagerService;
    private final Map<UUID, Integer> playerPages = new HashMap<>();
    private final Map<UUID, Boolean> activeStatus = new HashMap<>();
    private final UUID managerId;

    public Pagination(int pageSize, ItemManagerService itemManagerService, UUID managerId) {
        this.pageSize = pageSize;
        this.itemManagerService = itemManagerService;
        this.managerId = managerId;
    }

    private int getPageStart(int page) {
        return page * pageSize;
    }

    private int getPageEnd(int page) {
        return Math.min((page + 1) * pageSize, itemManagerService.getItems(managerId).size());
    }

    public List<ItemStack> getCurrentPageItems(UUID playerId) {
        int page = playerPages.getOrDefault(playerId, 0);
        return itemManagerService.getItems(managerId).subList(getPageStart(page), getPageEnd(page));
    }

    public void rememberPages(UUID playerId, boolean remember) {
        if (!remember) playerPages.put(playerId, 0);
    }

    public void setPageForPlayer(UUID playerId, int page) {
        playerPages.put(playerId, page);
    }

    public int getPageForPlayer(UUID playerId) {
        return playerPages.getOrDefault(playerId, 0);
    }

    public void nextPage(UUID playerId) {
        if (hasNextPage(playerId)) playerPages.put(playerId, getPageForPlayer(playerId) + 1);
    }

    public void previousPage(UUID playerId) {
        if (hasPreviousPage(playerId)) playerPages.put(playerId, getPageForPlayer(playerId) - 1);
    }

    public boolean hasNextPage(UUID playerId) {
        return getPageEnd(getPageForPlayer(playerId)) < itemManagerService.getItems(managerId).size();
    }

    public boolean hasPreviousPage(UUID playerId) {
        return getPageForPlayer(playerId) > 0;
    }

    public boolean isPageEmpty() {
        return itemManagerService.getItems(managerId).subList(getPageStart(0), getPageEnd(0)).isEmpty();
    }

    public boolean isPageFull() {
        return itemManagerService.getItems(managerId).subList(getPageStart(0), getPageEnd(0)).size() == pageSize;
    }

    public void openPageForPlayer(UUID playerId, int pageNumber) {
        if (pageNumber >= 0 && getPageStart(pageNumber) < itemManagerService.getItems(managerId).size()) setPageForPlayer(playerId, pageNumber);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setActive(UUID playerId) {
        activeStatus.put(playerId, true);
    }

    public boolean isActive(UUID playerId) {
        return activeStatus.getOrDefault(playerId, false);
    }

    public void close(UUID playerId) {
        activeStatus.remove(playerId);
    }
}
