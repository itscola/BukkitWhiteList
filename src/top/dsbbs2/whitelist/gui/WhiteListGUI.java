package top.dsbbs2.whitelist.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import top.dsbbs2.whitelist.WhiteListPlugin;
import top.dsbbs2.whitelist.util.ItemUtil;
import top.dsbbs2.whitelist.util.PlayerUtil;

import java.util.Vector;

public class WhiteListGUI implements IGUIWrapper{
    private Player p;
    private int currentPage=0;
    public volatile Vector<Inventory> pages;
    public WhiteListGUI(Player p)
    {
        this.p=p;
        this.pages=PlayerUtil.getPages(p);
        WhiteListGUI instance=this;
        WhiteListPlugin.instance.registerListener(new Listener(){
            @EventHandler(priority= EventPriority.HIGHEST,ignoreCancelled = false)
            public void onPlayerClickInventory(InventoryClickEvent e)
            {
                if(instance.pages.contains(e.getClickedInventory()))
                {
                    e.setCancelled(true);
                    ItemStack item=e.getCurrentItem();
                    if(item!=null && item.getType()==Material.BOOK)
                    {
                        int page=instance.getPage();
                        instance.refresh();
                        if(ItemUtil.getDisplayName(item).equals("上一页"))
                        {
                            instance.setPage(page-1);
                        }else if(ItemUtil.getDisplayName(item).equals("下一页"))
                        {
                            instance.setPage(page+1);
                        }
                        instance.open();
                    }
                }
            }
        });
    }
    public WhiteListGUI(Player p,int page)
    {
        this(p);
        setPage(page);
    }
    @Override
    public void open() {
        this.p.openInventory(this.getInventory());
    }

    @Override
    public int getPageNum() {
        return this.pages.size();
    }

    @Override
    public void close() {
        if(this.pages.contains(this.p.getOpenInventory().getTopInventory()))
          this.p.closeInventory();
    }

    @Override
    public void setPage(int page) {
        int temp=page;
        if(temp>=this.pages.size())
            temp=this.pages.size()-1;
        else if(temp<0)
            temp=0;
        this.currentPage=temp;
    }

    @Override
    public void refresh() {
        boolean opened=this.pages.contains(this.p.getOpenInventory());
        this.pages=PlayerUtil.getPages(p);
        setPage(this.currentPage);
        if(opened)
          this.open();
    }

    @Override
    public int getPage() {
        return this.currentPage;
    }

    @Override
    public Inventory getInventory() {
        return this.pages.get(this.currentPage);
    }

    @Override
    public Player getOwner() {
        return this.p;
    }
}
