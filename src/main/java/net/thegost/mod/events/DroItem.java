package net.thegost.mod.events;

import net.thegost.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DroItem implements Listener {

    Core core;

    public DroItem(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if(core.setMod.contains(player)) {
            event.setCancelled(true);
        } else {
            event.setCancelled(false);
        }
    }
}
