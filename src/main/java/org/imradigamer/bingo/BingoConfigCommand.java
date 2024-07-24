package org.imradigamer.bingo;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BingoConfigCommand implements CommandExecutor, Listener {
    private BingoManager bingoManager;

    public BingoConfigCommand(BingoManager bingoManager) {
        this.bingoManager = bingoManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1 || !(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;
        Difficulty difficulty = Difficulty.valueOf(args[0].toUpperCase());

        // Open a double chest GUI for item configuration
        Inventory configInventory = Bukkit.createInventory(null, 54, "Configure " + difficulty + " Items");
        List<ItemStack> items = bingoManager.getItemsForDifficulty(difficulty);
        for (int i = 0; i < items.size() && i < 54; i++) {
            configInventory.setItem(i, items.get(i));
        }
        player.openInventory(configInventory);

        // Register the event listener to handle inventory close
        Bukkit.getPluginManager().registerEvents(this, bingoManager.getPlugin());

        return true;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().startsWith("Configure ")) {
            Inventory inventory = event.getInventory();
            Player player = (Player) event.getPlayer();
            String title = event.getView().getTitle();
            Difficulty difficulty = Difficulty.valueOf(title.split(" ")[1].toUpperCase());

            // Save the items in the inventory to the configuration
            List<ItemStack> items = bingoManager.getItemsForDifficulty(difficulty);
            items.clear();
            for (ItemStack item : inventory.getContents()) {
                if (item != null) {
                    items.add(item);
                }
            }
            bingoManager.saveConfig();
            InventoryCloseEvent.getHandlerList().unregister(this);
        }
    }
}
