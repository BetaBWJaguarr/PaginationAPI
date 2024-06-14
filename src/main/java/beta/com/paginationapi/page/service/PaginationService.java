package beta.com.paginationapi.page.service;


import beta.com.paginationapi.itemmanager.ItemManager;
import beta.com.paginationapi.page.Pagination;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public interface PaginationService {


    ItemManager getItemManager();

    Pagination createPagination();

    Pagination getPagination();

    List<ItemStack> getCurrentPageItems(UUID playerId);

    void rememberPages(UUID playerId, boolean remember);

    void setPageForPlayer(UUID playerId, int page);

    int getPageForPlayer(UUID playerId);

    int getCurrentPageForPlayer(UUID playerId);

    void nextPage(UUID playerId);

    void previousPage(UUID playerId);

    boolean hasNextPage(UUID playerId);

    boolean hasPreviousPage(UUID playerId);

    boolean isPageEmpty();

    boolean isPageFull();

    void openPageForPlayer(UUID playerId, int pageNumber);

    int getPageSize();
}
