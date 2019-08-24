package com.clancraft.turnmanager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TM implements CommandExecutor {

    private Cycle cycle;

    public TM() {
        cycle = new Cycle();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                switch (args[0]) {
                    case "cycle":
                        handleCycle(player, args);
                        break;
                }
            } else {
                //tm has no argument
                player.sendMessage(TMStrings.MISSING_ARGUMENT_ERROR);
            }
        }

        return true;
    }

    //TODO potential to refactor into another class if this method gets more complicated
    public boolean handleCycle(Player player, String[] args) {
        switch (args[1]) {
            case "list":
                player.sendMessage(String.format(TMStrings.PLAYER_LIST, cycle.getTurnSequence()));
                break;
            case "add":
                if (cycle.addPlayer(args[2])) {
                    player.sendMessage(String.format(TMStrings.ADD_PLAYER_SUCCESS, args[2]));
                } else {
                    player.sendMessage(String.format(TMStrings.ADD_PLAYER_FAILED, args[2]));
                }
                break;
            case "remove":
                if (cycle.removePlayer(args[2])) {
                    player.sendMessage(String.format(TMStrings.REMOVE_PLAYER_SUCCESS, args[2]));
                } else {
                    player.sendMessage(String.format(TMStrings.REMOVE_PLAYER_FAILED, args[2]));
                }
                break;
            case "swap":
                if (cycle.swap(args[2], args[3])) {
                    player.sendMessage(String.format(TMStrings.SWAP_PLAYER_SUCCESS, args[2], args[3]));
                } else {
                    player.sendMessage(String.format(TMStrings.SWAP_PLAYER_FAILED, args[2], args[3]));
                }
                break;
            case "next":
                cycle.nextTurn();
                break;
            default:
                player.sendMessage(TMStrings.INVALID_ARGUMENT_ERROR);
                break;
        }
        return true;
    }
}