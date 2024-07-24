package org.imradigamer.bingo;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;

import java.util.Collections;
import java.util.List;

public class BingoCard {
    private Player player;
    private ItemStack[][] grid = new ItemStack[5][5];
    private boolean[][] collected = new boolean[5][5];
    private Inventory cardInventory;

    public BingoCard(Player player, List<ItemStack> items) {
        this.player = player;
        this.cardInventory = Bukkit.createInventory(null, 54, "Bingo Card");
        Collections.shuffle(items);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = items.get(i * 5 + j);
                collected[i][j] = false;
                cardInventory.setItem(i * 9 + j, grid[i][j]);
            }
        }
    }

    public void markItemAsCollected(ItemStack item) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid[i][j].isSimilar(item)) {
                    collected[i][j] = true;
                    grid[i][j].addUnsafeEnchantment(Enchantment.LUCK_OF_THE_SEA, 1); // Add enchantment glint
                    cardInventory.setItem(i * 9 + j, grid[i][j]); // Update the inventory display
                    return;
                }
            }
        }
    }

    public boolean isLineCompleted() {
        for (int i = 0; i < 5; i++) {
            // Check rows
            if (collected[i][0] && collected[i][1] && collected[i][2] && collected[i][3] && collected[i][4]) {
                return true;
            }
            // Check columns
            if (collected[0][i] && collected[1][i] && collected[2][i] && collected[3][i] && collected[4][i]) {
                return true;
            }
        }
        // Check diagonals
        if (collected[0][0] && collected[1][1] && collected[2][2] && collected[3][3] && collected[4][4]) {
            return true;
        }
        if (collected[0][4] && collected[1][3] && collected[2][2] && collected[3][1] && collected[4][0]) {
            return true;
        }
        return false;
    }

    public void displayCard() {
        player.openInventory(cardInventory);
    }
}
