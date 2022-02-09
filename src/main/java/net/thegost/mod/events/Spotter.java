package net.thegost.mod.events;

import net.thegost.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class Spotter implements Listener {

    Core core;

    public Spotter(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onTaking(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if(core.setSpotter.contains(player)) {
            event.setCancelled(true);
        } else if(!(core.setSpotter.contains(player))){
            event.setCancelled(false);
        }
    }

}
