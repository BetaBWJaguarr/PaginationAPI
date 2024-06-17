package beta.com.paginationapi.page.service.impl;


import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import beta.com.paginationapi.page.Pagination;
import beta.com.paginationapi.page.service.PaginationService;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class PaginationServiceImpl implements PaginationService {

    private Pagination pagination;

    private int Pagesize;

    private ItemManagerService itemManager;

    public PaginationServiceImpl(int pagesize,ItemManagerService itemManager) {
        Pagesize = pagesize;
        this.itemManager = itemManager;
    }


    @Override
    public ItemManagerService getItemManager() {
        return itemManager;
    }

    @Override
    public Pagination createPagination() {
        if (pagination == null) {
            pagination = new Pagination(Pagesize,itemManager);
        }
        return pagination;
    }

    @Override
    public Pagination getPagination() {
        return null;
    }

    @Override
    public List<ItemStack> getCurrentPageItems(UUID playerId) {
        return pagination.getCurrentPageItems(playerId);
    }

    @Override
    public void rememberPages(UUID playerId, boolean remember) {
        pagination.rememberPages(playerId, remember);
    }

    @Override
    public void setPageForPlayer(UUID playerId, int page) {
        pagination.setPageForPlayer(playerId, page);
    }

    @Override
    public int getPageForPlayer(UUID playerId) {
        return pagination.getPageForPlayer(playerId);
    }

    @Override
    public int getCurrentPageForPlayer(UUID playerId) {
        return pagination.getCurrentPageForPlayer(playerId);
    }

    @Override
    public void nextPage(UUID playerId) {
        pagination.nextPage(playerId);
    }

    @Override
    public void previousPage(UUID playerId) {
        pagination.previousPage(playerId);
    }

    @Override
    public boolean hasNextPage(UUID playerId) {
        return pagination.hasNextPage(playerId);
    }

    @Override
    public boolean hasPreviousPage(UUID playerId) {
        return pagination.hasPreviousPage(playerId);
    }

    @Override
    public boolean isPageEmpty() {
        return pagination.isPageEmpty();
    }

    @Override
    public boolean isPageFull() {
        return pagination.isPageFull();
    }

    @Override
    public void openPageForPlayer(UUID playerId, int pageNumber) {
        pagination.openPageForPlayer(playerId, pageNumber);
    }

    @Override
    public int getPageSize() {
        return pagination.getPageSize();
    }
}