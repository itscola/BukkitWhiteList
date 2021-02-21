package top.dsbbs2.whitelist.gui;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.dsbbs2.whitelist.util.ItemUtil;

import java.lang.reflect.Constructor;

public class ItemBuilder {
    private ItemStack is;
    public ItemBuilder()
    {
        try {
            Constructor<ItemStack> con = ItemStack.class.getDeclaredConstructor(new Class<?>[0]);
            con.setAccessible(true);
            is=con.newInstance(new Object[0]);
        }catch(Throwable e){throw new RuntimeException(e);}
    }
    public ItemBuilder(ItemStack is)
    {
        this.is=is;
    }
    public ItemBuilder setType(Material m)
    {
        this.is.setType(m);
        return this;
    }

//    public ItemBuilder setProbability(int probability)
//    {
//
//        return this;
//    }

    public ItemBuilder setAmount(int m)
    {
        this.is.setAmount(m);
        return this;
    }
    public ItemBuilder setDamage(short d)
    {
        this.is.setDurability(d);
        return this;
    }
    public ItemBuilder setItemMeta(ItemMeta i)
    {
        this.is.setItemMeta(i);
        return this;
    }
    public ItemBuilder setDisplayName(String name)
    {
        ItemUtil.setDisplayName(this.is,name);
        return this;
    }
    public ItemBuilder setSkullOwner(OfflinePlayer p)
    {
        ItemUtil.setSkullOwner(this.is,p);
        return this;
    }
    public ItemBuilder addLore(String... l)
    {
        ItemUtil.addLore(this.is,l);
        return this;
    }
    public ItemBuilder setLore(String... l)
    {
        ItemUtil.setLore(this.is,l);
        return this;
    }
    public ItemStack create()
    {
        return this.is;
    }
}
