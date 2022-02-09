package net.thegost.mod.events;

import net.thegost.Core;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ModItemInteraction implements Listener {

    Core core;

    public ModItemInteraction(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onInteraction(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        //Vanish item init
        ItemStack vanish = new ItemStack(Material.ENDER_EYE);
        ItemMeta vM = vanish.getItemMeta();
        vM.setDisplayName("§6Vanish");
        vM.setLore(Arrays.asList("§3Te rend invisible"));
        vM.addEnchant(Enchantment.DURABILITY,1,true);
        vM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        vanish.setItemMeta(vM);

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

        if((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.getPlayer().getItemInHand().equals(hopper))) {
            core.setSpotter.add(player);
            player.getInventory().setItem(8, barrier);
            event.setCancelled(true);
        } else if((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.getPlayer().getItemInHand().equals(barrier))) {
            core.setSpotter.remove(player);
            player.getInventory().setItem(8, hopper);
            event.setCancelled(true);
        } else if((event.getAction() == Action.RIGHT_CLICK_AIR ||event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.getPlayer().getItemInHand().equals(vanish))) {
            event.setCancelled(true);
            if(core.setVanish.contains(player)) {
                core.setVanish.remove(player);
                player.sendMessage(core.getConfig().getString("messages.vanish.voff").replace("&", "§"));
            } else if(!(core.setVanish.contains(player))) {
                core.setVanish.add(player);
                player.sendMessage(core.getConfig().getString("messages.vanish.von").replace("&", "§"));
            }
        } else if(!event.getPlayer().getItemInHand().hasItemMeta()){
            event.setCancelled(false);
        }

        for(Player online : Bukkit.getOnlinePlayers()) {
            if(core.setVanish.contains(player)) {
                online.hidePlayer(player);
            } else {
                online.showPlayer(player);
            }
        }
    }
}
