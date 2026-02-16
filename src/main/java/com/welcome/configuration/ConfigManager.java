package com.welcome.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;

/**
 * Manages configuration files (config.yml and messages.yml).
 * Uses standard Bukkit FileConfiguration API.
 */
public class ConfigManager {

    private final JavaPlugin plugin;
    private FileConfiguration config;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        // Load config.yml
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

    public void reloadConfig() {
        loadConfig();
    }

    public boolean isDebugMode() {
        return config.getBoolean("debug-mode", false);
    }

    public String getPrefix() {
        return config.getString("prefix", "&8[&bWelcome&8] ");
    }

    public String getLanguage() {
        return config.getString("language", "en_US");
    }

    public int getAutocompleteMaxPlayers() {
        int max = config.getInt("autocomplete.max-players", 100);
        if (max <= 0) {
            plugin.getLogger().warning("Invalid max-players setting: " + max + ". Falling back to 100.");
            return 100;
        }
        return max;
    }

    public boolean isCheckWhitelist() {
        return config.getBoolean("check-whitelist", true);
    }

    public boolean isBroadcastOnWhitelist() {
        return config.getBoolean("broadcast-on-whitelist", true);
    }
}
