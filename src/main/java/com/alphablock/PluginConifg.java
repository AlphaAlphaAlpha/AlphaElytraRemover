package com.alphablock;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PluginConifg {

    private List<String> endWorldNames;

    private ItemStack replaceItem;


    public PluginConifg(){
        load();
    }

    public void load(){
        AlphaElytraRemover.getInstance().reloadConfig();
        endWorldNames = AlphaElytraRemover.getInstance().getConfig().getStringList("endworlds");
        replaceItem = AlphaElytraRemover.getInstance().getConfig().getItemStack("replace-item");
    }

    public void createDefaultIfNotExist() {
        AlphaElytraRemover.getInstance().saveDefaultConfig();
        load();
    }

    public boolean isValidEndWorld(World world){
        return endWorldNames.contains(world.getName());
    }
    public ItemStack getReplaceItem(){
        return this.replaceItem;
    };
}
