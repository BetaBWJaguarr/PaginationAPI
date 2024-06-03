package beta.com.paginationapi.navigation;


import beta.com.paginationapi.page.Pagination;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class Navigation {

    private final Pagination pagination;

    public Navigation(Pagination pagination) {
        this.pagination = pagination;
    }

    public ItemStack createNextPageButton(UUID playerId) {
        if (pagination.hasNextPage(playerId) && pagination.isPageFull()) {
            ItemStack nextPageButton = new ItemStack(Material.ARROW);
            ItemMeta meta = nextPageButton.getItemMeta();
            meta.setDisplayName("Next Page");
            nextPageButton.setItemMeta(meta);
            return nextPageButton;
        }
        return null;
    }

    public ItemStack createPreviousPageButton(UUID playerId) {
        if (pagination.hasPreviousPage(playerId) && !pagination.isPageEmpty()) {
            ItemStack previousPageButton = new ItemStack(Material.ARROW);
            ItemMeta meta = previousPageButton.getItemMeta();
            meta.setDisplayName("Previous Page");
            previousPageButton.setItemMeta(meta);
            return previousPageButton;
        }
        return null;
    }
}