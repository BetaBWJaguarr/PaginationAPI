package beta.com.paginationapi.listener;

import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import beta.com.paginationapi.page.service.PaginationService;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PaginationListener {

    private final PaginationService pagination;
    private final ItemManagerService itemManager;

    public PaginationListener(PaginationService pagination, ItemManagerService itemManager) {
        this.pagination = pagination;
        this.itemManager = itemManager;
    }

    public void onPageAction(Player player, boolean nextPage) {
        UUID playerId = player.getUniqueId();
        if (nextPage ? pagination.hasNextPage(playerId) : pagination.hasPreviousPage(playerId)) {
            if (nextPage) {
                pagination.nextPage(playerId);
            } else {
                pagination.previousPage(playerId);
            }
        }
    }

    public void onItemAction(ItemStack item, boolean addItem) {
        if (addItem) {
            itemManager.addItem(item);
        } else {
            itemManager.removeItem(item);
        }
    }
}
