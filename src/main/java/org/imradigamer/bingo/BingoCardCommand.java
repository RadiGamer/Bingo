package org.imradigamer.bingo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BingoCardCommand implements CommandExecutor {
    private BingoManager bingoManager;

    public BingoCardCommand(BingoManager bingoManager) {
        this.bingoManager = bingoManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;
        bingoManager.showCard(player);

        return true;
    }
}
