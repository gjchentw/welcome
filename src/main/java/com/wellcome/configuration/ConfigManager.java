package com.wellcome.configuration;

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
    private FileConfiguration messages;
    private File messagesFile;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        // Load config.yml
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        this.config = plugin.getConfig();

        // Load messages.yml
        if (messagesFile == null) {
            messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        }
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        
        // Load default messages from jar if missing
        try (InputStreamReader reader = new InputStreamReader(plugin.getResource("messages.yml"), StandardCharsets.UTF_8)) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(reader);
            messages.setDefaults(defConfig);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Could not load default messages.yml", e);
        }
    }

    public void reloadConfig() {
        loadConfig();
    }

    public boolean isDebugMode() {
        return config.getBoolean("debug-mode", false);
    }

    public String getPrefix() {
        return config.getString("prefix", "&8[&bWellcome&8] ");
    }

    public String getMessage(String key) {
        return messages.getString(key, "Message not found: " + key);
    }

    public List<String> getMessageList(String key) {
        return messages.getStringList(key);
    }

    public boolean isCheckWhitelist() {
        return config.getBoolean("check-whitelist", true);
    }

    public boolean isBroadcastOnWhitelist() {
        return config.getBoolean("broadcast-on-whitelist", true);
    }
}
