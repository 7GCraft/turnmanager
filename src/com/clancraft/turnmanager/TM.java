package com.clancraft.turnmanager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TM implements CommandExecutor {

    private Cycle cycle;

    public TM() {
        cycle = new Cycle();
        cycle.init();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                switch (args[0]) {
                    case "cycle":
                        cycleHandler(player, args);
                        break;
                }
            } else {
                player.sendMessage(TurnManager.pluginPrefix + "/tm requires an argument:");
                player.sendMessage(TurnManager.pluginPrefix + "/tm [argument]");
            }
        }

        return true;
    }

    public boolean cycleHandler(Player player, String[] args) {
        switch (args[1]) {
            case "list":
                player.sendMessage(TurnManager.pluginPrefix + cycle.getTurnSequence());
                break;
            case "add":
                if (cycle.addPlayer(args[2])) {
                    player.sendMessage(TurnManager.pluginPrefix + args[2] + " has been added to the turn sequence.");
                } else {
                    player.sendMessage(TurnManager.pluginPrefix + ChatColor.RED + args[2] + "could not be added to the turn sequence.");
                }
                break;
            case "remove":
                if (cycle.removePlayer(args[2])) {
                    player.sendMessage(TurnManager.pluginPrefix + args[2] + " has been removed from the turn sequence.");
                } else {
                    player.sendMessage(TurnManager.pluginPrefix + ChatColor.RED + args[2] + "could not be removed from the turn sequence.");
                }
                break;
            case "swap":
                if (cycle.swap(args[2], args[3])) {
                    player.sendMessage(TurnManager.pluginPrefix + "Players have been swapped in the turn sequence.");
                } else {
                    player.sendMessage(TurnManager.pluginPrefix + ChatColor.RED + "Players could not be swapped in the turn sequence.");
                }
                break;
            case "next":
                cycle.nextTurn();
                break;
            default:
                player.sendMessage(TurnManager.pluginPrefix + ChatColor.RED + "Invalid argument.");
                break;
        }
        return true;
    }
}