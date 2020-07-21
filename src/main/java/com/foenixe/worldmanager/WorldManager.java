package com.foenixe.worldmanager;

import com.foenixe.worldmanager.command.WorldCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class WorldManager extends JavaPlugin {

    private static WorldManager plugin;
    public static WorldManager get() { return plugin; }

    @Override
    public void onEnable() {
        loadPlugin();
        loadCommands();
    }

    private void loadPlugin() {
        plugin = this;
    }

    private void loadCommands() {
        getCommand("world").setExecutor(new WorldCommand());
        getCommand("world").setTabCompleter(new WorldCommand.WorldCommandTabCompleter());
    }

}
