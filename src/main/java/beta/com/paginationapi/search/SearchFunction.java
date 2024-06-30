package beta.com.paginationapi.search;

import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import beta.com.paginationapi.page.service.PaginationService;
import beta.com.paginationapi.search.utils.SearchUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * The SearchFunction class is a listener that handles search functionality within a pagination system.
 *
 * It listens for inventory click events and player chat events to facilitate a search operation.
 * The search can be performed based on item name or lore within the items managed by an ItemManagerService.
 * The results of the search are navigated using a PaginationService.
 *
 * The class maintains the state of the search operation, including the player in search mode, the current search type, and the manager ID.
 */

public class SearchFunction implements Listener {

    private final PaginationService pagination;
    private final ItemManagerService itemManager;
    private UUID playerInSearchMode;
    private SearchType currentSearchType = SearchType.NONE;
    private UUID managerID;

    public SearchFunction(PaginationService pagination, ItemManagerService itemManager, UUID managerID) {
        this.pagination = pagination;
        this.itemManager = itemManager;
        this.managerID = managerID;
    }

    public ItemStack createSearchButton() {
        ItemStack searchButton = new ItemStack(Material.COMPASS);
        ItemMeta meta = searchButton.getItemMeta();
        meta.setDisplayName("Search");
        searchButton.setItemMeta(meta);
        return searchButton;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.COMPASS) {
            playerInSearchMode = player.getUniqueId();
            player.closeInventory();
            player.sendMessage(ChatColor.GREEN + "Type 'name' to search by item name or 'lore' to search by item lore.");
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) throws ExecutionException, InterruptedException {
        if (playerInSearchMode == null || !event.getPlayer().getUniqueId().equals(playerInSearchMode)) return;

        event.setCancelled(true);
        String message = event.getMessage().toLowerCase();

        if (currentSearchType == SearchType.NONE) {
            handleInitialSearchType(event, message);
        } else {
            handleItemSearch(event, message);
        }
    }

    private void handleInitialSearchType(AsyncPlayerChatEvent event, String message) {
        if (message.equals("name") || message.equals("lore")) {
            currentSearchType = message.equals("name") ? SearchType.NAME : SearchType.LORE;
            event.getPlayer().sendMessage(ChatColor.GREEN + "Type the item " + currentSearchType.name().toLowerCase() + " in the chat.");
        } else {
            event.getPlayer().sendMessage(ChatColor.RED + "Invalid search type! Type 'name' or 'lore'.");
        }
    }

    private void handleItemSearch(AsyncPlayerChatEvent event, String query) throws ExecutionException, InterruptedException {
        boolean found = SearchUtils.searchItems(itemManager, pagination, currentSearchType, event.getPlayer(), query,managerID);
        event.getPlayer().sendMessage(found ? ChatColor.GREEN + "Result found. Please open the menu again!" : ChatColor.RED + "Result not found!");
        resetSearch();
    }

    private void resetSearch() {
        currentSearchType = SearchType.NONE;
        playerInSearchMode = null;
    }
}
