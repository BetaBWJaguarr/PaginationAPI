package beta.com.paginationapi.navigation;

import beta.com.paginationapi.page.service.PaginationService;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class Navigation {
    private final PaginationService pagination;

    public Navigation(PaginationService pagination) {
        this.pagination = pagination;
    }

    private ItemStack createPageButton(UUID playerId, boolean isNext) {
        if ((isNext ? pagination.hasNextPage(playerId) && pagination.isPageFull() : pagination.hasPreviousPage(playerId) && !pagination.isPageEmpty())) {
            ItemStack pageButton = new ItemStack(Material.ARROW);
            ItemMeta meta = pageButton.getItemMeta();
            meta.setDisplayName(isNext ? "Next Page" : "Previous Page");
            pageButton.setItemMeta(meta);
            return pageButton;
        }
        return null;
    }

    public ItemStack createNextPageButton(UUID playerId) {
        return createPageButton(playerId, true);
    }

    public ItemStack createPreviousPageButton(UUID playerId) {
        return createPageButton(playerId, false);
    }
}