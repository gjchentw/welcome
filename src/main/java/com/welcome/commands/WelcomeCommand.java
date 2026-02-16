package com.welcome.commands;

import com.welcome.configuration.ConfigManager;
import com.welcome.managers.PlayerCacheManager;
import com.welcome.managers.VoteManager;
import com.welcome.utils.MessageUtils;
import com.welcome.utils.WhitelistUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WelcomeCommand implements CommandExecutor, TabCompleter {

    private final JavaPlugin plugin;
    private final ConfigManager configManager;
    private final VoteManager voteManager;
    private final PlayerCacheManager playerCacheManager;

    public WelcomeCommand(JavaPlugin plugin, ConfigManager configManager, VoteManager voteManager, PlayerCacheManager playerCacheManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.voteManager = voteManager;
        this.playerCacheManager = playerCacheManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            return handleHelp(sender);
        }

        return handleVote(sender, args[0]);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            if (!sender.hasPermission("welcome.use")) {
                return Collections.emptyList();
            }
            
            List<String> completions = new ArrayList<>();
            StringUtil.copyPartialMatches(args[0], playerCacheManager.getCachedNames(), completions);
            Collections.sort(completions);
            return completions;
        }
        return Collections.emptyList();
    }

    private boolean handleHelp(CommandSender sender) {
        if (!sender.hasPermission("welcome.admin") && !sender.hasPermission("welcome.use")) {
             sender.sendMessage(MessageUtils.colorize(configManager.getPrefix() + configManager.getMessage("no-permission")));
             return true;
        }
        configManager.getMessageList("help-message").forEach(line ->
            sender.sendMessage(MessageUtils.colorize(line))
        );
        return true;
    }

    private boolean handleVote(CommandSender sender, String targetName) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageUtils.colorize("&cOnly players can vote."));
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("welcome.use")) {
            player.sendMessage(MessageUtils.colorize(configManager.getPrefix() + configManager.getMessage("no-permission")));
            return true;
        }

        if (configManager.isCheckWhitelist() && !WhitelistUtils.isWhitelistEnabled()) {
            player.sendMessage(MessageUtils.colorize(configManager.getPrefix() + configManager.getMessage("whitelist-disabled")));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
        // Updated check: allow voting for players who might not have played before if they are online
        // but typically we use cached names.
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

        int votes = voteManager.getVoteCount(target.getUniqueId());
        int online = Bukkit.getOnlinePlayers().size();
        if (online == 0) online = 1;

        double percentage = (double) votes / online * 100;
        
        World mainWorld = Bukkit.getWorlds().get(0);
        Integer requiredInt = mainWorld.getGameRuleValue(GameRule.PLAYERS_SLEEPING_PERCENTAGE);
        int required = requiredInt != null ? requiredInt : 100;

        if (percentage >= required) {
            WhitelistUtils.setWhitelisted(target, true);
            voteManager.clearVotes(target.getUniqueId());
            playerCacheManager.removePlayer(target.getName());
            
            if (configManager.isBroadcastOnWhitelist()) {
                String broadcast = configManager.getMessage("broadcast-message").replace("{target}", target.getName());
                Bukkit.broadcastMessage(MessageUtils.colorize(configManager.getPrefix() + broadcast));
            }
        } 
        
        int votesNeeded = (int) Math.ceil((double) online * required / 100);
        
        String msg = configManager.getMessage("welcome-success")
                .replace("{target}", target.getName())
                .replace("{current_votes}", String.valueOf(votes))
                .replace("{required_votes}", String.valueOf(votesNeeded));
        player.sendMessage(MessageUtils.colorize(configManager.getPrefix() + msg));

        return true;
    }
}
