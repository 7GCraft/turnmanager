package com.clancraft.turnmanager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TM implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                switch (args[0]) {
                    case "cycle":
                        handleCycle(player, args);
                        break;
                    case "turn":
                        handleTurn(player, args);
                        break;
                    default:
                        player.sendMessage(TMStrings.INVALID_ARGUMENT_ERROR);
                        break;
                }
            } else {
                //tm has no argument
                player.sendMessage(TMStrings.MISSING_ARGUMENT_ERROR);
            }
        }

        return true;
    }

    public boolean handleTurn(Player player, String[] args) {
        switch(args[1]) {
            case "next":
                TurnManager.turn.nextTurn();
                break;
            case "announce":
                TurnManager.turn.announceTurn();
                break;
        }
        return true;
    }

    public boolean handleCycle(Player player, String[] args) {
        switch (args[1]) {
            case "list":
                player.sendMessage(String.format(TMStrings.PLAYER_LIST, TurnManager.cycle.toString()));
                break;
            case "add":
                if (TurnManager.cycle.addPlayer(args[2])) {
                    player.sendMessage(String.format(TMStrings.ADD_PLAYER_SUCCESS, args[2]));
                } else {
                    player.sendMessage(String.format(TMStrings.ADD_PLAYER_FAILED, args[2]));
                }
                break;
            case "remove":
                if (TurnManager.cycle.removePlayer(args[2])) {
                    player.sendMessage(String.format(TMStrings.REMOVE_PLAYER_SUCCESS, args[2]));
                } else {
                    player.sendMessage(String.format(TMStrings.REMOVE_PLAYER_FAILED, args[2]));
                }
                break;
            case "swap":
                if (TurnManager.cycle.swap(args[2], args[3])) {
                    player.sendMessage(String.format(TMStrings.SWAP_PLAYER_SUCCESS, args[2], args[3]));
                } else {
                    player.sendMessage(String.format(TMStrings.SWAP_PLAYER_FAILED, args[2], args[3]));
                }
                break;
            default:
                player.sendMessage(TMStrings.INVALID_ARGUMENT_ERROR);
                break;
        }
        return true;
    }
}