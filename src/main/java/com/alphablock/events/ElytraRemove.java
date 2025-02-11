package com.alphablock.events;


import com.alphablock.AlphaElytraRemover;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.AsyncStructureSpawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.generator.structure.Structure;


public class ElytraRemove implements Listener {

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        // Ist in der richtigen Welt
        if(!AlphaElytraRemover.getInstance().getPluginConfig().isValidEndWorld(event.getWorld())) return;

        // Ist neuer Chunk
        if(! event.isNewChunk()) return;

        // Checkt alle Entities in diesem Chunk
        for (Entity entity : event.getChunk().getEntities()) {
            if (entity instanceof ItemFrame frame) {
                if (frame.getItem().getType() == Material.ELYTRA) {
                    frame.setItem(AlphaElytraRemover.getInstance().getPluginConfig().getReplaceItem());
                }
            }
        }
    }
}
