package me.realism.banelytra;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class Utils {

    public static boolean IsExempt(Player player, FileConfiguration config) {
        if (config.getBoolean("exempt.exempt-ops")) {
            if (player.isOp()) {
                return true;
            }
        }
        for (String exemptplayer: config.getStringList("exempt.exempt-players")) {
            if (player.getDisplayName().equalsIgnoreCase(exemptplayer))
                return true;
        }
        // Player wasn't exempt.
        return false;
    }

    public static boolean IsValidPlayer(Player player) {
        if(player != null){
            // The player is valid
            return true;
        } else {
            // The player is not valid.
            return false;
        }
    }
}
