package com.clancraft.turnmanager;

import com.clancraft.turnmanager.exception.DateSyncException;
import com.clancraft.turnmanager.exception.DuplicatePlayerException;
import com.clancraft.turnmanager.exception.InsufficientPermissionException;
import com.clancraft.turnmanager.exception.PlayerNotFoundException;
import com.clancraft.turnmanager.exception.ShieldBreachException;
import com.clancraft.turnmanager.shield.ShieldObserver;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class to handle commands passed from Bukkit.
 */
public class TMCommandHandler implements CommandExecutor, ShieldObserver {
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

            try {
                switch (args[0]) {
                case "cycle":
                    assertSufficientPermission(player, TMPermissions.CYCLE_PERMISSION);
                    handleCycle(player, args);
                    break;
                case "turn":
                    assertSufficientPermission(player, TMPermissions.TURN_PERMISSION);
                    handleTurn(player, args);
                    break;
                case "timer":
                    assertSufficientPermission(player, TMPermissions.TIMER_PERMISSION);
                    handleTimer(player, args);
                    break;
                case "teleport":
                    assertSufficientPermission(player, TMPermissions.TELEPORT_PERMISSION);
                    try {
                        TurnManager.getTeleport().teleport(player);
                    } catch (ShieldBreachException e) {
                        player.sendMessage("Unable to teleport. You are in the current player's shield list.");
                        // TODO update message
                    } catch (PlayerNotFoundException e) {
                        player.sendMessage("Unable to teleport. There is no active player in turn");
                        // TODO update message
                    }
                    break;
                case "shield":
                    assertSufficientPermission(player, TMPermissions.SHIELD_PERMISSION);
                    handleShield(player, args);
                    break;
                case "date":
                    assertSufficientPermission(player, TMPermissions.DATE_PERMISSION);
                    handleDate(player, args);
                    break;
                default:
                    player.sendMessage(TMConstants.INVALID_ARGUMENT_ERROR);
                    break;
                }
            } catch (InsufficientPermissionException e) {
                player.sendMessage(TMConstants.NO_PERMISSION_ERROR);
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
    private void handleTimer(Player player, String[] args) throws InsufficientPermissionException {
        if (args.length < 2) {
            // tm timer missing 1 argument
            // TODO add a tm timer custom error message
            player.sendMessage(TMConstants.MISSING_ARGUMENT_ERROR);
            return;
        }

        switch (args[1]) {
        case "start":
            assertSufficientPermission(player, TMPermissions.TIMER_START_PERMISSION);
            if (args.length > 2) {
                TurnManager.getTurn().startTimer(Integer.parseInt(args[2]));
            } else {
                TurnManager.getTurn().startTimer();
            }
            break;
        case "stop":
            assertSufficientPermission(player, TMPermissions.TIMER_STOP_PERMISSION);
            TurnManager.getTurn().stopTimer();
            break;
        case "pause":
            assertSufficientPermission(player, TMPermissions.TIMER_PAUSE_PERMISSION);
            TurnManager.getTurn().pauseTimer();
            break;
        case "resume":
            assertSufficientPermission(player, TMPermissions.TIMER_RESUME_PERMISSION);
            TurnManager.getTurn().resumeTimer();
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
    private void handleTurn(Player player, String[] args) throws InsufficientPermissionException {
        if (args.length < 2) {
            // tm turn missing 1 argument
            // TODO add a tm turn custom error message
            player.sendMessage(TMConstants.MISSING_ARGUMENT_ERROR);
            return;
        }

        switch (args[1]) {
        case "next":
            assertSufficientPermission(player, TMPermissions.TURN_NEXT_PERMISSION);
            TurnManager.getTurn().nextTurn();
            break;
        case "announce":
            assertSufficientPermission(player, TMPermissions.TURN_ANNOUNCE_PERMISSION);
            TurnManager.getTurn().announceTurn();
        case "accept":
            assertSufficientPermission(player, TMPermissions.TURN_ACCEPT_PERMISSION);
            TurnManager.getTurn().acceptTurn(player);
            break;
        case "reject":
            assertSufficientPermission(player, TMPermissions.TURN_REJECT_PERMISSION);
            TurnManager.getTurn().rejectTurn(player);
            break;
        case "override":
            assertSufficientPermission(player, TMPermissions.TURN_OVERRIDE_PERMISSION);
            TurnManager.getTurn().rejectTurn();
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
    private void handleCycle(Player player, String[] args) throws InsufficientPermissionException {
        if (args.length < 2) {
            // tm cycle missing 1 argument
            // TODO add a tm cycle custom error message
            player.sendMessage(TMConstants.MISSING_ARGUMENT_ERROR);
            return;
        }

        switch (args[1]) {
        case "list":
            assertSufficientPermission(player, TMPermissions.CYCLE_LIST_PERMISSION);
            player.sendMessage(String.format(TMConstants.ANNOUNCE_SEQUENCE, TurnManager.getCycle().toString()));
            break;
        case "add":
            if (args.length < 3) {
                // tm cycle add missing 1 argument
                // TODO add a tm cycle add custom error message
                player.sendMessage(TMConstants.MISSING_ARGUMENT_ERROR);
                return;
            }

            assertSufficientPermission(player, TMPermissions.CYCLE_ADD_PERMISSION);
            try {
                TurnManager.getCycle().addPlayer(args[2]);
                player.sendMessage(String.format(TMConstants.ADD_PLAYER_SUCCESS, args[2]));
            } catch (DuplicatePlayerException e) {
                //TODO update
                player.sendMessage(String.format(TMConstants.ADD_PLAYER_FAILED, args[2]));
            } catch (PlayerNotFoundException e) {
                //TODO updat
                player.sendMessage(String.format(TMConstants.ADD_PLAYER_FAILED, args[2]));
            }
            break;
        case "remove":
            if (args.length < 3) {
                // tm cycle remove missing 1 argument
                // TODO add a tm cycle remove custom error message
                player.sendMessage(TMConstants.MISSING_ARGUMENT_ERROR);
                return;
            }

            assertSufficientPermission(player, TMPermissions.CYCLE_REMOVE_PERMISSION);
            try {
                TurnManager.getCycle().removePlayer(args[2]);
                player.sendMessage(String.format(TMConstants.REMOVE_PLAYER_SUCCESS, args[2]));
            } catch (PlayerNotFoundException e) {
                player.sendMessage(String.format(TMConstants.REMOVE_PLAYER_FAILED, args[2]));
            }
            break;
        case "swap":
            if (args.length < 4) {
                // tm cycle swap missing 1 or 2 arguments
                // TODO add a tm cycle swap custom error message
                player.sendMessage(TMConstants.MISSING_ARGUMENT_ERROR);
                return;
            }

            assertSufficientPermission(player, TMPermissions.CYCLE_SWAP_PERMISSION);
            try {
                TurnManager.getCycle().swap(args[2], args[3]);
                player.sendMessage(String.format(TMConstants.SWAP_PLAYER_SUCCESS, args[2], args[3]));
            } catch (PlayerNotFoundException e) {
                player.sendMessage(String.format(TMConstants.SWAP_PLAYER_FAILED, args[2], args[3]));
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
    private void handleShield(Player player, String[] args) throws InsufficientPermissionException {
        if (args.length < 2) {
            // tm shield missing 1 argument
            // TODO add a tm shield custom error message
            player.sendMessage(TMConstants.MISSING_ARGUMENT_ERROR);
            return;
        }
        
        switch (args[1]) {
        case "add":
            assertSufficientPermission(player, TMPermissions.SHIELD_ADD_PERMISSION);
            if (args.length == 4) {
                TurnManager.getShield().addPlayer(args[2], args[3]);
            } else {
                TurnManager.getShield().addPlayer(args[2]);
            }
            break;
        case "remove":
            assertSufficientPermission(player, TMPermissions.SHIELD_REMOVE_PERMISSION);
            if (args.length == 4) {
                TurnManager.getShield().removePlayer(args[2], args[3]);
            } else {
                TurnManager.getShield().removePlayer(args[2]);
            }
            break;
        case "all":
            assertSufficientPermission(player, TMPermissions.SHIELD_ALL_PERMISSION);
            if (args.length == 3) {
                TurnManager.getShield().addAllPlayers(args[2]);
            } else {
                TurnManager.getShield().addAllPlayers();
            }
            break;
        case "clear":
            assertSufficientPermission(player, TMPermissions.SHIELD_CLEAR_PERMISSION);
            if (args.length == 3) {
                TurnManager.getShield().clearShield(args[2]);
            } else {
                TurnManager.getShield().clearShield();
            }
            break;
        case "list":
            assertSufficientPermission(player, TMPermissions.SHIELD_LIST_PERMISSION);
            if (args.length == 3) {
                player.sendMessage(TurnManager.getShield().toString(args[2]));
            } else {
                player.sendMessage(TurnManager.getShield().toString());
            }
            break;
        case "on":
            assertSufficientPermission(player, TMPermissions.SHIELD_TOGGLE_PERMISSION, TMPermissions.SHIELD_ON_PERMISSION);
            if (args.length == 4) {
                TurnManager.getShield().toggle(args[2], true);
            } else {
                TurnManager.getShield().toggle(true);
            }
            break;
        case "off":
            assertSufficientPermission(player, TMPermissions.SHIELD_TOGGLE_PERMISSION, TMPermissions.SHIELD_OFF_PERMISSION);
            if (args.length == 4) {
                TurnManager.getShield().toggle(args[2], false);
            } else {
                TurnManager.getShield().toggle(false);
            }
            break;
        case "register":
            assertSufficientPermission(player, TMPermissions.SHIELD_REGISTER_PERMISSION);
            TurnManager.getShield().registerPlayer(args[2]);
            break;
        case "unregister":
            assertSufficientPermission(player, TMPermissions.SHIELD_UNREGISTER_PERMISSION);
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
    private void handleDate(Player player, String[] args) throws InsufficientPermissionException {
        if (args.length < 2) {
            // tm shield missing 1 argument
            // TODO add a tm date custom error message
            player.sendMessage(TMConstants.MISSING_ARGUMENT_ERROR);
            return;
        }

        switch(args[1]) {
        case "add":
            if (args.length == 4) {
                assertSufficientPermission(player, TMPermissions.DATE_ADD_PERMISSION, TMPermissions.DATE_ADD_PLAYER_PERMISSION);
                try {
                    TurnManager.getCalendar().addPlayerDate(args[2], Integer.parseInt(args[3]));
                } catch (DateSyncException e) {
                    //TODO print error msg
                } 
            } else if (args.length == 3) {
                assertSufficientPermission(player, TMPermissions.DATE_ADD_PERMISSION, TMPermissions.DATE_ADD_WORLD_PERMISSION);
                TurnManager.getCalendar().addWorldDate(Integer.parseInt(args[2]));
            }
            break;
        case "set":
            if (args.length == 6) {
                assertSufficientPermission(player, TMPermissions.DATE_SET_PERMISSION, TMPermissions.DATE_SET_PLAYER_PERMISSION);
                try {
                    TurnManager.getCalendar().setPlayerDate(args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
                } catch (DateSyncException e) {
                    //TODO error msg
                } 
            } else if (args.length == 5) {
                assertSufficientPermission(player, TMPermissions.DATE_SET_PERMISSION, TMPermissions.DATE_SET_WORLD_PERMISSION);
                TurnManager.getCalendar().setWorldDate(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
            }
            break;
        case "sync":
            assertSufficientPermission(player, TMPermissions.DATE_SYNC_PERMISSION);
            TurnManager.getCalendar().sync(args[2]);
            break;
        case "unsync":
            assertSufficientPermission(player, TMPermissions.DATE_UNSYNC_PERMISSION);
            TurnManager.getCalendar().unsync(args[2]);
            break;
        case "today":
            if (args.length == 3 && args[2].equalsIgnoreCase("world")) {
                assertSufficientPermission(player, TMPermissions.DATE_TODAY_WORLD_PERMISSION);
                player.sendMessage(TurnManager.getCalendar().getWorldDate());
            } else if (args.length == 2) {
                assertSufficientPermission(player, TMPermissions.DATE_TODAY_SELF_PERMISSION);
                player.sendMessage(TurnManager.getCalendar().getPlayerDate(player.getName()));
            } else if (args.length == 3) {
                assertSufficientPermission(player, TMPermissions.DATE_TODAY_PLAYER_PERMISSION);
                player.sendMessage(TurnManager.getCalendar().getPlayerDate(args[2]));
            }
            break;
        case "auto":
            if (args[2].equalsIgnoreCase("on")) {
                assertSufficientPermission(player, TMPermissions.DATE_AUTO_ON_PERMISSION);
                TurnManager.getCalendar().setIsAuto(true);
            } else if (args[2].equalsIgnoreCase("off")) {
                assertSufficientPermission(player, TMPermissions.DATE_AUTO_OFF_PERMISSION);
                TurnManager.getCalendar().setIsAuto(false);
            }
            break;
        case "register":
            assertSufficientPermission(player, TMPermissions.DATE_REGISTER_PERMISSION);
            try {
                TurnManager.getCalendar().registerPlayer(args[2]);
            } catch (DuplicatePlayerException e) {
                // TODO error msg
            }
            break;
        case "unregister":
            assertSufficientPermission(player, TMPermissions.DATE_UNREGISTER_PERMISSION);
            try {
                TurnManager.getCalendar().unregisterPlayer(args[2]);
            } catch (PlayerNotFoundException e) {
                //TODO error msg
            }
            break;
        }
    }

    /**
     * Asserts that the player has one of the specified permissions
     * @param player player whose permission is checked
     * @param perms  list of permissions where one permission has to be satisfied
     */
    private static void assertSufficientPermission(Player player, String... perms) throws InsufficientPermissionException {
        boolean valid = false;

        for (String perm : perms) {
            valid = valid || player.hasPermission(perm);
        }

        if (!valid) {
            throw new InsufficientPermissionException();
        }
    }

    @Override
    public void updateShieldBreach(Player player) {
        player.sendMessage("YO SHIELD IS BREACHED, GOTTA GO BACK");
        // TODO send error message to player saying the player violated shield boundary.
    }
}