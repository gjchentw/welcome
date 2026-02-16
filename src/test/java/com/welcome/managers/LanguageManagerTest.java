package com.welcome.managers;

import com.welcome.configuration.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class LanguageManagerTest {

    private LanguageManager languageManager;
    private JavaPlugin plugin;
    private ConfigManager configManager;
    private File dataFolder;

    @BeforeEach
    public void setUp() {
        plugin = mock(JavaPlugin.class);
        configManager = mock(ConfigManager.class);
        dataFolder = new File("build/tmp/testData");
        dataFolder.mkdirs();
        
        when(plugin.getDataFolder()).thenReturn(dataFolder);
        when(plugin.getLogger()).thenReturn(Logger.getLogger("Welcome"));
        when(configManager.getLanguage()).thenReturn("en_US");
        
        // We can't easily test real resource loading here without MockBukkit or real files
        // but we can verify initialization doesn't crash if we mock enough.
        languageManager = new LanguageManager(plugin, configManager);
    }

    @Test
    public void testInitialization() {
        assertNotNull(languageManager);
    }
}
