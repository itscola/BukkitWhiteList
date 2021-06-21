package top.dsbbs2.whitelist.util;

import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import top.dsbbs2.whitelist.WhiteListPlugin;

import java.lang.reflect.Method;

public class ServerUtil {
    public static void updateJson(){
        try {
            WhiteListPlugin.instance.whitelist.saveConfig();
            WhiteListPlugin.instance.extraConfig.saveConfig();
            System.out.println("§a[WL]配置文件已刷新!");
        }catch (Throwable e){
            e.printStackTrace();
            System.out.println("§c[WL]配置文件刷新失败!");
            System.out.println("§c[WL]您的配置文件可能因为改动而损坏!");
        }
    }
    public static boolean getOnlineMode(){
        if(Bukkit.getOnlineMode()){
            MsgUtil.makeDebugMsgAndSend("Bukkit.getOnlineMode() is true");
            return true;
        }else{
            MsgUtil.makeDebugMsgAndSend("Bukkit.getOnlineMode() is false");
            return false;
        }
    }
    public static boolean isOnlineStorageMode()
    {
        return (Bukkit.getOnlineMode() || WhiteListPlugin.instance.whitelist.con.forceOnline.equalsIgnoreCase("Online"))&&!WhiteListPlugin.instance.whitelist.con.forceOnline.equalsIgnoreCase("Offline");
    }

    public static String getServerVersion ( )
    {
        return Bukkit.getServer ( ).getClass ( ).getPackage ( ).getName ( ).substring ( 23 );
    }


    public static float getTps() throws Throwable{
//        Class<?> server = HiReflect.getNMSClass()
        Class<?> MSClass = MinecraftServer.class;
        Object NMSServer = MSClass.getMethod("getServer").invoke(null);
        double[] tps = (double[]) NMSServer.getClass().getField("recentTps").get(NMSServer);
        return (float)tps[0];
    }
}
