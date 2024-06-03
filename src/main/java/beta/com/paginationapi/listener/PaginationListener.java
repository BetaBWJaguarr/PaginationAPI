package beta.com.paginationapi.listener;

import beta.com.paginationapi.itemmanager.ItemManager;
import beta.com.paginationapi.page.Pagination;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PaginationListener {

    private final Pagination pagination;
    private final ItemManager itemManager;

    public PaginationListener(Pagination pagination,ItemManager itemManager) {
        this.pagination = pagination;
        this.itemManager = itemManager;
    }

    public void onNextPage(Player player) {
        UUID playerId = player.getUniqueId();
        if (pagination.hasNextPage(playerId)) {
            pagination.nextPage(playerId);
        }
    }

    public void onPreviousPage(Player player) {
        UUID playerId = player.getUniqueId();
        if (pagination.hasPreviousPage(playerId)) {
            pagination.previousPage(playerId);
        }
    }

    public void onAddItem(ItemStack item) {
        itemManager.addItem(item);
    }

    public void onRemoveItem(ItemStack item) {
        itemManager.removeItem(item);
    }
}
