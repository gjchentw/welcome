package com.welcome.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class VoteManagerTest {

    private VoteManager voteManager;
    private UUID target;
    private UUID voter1;
    private UUID voter2;

    @BeforeEach
    public void setUp() {
        voteManager = new VoteManager();
        target = UUID.randomUUID();
        voter1 = UUID.randomUUID();
        voter2 = UUID.randomUUID();
    }

    @Test
    public void testVoteSuccess() {
        assertTrue(voteManager.vote(target, voter1), "First vote should succeed");
        assertEquals(1, voteManager.getVoteCount(target), "Vote count should be 1");
    }

    @Test
    public void testDuplicateVote() {
        assertTrue(voteManager.vote(target, voter1));
        assertFalse(voteManager.vote(target, voter1), "Duplicate vote should fail");
        assertEquals(1, voteManager.getVoteCount(target), "Vote count should still be 1");
    }

    @Test
    public void testMultipleVoters() {
        assertTrue(voteManager.vote(target, voter1));
        assertTrue(voteManager.vote(target, voter2));
        assertEquals(2, voteManager.getVoteCount(target), "Vote count should be 2");
    }

    @Test
    public void testClearVotes() {
        voteManager.vote(target, voter1);
        voteManager.clearVotes(target);
        assertEquals(0, voteManager.getVoteCount(target), "Vote count should be 0 after clear");
    }

    @Test
    public void testClearAll() {
        voteManager.vote(target, voter1);
        voteManager.vote(UUID.randomUUID(), voter2);
        voteManager.clearAll();
        assertEquals(0, voteManager.getVoteCount(target));
    }
}
