package com.welcome;

import com.welcome.commands.WelcomeCommand;
import com.welcome.configuration.ConfigManager;
import com.welcome.listeners.PlayerJoinListener;
import com.welcome.managers.PlayerCacheManager;
import com.welcome.managers.VoteManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class WelcomePlugin extends JavaPlugin {

    private ConfigManager configManager;
    private VoteManager voteManager;
    private PlayerCacheManager playerCacheManager;

    @Override
    public void onLoad() {
        // Data folder migration: Wellcome -> Welcome
        File oldFolder = new File(getDataFolder().getParentFile(), "Wellcome");
        File newFolder = getDataFolder();
        if (oldFolder.exists() && !newFolder.exists()) {
            if (oldFolder.renameTo(newFolder)) {
                getLogger().info("Successfully migrated data folder from Wellcome to Welcome.");
            } else {
                getLogger().warning("Failed to migrate data folder from Wellcome to Welcome.");
            }
        }
    }

    @Override
    public void onEnable() {
        // Initialize Config Manager
        this.configManager = new ConfigManager(this);
        
        // Initialize Managers
        this.voteManager = new VoteManager();
        this.playerCacheManager = new PlayerCacheManager(this, configManager);

        // Register Listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this, playerCacheManager), this);

        // Register Command
        if (getCommand("welcome") != null) {
            getCommand("welcome").setExecutor(new WelcomeCommand(this, configManager, voteManager, playerCacheManager));
        }

        getLogger().info("Welcome plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        // Clean up resources
        if (playerCacheManager != null) {
            playerCacheManager.stop();
        }
        if (voteManager != null) {
            voteManager.clearAll();
        }
        getLogger().info("Welcome plugin has been disabled!");
    }
}
