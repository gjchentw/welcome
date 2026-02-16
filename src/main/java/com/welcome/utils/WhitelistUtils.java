package com.welcome.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

public class WhitelistUtils {

    public static List<String> getVotablePlayerNames() {
        List<String> players = new ArrayList<>();
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (!player.isWhitelisted() && player.getName() != null) {
                players.add(player.getName());
            }
        }
        return players;
    }

    public static boolean isWhitelistEnabled() {
        return Bukkit.hasWhitelist();
    }

    public static boolean isWhitelisted(OfflinePlayer player) {
        return player.isWhitelisted();
    }

    public static void setWhitelisted(OfflinePlayer player, boolean value) {
        player.setWhitelisted(value);
    }
}
