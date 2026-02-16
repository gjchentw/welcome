package com.welcome.utils;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WhitelistUtilsTest {

    private ServerMock server;

    @BeforeEach
    public void setUp() {
        server = MockBukkit.mock();
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testGetVotablePlayerNames() {
        // Create a player who is whitelisted
        PlayerMock whitelistedPlayer = server.addPlayer("WhitelistedPlayer");
        whitelistedPlayer.setWhitelisted(true);

        // Create a player who is not whitelisted
        server.addPlayer("NotWhitelistedPlayer");

        List<String> votablePlayers = WhitelistUtils.getVotablePlayerNames();

        assertTrue(votablePlayers.contains("NotWhitelistedPlayer"), "Should contain non-whitelisted player");
        assertFalse(votablePlayers.contains("WhitelistedPlayer"), "Should NOT contain whitelisted player");
    }
}
