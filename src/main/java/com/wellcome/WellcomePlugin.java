package com.wellcome;

import com.wellcome.commands.WellcomeCommand;
import com.wellcome.configuration.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WellcomePlugin extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        // Initialize Config Manager
        this.configManager = new ConfigManager(this);

        // Register Command
        if (getCommand("wellcome") != null) {
            getCommand("wellcome").setExecutor(new WellcomeCommand(this, configManager));
        }

        getLogger().info("Wellcome plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        // Clean up resources
        getLogger().info("Wellcome plugin has been disabled!");
    }
}
