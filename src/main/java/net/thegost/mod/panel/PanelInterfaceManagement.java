package net.thegost.mod.panel;

import net.thegost.Core;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class PanelInterfaceManagement implements Listener {

    Core core;

    public PanelInterfaceManagement(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onInteractWPanel(PlayerInteractEvent event) {

        //Player init
        Player player = event.getPlayer();

        //Book panel item
        ItemStack book = new ItemStack(Material.BOOK);
        ItemMeta inter = book.getItemMeta();
        inter.setDisplayName("§ePanel");
        inter.setLore(Arrays.asList("§3Interface avec différentes options/vues sur le serveur"));
        inter.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        inter.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        book.setItemMeta(inter);

        //Panel Inventory Init
        Inventory panel = Bukkit.createInventory(null, 9, "Panel");
        //Panel stuff init
        core.PanelItems(panel);

        if((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getPlayer().getItemInHand().equals(book)) {
            player.openInventory(panel);
        }

    }

}
