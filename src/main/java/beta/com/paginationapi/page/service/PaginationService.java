package beta.com.paginationapi.page.service;

import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import beta.com.paginationapi.page.Pagination;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

/**
 * The PaginationService interface defines the contract for a service that manages pagination in the application.
 * It provides methods for creating menus, getting and setting active menus, navigating through pages, and querying page states.
 *
 * The interface is designed to work with the ItemManagerService, which manages items that are displayed in the pages.
 * It also uses UUIDs to identify menus and players, providing a way to manage multiple menus and player states simultaneously.
 *
 * The methods in this interface allow the application to create a flexible and robust pagination system, where each player can have their own active menu and navigate through it independently.
 */

public interface PaginationService {

    ItemManagerService getItemManager();

    Pagination createMenu(int pageSize, ItemManagerService itemManager,UUID menuID,UUID managerID);

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
