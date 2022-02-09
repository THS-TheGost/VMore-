package net.thegost;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private Core core = Core.getPlugin(Core.class);

    private FileConfiguration permission;
    private File permFile;

    public void setup(){
        if(!core.getDataFolder().exists()) {
            core.getDataFolder().mkdir();
        }
        permFile = new File(core.getDataFolder(), "permissions.yml");
        if(!permFile.exists()) {
            try {
                permFile.createNewFile();
                Bukkit.getServer().getConsoleSender().sendMessage("§aLe fichier permissions.yml a bien été créé");
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage("§cImpossible de créer le fichier permissions.yml");
            }
        }
        permission = YamlConfiguration.loadConfiguration(permFile);
    }

    public void fill() {
        permission.createSection("Permissions");
        permission.set("Permissions.whois", "vmore.whois");
        permission.set("Permissions.mod", "vmore.mod");
        permission.set("Permissions.mod.freeze", "vmore.mod.freeze");
        permission.set("Permissions.mod.invsee", "vmore.mod.invsee");
        permission.set("Permissions.mod.tp", "vmore.mod.tp");
    }

    public FileConfiguration getPermission() {
        return permission;
    }

    public void savePermissions() {
        try{
            permission.save(permFile);
            Bukkit.getServer().getConsoleSender().sendMessage("§aLe fichier permissions.yml a bien été sauvegardé");
        } catch(IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("§cImpossible de sauvegarder le fichier permissions.yml");
        }
    }

    public void reloadPermissions() {
        permission = YamlConfiguration.loadConfiguration(permFile);
        Bukkit.getServer().getConsoleSender().sendMessage("§aLe fichier permissions.yml a bien été créé");
    }

}
