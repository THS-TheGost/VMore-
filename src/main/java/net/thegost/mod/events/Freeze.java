package net.thegost.mod.events;

import net.thegost.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Freeze implements Listener {

    Core core;

    public Freeze(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onMovingWhileFreeze(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(core.setFreeze.contains(player)) {
            player.sendMessage(core.getConfig().getString("messages.freeze.freezemsg").replace("&", "§"));
            event.setCancelled(true);
        } else {
            event.setCancelled(false);
        }
    }

}
