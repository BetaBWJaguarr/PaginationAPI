package beta.com.paginationapi.search.utils;

import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import beta.com.paginationapi.page.service.PaginationService;
import beta.com.paginationapi.search.SearchType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Utility class for searching items within a pagination system.
 * <p>
 * This class provides methods to search for items based on a specified search query,
 * using the provided ItemManagerService to retrieve items and PaginationService for navigation.
 * It supports searching by item name or lore based on the current search type.
 * <p>
 * The search is performed by iterating through items retrieved from the ItemManagerService
 * and comparing the target (item name or lore) with the query using partial matching.
 * If a match is found, it opens the corresponding page for the player using PaginationService.
 * <p>
 * This class is designed for use in Bukkit/Spigot plugins, targeting players identified by UUIDs
 * and managing items associated with specific manager IDs.
 *
 * @see SearchType       Enum specifying the type of search (by item name or lore).
 * @see ItemManagerService Service for managing items associated with a manager ID.
 * @see PaginationService Service for handling pagination and page navigation.
 */

public class SearchUtils {

    public static boolean searchItems(ItemManagerService itemManagerService, PaginationService pagination, SearchType currentSearchType, Player player, String query, UUID managerId) throws ExecutionException, InterruptedException {
        for (int i = 0; i < itemManagerService.getItems(managerId).get().size(); i++) {
            ItemStack item = itemManagerService.getItems(managerId).get().get(i);
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                String target = currentSearchType == SearchType.NAME ? meta.getDisplayName() : String.join(" ", meta.getLore());
                if (target != null && isPartialMatch(ChatColor.stripColor(target).toLowerCase(), query)) {
                    pagination.openPageForPlayer(player.getUniqueId(), i / pagination.getPageSize());
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isPartialMatch(String str1, String str2) {
        for (String word : str1.split(" ")) {
            if (word.equals(str2)) return true;
        }
        return false;
    }
}
