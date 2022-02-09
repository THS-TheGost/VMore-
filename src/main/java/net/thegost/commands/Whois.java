package net.thegost.commands;

import net.thegost.Core;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Whois implements CommandExecutor {

    Core core;

    String nameB = "§7---------§8§l[§a";
    String nameA = "§8§l]§7---------";
    String close = "§7---------------------------";

    public Whois(Core core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("§b§lVous devez être un joueur pour éxécuter cette commande !");
            return true;
        }

        Player player = (Player)sender;

        if(cmd.getName().equalsIgnoreCase("whois") && player.hasPermission("vmore.whois")) {
            if(!(player.hasPermission("vmore.whois"))) {
                player.sendMessage(core.getConfig().getString("messages.noperm").replace("&", "§"));
                return true;
            } else {
                if(args.length == 0) {
                    player.sendMessage("§a/whois <player>");
                    return true;
                } else {
                    Player t = Bukkit.getPlayer(args[0]);
                    if(t == null) return true;
                    if(args[0].equalsIgnoreCase(player.getDisplayName())) {
                        sender.sendMessage("§cVous ne pouvez pas vous '/whois' vous mêmes...");
                        return true;
                    }else if(args[0].equalsIgnoreCase(t.getDisplayName())) {
                        player.sendMessage(nameB + t.getDisplayName() + nameA);
                        player.sendMessage("Mobs tués : §c§l" + t.getStatistic(Statistic.MOB_KILLS));
                        player.sendMessage("ID : §c§l" + t.getUniqueId());
                        player.sendMessage("Joueurs éliminés : §c§l" + t.getStatistic(Statistic.PLAYER_KILLS));
                        player.sendMessage(close);
                        return true;
                    }

                }
            }
        }
        return false;
    }
}
