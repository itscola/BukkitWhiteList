package top.dsbbs2.whitelist.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import top.dsbbs2.whitelist.WhiteListPlugin;
import top.dsbbs2.whitelist.config.struct.WhiteListConfig;
import top.dsbbs2.whitelist.util.*;

import java.util.UUID;
import java.util.Vector;

public class Confirm implements IChildCommand{
    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        if(!ServerUtil.isOnlineStorageMode()){
            arg0.sendMessage("此命令只有正版储存模式可用!");
            return true;
        }
        for(int i=0;i<arg3.length;i++){
            if(arg3[i].equals("")){
                arg0.sendMessage("第"+(i+1)+"个参数,不能为空!");
                return true;
            }
        }
        if(arg3.length==2){
            if(MsgUtil.hasNullString(arg3,arg0,false)){
                return true;
            }
            if(!CommandUtil.ArgumentUtil.isLong(arg3[1])){
                arg0.sendMessage("QQ号必须是一个整数!");
                return true;
            }
            long QQ = Long.parseLong(arg3[1]);
            if(PlayerUtil.getWLPlayerByQQ(QQ)==null){
                arg0.sendMessage("在白名单中没有找到您的QQ,请先申请白名单!");
                return true;
            }
            Player p = Bukkit.getPlayer(PlayerUtil.getWLPlayerByQQ(QQ).uuid);
            if(p==null){
                p = Bukkit.getPlayer(PlayerUtil.getWLPlayerByQQ(QQ).name);
            }
            if(p==null||!p.isOnline()){
                arg0.sendMessage("您必须在线才能进行验证!");
                return true;
            }
            if(WhiteListPlugin.CUCN.containsKey(QQ)){
                try{
                    arg0.sendMessage("验证成功,开始为您更改Name!");
                    UUID uuid = WhiteListPlugin.instance.CUCN.get(QQ);
                    WhiteListConfig.WLPlayer wlp = PlayerUtil.getWLPlayerByUUID(uuid);
                    updateHisWhitelist(wlp,p,arg0,"Name");
                    WhiteListPlugin.instance.CUCN.remove(QQ);
                    if(p!=null&&p.isOnline()){
                        p.sendMessage("§a恭喜您验证成功!");
                        PlayerUtil.setInv(p,false);
                    }
                    return true;
                }catch (Throwable e){
                    arg0.sendMessage(e.getStackTrace()+"! 更改Name失败,请联系管理员,错误日志已经在控制台打印!");
                    e.printStackTrace();
                }
            }
            if(WhiteListPlugin.CNCU.containsKey(QQ)){
                try {
                    arg0.sendMessage("验证成功,开始为您更改UUID!");
                    String name = WhiteListPlugin.instance.CNCU.get(QQ);
                    WhiteListConfig.WLPlayer wlp = PlayerUtil.getWLPlayerByName(name);
                    updateHisWhitelist(wlp,p,arg0,"UUID");
                    WhiteListPlugin.instance.CNCU.remove(QQ);
                    if(p!=null&&p.isOnline()){
                        p.sendMessage("§a恭喜您验证成功!");
                        PlayerUtil.setInv(p,false);
                    }
                    return true;
                }catch (Throwable e){
                    arg0.sendMessage(e.getStackTrace()+"! 更改失败,请联系管理员,错误日志已经在服务器控制台打印!");
                    e.printStackTrace();
                }
            }
            arg0.sendMessage("验证信息失败,您目前无需验证!");
            return true;
        }
        return false;
    }
    public static void updateHisWhitelist(WhiteListConfig.WLPlayer wlp,Player p,CommandSender arg0,String mode)throws Throwable{
        long s1 = System.currentTimeMillis();
        if(mode.equalsIgnoreCase("UUID")) {
            arg0.sendMessage(wlp.uuid + " -> " + p.getUniqueId());
        }else if(mode.equalsIgnoreCase("Name")){
            arg0.sendMessage(wlp.name + " -> " + p.getName());
        }
        WhiteListPlugin.instance.whitelist.con.players.add(new WhiteListConfig.WLPlayer(p.getPlayer().getUniqueId(), p.getPlayer().getName(), wlp.QQ));
        WhiteListPlugin.instance.whitelist.con.players.remove(wlp);
        WhiteListPlugin.instance.whitelist.saveConfig();
        long s2 = System.currentTimeMillis();
        arg0.sendMessage("耗时: "+(s2-s1)+"毫秒!");
    }

    @Override
    public @NotNull String getUsage() {
        return "/wl confirm <QQ>";
    }

    @Override
    public @NotNull Vector<Class<?>> getArgumentsTypes() {
        return VectorUtil.toVector(long.class);
    }

    @Override
    public @NotNull Vector<String> getArgumentsDescriptions() {
        return VectorUtil.toVector("qq");
    }

    @Override
    public @NotNull String getPermission() {

        return "whitelist.confirm";
    }
    @NotNull
    @Override
    public String getDescription(){
        return "对用户进行验证";
    }
}
