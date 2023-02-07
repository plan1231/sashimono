package me.walldrill.sashimono.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EquipBannerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p) {
            ItemStack banner = new ItemStack(Material.WHITE_BANNER, 1);

            p.getInventory().setHelmet(banner);
        }
        return true;
    }
}
