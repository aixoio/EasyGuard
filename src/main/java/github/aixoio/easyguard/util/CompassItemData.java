package github.aixoio.easyguard.util;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class CompassItemData {

    private ItemStack item;
    private ItemStack resetItem;
    private Location originalCompassLocation;

    public CompassItemData(ItemStack item, ItemStack resetItem, Location originalCompassLocation) {
        this.item = item;
        this.resetItem = resetItem;
        this.originalCompassLocation = originalCompassLocation;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public ItemStack getResetItem() {
        return resetItem;
    }

    public void setResetItem(ItemStack resetItem) {
        this.resetItem = resetItem;
    }

    public Location getOriginalCompassLocation() {
        return originalCompassLocation;
    }

    public void setOriginalCompassLocation(Location originalCompassLocation) {
        this.originalCompassLocation = originalCompassLocation;
    }

}
