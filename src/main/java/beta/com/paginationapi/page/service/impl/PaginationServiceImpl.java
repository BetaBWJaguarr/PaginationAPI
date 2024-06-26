package beta.com.paginationapi.page.service.impl;

import beta.com.paginationapi.errorevents.HandleExceptions;
import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import beta.com.paginationapi.page.Pagination;
import beta.com.paginationapi.page.service.PaginationService;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * The PaginationServiceImpl class is an implementation of the PaginationService interface.
 * It provides a concrete implementation of the pagination system in the application.
 *
 * The class maintains a map of menus, each identified by a UUID, and a map of active menus for each player.
 * It uses an instance of ItemManagerService to manage the items that are displayed in the menus.
 *
 * The class provides methods for creating menus, getting and setting active menus, navigating through pages, and querying page states.
 * It also handles exceptions that occur during these operations, ensuring that the application can recover gracefully from errors.
 *
 * The PaginationServiceImpl class is a key part of the application's pagination system, providing the functionality needed to manage multiple menus and player states simultaneously.
 * getItemManager: Returns the ItemManagerService instance used by this class.
 *
 * createMenu: Creates a new Pagination menu with the specified page size, item manager, menu ID, and manager ID. It also handles exceptions related to invalid arguments and other issues.
 * getMenu: Retrieves a Pagination menu by its ID. It also handles exceptions related to invalid arguments and other issues.
 * setActiveMenu: Sets the active menu for a player. It also handles exceptions related to invalid arguments and other issues.
 * closeMenu: Closes the active menu for a player. It also handles exceptions related to invalid arguments and other issues.
 * getActiveMenu: Retrieves the active menu ID for a player. It also handles exceptions related to invalid arguments and other issues.
 * getCurrentPageItems: Retrieves the items on the current page for a player. It also handles exceptions related to invalid arguments and other issues.
 * rememberPages: Sets whether the system should remember the pages for a player. It also handles exceptions related to invalid arguments and other issues.
 * setPageForPlayer: Sets the current page for a player. It also handles exceptions related to invalid arguments and other issues.
 * getPageForPlayer: Retrieves the current page for a player. It also handles exceptions related to invalid arguments and other issues.
 * nextPage: Navigates to the next page for a player. It also handles exceptions related to invalid arguments and other issues.
 * previousPage: Navigates to the previous page for a player. It also handles exceptions related to invalid arguments and other issues.
 * hasNextPage: Checks if there is a next page for a player. It also handles exceptions related to invalid arguments and other issues.
 * hasPreviousPage: Checks if there is a previous page for a player. It also handles exceptions related to invalid arguments and other issues.
 * isPageEmpty: Checks if the current page for a player is empty. It also handles exceptions related to invalid arguments and other issues.
 * isPageFull: Checks if the current page for a player is full. It also handles exceptions related to invalid arguments and other issues.
 * openPageForPlayer: Opens a specific page for a player. It also handles exceptions related to invalid arguments and other issues.
 * getPageSize: Retrieves the page size. It also handles exceptions related to invalid arguments and other issues.
 */

public class PaginationServiceImpl implements PaginationService {

    private final Map<UUID, Pagination> menus = new HashMap<>();
    private final Map<UUID, UUID> activeMenus = new HashMap<>();
    private final ItemManagerService itemManager;
    private final HandleExceptions handleExceptions = new HandleExceptions();

    public PaginationServiceImpl(ItemManagerService itemManager) {
        this.itemManager = itemManager;
    }

    @Override
    public ItemManagerService getItemManager() {
        return itemManager;
    }

    @Override
    public Pagination createMenu(int pageSize, ItemManagerService itemManager,UUID menuId,UUID managerID) {

        if (pageSize <= 0) {
            handleExceptions.handle(new IllegalArgumentException("Page size must be greater than 0"), this.getClass().getSimpleName(), "createMenu");
        }

        if (itemManager == null) {
            handleExceptions.handle(new IllegalArgumentException("Item manager cannot be null"), this.getClass().getSimpleName(), "createMenu");
        }

        try {
            Pagination pagination = new Pagination(pageSize, itemManager,managerID);
            menus.put(menuId, pagination);
            return pagination;
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "createMenu");
            return null;
        }
    }

    @Override
    public Pagination getMenu(UUID menuId) {
        if (menuId == null) {
            handleExceptions.handle(new IllegalArgumentException("Menu ID cannot be null"), this.getClass().getSimpleName(), "getMenu");
            return null;
        }

        try {
            return menus.get(menuId);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "getMenu");
            return null;
        }
    }

    @Override
    public void setActiveMenu(UUID playerId, UUID menuId) {
        if (playerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Player ID cannot be null"), this.getClass().getSimpleName(), "setActiveMenu");
            return;
        }

        if (menuId == null) {
            handleExceptions.handle(new IllegalArgumentException("Menu ID cannot be null"), this.getClass().getSimpleName(), "setActiveMenu");
            return;
        }

        try {
            activeMenus.put(playerId, menuId);
            menus.get(menuId).setActive(playerId);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "setActiveMenu");
        }
    }

    @Override
    public void closeMenu(UUID playerId) {
        if (playerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Player ID cannot be null"), this.getClass().getSimpleName(), "closeMenu");
            return;
        }
        try {
            UUID activeMenuId = activeMenus.get(playerId);
            if (activeMenuId == null) {
                handleExceptions.handle(new IllegalArgumentException("Player does not have an active menu"), this.getClass().getSimpleName(), "closeMenu");
            }
            menus.get(activeMenuId).close(playerId);
            activeMenus.remove(playerId);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "closeMenu");
        }
    }

    @Override
    public UUID getActiveMenu(UUID playerId) {

        if (playerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Player ID cannot be null"), this.getClass().getSimpleName(), "getActiveMenu");
            return null;
        }
        try {
            return activeMenus.get(playerId);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "getActiveMenu");
            return null;
        }
    }

    @Override
    public List<ItemStack> getCurrentPageItems(UUID playerId) {

        if (playerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Player ID cannot be null"), this.getClass().getSimpleName(), "getCurrentPageItems");
            return Collections.emptyList();
        }
        try {
            UUID activeMenuId = activeMenus.get(playerId);
            if (activeMenuId == null) {
                handleExceptions.handle(new IllegalArgumentException("Player does not have an active menu"), this.getClass().getSimpleName(), "getCurrentPageItems");
                return Collections.emptyList();
            }
            return menus.get(activeMenuId).getCurrentPageItems(playerId);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "getCurrentPageItems");
            return Collections.emptyList();
        }
    }

    @Override
    public void rememberPages(UUID playerId, boolean remember) {

        if (playerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Player ID cannot be null"), this.getClass().getSimpleName(), "rememberPages");
            return;
        }

        try {
            UUID activeMenuId = activeMenus.get(playerId);
            if (activeMenuId == null) {
                handleExceptions.handle(new IllegalArgumentException("Player does not have an active menu"), this.getClass().getSimpleName(), "rememberPages");
                return;
            }
            menus.get(activeMenuId).rememberPages(playerId, remember);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "rememberPages");
        }
    }

    @Override
    public void setPageForPlayer(UUID playerId, int page) {
        if (playerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Player ID cannot be null"), this.getClass().getSimpleName(), "setPageForPlayer");
            return;
        }

        if (page < 0) {
            handleExceptions.handle(new IllegalArgumentException("Page number cannot be negative"), this.getClass().getSimpleName(), "setPageForPlayer");
            return;
        }

        try {
            UUID activeMenuId = activeMenus.get(playerId);

            if (activeMenuId == null) {
                handleExceptions.handle(new IllegalArgumentException("Player does not have an active menu"), this.getClass().getSimpleName(), "setPageForPlayer");
                return;
            }
            menus.get(activeMenuId).setPageForPlayer(playerId, page);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "setPageForPlayer");
        }
    }

    @Override
    public int getPageForPlayer(UUID playerId) {
        if (playerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Player ID cannot be null"), this.getClass().getSimpleName(), "getPageForPlayer");
            return 0;
        }

        try {
            UUID activeMenuId = activeMenus.get(playerId);
            if (activeMenuId == null) {
                handleExceptions.handle(new IllegalArgumentException("Player does not have an active menu"), this.getClass().getSimpleName(), "getPageForPlayer");
                return 0;
            }
            return menus.get(activeMenuId).getPageForPlayer(playerId);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "getPageForPlayer");
            return 0;
        }
    }

    @Override
    public void nextPage(UUID playerId) {
        if (playerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Player ID cannot be null"), this.getClass().getSimpleName(), "nextPage");
            return;
        }

        try {
            UUID activeMenuId = activeMenus.get(playerId);

            if (activeMenuId == null) {
                handleExceptions.handle(new IllegalArgumentException("Player does not have an active menu"), this.getClass().getSimpleName(), "nextPage");
                return;
            }
            menus.get(activeMenuId).nextPage(playerId);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "nextPage");
        }
    }

    @Override
    public void previousPage(UUID playerId) {
         if (playerId == null) {
             handleExceptions.handle(new IllegalArgumentException("Player ID cannot be null"), this.getClass().getSimpleName(), "previousPage");
             return;
         }

        try {
            UUID activeMenuId = activeMenus.get(playerId);
            if (activeMenuId == null) {
                handleExceptions.handle(new IllegalArgumentException("Player does not have an active menu"), this.getClass().getSimpleName(), "previousPage");
                return;
            }
            menus.get(activeMenuId).previousPage(playerId);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "previousPage");
        }
    }

    @Override
    public boolean hasNextPage(UUID playerId) {
        if (playerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Player ID cannot be null"), this.getClass().getSimpleName(), "hasNextPage");
            return false;
        }

        try {
            UUID activeMenuId = activeMenus.get(playerId);
            if (activeMenuId == null) {
                return false;
            }
            return menus.get(activeMenuId).hasNextPage(playerId);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "hasNextPage");
            return false;
        }
    }

    @Override
    public boolean hasPreviousPage(UUID playerId) {
        if (playerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Player ID cannot be null"), this.getClass().getSimpleName(), "hasPreviousPage");
            return false;
        }

        try {
            UUID activeMenuId = activeMenus.get(playerId);
            if (activeMenuId == null) {
                handleExceptions.handle(new IllegalArgumentException("Player does not have an active menu"), this.getClass().getSimpleName(), "hasPreviousPage");
                return false;
            }
            return menus.get(activeMenuId).hasPreviousPage(playerId);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "hasPreviousPage");
            return false;
        }
    }

    @Override
    public boolean isPageEmpty(UUID playerId) {
        if (playerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Player ID cannot be null"), this.getClass().getSimpleName(), "isPageEmpty");
            return true;
        }

        try {
            UUID activeMenuId = activeMenus.get(playerId);
            if (activeMenuId == null) {
                return true;
            }
            return menus.get(activeMenuId).isPageEmpty();
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "isPageEmpty");
            return true;
        }
    }

    @Override
    public boolean isPageFull(UUID playerId) {
        if (playerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Player ID cannot be null"), this.getClass().getSimpleName(), "isPageFull");
            return false;
        }

        try {
            UUID activeMenuId = activeMenus.get(playerId);
            if (activeMenuId == null) {
                handleExceptions.handle(new IllegalArgumentException("Player does not have an active menu"), this.getClass().getSimpleName(), "isPageFull");
                return false;
            }
            return menus.get(activeMenuId).isPageFull();
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "isPageFull");
            return false;
        }
    }

    @Override
    public void openPageForPlayer(UUID playerId, int pageNumber) {

        if (playerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Player ID cannot be null"), this.getClass().getSimpleName(), "openPageForPlayer");
            return;
        }

        if (pageNumber < 0) {
            handleExceptions.handle(new IllegalArgumentException("Page number cannot be negative"), this.getClass().getSimpleName(), "openPageForPlayer");
            return;
        }

        try {
            UUID activeMenuId = activeMenus.get(playerId);
            if (activeMenuId == null) {
                handleExceptions.handle(new IllegalArgumentException("Player does not have an active menu"), this.getClass().getSimpleName(), "openPageForPlayer");
                return;
            }
            menus.get(activeMenuId).openPageForPlayer(playerId, pageNumber);

        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "openPageForPlayer");
        }
    }

    @Override
    public int getPageSize() {
        try {
            UUID firstMenuId = menus.keySet().stream().findFirst().orElse(null);
            if (firstMenuId == null) {
                handleExceptions.handle(new IllegalArgumentException("No menus have been created"), this.getClass().getSimpleName(), "getPageSize");
            }
            return menus.get(firstMenuId).getPageSize();
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "getPageSize");
            return 0;
        }
    }
}
