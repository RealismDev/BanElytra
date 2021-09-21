package me.realism.banelytra;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class BanElytraCommand implements CommandExecutor {

    private final Main main;
    public BanElytraCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("banelytra")) {
            if (args.length == 1) {
                if (args[0].equals("reload")) {
                    // /banelytra reload
                    reloadCommand(sender);
                    return true;
                }
                if (args[0].equals("help")) {
                    // /banelytra help
                    helpCommand(sender);
                    return true;
                }

            }
            if (args.length == 3) {
                if (args[0].equals("exempt")) {
                    // /banelytra exempt <argument> <argument>
                    exemptCommand(sender, args, main.getConfig());
                    return true;
                }
            }
            sender.sendMessage(ChatColor.RED + "Incorrect usage. Use '/banelytra help' for help.");
            return true;
        }
        return false;
    }

    public void reloadCommand(CommandSender sender) {
        if (sender.hasPermission("banelytra.reload")) {
            main.reloadConfig();
            String reloadMessage = main.getConfig().getString("reload.message");

            if (!(reloadMessage == null))
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', reloadMessage));

            return;


        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cInsufficient permission."));
    }

    public void exemptCommand(CommandSender sender, String[] args, FileConfiguration config) {

        if (sender.hasPermission("banelytra.exempt")) {
            Server server = sender.getServer();
            Player targetPlayer = server.getPlayer(args[2]);
            String func = args[1];

            if (Utils.IsValidPlayer(targetPlayer)) {

                List<String> ExemptPlayers = config.getStringList("exempt.exempt-players");
                String targetPlayerName = targetPlayer.getDisplayName();

                if (func.equals("add")) {

                    if (ExemptPlayers.contains(targetPlayerName)) {
                        sender.sendMessage(ChatColor.YELLOW + "That player is already exempt.");
                        return;
                    }

                    ExemptPlayers.add(targetPlayer.getDisplayName());
                    config.set("exempt.exempt-players", ExemptPlayers);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2Successfully added &6" + targetPlayerName + "&2 to the exempt list."));

                    main.saveConfig();
                    main.reloadConfig();
                    return;
                }
                if (func.equals("remove")) {

                    if (!(ExemptPlayers.contains(targetPlayerName))) {
                        // Cant unexempt someone that isn't exempt
                        sender.sendMessage(ChatColor.YELLOW + "That player isn't exempt.");
                        return;
                    }

                    ExemptPlayers.remove(targetPlayerName);
                    config.set("exempt.exempt-players", ExemptPlayers);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2Successfully removed &6 " + targetPlayerName + "&2 from the exempt list."));

                    main.saveConfig();
                    main.reloadConfig();
                    return;
                }
                sender.sendMessage(ChatColor.RED + "Incorrect usage. Correct usage: /banelytra exempt <add|remove> <player>");
                return;

            }
            sender.sendMessage(ChatColor.RED + "Player needs to be online.");
            return;
        }
        sender.sendMessage(ChatColor.RED + "Insufficient permission.");

    }

    public void helpCommand(CommandSender sender) {
        if (sender.hasPermission("banelytra.help")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Ban elytra help.\n&9/banelytra reload - &6Reloads the config.yml file.\n&9/banelytra exempt <add|remove> <player> - &6Exempts a player from the plugin."));
        }
    }
}
