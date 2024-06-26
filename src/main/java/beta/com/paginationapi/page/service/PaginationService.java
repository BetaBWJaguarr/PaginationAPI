package beta.com.paginationapi.page.service;

import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import beta.com.paginationapi.page.Pagination;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public interface PaginationService {

    ItemManagerService getItemManager();

    Pagination createMenu(int pageSize, ItemManagerService itemManager,UUID menuID);

    Pagination getMenu(UUID menuId);

    void setActiveMenu(UUID playerId, UUID menuId);

    void closeMenu(UUID playerId);

    UUID getActiveMenu(UUID playerId);

    List<ItemStack> getCurrentPageItems(UUID playerId);

    void rememberPages(UUID playerId, boolean remember);

    void setPageForPlayer(UUID playerId, int page);

    int getPageForPlayer(UUID playerId);

    void nextPage(UUID playerId);

    void previousPage(UUID playerId);

    boolean hasNextPage(UUID playerId);

    boolean hasPreviousPage(UUID playerId);

    boolean isPageEmpty(UUID playerId);

    boolean isPageFull(UUID playerId);

    void openPageForPlayer(UUID playerId, int pageNumber);

    int getPageSize();
}
