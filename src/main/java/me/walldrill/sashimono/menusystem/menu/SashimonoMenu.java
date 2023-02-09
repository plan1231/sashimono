package me.walldrill.sashimono.menusystem.menu;

import me.walldrill.sashimono.Sashimono;
import me.walldrill.sashimono.menusystem.Menu;
import me.walldrill.sashimono.menusystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
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


    private boolean isInteractingBlockedSlot(InventoryClickEvent e){
        return e.getClickedInventory() == inventory && e.getSlot() != 0;
    }

    private boolean isMovingInWrong(InventoryClickEvent e){
        return e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY
                &&
                e.getClickedInventory() != inventory
                &&
                (!(e.getCurrentItem().getItemMeta() instanceof BannerMeta) ||
                 e.getCurrentItem().getAmount() != 1);
    }
    private boolean isActionPlace(InventoryClickEvent e){
        return e.getAction() == InventoryAction.PLACE_SOME ||
                e.getAction() == InventoryAction.PLACE_ONE ||
                e.getAction() == InventoryAction.PLACE_ALL;
    }
    private boolean isActionPickup(InventoryClickEvent e){
        return e.getAction() == InventoryAction.PICKUP_ALL ||
                e.getAction() == InventoryAction.PICKUP_HALF ||
                e.getAction() == InventoryAction.PICKUP_ONE ||
                e.getAction() == InventoryAction.PICKUP_SOME;
    }
    private boolean isPlacingDownWrong(InventoryClickEvent e){
        return (isActionPlace(e) || e.getAction() == InventoryAction.SWAP_WITH_CURSOR)
                &&
                e.getClickedInventory() == inventory
                &&
                (!(e.getWhoClicked().getItemOnCursor().getItemMeta() instanceof BannerMeta) || e.getWhoClicked().getItemOnCursor().getAmount() != 1);
    }
    @Override
    public void handleMenu(InventoryClickEvent e) {
        System.out.println("inside inventoryclick handlemenu");
        System.out.println(e.getAction());
        System.out.println("isInteractingBlockedSlot(e) " + isInteractingBlockedSlot(e));
        System.out.println("isMovingInWrong " + isMovingInWrong(e));
        System.out.println("isPlacingDownWrong " + isPlacingDownWrong(e));
        if(isInteractingBlockedSlot(e) || isMovingInWrong(e) || isPlacingDownWrong(e)
                ){
            e.setCancelled(true);
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
