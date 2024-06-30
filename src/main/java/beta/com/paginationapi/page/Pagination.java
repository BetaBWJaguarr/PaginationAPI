package beta.com.paginationapi.page;

import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import beta.com.paginationapi.page.utils.PaginationUtils;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * The Pagination class manages the pagination system for displaying items to individual players identified by UUIDs.
 *
 * This class maintains player-specific pagination states using maps:
 * - Player pages map: Tracks the current page for each player.
 * - Active status map: Indicates whether a player is currently active.
 *
 * Pagination relies on the ItemManagerService to handle item management:
 * - Items are fetched and organized into pages based on a specified page size.
 *
 * Key functionalities include:
 * - Retrieving items on the current page for a player, using PaginationUtils for index calculations.
 * - Navigating between pages by updating player pages and checking for next or previous pages.
 * - Checking if a page is empty or full based on item availability and page size.
 * - Opening a specific page for a player after validating the page number.
 * - Providing access to the configured page size.
 * - Managing player active status updates in the active status map.
 *
 * The Pagination class supports multiple managers identified by manager IDs within the ItemManagerService,
 * allowing simultaneous management of different item sets.
 *
 * Overall, it offers a robust implementation for handling paginated views of items across multiple players.
 */

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

    public List<ItemStack> getCurrentPageItems(UUID playerId) throws ExecutionException, InterruptedException {
        int page = playerPages.getOrDefault(playerId, 0);
        return PaginationUtils.getItemsForPage(itemManagerService, managerId, page, pageSize);
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

    public void nextPage(UUID playerId) throws ExecutionException, InterruptedException {
        if (PaginationUtils.hasNextPage(getPageForPlayer(playerId), pageSize, itemManagerService.getItems(managerId).get().size())) {
            playerPages.put(playerId, getPageForPlayer(playerId) + 1);
        }
    }

    public void previousPage(UUID playerId) {
        if (PaginationUtils.hasPreviousPage(getPageForPlayer(playerId))) {
            playerPages.put(playerId, getPageForPlayer(playerId) - 1);
        }
    }

    public boolean hasNextPage(UUID playerId) throws ExecutionException, InterruptedException {
        return PaginationUtils.hasNextPage(getPageForPlayer(playerId), pageSize, itemManagerService.getItems(managerId).get().size());
    }

    public boolean hasPreviousPage(UUID playerId) {
        return PaginationUtils.hasPreviousPage(getPageForPlayer(playerId));
    }

    public boolean isPageEmpty() throws ExecutionException, InterruptedException {
        return PaginationUtils.isPageEmpty(itemManagerService, managerId, pageSize);
    }

    public boolean isPageFull() throws ExecutionException, InterruptedException {
        return PaginationUtils.isPageFull(itemManagerService, managerId, pageSize);
    }

    public void openPageForPlayer(UUID playerId, int pageNumber) throws ExecutionException, InterruptedException {
        if (pageNumber >= 0 && PaginationUtils.getPageStart(pageNumber, pageSize) < itemManagerService.getItems(managerId).get().size()) {
            setPageForPlayer(playerId, pageNumber);
        }
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
