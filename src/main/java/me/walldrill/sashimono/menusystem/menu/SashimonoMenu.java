package me.walldrill.sashimono.menusystem.menu;

import me.walldrill.sashimono.Sashimono;
import me.walldrill.sashimono.menusystem.Menu;
import me.walldrill.sashimono.menusystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SashimonoMenu extends Menu {

    public SashimonoMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Equip a sashimono";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getCurrentItem().getItemMeta() instanceof BannerMeta bannerMeta){
            e.getWhoClicked().sendMessage("Banner clicked.");

        }
        else if(e.getCurrentItem() == null){
            e.getWhoClicked().sendMessage("empty currentitem");
        }
    }

    @Override
    public void setMenuItems() {

        ItemStack yes = new ItemStack(Material.EMERALD, 1);
        ItemMeta yes_meta = yes.getItemMeta();
        yes_meta.setDisplayName(ChatColor.YELLOW + "Drop a banner into the slot on the left");
        ArrayList<String> yes_lore = new ArrayList<>();
        yes_lore.add(ChatColor.AQUA + "Cosmetic change only.");
        yes_lore.add(ChatColor.AQUA + "You'll appear as if you've equipped the banner");
        yes_lore.add(ChatColor.AQUA + "but damage calculations still use your actual helmet");
        yes_meta.setLore(yes_lore);
        yes.setItemMeta(yes_meta);

        inventory.setItem(1, yes);

        setFillerGlass();

        // may want to reduce verbosity
        if(Sashimono.getPlayerBannerManager().playerHasCustomBanner(playerMenuUtility.getOwner()))
            inventory.setItem(0, Sashimono.getPlayerBannerManager().getPlayerBanner(playerMenuUtility.getOwner()));
        else
            inventory.clear(0);
    }
}
