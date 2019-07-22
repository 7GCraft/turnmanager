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
                player.sendMessage(args[0]);
                // switch (args[0])
                // {
                //     case "cycle":
                //         switch (args[1]) {
                //             case "add":
                //                 cycle.addPlayer(args[2]);
                //                 break;
                //             case "remove":
                //                 cycle.removePlayer(args[2]);
                //                 break;
                //             case "swap":
                //                 cycle.swap(args[2], args[3]);
                //                 break;
                //             case "next":
                //                 cycle.nextTurn();
                //                 break;
                //         }
                //         break;
                // }
            } else {
                player.sendMessage("/tm requires an argument:");
                player.sendMessage("/tm [argument]");
            }
        }

        return true;
    }

}