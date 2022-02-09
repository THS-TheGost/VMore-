package net.thegost.mod;

import net.thegost.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Mod implements CommandExecutor {

     Core core;


    public Mod(Core core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("§cUne machine ne peut exécuter cette commande!");
            return true;
        }

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("mod") && player.hasPermission("vmore.mod")) {
            if(!(player.hasPermission("vmore.mod"))) {
                player.sendMessage(core.getConfig().getString("messages.noperm").replace("&", "§"));
                return true;
            } else {
                if(!core.setMod.contains(player)) {
                    core.setMod.add(player);
                    Core.getInstance().modItems(player);
                    player.sendMessage(core.getConfig().getString("messages.modcmd.modon").replace("&", "§"));
                } else if(core.setMod.contains(player)) {
                    core.setMod.remove(player);
                    core.setSpotter.remove(player);
                    player.getInventory().clear();
                    player.sendMessage(core.getConfig().getString("messages.modcmd.modoff").replace("&", "§"));
                }
            }
        }
        return false;
    }
}
