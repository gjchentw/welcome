package com.welcome.managers;

import com.welcome.configuration.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Manages a dynamic cache of players for tab completion and non-blocking lookups.
 * Updates when non-whitelisted players join.
 */
public class PlayerCacheManager {

    private final JavaPlugin plugin;
    private final ConfigManager configManager;
    private final Map<String, UUID> playerCache;

    public PlayerCacheManager(JavaPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        // FR-008: Initialize with LRU map
        this.playerCache = Collections.synchronizedMap(new LinkedHashMap<String, UUID>(configManager.getAutocompleteMaxPlayers(), 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, UUID> eldest) {
                return size() > configManager.getAutocompleteMaxPlayers();
            }
        });
    }

    /**
     * Adds a player to the cache using LRU logic.
     * 
     * @param name The player name to add
     * @param uuid The player UUID to add
     */
    public void addPlayer(String name, UUID uuid) {
        if (name == null || uuid == null) return;

        playerCache.put(name, uuid);
        
        if (configManager.isDebugMode()) {
            plugin.getLogger().info("Player added to cache: " + name + " (" + uuid + ") (Total: " + playerCache.size() + ")");
        }
    }

    /**
     * Removes a player from the cache (e.g., when whitelisted).
     * 
     * @param name The player name to remove
     */
    public void removePlayer(String name) {
        if (name == null) return;
        
        if (playerCache.remove(name) != null && configManager.isDebugMode()) {
            plugin.getLogger().info("Player removed from cache: " + name);
        }
    }

    /**
     * Gets the UUID for a cached player name.
     * 
     * @param name The player name
     * @return The UUID, or null if not in cache
     */
    public UUID getUuid(String name) {
        return playerCache.get(name);
    }

    /**
     * Gets the current cached names for tab completion.
     * 
     * @return A list of names in LRU order (most recent first)
     */
    public List<String> getCachedNames() {
        synchronized (playerCache) {
            List<String> names = new ArrayList<>(playerCache.keySet());
            Collections.reverse(names); // Reverse to get most recent first if desired, 
            // but LinkedHashMap with accessOrder=true and removeEldestEntry 
            // actually keeps oldest at front. Our requirement said "kept at beginning".
            // Let's check the previous implementation logic: "newList.add(0, name);" -> most recent at 0.
            return Collections.unmodifiableList(names);
        }
    }

    public void stop() {
        // No tasks to stop anymore
    }
}
