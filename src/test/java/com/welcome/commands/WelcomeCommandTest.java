package com.welcome.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.welcome.WelcomePlugin;
import org.bukkit.GameRule;
import org.bukkit.plugin.PluginDescriptionFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WelcomeCommandTest {

    private ServerMock server;
    private WelcomePlugin plugin;

    @BeforeEach
    public void setUp() {
        server = MockBukkit.mock();
        server.setWhitelist(true);
        server.addSimpleWorld("world").setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, 100);
        
        // Manual load to bypass jar requirement
        InputStream is = getClass().getResourceAsStream("/plugin.yml");
        if (is != null) {
            try {
                PluginDescriptionFile desc = new PluginDescriptionFile(is);
                plugin = MockBukkit.load(WelcomePlugin.class, desc);
            } catch (Exception e) {
                plugin = MockBukkit.load(WelcomePlugin.class);
            }
        } else {
            plugin = MockBukkit.load(WelcomePlugin.class);
        }
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testOldCommandIsRemoved() {
        PlayerMock player = server.addPlayer();
        // /wellcome should not exist anymore
        assertFalse(server.dispatchCommand(player, "wellcome targetPlayer"));
    }

    @Test
    public void testNewCommandIsRegistered() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);
        // /welcome should exist
        assertTrue(server.dispatchCommand(player, "welcome targetPlayer"));
    }

    @Test
    public void testVoteSuccess() {
        PlayerMock voter = server.addPlayer();
        voter.addAttachment(plugin, "welcome.use", true);
        
        PlayerMock target = server.addPlayer("targetPlayer");
        target.addAttachment(plugin, "welcome.use", true);
        target.setWhitelisted(false);
        
        assertFalse(target.isWhitelisted());
        
        assertTrue(server.dispatchCommand(voter, "welcome targetPlayer"));
        assertTrue(server.dispatchCommand(target, "welcome targetPlayer"));
        assertTrue(target.isWhitelisted(), "Target should be whitelisted after successful vote");
    }

    @Test
    public void testVoteForCachedPlayer() {
        PlayerMock voter = server.addPlayer();
        voter.addAttachment(plugin, "welcome.use", true);
        
        String targetName = "UnseenPlayer";
        // Ensure they haven't played before
        assertFalse(server.getOfflinePlayer(targetName).hasPlayedBefore());
        
        // Add to cache manually (simulating LoginAttemptListener)
        plugin.getPlayerCacheManager().addPlayer(targetName);
        
        // This should now be valid even if they haven't played before
        assertTrue(server.dispatchCommand(voter, "welcome " + targetName));
    }
}
