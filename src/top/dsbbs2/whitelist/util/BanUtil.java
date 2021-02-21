package top.dsbbs2.whitelist.util;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;


public class BanUtil {
    public static void ban(String playerName, String reason)throws Throwable{
        BanList list=Bukkit.getServer().getBanList(BanList.Type.NAME);
        if(!list.isBanned(playerName))
        {
            list.addBan(playerName,reason,null,null);

        }
        OfflinePlayer op = Bukkit.getOfflinePlayer(playerName);
        if(op.isOnline())
            op.getPlayer().kickPlayer(reason);
    }
}
