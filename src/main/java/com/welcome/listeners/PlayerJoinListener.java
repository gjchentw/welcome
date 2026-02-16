package com.welcome.listeners;

import com.welcome.managers.PlayerCacheManager;
import com.welcome.utils.WhitelistUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Listens for players joining the server and updates the tab completion cache.
 */
public class PlayerJoinListener implements Listener {

    private final JavaPlugin plugin;
    private final PlayerCacheManager playerCacheManager;

    public PlayerJoinListener(JavaPlugin plugin, PlayerCacheManager playerCacheManager) {
        this.plugin = plugin;
        this.playerCacheManager = playerCacheManager;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // FR-005: Ensure cache operation is asynchronous
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            // FR-003: Check if player is whitelisted
            if (!WhitelistUtils.isWhitelisted(player)) {
                // FR-004: Add to cache
                playerCacheManager.addPlayer(player.getName());
            }
        });
    }
}
