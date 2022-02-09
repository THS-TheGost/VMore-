package net.thegost;

import net.thegost.mod.Mod;
import net.thegost.commands.Whois;
import net.thegost.events.Join;
import net.thegost.events.Leave;
import net.thegost.mod.commands.FreezeCMD;
import net.thegost.mod.commands.InvseeCMD;
import net.thegost.mod.events.*;
import net.thegost.mod.panel.PanelInterfaceClickEvent;
import net.thegost.mod.panel.PanelInterfaceManagement;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Statistic;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

public final class Core extends JavaPlugin {

    private static Core instance;
    private FileManager fm;

    public ArrayList<Player> setMod = new ArrayList<>();
    public ArrayList<Player> setSpotter = new ArrayList<>();
    public ArrayList<Player> setFreeze = new ArrayList<>();
    public ArrayList<Player> setVanish = new ArrayList<>();

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("§a§lVMore+ §a> Working!");
        registerCommands();
        registerEvents();
        ModCommands();
        ModEvents();
        getConfig().options().copyDefaults(true);
        saveConfig();
        LoadConfigManager();
        instance = this;
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§c§lVMore+ §c> Off!");
    }

    public void LoadConfigManager() {
        fm = new FileManager();
        fm.setup();
        fm.fill();
        fm.savePermissions();
        fm.reloadPermissions();
    }

    private void registerCommands() {
        getCommand("whois").setExecutor(new Whois(this));
        getCommand("mod").setExecutor(new Mod(this));
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new Join(this), this);
        pm.registerEvents(new Leave(this), this);
    }

    private void ModEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ModItemInteraction(this), this);
        pm.registerEvents(new Spotter(this), this);
        pm.registerEvents(new ModItemPlace(this), this);
        pm.registerEvents(new PanelInterfaceManagement(this), this);
        pm.registerEvents(new PanelInterfaceClickEvent(this), this);
        pm.registerEvents(new Freeze(this),this);
        pm.registerEvents(new DroItem(this), this);
    }

    private void ModCommands() {
        getCommand("freeze").setExecutor(new FreezeCMD(this));
        getCommand("invsee").setExecutor(new InvseeCMD(this));
    }

    public static Core getInstance() {
        return instance;
    }

    public void modItems(Player player) {

        //ItemStack init
        ItemStack inv = new ItemStack(Material.SPYGLASS);
        ItemStack knock = new ItemStack(Material.STICK);
        ItemStack freeze = new ItemStack(Material.PACKED_ICE);
        ItemStack hopper = new ItemStack(Material.HOPPER);
        ItemStack vanish = new ItemStack(Material.ENDER_EYE);
        ItemStack book = new ItemStack(Material.BOOK);

        //ItemMeta init

        ItemMeta see = inv.getItemMeta();
        see.setDisplayName("§6Invsee (Futur update)");
        see.setLore(Arrays.asList("§3Voir l'inventaire du joueur"));
        inv.setItemMeta(see);

        ItemMeta s = knock.getItemMeta();
        s.setDisplayName("§dKnockback X");
        s.addEnchant(Enchantment.KNOCKBACK, 10, true);
        s.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        s.setLore(Arrays.asList("§3Test knockback"));
        knock.setItemMeta(s);

        ItemMeta ice = freeze.getItemMeta();
        ice.setDisplayName("§bFreeze (Futur update)");
        ice.setLore(Arrays.asList("§3Freeze un joueur"));
        freeze.setItemMeta(ice);

        ItemMeta spotO = hopper.getItemMeta();
        spotO.setDisplayName("§aSpotter");
        spotO.setLore(Arrays.asList("§3Cancel le ramassage des items"));
        hopper.setItemMeta(spotO);

        ItemMeta inter = book.getItemMeta();
        inter.setDisplayName("§ePanel");
        inter.setLore(Arrays.asList("§3Interface avec différentes options/vues sur le serveur"));
        inter.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        inter.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        book.setItemMeta(inter);

        ItemMeta vM = vanish.getItemMeta();
        vM.setDisplayName("§6Vanish");
        vM.setLore(Arrays.asList("§3Te rend invisible"));
        vM.addEnchant(Enchantment.DURABILITY,1,true);
        vM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        vanish.setItemMeta(vM);

        //Item in player inv init
        player.getInventory().setItem(0, inv);
        player.getInventory().setItem(1, knock);
        player.getInventory().setItem(2, freeze);
        player.getInventory().setItem(7, vanish);
        player.getInventory().setItem(8, hopper);
        player.getInventory().setItem(4, book);
    }

    public void PlayerInventoryItems(Inventory inv) {
        for(Player isco : Bukkit.getOnlinePlayers()) {
            ItemStack Iinv = new ItemStack(Material.PLAYER_HEAD, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta IData = (SkullMeta) Iinv.getItemMeta();
            IData.setOwningPlayer(isco);
            IData.setDisplayName(isco.getDisplayName());
            Iinv.setItemMeta(IData);

            ItemStack tpI = new ItemStack(Material.ENDER_PEARL, 1);
            ItemMeta iscTP = tpI.getItemMeta();
            iscTP.setDisplayName("§aTP à : §3" + isco.getDisplayName());
            tpI.setItemMeta(iscTP);

            ItemStack stats = new ItemStack(Material.PAPER, 1);
            ItemMeta stM = stats.getItemMeta();
            stM.setDisplayName("§6Invsee");
            stM.setLore(Arrays.asList("§3Inventaire du joueur"));
            stats.setItemMeta(stM);

            ItemStack free = new ItemStack(Material.PACKED_ICE, 1);
            ItemMeta freeze = free.getItemMeta();
            freeze.setDisplayName("§bFreeze");
            free.setItemMeta(freeze);

            ItemStack back = new ItemStack(Material.REDSTONE_TORCH, 1);
            ItemMeta bM = back.getItemMeta();
            bM.setDisplayName("§cRetour");
            back.setItemMeta(bM);

            inv.setItem(4, Iinv);
            inv.setItem(11, tpI);
            inv.setItem(13, stats);
            inv.setItem(15, free);
            inv.setItem(18, back);
        }
    }

    public void OnlineInterfaceItem(Inventory inv) {
        for (Player isco : Bukkit.getOnlinePlayers()) {
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta pH = (SkullMeta) skull.getItemMeta();
            pH.setOwningPlayer(isco);
            pH.setDisplayName(isco.getDisplayName());
            pH.setLore(Arrays.asList("§cID: §3" + isco.getUniqueId(), "§cMob killed: §3" + isco.getStatistic(Statistic.MOB_KILLS), "§cPing: §3" + isco.getPing()));
            skull.setItemMeta(pH);

            inv.addItem(skull);
        }
    }

    public void PanelItems(Inventory inv) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta pH = (SkullMeta) skull.getItemMeta();
        pH.setDisplayName("§aJoueurs connectés");
        pH.setLore(Arrays.asList("§3Interface des joueurs sur le serveur"));
        skull.setItemMeta(pH);

        //Panel stuff init
        inv.setItem(0, skull);
    }
}
