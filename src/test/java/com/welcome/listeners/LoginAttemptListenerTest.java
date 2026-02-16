package com.welcome.listeners;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.welcome.managers.PlayerCacheManager;
import com.welcome.utils.WhitelistUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class LoginAttemptListenerTest {

    private LoginAttemptListener listener;
    private PlayerCacheManager cacheManager;
    private JavaPlugin plugin;

    @BeforeEach
    public void setUp() {
        MockBukkit.mock();
        cacheManager = mock(PlayerCacheManager.class);
        listener = new LoginAttemptListener(cacheManager);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testOnPlayerPreLoginNonWhitelisted() throws UnknownHostException {
        String name = "AdamChenCFD";
        UUID uuid = UUID.randomUUID();
        AsyncPlayerPreLoginEvent event = new AsyncPlayerPreLoginEvent(name, InetAddress.getByName("127.0.0.1"), uuid);

        OfflinePlayer offlinePlayer = mock(OfflinePlayer.class);
        
        try (MockedStatic<Bukkit> bukkit = mockStatic(Bukkit.class);
             MockedStatic<WhitelistUtils> whitelistUtils = mockStatic(WhitelistUtils.class)) {
            
            bukkit.when(() -> Bukkit.getOfflinePlayer(uuid)).thenReturn(offlinePlayer);
            whitelistUtils.when(() -> WhitelistUtils.isWhitelisted(offlinePlayer)).thenReturn(false);
            
            listener.onPlayerPreLogin(event);
            
            verify(cacheManager).addPlayer(name, uuid);
        }
    }

    @Test
    public void testOnPlayerPreLoginWhitelisted() throws UnknownHostException {
        String name = "KnownPlayer";
        UUID uuid = UUID.randomUUID();
        AsyncPlayerPreLoginEvent event = new AsyncPlayerPreLoginEvent(name, InetAddress.getByName("127.0.0.1"), uuid);

        OfflinePlayer offlinePlayer = mock(OfflinePlayer.class);
        
        try (MockedStatic<Bukkit> bukkit = mockStatic(Bukkit.class);
             MockedStatic<WhitelistUtils> whitelistUtils = mockStatic(WhitelistUtils.class)) {
            
            bukkit.when(() -> Bukkit.getOfflinePlayer(uuid)).thenReturn(offlinePlayer);
            whitelistUtils.when(() -> WhitelistUtils.isWhitelisted(offlinePlayer)).thenReturn(true);
            
            listener.onPlayerPreLogin(event);
            
            verify(cacheManager, never()).addPlayer(anyString(), any(UUID.class));
        }
    }
}
