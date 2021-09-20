package me.realism.banelytra;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private final Main main;
    public ReloadCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("banelytra")) {
            if (sender.hasPermission("banelytra.reload")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        main.reloadConfig();
                        String reloadMessage = main.getConfig().getString("reload.message");

                        if (!(reloadMessage == null))
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', reloadMessage));

                        return true;
                    }
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cIncorrect usage. Correct usage:\n&6/banelytra reload"));
                return true;
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cInsufficient permission."));
            return true;
        }
        return false;
    }
}
