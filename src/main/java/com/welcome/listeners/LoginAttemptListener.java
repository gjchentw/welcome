package com.welcome.listeners;

import com.welcome.managers.PlayerCacheManager;
import com.welcome.utils.WhitelistUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

/**
 * Listens for login attempts to capture non-whitelisted players in the cache.
 */
public class LoginAttemptListener implements Listener {

    private final PlayerCacheManager playerCacheManager;

    public LoginAttemptListener(PlayerCacheManager playerCacheManager) {
        this.playerCacheManager = playerCacheManager;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        String name = event.getName();
        UUID uuid = event.getUniqueId();

        // Use Bukkit.getOfflinePlayer(uuid) to check whitelist status
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

        if (!WhitelistUtils.isWhitelisted(offlinePlayer)) {
            // Add to cache even if they are about to be rejected by the whitelist
            playerCacheManager.addPlayer(name, uuid);
        }
    }
}
