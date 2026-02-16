package com.welcome.managers;

import org.bukkit.OfflinePlayer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import static java.time.Duration.ofMillis;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerCachePerformanceTest {

    @Test
    public void testPerformanceWith1000Players() {
        List<OfflinePlayer> players = new ArrayList<>();
        long now = System.currentTimeMillis();
        
        for (int i = 0; i < 1000; i++) {
            OfflinePlayer p = mock(OfflinePlayer.class);
            when(p.getName()).thenReturn("Player" + i);
            when(p.getLastPlayed()).thenReturn(now - (i * 1000L)); // all within 3 days
            players.add(p);
        }

        assertTimeout(ofMillis(500), () -> {
            players.stream()
                .filter(p -> p.getName() != null)
                .filter(p -> p.getLastPlayed() > (System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000L))
                .sorted(Comparator.comparingLong(OfflinePlayer::getLastPlayed).reversed())
                .limit(100)
                .map(OfflinePlayer::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();
        });
    }
}
