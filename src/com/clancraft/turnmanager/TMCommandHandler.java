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
                    case "timer":
                        handleTimer(player, args);
                        break;
                    default:
                        player.sendMessage(TMConstants.INVALID_ARGUMENT_ERROR);
                        break;
                }
            } else {
                //tm has no argument
                player.sendMessage(TMConstants.MISSING_ARGUMENT_ERROR);
            }
        }

        return true;
    }

    public void handleTimer(Player player, String[] args) {
        switch(args[1]) {
            case "start":
            	// TO-DO:
            	// add permission validation
                if (args.length > 2) {
                    TurnManager.turn.startTimer(Integer.parseInt(args[2]));
                } else {
                    TurnManager.turn.startTimer();
                }
                break;
            case "stop":
            	if (player.hasPermission(TMConstants.TIMER_STOP_PERMISSION)) {
            		TurnManager.turn.stopTimer();
            	}
            	else {
            		player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            	}
                break;
            default:
                player.sendMessage(TMConstants.INVALID_ARGUMENT_ERROR);
                break;
        }
    }

    public void handleTurn(Player player, String[] args) {
        switch(args[1]) {
            case "next":
                if (player.hasPermission(TMConstants.TURN_NEXT_PERMISSION)) {
                	TurnManager.turn.nextTurn();
                } else {
                	player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
                }
                break;
            case "announce":
            	if (player.hasPermission(TMConstants.TURN_ANNOUNCE_PERMISSION)) {
                	TurnManager.turn.announceTurn();
                } else {
                	player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
                }
            default:
            	player.sendMessage(TMConstants.INVALID_ARGUMENT_ERROR);
                break;
        }
    }

    public void handleCycle(Player player, String[] args) {
        switch (args[1]) {
            case "list":
                if (player.hasPermission(TMConstants.CYCLE_LIST_PERMISSION)) {
                	player.sendMessage(String.format(TMConstants.ANNOUNCE_SEQUENCE, TurnManager.cycle.toString()));
                } else {
                	player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
                }
                break;
            case "add":
                if (player.hasPermission(TMConstants.CYCLE_ADD_PERMISSION)) {
                	if (TurnManager.cycle.addPlayer(args[2])) {
                        player.sendMessage(String.format(TMConstants.ADD_PLAYER_SUCCESS, args[2]));
                    } else {
                        player.sendMessage(String.format(TMConstants.ADD_PLAYER_FAILED, args[2]));
                    }
                } else {
                	player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
                }
                break;
            case "remove":
                if (player.hasPermission(TMConstants.CYCLE_REMOVE_PERMISSION)) {
                	if (TurnManager.cycle.removePlayer(args[2])) {
                        player.sendMessage(String.format(TMConstants.REMOVE_PLAYER_SUCCESS, args[2]));
                    } else {
                        player.sendMessage(String.format(TMConstants.REMOVE_PLAYER_FAILED, args[2]));
                    }
                } else {
                	player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
                }
                break;
            case "swap":
                if (player.hasPermission(TMConstants.CYCLE_SWAP_PERMISSION)) {
                	if (TurnManager.cycle.swap(args[2], args[3])) {
                        player.sendMessage(String.format(TMConstants.SWAP_PLAYER_SUCCESS, args[2], args[3]));
                    } else {
                        player.sendMessage(String.format(TMConstants.SWAP_PLAYER_FAILED, args[2], args[3]));
                    }
                } else {
                	player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
                }
                break;
            default:
                player.sendMessage(TMConstants.INVALID_ARGUMENT_ERROR);
                break;
        }
    }
}