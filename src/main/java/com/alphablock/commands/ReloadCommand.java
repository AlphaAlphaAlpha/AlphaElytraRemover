package com.alphablock.commands;

import com.alphablock.AlphaElytraRemover;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        AlphaElytraRemover.getInstance().getPluginConfig().createDefaultIfNotExist();
        commandSender.sendMessage("Reloaded \"config.yml\"");
        return true;
    }
}
