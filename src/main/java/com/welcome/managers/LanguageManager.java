package com.welcome.managers;

import com.welcome.configuration.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Manages internationalization (i18n) by loading and caching language files.
 * Supports fallback to en_US for missing keys.
 */
public class LanguageManager {

    private final JavaPlugin plugin;
    private final ConfigManager configManager;
    private final Map<String, FileConfiguration> languageConfigs = new HashMap<>();
    private FileConfiguration fallbackConfig;
    private String currentLanguage;

    public LanguageManager(JavaPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        loadLanguages();
    }

    /**
     * Loads all supported language files from the jar and plugin folder.
     */
    public void loadLanguages() {
        languageConfigs.clear();
        currentLanguage = configManager.getLanguage();
        
        // Ensure lang directory exists
        File langDir = new File(plugin.getDataFolder(), "lang");
        if (!langDir.exists()) {
            langDir.mkdirs();
        }

        // List of default languages to extract
        String[] defaults = {"en_US", "zh_TW", "zh_CN", "ja_JP", "la_US"};
        for (String lang : defaults) {
            saveDefaultLanguage(lang);
        }

        // Load the fallback language (en_US)
        fallbackConfig = loadLanguageFile("en_US");
        
        // Load the current language
        if (!currentLanguage.equals("en_US")) {
            languageConfigs.put(currentLanguage, loadLanguageFile(currentLanguage));
        }
    }

    private void saveDefaultLanguage(String lang) {
        File file = new File(plugin.getDataFolder(), "lang/" + lang + ".yml");
        if (!file.exists()) {
            plugin.saveResource("lang/" + lang + ".yml", false);
        }
    }

    private FileConfiguration loadLanguageFile(String lang) {
        File file = new File(plugin.getDataFolder(), "lang/" + lang + ".yml");
        if (!file.exists()) {
            plugin.getLogger().warning("Language file " + lang + ".yml not found! Falling back to defaults.");
            // Try to load from jar as last resort
            try (InputStreamReader reader = new InputStreamReader(plugin.getResource("lang/" + lang + ".yml"), StandardCharsets.UTF_8)) {
                return YamlConfiguration.loadConfiguration(reader);
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Could not load language " + lang + " from JAR!", e);
                return new YamlConfiguration();
            }
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Retrieves a localized message string.
     * 
     * @param key The message key
     * @return The translated message, or fallback if not found
     */
    public String getMessage(String key) {
        FileConfiguration config = languageConfigs.get(currentLanguage);
        if (config != null && config.contains(key)) {
            return config.getString(key);
        }
        
        // Fallback to en_US
        if (fallbackConfig != null && fallbackConfig.contains(key)) {
            return fallbackConfig.getString(key);
        }
        
        return "Message key not found: " + key;
    }

    /**
     * Retrieves a localized message list.
     * 
     * @param key The message key
     * @return The translated message list, or fallback if not found
     */
    public List<String> getMessageList(String key) {
        FileConfiguration config = languageConfigs.get(currentLanguage);
        if (config != null && config.contains(key)) {
            return config.getStringList(key);
        }
        
        // Fallback to en_US
        if (fallbackConfig != null && fallbackConfig.contains(key)) {
            return fallbackConfig.getStringList(key);
        }
        
        return List.of("Message list key not found: " + key);
    }

    public void reload() {
        loadLanguages();
    }
}
