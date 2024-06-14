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

import java.util.List;
import java.util.UUID;

public class SearchFunction implements Listener {

    private final PaginationService pagination;
    private final ItemManagerService itemManager;
    private UUID playerInSearchMode;

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

    private SearchType currentSearchType = SearchType.NONE;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem != null && clickedItem.getType() == Material.COMPASS) {
            playerInSearchMode = player.getUniqueId();
            player.closeInventory();
            player.sendMessage(ChatColor.GREEN + "Please type 'name' to search by item name or 'lore' to search by item lore.");
        }
    }


    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (playerInSearchMode != null && event.getPlayer().getUniqueId().equals(playerInSearchMode)) {
            event.setCancelled(true);
            String message = event.getMessage().toLowerCase();

            if (currentSearchType == SearchType.NONE) {
                if (message.equals("name")) {
                    currentSearchType = SearchType.NAME;
                    event.getPlayer().sendMessage(ChatColor.GREEN + "Please type the item name in the chat.");
                } else if (message.equals("lore")) {
                    currentSearchType = SearchType.LORE;
                    event.getPlayer().sendMessage(ChatColor.GREEN + "Please type the item lore in the chat.");
                } else {
                    event.getPlayer().sendMessage(ChatColor.RED + "Invalid search type! Please type 'name' or 'lore'.");
                }
            } else {
                boolean found = false;

                for (int i = 0; i < itemManager.getItems().size(); i++) {
                    ItemStack item = itemManager.getItems().get(i);
                    if (item.hasItemMeta()) {
                        ItemMeta meta = item.getItemMeta();
                        if (currentSearchType == SearchType.NAME && meta.hasDisplayName()) {
                            String displayName = ChatColor.stripColor(meta.getDisplayName()).toLowerCase();
                            if (isPartialMatch(displayName, message)) {
                                int pageNumber = i / pagination.getPageSize();
                                pagination.openPageForPlayer(event.getPlayer().getUniqueId(), pageNumber);
                                found = true;
                                break;
                            }
                        } else if (currentSearchType == SearchType.LORE && meta.hasLore()) {
                            List<String> lore = meta.getLore();
                            for (String loreLine : lore) {
                                if (ChatColor.stripColor(loreLine).toLowerCase().contains(message)) {
                                    int pageNumber = i / pagination.getPageSize();
                                    pagination.openPageForPlayer(event.getPlayer().getUniqueId(), pageNumber);
                                    found = true;
                                    break;
                                }
                            }
                            if (found) break;
                        }
                    }
                }

                if (found) {
                    event.getPlayer().sendMessage(ChatColor.GREEN + "Result found. Please open menu again!");
                } else {
                    event.getPlayer().sendMessage(ChatColor.RED + "Result not found!");
                }

                currentSearchType = SearchType.NONE;
                playerInSearchMode = null;
            }
        }
    }

    private boolean isPartialMatch(String str1, String str2) {
        String[] words = str1.split(" ");
        for (String word : words) {
            if (word.equals(str2)) {
                return true;
            }
        }
        return false;
    }
}