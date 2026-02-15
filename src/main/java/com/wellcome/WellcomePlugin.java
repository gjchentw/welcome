package com.wellcome;

import com.wellcome.commands.WellcomeCommand;
import com.wellcome.configuration.ConfigManager;
import com.wellcome.managers.VoteManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WellcomePlugin extends JavaPlugin {

    private ConfigManager configManager;
    private VoteManager voteManager;

    @Override
    public void onEnable() {
        // Initialize Config Manager
        this.configManager = new ConfigManager(this);
        
        // Initialize Vote Manager
        this.voteManager = new VoteManager();

        // Register Command
        if (getCommand("wellcome") != null) {
            getCommand("wellcome").setExecutor(new WellcomeCommand(this, configManager, voteManager));
        }

        getLogger().info("Wellcome plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        // Clean up resources
        if (voteManager != null) {
            voteManager.clearAll();
        }
        getLogger().info("Wellcome plugin has been disabled!");
    }
}
