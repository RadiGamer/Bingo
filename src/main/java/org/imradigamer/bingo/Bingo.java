package org.imradigamer.bingo;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bingo extends JavaPlugin {
    private BingoManager bingoManager;


    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        this.bingoManager = new BingoManager(this);
        this.getCommand("bingoconfig").setExecutor(new BingoConfigCommand(bingoManager));
        this.getCommand("bingo").setExecutor(new BingoCommand(bingoManager));
        getServer().getPluginManager().registerEvents(new BingoListener(bingoManager), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
