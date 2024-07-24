package org.imradigamer.bingo;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

public class BingoListener implements Listener {
    private BingoManager bingoManager;

    public BingoListener(BingoManager bingoManager) {
        this.bingoManager = bingoManager;
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem().getItemStack();
        bingoManager.trackItemCollection(player, item);
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        bingoManager.trackItemCollection(player, item);
    }

    @EventHandler
    public void onFurnaceExtract(FurnaceExtractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = new ItemStack(event.getItemType(), event.getItemAmount());
        bingoManager.trackItemCollection(player, item);
    }
}
