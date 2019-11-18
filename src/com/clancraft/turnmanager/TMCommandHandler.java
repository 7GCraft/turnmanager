package com.clancraft.turnmanager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TMCommandHandler implements CommandExecutor {
	
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
                if (player.hasPermission(TMStrings.TURN_NEXT_PERMISSION)) {
                	TurnManager.turn.nextTurn();
                } else {
                	player.sendMessage(TMStrings.NO_PERMISSION_ERROR);
                }
                break;
            case "announce":
            	if (player.hasPermission(TMStrings.TURN_ANNOUNCE_PERMISSION)) {
                	TurnManager.turn.announceTurn();
                } else {
                	player.sendMessage(TMStrings.NO_PERMISSION_ERROR);
                }
            default:
            	player.sendMessage(TMStrings.INVALID_ARGUMENT_ERROR);
                break;
        }
        return true;
    }

    public boolean handleCycle(Player player, String[] args) {
        switch (args[1]) {
            case "list":
                if (player.hasPermission(TMStrings.CYCLE_LIST_PERMISSION)) {
                	player.sendMessage(String.format(TMStrings.ANNOUNCE_SEQUENCE, TurnManager.cycle.toString()));
                } else {
                	player.sendMessage(TMStrings.NO_PERMISSION_ERROR);
                }
                break;
            case "add":
                if (player.hasPermission(TMStrings.CYCLE_ADD_PERMISSION)) {
                	if (TurnManager.cycle.addPlayer(args[2])) {
                        player.sendMessage(String.format(TMStrings.ADD_PLAYER_SUCCESS, args[2]));
                    } else {
                        player.sendMessage(String.format(TMStrings.ADD_PLAYER_FAILED, args[2]));
                    }
                } else {
                	player.sendMessage(TMStrings.NO_PERMISSION_ERROR);
                }
                break;
            case "remove":
                if (player.hasPermission(TMStrings.CYCLE_REMOVE_PERMISSION)) {
                	if (TurnManager.cycle.removePlayer(args[2])) {
                        player.sendMessage(String.format(TMStrings.REMOVE_PLAYER_SUCCESS, args[2]));
                    } else {
                        player.sendMessage(String.format(TMStrings.REMOVE_PLAYER_FAILED, args[2]));
                    }
                } else {
                	player.sendMessage(TMStrings.NO_PERMISSION_ERROR);
                }
                break;
            case "swap":
                if (player.hasPermission(TMStrings.CYCLE_SWAP_PERMISSION)) {
                	if (TurnManager.cycle.swap(args[2], args[3])) {
                        player.sendMessage(String.format(TMStrings.SWAP_PLAYER_SUCCESS, args[2], args[3]));
                    } else {
                        player.sendMessage(String.format(TMStrings.SWAP_PLAYER_FAILED, args[2], args[3]));
                    }
                } else {
                	player.sendMessage(TMStrings.NO_PERMISSION_ERROR);
                }
                break;
            default:
                player.sendMessage(TMStrings.INVALID_ARGUMENT_ERROR);
                break;
        }
        return true;
    }
}