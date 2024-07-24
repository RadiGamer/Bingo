package org.imradigamer.bingo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class BingoManager {
    private Bingo plugin;
    private Map<Player, BingoCard> playerCards = new HashMap<>();
    private List<ItemStack> easyItems = new ArrayList<>();
    private List<ItemStack> mediumItems = new ArrayList<>();
    private List<ItemStack> hardItems = new ArrayList<>();

    public BingoManager(Bingo plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    private void loadConfig() {
        FileConfiguration config = plugin.getConfig();
        easyItems = ItemStackUtil.loadItemsFromConfig(config, "easyItems");
        mediumItems = ItemStackUtil.loadItemsFromConfig(config, "mediumItems");
        hardItems = ItemStackUtil.loadItemsFromConfig(config, "hardItems");
    }


    public void saveConfig() {
        FileConfiguration config = plugin.getConfig();
        ItemStackUtil.saveItemsToConfig(config, "easyItems", easyItems);
        ItemStackUtil.saveItemsToConfig(config, "mediumItems", mediumItems);
        ItemStackUtil.saveItemsToConfig(config, "hardItems", hardItems);
        plugin.saveConfig();
    }

    public void addItemToDifficulty(ItemStack item, Difficulty difficulty) {
        List<ItemStack> items = getItemsForDifficulty(difficulty);
        items.add(item);
        saveConfig();
    }

    public void startGame(Difficulty difficulty) {
        List<ItemStack> items;
        switch (difficulty) {
            case EASY:
                items = new ArrayList<>(easyItems);
                break;
            case MEDIUM:
                items = new ArrayList<>(mediumItems);
                break;
            case HARD:
                items = new ArrayList<>(hardItems);
                break;
            default:
                return;
        }
        Collections.shuffle(items);
        List<ItemStack> selectedItems = items.subList(0, 25);

        for (Player player : Bukkit.getOnlinePlayers()) {
            BingoCard card = new BingoCard(player, selectedItems);
            playerCards.put(player, card);
        }
    }

    public void trackItemCollection(Player player, ItemStack item) {
        BingoCard card = playerCards.get(player);
        if (card != null) {
            card.markItemAsCollected(item);
            player.sendMessage(ChatColor.GREEN + "You have collected a bingo item!");
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

            if (card.isLineCompleted()) {
                Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + " has completed a Bingo line!");
                player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f);
            }
        }
    }

    public void showCard(Player player) {
        BingoCard card = playerCards.get(player);
        if (card != null) {
            card.displayCard();
        } else {
            player.sendMessage(ChatColor.RED + "You don't have a bingo card yet!");
        }
    }
    public List<ItemStack> getItemsForDifficulty(Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                return easyItems;
            case MEDIUM:
                return mediumItems;
            case HARD:
                return hardItems;
            default:
                return new ArrayList<>();
        }
    }

}
