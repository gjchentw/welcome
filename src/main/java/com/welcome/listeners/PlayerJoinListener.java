package com.welcome.listeners;

import com.welcome.managers.LanguageManager;
import com.welcome.managers.PlayerCacheManager;
import com.welcome.utils.MessageUtils;
import com.welcome.utils.WhitelistUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Listens for players joining the server and updates the tab completion cache.
 * Also sends localized welcome messages.
 */
public class PlayerJoinListener implements Listener {

    private final JavaPlugin plugin;
    private final PlayerCacheManager playerCacheManager;
    private final LanguageManager languageManager;

    public PlayerJoinListener(JavaPlugin plugin, PlayerCacheManager playerCacheManager, LanguageManager languageManager) {
        this.plugin = plugin;
        this.playerCacheManager = playerCacheManager;
        this.languageManager = languageManager;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // Send localized welcome message
        String welcomeMsg = languageManager.getMessage("welcome-message").replace("%player%", player.getName());
        player.sendMessage(MessageUtils.colorize(welcomeMsg));

        // If whitelisted, notify admins/broadcast
        if (WhitelistUtils.isWhitelisted(player)) {
            String whitelistMsg = languageManager.getMessage("whitelist-join").replace("%player%", player.getName());
            plugin.getServer().broadcastMessage(MessageUtils.colorize(whitelistMsg));
        }

        // FR-005: Ensure cache operation is asynchronous
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            // FR-003: Check if player is whitelisted
            if (!WhitelistUtils.isWhitelisted(player)) {
                // FR-004: Add to cache
                playerCacheManager.addPlayer(player.getName(), player.getUniqueId());
            }
        });
    }
}
