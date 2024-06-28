package beta.com.paginationapi.listener;

import beta.com.paginationapi.errorevents.HandleExceptions;
import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import beta.com.paginationapi.page.service.PaginationService;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PaginationListener {

    private final PaginationService pagination;
    private final ItemManagerService itemManagerService;
    private final HandleExceptions handleExceptions;
    private final UUID managerId;

    public PaginationListener(PaginationService pagination, ItemManagerService itemManagerService, UUID managerId) {
        this.pagination = pagination;
        this.itemManagerService = itemManagerService;
        this.handleExceptions = new HandleExceptions();
        this.managerId = managerId;
    }

    public void onPageAction(Player player, boolean nextPage) {
        try {
            if (player == null) {
                handleExceptions.handle(new NullPointerException("Player cannot be null"), this.getClass().getSimpleName(), "onPageAction");
                return;
            }
            UUID playerId = player.getUniqueId();
            if (nextPage ? pagination.hasNextPage(playerId) : pagination.hasPreviousPage(playerId)) {
                if (nextPage) {
                    pagination.nextPage(playerId);
                } else {
                    pagination.previousPage(playerId);
                }
            }
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "onPageAction");
        }
    }

    public void onItemAction(ItemStack item, boolean addItem) {
        try {
            if (item == null) {
                handleExceptions.handle(new NullPointerException("Item cannot be null"), this.getClass().getSimpleName(), "onItemAction");
                return;
            }
            if (addItem) {
                itemManagerService.addItem(managerId, item);
            } else {
                itemManagerService.removeItem(managerId, item);
            }
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "onItemAction");
        }
    }
}
