package com.alphablock;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class AlphaElytraRemover extends JavaPlugin implements Listener {

    private List<String> endWorldNames;
    private boolean consoleLogging;  // Wird für das Konsolen-Logging verwendet
    private boolean chatLogging;  // Wird für das Chat-Logging verwendet
    private boolean preventElytraFrameLog;  // Prevent Elytra Log in ItemFrames

    @Override
    public void onEnable() {
        saveDefaultConfig();  // Standard-Konfiguration speichern
        endWorldNames = getConfig().getStringList("endworlds");
        consoleLogging = getConfig().getBoolean("console-logging", true);  // Standardmäßig ist das Konsolen-Logging aktiv
        chatLogging = getConfig().getBoolean("chat-logging", true);  // Standardmäßig ist das Chat-Logging aktiv
        preventElytraFrameLog = getConfig().getBoolean("prevent-elytra-frame-log", true);  // Prevent Elytra Frame Log aktiviert

        // Registriere Events
        getServer().getPluginManager().registerEvents(this, this);

        // Starte den Scheduler für das Entfernen der Elytras in regelmäßigen Intervallen
        startScheduler();

        getLogger().info("Alpha Elytra Remover Plugin aktiviert!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Alpha Elytra Remover Plugin deaktiviert.");
    }

    // Wenn eine Welt geladen wird, überprüfe, ob sie in der config steht und Elytras entfernen
    @EventHandler
    public void onWorldLoad(org.bukkit.event.world.WorldLoadEvent event) {
        if (endWorldNames.contains(event.getWorld().getName())) {
            removeElytrasInEndWorld(event.getWorld());
        }
    }

    // Verhindere das Ablegen von Elytras in ItemFrames in den gelisteten Welten
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof ItemFrame)) {
            return;  // Nur wenn der Spieler mit einem ItemFrame interagiert
        }

        ItemFrame itemFrame = (ItemFrame) event.getRightClicked();
        Player player = event.getPlayer();

        // Überprüfen, ob die Welt in der Konfiguration gelistet ist
        if (endWorldNames.contains(player.getWorld().getName())) {
            ItemStack item = player.getInventory().getItemInMainHand();  // Das Item in der rechten Hand des Spielers

            // Wenn das Item eine Elytra ist, verhindere das Ablegen
            if (item != null && item.getType() == Material.ELYTRA) {
                event.setCancelled(true);  // Verhindere die Interaktion

                // Wenn das Prevent Elytra Frame Log aktiviert ist, sende die Nachricht
                if (preventElytraFrameLog) {
                    sendMessage("prevent-place", player);  // Nachricht an den Spieler senden
                }
            }
        }
    }

    // Entferne Elytras aus Itemframes im gesamten End
    public void removeElytrasInEndWorld(World world) {
        for (ItemFrame itemFrame : world.getEntitiesByClass(ItemFrame.class)) {
            // Überprüfe, ob das Item im Itemframe eine Elytra ist
            if (itemFrame.getItem().getType() == Material.ELYTRA) {
                itemFrame.setItem(null);  // Entferne die Elytra aus dem Itemframe
                sendMessage("remove-elytra");  // Minecraft-Chat-Nachricht
            }
        }
    }

    // Methode, um das Logging zu steuern und Nachrichten zu senden
    private void sendMessage(String configKey) {
        if (chatLogging) {
            String message = getConfig().getString("messages." + configKey, "Fehler: Nachricht nicht gefunden!");
            message = org.bukkit.ChatColor.translateAlternateColorCodes('&', message);  // Farbcodes umwandeln
            Bukkit.broadcastMessage(message);  // Nachricht an alle Spieler im Chat senden
        }

        if (consoleLogging) {
            getLogger().info("LOG: " + configKey);  // Nachricht in der Konsole
        }
    }

    // Methode, um das Logging an den spezifischen Spieler zu senden
    private void sendMessage(String configKey, Player player) {
        if (chatLogging) {
            String message = getConfig().getString("messages." + configKey, "Fehler: Nachricht nicht gefunden!");
            message = org.bukkit.ChatColor.translateAlternateColorCodes('&', message);  // Farbcodes umwandeln
            player.sendMessage(message);  // Nachricht an den Spieler im Chat senden
        }

        if (consoleLogging) {
            getLogger().info("LOG: " + configKey);  // Nachricht in der Konsole
        }
    }

    // Methode, um den Scheduler zu starten
    private void startScheduler() {
        int intervalTicks = getConfig().getInt("interval-ticks", 6000);  // Intervall in Ticks aus der config.yml
        getServer().getScheduler().runTaskTimer(this, () -> {
            // Überprüfe alle Endwelten
            for (String worldName : endWorldNames) {
                World world = getServer().getWorld(worldName);
                if (world != null) {
                    removeElytrasInEndWorld(world);  // Elytras entfernen
                }
            }
        }, 0L, intervalTicks);  // Intervall in Ticks (wird alle 'intervalTicks' Ticks wiederholt)
    }

    // Befehl zum Deaktivieren des Konsolen- und Chat-Loggings
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            if (command.getName().equalsIgnoreCase("aetoggle")) {
                String option = args[0].toLowerCase();
                String action = args[1].toLowerCase();

                if (option.equals("chat") && sender.hasPermission("ae.toggle.chat")) {
                    if (action.equals("on")) {
                        chatLogging = true;
                        sender.sendMessage("Chat-Logging aktiviert.");
                    } else if (action.equals("off")) {
                        chatLogging = false;
                        sender.sendMessage("Chat-Logging deaktiviert.");
                    } else {
                        sender.sendMessage("Ungültige Option für Chat-Logging. Benutze 'on' oder 'off'.");
                    }
                } else if (option.equals("console") && sender.hasPermission("ae.toggle.console")) {
                    if (action.equals("on")) {
                        consoleLogging = true;
                        sender.sendMessage("Konsolen-Logging aktiviert.");
                    } else if (action.equals("off")) {
                        consoleLogging = false;
                        sender.sendMessage("Konsolen-Logging deaktiviert.");
                    } else {
                        sender.sendMessage("Ungültige Option für Konsolen-Logging. Benutze 'on' oder 'off'.");
                    }
                } else if (option.equals("frame") && sender.hasPermission("ae.toggle.frame")) {
                    if (action.equals("on")) {
                        preventElytraFrameLog = true;
                        sender.sendMessage("Prevent Elytra in ItemFrames Log aktiviert.");
                    } else if (action.equals("off")) {
                        preventElytraFrameLog = false;
                        sender.sendMessage("Prevent Elytra in ItemFrames Log deaktiviert.");
                    } else {
                        sender.sendMessage("Ungültige Option für Elytra-Frame-Log. Benutze 'on' oder 'off'.");
                    }
                } else {
                    sender.sendMessage("Du hast keine Berechtigung für diesen Befehl.");
                }
                return true;
            }
        }

        if (command.getName().equalsIgnoreCase("aereload")) {
            // Konfiguration neu laden
            reloadConfig();
            endWorldNames = getConfig().getStringList("endworlds");
            consoleLogging = getConfig().getBoolean("console-logging", true);
            chatLogging = getConfig().getBoolean("chat-logging", true);
            preventElytraFrameLog = getConfig().getBoolean("prevent-elytra-frame-log", true);
            sender.sendMessage("Die Konfiguration wurde erfolgreich neu geladen.");
            return true;
        }

        return false;
    }

    // Tab-Vervollständigung
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("aetoggle")) {
            List<String> options = new ArrayList<>();
            if (args.length == 1) {
                options.add("chat");
                options.add("console");
                options.add("frame");
            } else if (args.length == 2) {
                options.add("on");
                options.add("off");
            }
            return options;
        }
        return null;
    }
}
