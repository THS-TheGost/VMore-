package net.thegost.events;

import net.thegost.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Leave implements Listener {

    Core core;

    public Leave(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onJoin(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(core.getConfig().getString("messages.leave").replace("&", "§") + " " + player.getDisplayName());
    }
}
