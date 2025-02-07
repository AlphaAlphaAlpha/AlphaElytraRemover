package com.alphablock;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ElytraFramePreventer implements Listener {

    private final AlphaElytraRemover plugin;

    public ElytraFramePreventer(AlphaElytraRemover plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerPlaceElytraInItemFrame(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        // Überprüfen, ob der Spieler mit einem ItemFrame interagiert
        if (event.getRightClicked() instanceof ItemFrame) {
            ItemFrame itemFrame = (ItemFrame) event.getRightClicked();

            // Das Item, das der Spieler in der Hand hält (sollte die Elytra sein)
            ItemStack itemInHand = player.getInventory().getItemInMainHand();

            // Wenn das Item eine Elytra ist
            if (itemInHand.getType() == Material.ELYTRA) {
                World world = player.getWorld();

                // Überprüfen, ob die Welt in der config aktiviert ist
                if (plugin.getConfig().getStringList("endworlds").contains(world.getName())) {
                    event.setCancelled(true); // Verhindert das Ablegen der Elytra in das ItemFrame
                    String message = plugin.getConfig().getString("messages.prevent-place", "Du kannst keine Elytra in Itemframes in dieser Welt ablegen!");
                    player.sendMessage(message); // Zeigt die Fehlermeldung im Chat an
                }
            }
        }
    }
}
