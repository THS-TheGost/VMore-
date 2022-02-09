package net.thegost.mod.commands;

import net.thegost.Core;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCMD implements CommandExecutor {

    Core core;

    public FreezeCMD(Core core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("§cSeul un joueur peut exécuter cette commande!");
        }

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("freeze") && player.hasPermission("vmore.mod.freeze")) {
            if(!(player.hasPermission("vmore.mod.freeze"))) {
                player.sendMessage(core.getConfig().getString("messages.noperm").replace("&", "§"));
                return true;
            } else {
                if(args.length == 0) {
                    player.sendMessage("§a/freeze <player>");
                    return true;
                } else {
                    Player t = Bukkit.getPlayer(args[0]);
                    if(t == null) return true;
                    if(!(t.isOnline())) return true;
                    if(args[0].equalsIgnoreCase(t.getDisplayName())) {
                        if(core.setFreeze.contains(t)) {
                            core.setFreeze.remove(t);
                            System.out.println("REMOVE REMOVE REMOVE REMOVE");
                            t.sendMessage(core.getConfig().getString("messages.freeze.removefreeze").replace("&", "§"));
                        } else if(!(core.setFreeze.contains(t))) {
                            core.setFreeze.add(t);
                            System.out.println("ADDED ADDED ADDED ADDED ADDED");
                            t.sendMessage(core.getConfig().getString("messages.freeze.setfreeze").replace("&", "§"));
                        }
                    }
                }
            }
        }
        return false;
    }
}
