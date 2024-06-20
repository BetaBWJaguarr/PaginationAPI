package beta.com.paginationapi.page.service.impl;

import beta.com.paginationapi.errorevents.HandleExceptions;
import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import beta.com.paginationapi.page.Pagination;
import beta.com.paginationapi.page.service.PaginationService;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class PaginationServiceImpl implements PaginationService {

    private Pagination pagination;
    private int pageSize;
    private ItemManagerService itemManager;
    private HandleExceptions handleExceptions = new HandleExceptions();

    public PaginationServiceImpl(int pageSize, ItemManagerService itemManager) {
        if (pageSize <= 0) {
            handleExceptions.handle(new IllegalArgumentException("Page size must be greater than zero"), this.getClass().getSimpleName(), "PaginationServiceImpl");
        }
        if (itemManager == null) {
            handleExceptions.handle(new IllegalArgumentException("ItemManagerService cannot be null"), this.getClass().getSimpleName(), "PaginationServiceImpl");
        }
        this.pageSize = pageSize;
        this.itemManager = itemManager;
    }


    @Override
    public ItemManagerService getItemManager() {
        return itemManager;
    }

    @Override
    public Pagination createPagination() {
        try {
            if (pagination == null) {
                pagination = new Pagination(pageSize, itemManager);
            }
            return pagination;
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "createPagination");
            return null;
        }
    }

    @Override
    public Pagination getPagination() {
        return null;
    }

    @Override
    public List<ItemStack> getCurrentPageItems(UUID playerId) {
        if (playerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Player ID cannot be null"), this.getClass().getSimpleName(), "getCurrentPageItems");
            return null;
        }
        try {
            return pagination.getCurrentPageItems(playerId);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "getCurrentPageItems");
            return null;
        }
    }

    @Override
    public void rememberPages(UUID playerId, boolean remember) {
        if (playerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Player ID cannot be null"), this.getClass().getSimpleName(), "rememberPages");
            return;
        }
        try {
            pagination.rememberPages(playerId, remember);
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
            pagination.setPageForPlayer(playerId, page);
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
            return pagination.getPageForPlayer(playerId);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "getPageForPlayer");
            return 0;
        }
    }

    @Override
    public int getCurrentPageForPlayer(UUID playerId) {
        if (playerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Player ID cannot be null"), this.getClass().getSimpleName(), "getCurrentPageForPlayer");
            return 0;
        }
        try {
            return pagination.getPageForPlayer(playerId);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "getCurrentPageForPlayer");
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
            pagination.nextPage(playerId);
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
            pagination.previousPage(playerId);
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
            return pagination.hasNextPage(playerId);
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
            return pagination.hasPreviousPage(playerId);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "hasPreviousPage");
            return false;
        }
    }

    @Override
    public boolean isPageEmpty() {
        try {
            return pagination.isPageEmpty();
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "isPageEmpty");
            return false;
        }
    }

    @Override
    public boolean isPageFull() {
        try {
            return pagination.isPageFull();
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
            pagination.openPageForPlayer(playerId, pageNumber);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "openPageForPlayer");
        }
    }

    @Override
    public int getPageSize() {
        try {
            return pagination.getPageSize();
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "getPageSize");
            return 0;
        }
    }
}
