package com.alphablock;

import com.alphablock.commands.ReloadCommand;
import com.alphablock.events.ElytraRemove;
import org.bukkit.plugin.java.JavaPlugin;

public class AlphaElytraRemover extends JavaPlugin{


    private static AlphaElytraRemover instance;

    private PluginConifg config;

    @Override
    public void onEnable() {
        instance = this;

        this.config = new PluginConifg();
        config.createDefaultIfNotExist();

        // Registriere Events
        getServer().getPluginManager().registerEvents(new ElytraRemove(), this);
        getCommand("aereload").setExecutor(new ReloadCommand());


        getLogger().info("Alpha Elytra Remover Plugin aktiviert!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Alpha Elytra Remover Plugin deaktiviert.");
    }



    public static AlphaElytraRemover getInstance() {
        return instance;
    }
    public PluginConifg getPluginConfig() {
        return config;
    }
}
