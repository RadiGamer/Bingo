package org.imradigamer.bingo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BingoCommand implements CommandExecutor {
    private BingoManager bingoManager;

    public BingoCommand(BingoManager bingoManager) {
        this.bingoManager = bingoManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /bingo <start/card> [easy/medium/hard]");
            return false;
        }

        if (args[0].equalsIgnoreCase("start")) {
            if (args.length != 2) {
                sender.sendMessage(ChatColor.RED + "Usage: /bingo start <easy/medium/hard>");
                return false;
            }

            Difficulty difficulty;
            try {
                difficulty = Difficulty.valueOf(args[1].toUpperCase());
            } catch (IllegalArgumentException e) {
                sender.sendMessage(ChatColor.RED + "Invalid difficulty. Use easy, medium, or hard.");
                return false;
            }

            bingoManager.startGame(difficulty);
            Bukkit.broadcastMessage(ChatColor.GOLD + "A Bingo game has started with difficulty: " + args[1].toLowerCase());
            return true;
        } else if (args[0].equalsIgnoreCase("card")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only players can view their bingo card.");
                return false;
            }

            Player player = (Player) sender;
            bingoManager.showCard(player);
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Usage: /bingo <start/card> [easy/medium/hard]");
        return false;
    }
}
