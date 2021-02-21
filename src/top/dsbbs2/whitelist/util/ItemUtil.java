package top.dsbbs2.whitelist.util;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemUtil {
    public static void setDisplayName(ItemStack is,String name)
    {
        ItemMeta im=is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
    }
    public static void addLore(ItemStack is,String... lore)
    {
        setLore(is,ListUtil.toArray(ListUtil.append(is.getItemMeta().getLore(),lore),String.class));
    }
    public static void setLore(ItemStack is,String... lore)
    {
        ItemMeta im=is.getItemMeta();
        im.setLore(ListUtil.toList(lore));
        is.setItemMeta(im);
    }
    public static boolean setSkullOwner(ItemStack s, OfflinePlayer p)
    {
        ItemMeta im=s.getItemMeta();
        if(!(im instanceof SkullMeta))
            return false;
        SkullMeta sm=(SkullMeta)im;
        sm.setOwningPlayer(p);
        s.setItemMeta(sm);
        return true;
    }
    public static String getDisplayName(ItemStack is)
    {
        return is.getItemMeta().getDisplayName();
    }
}
