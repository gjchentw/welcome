package com.wellcome.commands;

import com.wellcome.configuration.ConfigManager;
import com.wellcome.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class WellcomeCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final ConfigManager configManager;

    public WellcomeCommand(JavaPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            // Help command
            // Permission: wellcome.admin
            if (!sender.hasPermission("wellcome.admin")) {
                sender.sendMessage(MessageUtils.colorize(configManager.getPrefix() + configManager.getMessage("no-permission")));
                return true;
            }
            configManager.getMessageList("help-message").forEach(line ->
                sender.sendMessage(MessageUtils.colorize(line))
            );
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            // Reload command
            // Permission: wellcome.admin
            if (!sender.hasPermission("wellcome.admin")) {
                sender.sendMessage(MessageUtils.colorize(configManager.getPrefix() + configManager.getMessage("no-permission")));
                return true;
            }
            configManager.reloadConfig();
            sender.sendMessage(MessageUtils.colorize(configManager.getPrefix() + configManager.getMessage("reload-success")));
            return true;
        }

        // Invalid command -> Help
        configManager.getMessageList("help-message").forEach(line ->
            sender.sendMessage(MessageUtils.colorize(line))
        );
        return true;
    }
}
