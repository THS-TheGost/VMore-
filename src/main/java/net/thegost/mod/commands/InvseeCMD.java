package net.thegost.mod.commands;

import net.thegost.Core;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCMD implements CommandExecutor {

    Core core;

    public InvseeCMD(Core core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("§cTu dois être un joueur pour exécuter cette commande!");
            return true;
        }

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("invsee") && player.hasPermission("vmore.mod.invsee")) {
            if(!(player.hasPermission("vmore.mod.invsee"))) {
                player.sendMessage(core.getConfig().getString("messages.noperm").replace("&", "§"));
                return true;
            } else {
                Player t = Bukkit.getPlayer(args[0]);
                if(t == null) return true;
                if(args[0].equalsIgnoreCase(t.getDisplayName())) {
                    player.openInventory(t.getInventory());
                }
            }
        }
        return false;
    }
}
