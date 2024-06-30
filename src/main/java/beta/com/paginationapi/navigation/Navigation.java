package beta.com.paginationapi.navigation;

import beta.com.paginationapi.errorevents.HandleExceptions;
import beta.com.paginationapi.page.service.PaginationService;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

/**
 * The Navigation class is responsible for creating navigation buttons for the pagination system in the application.
 * It interacts with the PaginationService to determine the state of the pagination (whether there are next or previous pages) and creates the appropriate navigation buttons.
 * It also uses a HandleExceptions instance to handle any exceptions that occur during these operations, ensuring that the application can recover gracefully from errors.
 */

public class Navigation {
    private final PaginationService pagination;
    private final HandleExceptions handleExceptions;

    public Navigation(PaginationService pagination) {
        this.pagination = pagination;
        this.handleExceptions = new HandleExceptions();
    }

    private ItemStack createPageButton(UUID playerId, boolean isNext) {
        if (playerId == null) {
            handleExceptions.handle(new IllegalArgumentException("Player ID cannot be null"), this.getClass().getSimpleName(), "createPageButton");
            return null;
        }
        boolean condition = isNext ? pagination.hasNextPage(playerId) && pagination.isPageFull(playerId) : pagination.hasPreviousPage(playerId) && !pagination.isPageEmpty(playerId);
        if (!condition) return null;

        ItemStack pageButton = new ItemStack(Material.ARROW);
        ItemMeta meta = pageButton.getItemMeta();
        meta.setDisplayName(isNext ? "Next Page" : "Previous Page");
        pageButton.setItemMeta(meta);
        return pageButton;
    }

    public ItemStack createNextPageButton(UUID playerId) {
        return createPageButton(playerId, true);
    }

    public ItemStack createPreviousPageButton(UUID playerId) {
        return createPageButton(playerId, false);
    }
}
