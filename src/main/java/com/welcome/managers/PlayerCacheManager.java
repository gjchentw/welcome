package com.welcome.managers;

import com.welcome.configuration.ConfigManager;
import com.welcome.utils.WhitelistUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages a dynamic cache of players for tab completion.
 * Updates when non-whitelisted players join.
 */
public class PlayerCacheManager {

    private final JavaPlugin plugin;
    private final ConfigManager configManager;
    private volatile List<String> cachedNames = Collections.emptyList();

    public PlayerCacheManager(JavaPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        // FR-008: Initialize as empty list on startup
        this.cachedNames = Collections.emptyList();
    }

    /**
     * Adds a player to the cache using LRU logic.
     * Most recently visited players are kept at the beginning.
     * 
     * @param name The player name to add
     */
    public synchronized void addPlayer(String name) {
        if (name == null) return;

        List<String> newList = new ArrayList<>(cachedNames);
        
        // LRU logic: Remove if exists to re-add at front
        newList.remove(name);
        
        // Add to front (index 0)
        newList.add(0, name);
        
        // Cap size
        int maxSize = configManager.getAutocompleteMaxPlayers();
        while (newList.size() > maxSize) {
            newList.remove(newList.size() - 1);
        }
        
        // Atomic swap with unmodifiable list
        cachedNames = Collections.unmodifiableList(newList);
        
        if (configManager.isDebugMode()) {
            plugin.getLogger().info("Player added to cache: " + name + " (Total: " + cachedNames.size() + ")");
        }
    }

    /**
     * Removes a player from the cache (e.g., when whitelisted).
     * 
     * @param name The player name to remove
     */
    public synchronized void removePlayer(String name) {
        if (name == null || cachedNames.isEmpty()) return;
        
        if (cachedNames.contains(name)) {
            List<String> newList = new ArrayList<>(cachedNames);
            newList.remove(name);
            cachedNames = Collections.unmodifiableList(newList);
            
            if (configManager.isDebugMode()) {
                plugin.getLogger().info("Player removed from cache: " + name);
            }
        }
    }

    /**
     * Gets the current cached names.
     * 
     * @return An unmodifiable list of names in LRU order
     */
    public List<String> getCachedNames() {
        return cachedNames;
    }

    public void stop() {
        // No tasks to stop anymore
    }
}
