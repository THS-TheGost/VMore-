package net.thegost.mod.panel;

import net.thegost.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class PanelInterfaceClickEvent implements Listener {

    Core core;

    public PanelInterfaceClickEvent(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        //Player Online inv init
        Inventory online = Bukkit.createInventory(null, 45, "Online");

        for (Player isco : Bukkit.getOnlinePlayers()) {

            //Online player inventory init
            core.OnlineInterfaceItem(online);
            //iscInv init
            Inventory iscInv = Bukkit.createInventory(null, 27, isco.getDisplayName());
            core.PlayerInventoryItems(iscInv);

            if (!(event.getWhoClicked() instanceof Player)) return;

            if (event.getView().getTitle().equals("Panel")) {
                if (event.getCurrentItem() == null) return;
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§aJoueurs connectés")) {
                    event.setCancelled(true);
                    player.openInventory(online);
                }
            }

            if(event.getView().getTitle().equals("Online")) {
                if (event.getCurrentItem() == null) return;
                if(event.getCurrentItem().getItemMeta().getDisplayName().equals(isco.getDisplayName())) {
                    core.getServer().getConsoleSender().sendMessage(isco.displayName());
                    event.setCancelled(true);
                    player.openInventory(iscInv);
                }
            }

            if(event.getView().getTitle().equals(isco.getDisplayName())) {
                if (event.getCurrentItem() == null) return;
                if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§aTP à : §3" + isco.getDisplayName()) && player.hasPermission("vmore.mod.tp")){
                    if(!(player.hasPermission("vmore.mod.freeze"))) {
                        player.sendMessage(core.getConfig().getString("messages.noperm").replace("&", "§"));
                        return;
                    } else {
                        event.setCancelled(true);
                        player.teleport(isco.getLocation());
                        player.sendMessage("§aTP à : §3" + isco.getDisplayName()
                                + " \n§aX:§3" + isco.getLocation().getBlockX()
                                + " \n§aY:§3" + isco.getLocation().getBlockY()
                                + " \n§aZ:§3" + isco.getLocation().getBlockZ());
                    }
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§6Invsee") && player.hasPermission("vmore.mod.invsee")) {
                    if(!(player.hasPermission("vmore.mod.invsee"))) {
                        player.sendMessage(core.getConfig().getString("messages.noperm").replace("&", "§"));
                        return;
                    } else {
                        event.setCancelled(true);
                        player.openInventory(isco.getInventory());
                    }
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cRetour")) {
                    event.setCancelled(true);
                    player.openInventory(online);
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§bFreeze") && player.hasPermission("vmore.mod.freeze")) {
                    if(!(player.hasPermission("vmore.mod.freeze"))) {
                        player.sendMessage(core.getConfig().getString("messages.noperm").replace("&", "§"));
                        return;
                    } else {
                        if(core.setFreeze.contains(isco)) {
                            event.setCancelled(true);
                            core.setFreeze.remove(isco);
                            isco.sendMessage(core.getConfig().getString("messages.freeze.removefreeze").replace("&", "§"));
                            player.closeInventory();
                        } else if(!(core.setFreeze.contains(isco))) {
                            event.setCancelled(true);
                            core.setFreeze.add(isco);
                            isco.sendMessage(core.getConfig().getString("messages.freeze.setfreeze").replace("&", "§"));
                            player.closeInventory();
                        }
                    }
                }
            }

        }
    }
}
