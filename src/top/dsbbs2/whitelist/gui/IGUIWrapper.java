package top.dsbbs2.whitelist.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface IGUIWrapper {
    public void open();
    public void close();
    public int getPageNum();
    public int getPage();
    public void setPage(int page);
    public void refresh();
    public Inventory getInventory();
    public Player getOwner();
}
