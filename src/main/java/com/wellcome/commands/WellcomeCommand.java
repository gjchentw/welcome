package com.wellcome.commands;

import com.wellcome.configuration.ConfigManager;
import com.wellcome.managers.VoteManager;
import com.wellcome.utils.MessageUtils;
import com.wellcome.utils.WhitelistUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WellcomeCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final ConfigManager configManager;
    private final VoteManager voteManager;

    public WellcomeCommand(JavaPlugin plugin, ConfigManager configManager, VoteManager voteManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.voteManager = voteManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            return handleHelp(sender);
        }

        if (args[0].equalsIgnoreCase("reload")) {
            return handleReload(sender);
        }

        // Assume /wellcome <player>
        return handleVote(sender, args[0]);
    }

    private boolean handleHelp(CommandSender sender) {
        if (!sender.hasPermission("wellcome.admin") && !sender.hasPermission("wellcome.use")) {
             sender.sendMessage(MessageUtils.colorize(configManager.getPrefix() + configManager.getMessage("no-permission")));
             return true;
        }
        configManager.getMessageList("help-message").forEach(line ->
            sender.sendMessage(MessageUtils.colorize(line))
        );
        return true;
    }

    private boolean handleReload(CommandSender sender) {
        if (!sender.hasPermission("wellcome.admin")) {
            sender.sendMessage(MessageUtils.colorize(configManager.getPrefix() + configManager.getMessage("no-permission")));
            return true;
        }
        configManager.reloadConfig();
        sender.sendMessage(MessageUtils.colorize(configManager.getPrefix() + configManager.getMessage("reload-success")));
        return true;
    }

    private boolean handleVote(CommandSender sender, String targetName) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageUtils.colorize("&cOnly players can vote."));
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("wellcome.use")) {
            player.sendMessage(MessageUtils.colorize(configManager.getPrefix() + configManager.getMessage("no-permission")));
            return true;
        }

        if (configManager.isCheckWhitelist() && !WhitelistUtils.isWhitelistEnabled()) {
            player.sendMessage(MessageUtils.colorize(configManager.getPrefix() + configManager.getMessage("whitelist-disabled")));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            player.sendMessage(MessageUtils.colorize(configManager.getPrefix() + configManager.getMessage("target-invalid").replace("{target}", targetName)));
            return true;
        }

        if (WhitelistUtils.isWhitelisted(target)) {
            player.sendMessage(MessageUtils.colorize(configManager.getPrefix() + configManager.getMessage("target-already-whitelisted").replace("{target}", target.getName())));
            return true;
        }

        if (!voteManager.vote(target.getUniqueId(), player.getUniqueId())) {
            player.sendMessage(MessageUtils.colorize(configManager.getPrefix() + configManager.getMessage("already-voted").replace("{target}", target.getName())));
            return true;
        }

        // T011: Threshold Calculation
        int votes = voteManager.getVoteCount(target.getUniqueId());
        int online = Bukkit.getOnlinePlayers().size();
        
        // Avoid division by zero (should not happen as sender is online)
        if (online == 0) online = 1;

        double percentage = (double) votes / online * 100;
        
        // Get GameRule from main world
        World mainWorld = Bukkit.getWorlds().get(0);
        Integer requiredInt = mainWorld.getGameRuleValue(GameRule.PLAYERS_SLEEPING_PERCENTAGE);
        int required = requiredInt != null ? requiredInt : 100;

        if (percentage >= required) {
            // Success!
            WhitelistUtils.setWhitelisted(target, true);
            voteManager.clearVotes(target.getUniqueId());
            
            if (configManager.isBroadcastOnWhitelist()) {
                String broadcast = configManager.getMessage("broadcast-message").replace("{target}", target.getName());
                Bukkit.broadcastMessage(MessageUtils.colorize(configManager.getPrefix() + broadcast));
            }
        } 
        
        // Send success message to voter (showing progress)
        // Calculate required votes for display
        int votesNeeded = (int) Math.ceil((double) online * required / 100);
        
        String msg = configManager.getMessage("welcome-success")
                .replace("{target}", target.getName())
                .replace("{current_votes}", String.valueOf(votes))
                .replace("{required_votes}", String.valueOf(votesNeeded));
        player.sendMessage(MessageUtils.colorize(configManager.getPrefix() + msg));

        return true;
    }
}
