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
