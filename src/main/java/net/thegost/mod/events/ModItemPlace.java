package net.thegost.mod.events;

import net.thegost.Core;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ModItemPlace implements Listener {

    Core core;

    public ModItemPlace(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        //Barrier init
        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta bar = barrier.getItemMeta();
        bar.setDisplayName("§cSpotter");
        bar.setLore(Arrays.asList("§3Active le ramassage des items"));
        barrier.setItemMeta(bar);

        ItemStack hopper = new ItemStack(Material.HOPPER);
        ItemMeta spotO = hopper.getItemMeta();
        spotO.setDisplayName("§aSpotter");
        spotO.setLore(Arrays.asList("§3Cancel le ramassage des items"));
        hopper.setItemMeta(spotO);

        ItemStack freeze = new ItemStack(Material.PACKED_ICE);
        ItemMeta ice = freeze.getItemMeta();
        ice.setDisplayName("§bFreeze (Futur update)");
        ice.setLore(Arrays.asList("§3Freeze un joueur"));
        freeze.setItemMeta(ice);

        if(event.getPlayer().getItemInHand().equals(hopper)) {
            event.setCancelled(true);
        } else if(event.getPlayer().getItemInHand().equals(barrier)) {
            event.setCancelled(true);
        } else if(event.getPlayer().getItemInHand().equals(freeze)) {
            event.setCancelled(true);
        } else {
            event.setCancelled(false);
        }
    }

}
