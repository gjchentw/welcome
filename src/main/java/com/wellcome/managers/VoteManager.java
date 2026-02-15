package com.wellcome.managers;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VoteManager {

    // Target UUID -> Set of Voter UUIDs
    private final Map<UUID, Set<UUID>> votes = new ConcurrentHashMap<>();

    public boolean vote(UUID target, UUID voter) {
        Set<UUID> voters = votes.computeIfAbsent(target, k -> ConcurrentHashMap.newKeySet());
        return voters.add(voter); // Returns true if voter was added (not present before)
    }

    public int getVoteCount(UUID target) {
        Set<UUID> voters = votes.get(target);
        return voters == null ? 0 : voters.size();
    }

    public void clearVotes(UUID target) {
        votes.remove(target);
    }
    
    public void clearAll() {
        votes.clear();
    }
}
