package beta.com.paginationapi.search;

import beta.com.paginationapi.itemmanager.ItemManager;
import beta.com.paginationapi.page.Pagination;
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

    private final Pagination pagination;
    private final ItemManager itemManager;
    private UUID playerInSearchMode;

    public SearchFunction(Pagination pagination, ItemManager itemManager) {
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
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem != null && clickedItem.getType() == Material.COMPASS) {
            playerInSearchMode = player.getUniqueId();
            player.closeInventory();
            player.sendMessage("Please type the name in the chat.");
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (playerInSearchMode != null && event.getPlayer().getUniqueId().equals(playerInSearchMode)) {
            event.setCancelled(true);
            String searchName = event.getMessage();
            playerInSearchMode = null;

            for (int i = 0; i < itemManager.getItems().size(); i++) {
                ItemStack item = itemManager.getItems().get(i);
                if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                    String displayName = ChatColor.stripColor(item.getItemMeta().getDisplayName());
                    if (displayName.equals(searchName)) {
                        int pageNumber = i / pagination.getPageSize();
                        pagination.openPageForPlayer(event.getPlayer().getUniqueId(), pageNumber);
                        break;
                    }
                }
            }
        }
    }
}