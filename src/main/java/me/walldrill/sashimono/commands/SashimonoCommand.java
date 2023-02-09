package me.walldrill.sashimono.commands;

import me.walldrill.sashimono.Sashimono;
import me.walldrill.sashimono.menusystem.menu.SashimonoMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SashimonoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Player p = (Player) sender;

            //create the menu and then open it for the player
            new SashimonoMenu(Sashimono.getPlayerMenuUtility(p)).open();

        }

        return true;

    }
}
