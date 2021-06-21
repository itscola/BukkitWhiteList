package top.dsbbs2.whitelist.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.dsbbs2.whitelist.WhiteListPlugin;
import top.dsbbs2.whitelist.util.PlayerUtil;
import top.dsbbs2.whitelist.util.PluginUtil;
import top.dsbbs2.whitelist.util.ServerUtil;
import top.dsbbs2.whitelist.util.VectorUtil;
import java.util.Vector;

public class Reload implements IChildCommand {
    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        try {
            WhiteListPlugin.instance.whitelist.loadConfig();


            PluginUtil.checkConfigFileAndRecover();



            PlayerUtil.informMess = WhiteListPlugin.instance.whitelist.con.mess;
            WhiteListPlugin.compareOnlineMode();
            ServerUtil.updateJson();
            WhiteListPlugin.updateOnlineModeinWhiteListJson();
            arg0.sendMessage("[WL]配置文件重载成功");
            if(ServerUtil.isOnlineStorageMode()) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    PlayerUtil.checkUUIDAndName(player);
                }
                System.out.println("§a[WL]已检测全部玩家的UUID和Name!");
            }

        }catch(Throwable e){
            e.printStackTrace();
            System.out.println("§c您的whitelist.json文件已损坏!");
            System.out.println("§c请尝试人工修改掉错误内容!");
            System.out.println("§c如果无法修改回正常内容,建议您复制下玩家数据区域,然后删除whitelist.json后reload 把玩家数据粘贴进新的whitelist.json里去,然后/wl reload");
        }
        return true;
    }

    @Override
    public String getUsage() {
        return "/wl reload";
    }

    @Override
    public Vector<Class<?>> getArgumentsTypes()
    {
        return VectorUtil.toVector();
    }

    @Override
    public Vector<String> getArgumentsDescriptions()
    {
        return VectorUtil.toVector();
    }

    @Override
    public String getPermission()
    {
        return "whitelist.reload";
    }
    @Override
    public String getDescription(){
        return "重载BukkitWhitelist配置文件";
    }
}
