package com.wellcome.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class WhitelistUtils {

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
