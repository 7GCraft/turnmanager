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
                if (player.hasPermission(TMPermissions.CYCLE_PERMISSION)) {
                    handleCycle(player, args);
                } else {
                    player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
                }
                break;
            case "turn":
                if (player.hasPermission(TMPermissions.TURN_PERMISSION)) {
                    handleTurn(player, args);
                } else {
                    player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
                }
                break;
            case "timer":
                if (player.hasPermission(TMPermissions.TIMER_PERMISSION)) {
                    handleTimer(player, args);
                } else {
                    player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
                }
                break;
            case "teleport":
                // TODO add permission validation
                // TODO add shield validation
                TurnManager.getTeleport().teleport(player);
                break;
            case "shield":
                handleShield(player, args);
                break;
            case "date":
                handleDate(player, args);
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
            if (player.hasPermission(TMPermissions.TIMER_START_PERMISSION)) {
                if (args.length > 2) {
                    TurnManager.getTurn().startTimer(Integer.parseInt(args[2]));
                } else {
                    TurnManager.getTurn().startTimer();
                }
            } else {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            }
            break;
        case "stop":
            if (player.hasPermission(TMPermissions.TIMER_STOP_PERMISSION)) {
                TurnManager.getTurn().stopTimer();
            } else {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            }
            break;
        case "pause":
            if (player.hasPermission(TMPermissions.TIMER_PAUSE_PERMISSION)) {
                TurnManager.getTurn().pauseTimer();
            } else {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            }
            break;
        case "resume":
        if (player.hasPermission(TMPermissions.TIMER_RESUME_PERMISSION)) {
                TurnManager.getTurn().resumeTimer();
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
            if (player.hasPermission(TMPermissions.TURN_NEXT_PERMISSION)) {
                TurnManager.getTurn().nextTurn();
            } else {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            }
            break;
        case "announce":
            if (player.hasPermission(TMPermissions.TURN_ANNOUNCE_PERMISSION)) {
                TurnManager.getTurn().announceTurn();
            } else {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            }
        case "accept":
            if (player.hasPermission(TMPermissions.TURN_ACCEPT_PERMISSION)) {
                TurnManager.getTurn().acceptTurn(player);
            } else {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            }
            break;
        case "reject":
            if (player.hasPermission(TMPermissions.TURN_REJECT_PERMISSION)) {
                TurnManager.getTurn().rejectTurn(player);
            } else {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            }
            break;
        case "override":
            if (player.hasPermission(TMPermissions.TURN_OVERRIDE_PERMISSION)) {
                TurnManager.getTurn().rejectTurn();
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
            if (player.hasPermission(TMPermissions.CYCLE_LIST_PERMISSION)) {
                player.sendMessage(String.format(TMConstants.ANNOUNCE_SEQUENCE, TurnManager.getCycle().toString()));
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

            if (player.hasPermission(TMPermissions.CYCLE_ADD_PERMISSION)) {
                if (TurnManager.getCycle().addPlayer(args[2])) {
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

            if (player.hasPermission(TMPermissions.CYCLE_REMOVE_PERMISSION)) {
                if (TurnManager.getCycle().removePlayer(args[2])) {
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

            if (player.hasPermission(TMPermissions.CYCLE_SWAP_PERMISSION)) {
                if (TurnManager.getCycle().swap(args[2], args[3])) {
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
            if (player.hasPermission(TMPermissions.SHIELD_ADD_PERMISSION)) {
                if (args.length == 4) {
                    TurnManager.getShield().addPlayer(args[2], args[3]);
                } else {
                    TurnManager.getShield().addPlayer(args[2]);
                }
            } else {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            }
            break;
        case "remove":
            if (player.hasPermission(TMPermissions.SHIELD_REMOVE_PERMISSION)) {
                if (args.length == 4) {
                    TurnManager.getShield().removePlayer(args[2], args[3]);
                } else {
                    TurnManager.getShield().removePlayer(args[2]);
                }
            } else {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            }
            break;
        case "all":
            if (player.hasPermission(TMPermissions.SHIELD_ALL_PERMISSION)) {
                if (args.length == 3) {
                    TurnManager.getShield().addAllPlayers(args[2]);
                } else {
                    TurnManager.getShield().addAllPlayers();
                }
            } else {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            }
            break;
        case "clear":
            if (player.hasPermission(TMPermissions.SHIELD_CLEAR_PERMISSION)) {
                if (args.length == 3) {
                    TurnManager.getShield().clearShield(args[2]);
                } else {
                    TurnManager.getShield().clearShield();
                }
            } else {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            }
            break;
        case "list":
            if (player.hasPermission(TMPermissions.SHIELD_LIST_PERMISSION)) {
                if (args.length == 3) {
                    player.sendMessage(TurnManager.getShield().toString(args[2]));
                } else {
                    player.sendMessage(TurnManager.getShield().toString());
                }
            } else {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            }
            break;
        case "on":
            if (player.hasPermission(TMPermissions.SHIELD_TOGGLE_PERMISSION) || player.hasPermission(TMPermissions.SHIELD_ON_PERMISSION)) {
                if (args.length == 4) {
                    TurnManager.getShield().toggle(args[2], true);
                } else {
                    TurnManager.getShield().toggle(true);
                }
            } else {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            }
            break;
        case "off":
            if (player.hasPermission(TMPermissions.SHIELD_TOGGLE_PERMISSION) || player.hasPermission(TMPermissions.SHIELD_OFF_PERMISSION)) {
                if (args.length == 4) {
                    TurnManager.getShield().toggle(args[2], false);
                } else {
                    TurnManager.getShield().toggle(false);
                }
            } else {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
            }
            break;
        case "register":
            TurnManager.getShield().registerPlayer(args[2]);
            break;
        case "unregister":
            TurnManager.getShield().unregisterPlayer(args[2]);
            break;
        }
    }

    /**
     * Helper method to handle /tm date ... commands.
     * 
     * @param player player who executed the command
     * @param args   argument of the command calls
     */
    private void handleDate(Player player, String[] args) {
        if (args.length < 2) {
            // tm shield missing 1 argument
            // TODO add a tm date custom error message
            player.sendMessage(TMConstants.MISSING_ARGUMENT_ERROR);
            return;
        }

        switch(args[1]) {
            case "add":
                if (args.length == 4) {
                    TurnManager.getCalendar().addPlayerDate(args[2], Integer.parseInt(args[3]));
                } else if (args.length == 3) {
                    TurnManager.getCalendar().addWorldDate(Integer.parseInt(args[2]));
                }
                break;
            case "set":
                if (args.length == 6) {
                    TurnManager.getCalendar().setPlayerDate(args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
                } else if (args.length == 5) {
                    TurnManager.getCalendar().setWorldDate(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                }
                break;
            case "sync":
                TurnManager.getCalendar().sync(args[2]);
                break;
            case "unsync":
                TurnManager.getCalendar().unsync(args[2]);
                break;
            case "today":
                if (args.length == 3 && args[2].equalsIgnoreCase("world")) {
                    player.sendMessage(TurnManager.getCalendar().getWorldDate());
                } else if (args.length == 2) {
                    player.sendMessage(TurnManager.getCalendar().getPlayerDate(player.getName()));
                } else if (args.length == 3) {
                    player.sendMessage(TurnManager.getCalendar().getPlayerDate(args[2]));
                }
                break;
            case "auto":
                if (args[2].equalsIgnoreCase("on")) {
                    TurnManager.getCalendar().setIsAuto(true);
                } else if (args[2].equalsIgnoreCase("off")) {
                    TurnManager.getCalendar().setIsAuto(false);
                }
                break;
            case "register":
                TurnManager.getCalendar().registerPlayer(args[2]);
                break;
            case "unregister":
                TurnManager.getCalendar().unregisterPlayer(args[2]);
                break;
        }
    }
}