package com.welcome.listeners;

import com.welcome.managers.PlayerCacheManager;
import com.welcome.utils.WhitelistUtils;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.*;

public class PlayerJoinListenerTest {

    private PlayerJoinListener listener;
    private PlayerCacheManager cacheManager;
    private JavaPlugin plugin;
    private Server server;
    private BukkitScheduler scheduler;

    @BeforeEach
    public void setUp() {
        plugin = mock(JavaPlugin.class);
        server = mock(Server.class);
        scheduler = mock(BukkitScheduler.class);
        cacheManager = mock(PlayerCacheManager.class);
        
        when(plugin.getServer()).thenReturn(server);
        when(server.getScheduler()).thenReturn(scheduler);
        
        listener = new PlayerJoinListener(plugin, cacheManager);
    }

    @Test
    public void testOnPlayerJoin() {
        Player player = mock(Player.class);
        when(player.getName()).thenReturn("NewPlayer");
        PlayerJoinEvent event = new PlayerJoinEvent(player, "Welcome NewPlayer");

        try (MockedStatic<WhitelistUtils> whitelistUtils = mockStatic(WhitelistUtils.class)) {
            whitelistUtils.when(() -> WhitelistUtils.isWhitelisted(player)).thenReturn(false);
            
            listener.onPlayerJoin(event);
            
            // Capture the runnable and run it
            ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
            verify(scheduler).runTaskAsynchronously(eq(plugin), runnableCaptor.capture());
            
            runnableCaptor.getValue().run();
            
            // Verify that addPlayer was called
            verify(cacheManager).addPlayer("NewPlayer");
        }
    }

    @Test
    public void testOnWhitelistedPlayerJoin() {
        Player player = mock(Player.class);
        when(player.getName()).thenReturn("WhitePlayer");
        PlayerJoinEvent event = new PlayerJoinEvent(player, "Welcome WhitePlayer");

        try (MockedStatic<WhitelistUtils> whitelistUtils = mockStatic(WhitelistUtils.class)) {
            whitelistUtils.when(() -> WhitelistUtils.isWhitelisted(player)).thenReturn(true);
            
            listener.onPlayerJoin(event);
            
            ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
            verify(scheduler).runTaskAsynchronously(eq(plugin), runnableCaptor.capture());
            
            runnableCaptor.getValue().run();
            
            // Verify that addPlayer was NOT called
            verify(cacheManager, never()).addPlayer(anyString());
        }
    }
}
