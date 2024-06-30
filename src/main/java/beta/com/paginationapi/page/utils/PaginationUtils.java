package beta.com.paginationapi.page.utils;

import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * The PaginationUtils class provides utility methods for managing pagination in the application.
 *
 * It provides methods for calculating the start and end indices of a page, checking if there are next or previous pages, and retrieving the items for a specific page.
 * These methods are designed to work with an instance of ItemManagerService, which manages the items that are displayed in the pages.
 *
 * The class also provides methods for checking if a page is empty or full, which can be useful for managing the state of the pagination system.
 *
 * The methods in this class are static, meaning they can be called without creating an instance of the class. This makes them easy to use throughout the application.
 */

public class PaginationUtils {
    public static int getPageStart(int page, int pageSize) {
        return page * pageSize;
    }

    public static int getPageEnd(int page, int pageSize, int totalItems) {
        return Math.min((page + 1) * pageSize, totalItems);
    }

    public static boolean hasNextPage(int page, int pageSize, int totalItems) {
        return getPageEnd(page, pageSize, totalItems) < totalItems;
    }

    public static boolean hasPreviousPage(int page) {
        return page > 0;
    }

    public static List<ItemStack> getItemsForPage(ItemManagerService itemManagerService, UUID managerId, int page, int pageSize) throws ExecutionException, InterruptedException {
        int start = getPageStart(page, pageSize);
        int end = getPageEnd(page, pageSize, itemManagerService.getItems(managerId).get().size());
        return itemManagerService.getItems(managerId).get().subList(start, end);
    }

    public static boolean isPageEmpty(ItemManagerService itemManagerService, UUID managerId, int pageSize) throws ExecutionException, InterruptedException {
        return getItemsForPage(itemManagerService, managerId, 0, pageSize).isEmpty();
    }

    public static boolean isPageFull(ItemManagerService itemManagerService, UUID managerId, int pageSize) throws ExecutionException, InterruptedException {
        return getItemsForPage(itemManagerService, managerId, 0, pageSize).size() == pageSize;
    }
}
