package beta.com.paginationapi.search;

import beta.com.paginationapi.itemmanager.service.ItemManagerService;
import beta.com.paginationapi.page.service.PaginationService;
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

public class SearchFunction implements Listener {

    private final PaginationService pagination;
    private final ItemManagerService itemManager;
    private UUID playerInSearchMode;
    private SearchType currentSearchType = SearchType.NONE;

    public SearchFunction(PaginationService pagination, ItemManagerService itemManager) {
        this.pagination = pagination;
        this.itemManager = itemManager;
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
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (playerInSearchMode == null || !event.getPlayer().getUniqueId().equals(playerInSearchMode)) return;

        event.setCancelled(true);
        String message = event.getMessage().toLowerCase();

        if (currentSearchType == SearchType.NONE) {
            if (message.equals("name") || message.equals("lore")) {
                currentSearchType = message.equals("name") ? SearchType.NAME : SearchType.LORE;
                event.getPlayer().sendMessage(ChatColor.GREEN + "Type the item " + currentSearchType.name().toLowerCase() + " in the chat.");
            } else {
                event.getPlayer().sendMessage(ChatColor.RED + "Invalid search type! Type 'name' or 'lore'.");
            }
        } else {
            boolean found = searchItems(event.getPlayer(), message);
            event.getPlayer().sendMessage(found ? ChatColor.GREEN + "Result found. Please open the menu again!" : ChatColor.RED + "Result not found!");
            resetSearch();
        }
    }

    private boolean searchItems(Player player, String query) {
        for (int i = 0; i < itemManager.getItems().size(); i++) {
            ItemStack item = itemManager.getItems().get(i);
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

    private boolean isPartialMatch(String str1, String str2) {
        for (String word : str1.split(" ")) {
            if (word.equals(str2)) return true;
        }
        return false;
    }

    private void resetSearch() {
        currentSearchType = SearchType.NONE;
        playerInSearchMode = null;
    }
}
