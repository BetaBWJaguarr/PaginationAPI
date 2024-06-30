package beta.com.paginationapi.listener;

import beta.com.paginationapi.errorevents.HandleExceptions;
import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import beta.com.paginationapi.page.service.PaginationService;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * The PaginationListener class is responsible for handling actions related to pagination and item management in the application.
 * It uses instances of PaginationService and ItemManagerService to perform these actions.
 *
 * The class is identified by a UUID and uses an instance of HandleExceptions to handle any exceptions that occur during the execution of its methods.
 *
 * The onPageAction method handles actions related to pagination. It checks if the player is null or if the player's UUID is null, and if so, it handles the exception and returns. If the player is valid, it checks if there is a next or previous page (depending on the nextPage parameter) and if so, it navigates to that page.
 *
 * The onItemAction method handles actions related to items. It checks if the item is null, and if so, it handles the exception and returns. If the item is valid, it checks the addItem parameter to determine whether to add or remove the item, and performs the corresponding action.
 *
 * Usage:
 * PaginationService pagination = ... // get an instance of PaginationService
 * ItemManagerService itemManagerService = ... // get an instance of ItemManagerService
 * UUID managerId = ... // get a managerId
 * PaginationListener paginationListener = new PaginationListener(pagination, itemManagerService, managerId);
 * Player player = ... // get a Player
 * ItemStack item = ... // get an ItemStack
 * paginationListener.onPageAction(player, true); // navigate to the next page
 * paginationListener.onItemAction(item, true); // add the item
 */

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
            if (player == null || player.getUniqueId() == null) {
                handleExceptions.handle(new NullPointerException("Player or Player UUID cannot be null"), this.getClass().getSimpleName(), "onPageAction");
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
