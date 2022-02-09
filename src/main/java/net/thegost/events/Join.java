package net.thegost.events;

import net.thegost.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    Core core;

    public Join(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(core.getConfig().getString("messages.join").replace("&", "§") + " " + player.getDisplayName());
    }
}
