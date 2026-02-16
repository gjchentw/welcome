package com.welcome.managers;

import com.welcome.configuration.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerCacheManagerTest {

    private PlayerCacheManager cacheManager;
    private ConfigManager configManager;
    private JavaPlugin plugin;
    private final UUID uuid1 = UUID.randomUUID();
    private final UUID uuid2 = UUID.randomUUID();
    private final UUID uuid3 = UUID.randomUUID();
    private final UUID uuid4 = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        plugin = mock(JavaPlugin.class);
        when(plugin.getLogger()).thenReturn(Logger.getLogger("Welcome"));
        
        configManager = mock(ConfigManager.class);
        when(configManager.getAutocompleteMaxPlayers()).thenReturn(3); // Small limit for testing
        
        cacheManager = new PlayerCacheManager(plugin, configManager);
    }

    @Test
    public void testAddPlayer() {
        cacheManager.addPlayer("Player1", uuid1);
        List<String> names = cacheManager.getCachedNames();
        
        assertEquals(1, names.size());
        assertEquals("Player1", names.get(0));
        assertEquals(uuid1, cacheManager.getUuid("Player1"));
    }

    @Test
    public void testLRULogic() {
        cacheManager.addPlayer("Player1", uuid1);
        cacheManager.addPlayer("Player2", uuid2);
        cacheManager.addPlayer("Player3", uuid3);
        
        // Should be [Player3, Player2, Player1]
        List<String> names = cacheManager.getCachedNames();
        assertEquals("Player3", names.get(0));
        assertEquals("Player1", names.get(2));
        
        // Access Player1, should move to front
        cacheManager.getUuid("Player1"); 
        // Re-adding also moves to front in our implementation (Map.put)
        cacheManager.addPlayer("Player1", uuid1);
        
        names = cacheManager.getCachedNames();
        assertEquals(3, names.size());
        assertEquals("Player1", names.get(0));
        assertEquals("Player3", names.get(1));
        assertEquals("Player2", names.get(2));
    }

    @Test
    public void testCapacityLimit() {
        cacheManager.addPlayer("Player1", uuid1);
        cacheManager.addPlayer("Player2", uuid2);
        cacheManager.addPlayer("Player3", uuid3);
        cacheManager.addPlayer("Player4", uuid4);
        
        // Limit is 3, so Player1 should be evicted
        List<String> names = cacheManager.getCachedNames();
        assertEquals(3, names.size());
        assertEquals("Player4", names.get(0));
        assertEquals("Player3", names.get(1));
        assertEquals("Player2", names.get(2));
        assertFalse(names.contains("Player1"));
        assertNull(cacheManager.getUuid("Player1"));
    }

    @Test
    public void testRemovePlayer() {
        cacheManager.addPlayer("Player1", uuid1);
        cacheManager.addPlayer("Player2", uuid2);
        
        cacheManager.removePlayer("Player1");
        List<String> names = cacheManager.getCachedNames();
        
        assertEquals(1, names.size());
        assertEquals("Player2", names.get(0));
        assertFalse(names.contains("Player1"));
        assertNull(cacheManager.getUuid("Player1"));
    }
}
