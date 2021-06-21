package top.dsbbs2.whitelist.util;

import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import top.dsbbs2.common.file.FileUtils;
import top.dsbbs2.whitelist.WhiteListPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PluginUtil {
    public static String getPluginVersion(){
        return "更好的白名名单(WL) - "+ Bukkit.getPluginManager().getPlugin("WhiteList").getDescription().getVersion()+"[F17A]";
    }

    public static boolean isDebugMode(){
        return WhiteListPlugin.instance.debugMode;
    }

    public static boolean isLowerMode(){
        if(WhiteListPlugin.instance.forceLowerMode){
            return true;
        }

        if(WhiteListPlugin.instance.extraConfig.getConfig().pluginAotuExcuter){
            try {
                if (ServerUtil.getTps() < WhiteListPlugin.instance.extraConfig.getConfig().dengerousTPS) {
                    return true;

                }
            }catch (Throwable e){
                return !WhiteListPlugin.instance.whitelist.con.canNoWhitePlayerGetIn;
            }
        }
        return false;
    }

    public static void checkConfigFileAndRecover(){
        if (!FileUtils.readTextFile(new File(WhiteListPlugin.instance.getDataFolder()+"/whitelist.json"), StandardCharsets.UTF_8).trim().equals(WhiteListPlugin.instance.whitelist.g.toJson(WhiteListPlugin.instance.whitelist.getConfig()).trim()))
        {
            System.out.println("§c[WL]配置文件whitelist.json 已被您配置错误,无法加载. 我们会为您创建一个新的配置文件,并备份错误的配置文件.");
            FileUtils.writeTextFile(new File(WhiteListPlugin.instance.getDataFolder()+"/whitelist.json.bak"),FileUtils.readTextFile(new File(WhiteListPlugin.instance.getDataFolder()+"/whitelist.json"), StandardCharsets.UTF_8),StandardCharsets.UTF_8,false);
            try {
                WhiteListPlugin.instance.whitelist.saveConfig();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("§c[WL]储存whitelist,json文件失败");
            }
        }

        if (!FileUtils.readTextFile(new File(WhiteListPlugin.instance.getDataFolder()+"/ExtraConfig.json"), StandardCharsets.UTF_8).trim().equals(WhiteListPlugin.instance.extraConfig.g.toJson(WhiteListPlugin.instance.extraConfig.getConfig()).trim()))
        {
            System.out.println("§c[WL]配置文件ExtraConfig.json 已被您配置错误,无法加载. 我们会为您创建一个新的配置文件,并备份错误的配置文件.");
            FileUtils.writeTextFile(new File(WhiteListPlugin.instance.getDataFolder()+"/ExtraConfig.json.bak"),FileUtils.readTextFile(new File(WhiteListPlugin.instance.getDataFolder()+"/ExtraConfig.json"), StandardCharsets.UTF_8),StandardCharsets.UTF_8,false);
            try {
                WhiteListPlugin.instance.extraConfig.saveConfig();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("§c[WL]储存ExtraConfig.json文件失败");
            }
        }
    }



}
