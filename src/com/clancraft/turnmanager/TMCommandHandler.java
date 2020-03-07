package com.clancraft.turnmanager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class to handle commands passed from Bukkit.
 */
public class TMCommandHandler implements CommandExecutor {
    /**
     * Overridden from CommandExecutor. TODO comprehensive explanation
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length < 1) {
                // tm has no argument
                player.sendMessage(TMConstants.MISSING_ARGUMENT_ERROR);
                return true;
            }

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
            case "teleport":
            	// TODO add permission validation
            	// TODO add shield validation
            	TurnManager.teleport.teleport(player);
            	break;
            case "shield":
            	handleShield(player, args);
            	break;
            default:
                player.sendMessage(TMConstants.INVALID_ARGUMENT_ERROR);
                break;
            }
        }

        return true;
    }

    /**
     * Helper method to handle /tm timer ... commands.
     * 
     * @param player player who executed the command
     * @param args   argument of the command calls
     */
    private void handleTimer(Player player, String[] args) {
        if (args.length < 2) {
            // tm timer missing 1 argument
            // TODO add a tm timer custom error message
            player.sendMessage(TMConstants.MISSING_ARGUMENT_ERROR);
            return;
        }

        switch (args[1]) {
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
            } else {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            }
            break;
        case "pause":
            if (player.hasPermission(TMConstants.TIMER_PAUSE_PERMISSION)) {
                TurnManager.turn.pauseTimer();
            } else {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            }
            break;
        case "resume":
        if (player.hasPermission(TMConstants.TIMER_RESUME_PERMISSION)) {
                TurnManager.turn.resumeTimer();
            } else {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            }
            break;
        default:
            player.sendMessage(TMConstants.INVALID_ARGUMENT_ERROR);
            break;
        }
    }

    /**
     * Helper method to handle /tm turn ... commands.
     * 
     * @param player player who executed the command
     * @param args   argument of the command calls
     */
    private void handleTurn(Player player, String[] args) {
        if (args.length < 2) {
            // tm turn missing 1 argument
            // TODO add a tm turn custom error message
            player.sendMessage(TMConstants.MISSING_ARGUMENT_ERROR);
            return;
        }

        switch (args[1]) {
        case "next":
            if (player.hasPermission(TMConstants.TURN_NEXT_PERMISSION)) {
                TurnManager.turn.nextTurn();
                // TODO this is supposed to be a callback, call it inside Turn?
                TurnManager.shield.clearShield();
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

    /**
     * Helper method to handle /tm cycle ... commands.
     * 
     * @param player player who executed the command
     * @param args   argument of the command calls
     */
    private void handleCycle(Player player, String[] args) {
        if (args.length < 2) {
            // tm cycle missing 1 argument
            // TODO add a tm cycle custom error message
            player.sendMessage(TMConstants.MISSING_ARGUMENT_ERROR);
            return;
        }

        switch (args[1]) {
        case "list":
            if (player.hasPermission(TMConstants.CYCLE_LIST_PERMISSION)) {
                player.sendMessage(String.format(TMConstants.ANNOUNCE_SEQUENCE, TurnManager.cycle.toString()));
            } else {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            }
            break;
        case "add":
            if (args.length < 3) {
                // tm cycle add missing 1 argument
                // TODO add a tm cycle add custom error message
                player.sendMessage(TMConstants.MISSING_ARGUMENT_ERROR);
                return;
            }

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
            if (args.length < 3) {
                // tm cycle remove missing 1 argument
                // TODO add a tm cycle remove custom error message
                player.sendMessage(TMConstants.MISSING_ARGUMENT_ERROR);
                return;
            }

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
            if (args.length < 4) {
                // tm cycle swap missing 1 or 2 arguments
                // TODO add a tm cycle swap custom error message
                player.sendMessage(TMConstants.MISSING_ARGUMENT_ERROR);
                return;
            }

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
    
    /**
     * Helper method to handle /tm shield ... commands.
     * 
     * @param player player who executed the command
     * @param args   argument of the command calls
     */
    private void handleShield(Player player, String[] args) {
    	if (args.length < 2) {
            // tm shield missing 1 argument
            // TODO add a tm shield custom error message
            player.sendMessage(TMConstants.MISSING_ARGUMENT_ERROR);
            return;
        }
    	
    	// TODO add permission validation
    	switch (args[1]) {
    	case "add":
    		TurnManager.shield.addPlayer(args[2]);
    		break;
    	case "remove":
    		TurnManager.shield.removePlayer(args[2]);
    		break;
    	case "all":
    		TurnManager.shield.addAllPlayers();
    		break;
    	case "clear":
    		TurnManager.shield.clearShield();
    		break;
    	case "list":
    		player.sendMessage(TurnManager.shield.toString());
    		break;
    	case "on":
    		TurnManager.shield.setIsToggled(true);
    		break;
    	case "off":
    		TurnManager.shield.setIsToggled(false);
    		break;
    	}
    }
}