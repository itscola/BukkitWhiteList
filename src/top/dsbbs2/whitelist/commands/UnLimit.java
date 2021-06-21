package top.dsbbs2.whitelist.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import top.dsbbs2.whitelist.WhiteListPlugin;
import top.dsbbs2.whitelist.util.*;

import java.util.Vector;

public class UnLimit implements IChildCommand{
    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        if(arg3.length==2)
        {
            if(MsgUtil.hasNullString(arg3,arg0,false)){
                return true;
            }
            if(!CommandUtil.ArgumentUtil.isLong(arg3[1]))
            {
                arg0.sendMessage("QQ号必须是一个整数");
                return true;
            }else {
                try {
                    long qq = Long.parseLong(arg3[1]);
                    if(!WhiteListPlugin.instance.wblacklist.getConfig().WblackList.contains(qq)){
                        arg0.sendMessage("没有在管制名单中找到QQ["+arg3[1]+"]");
                        return true;
                    }
                    WhiteListPlugin.instance.wblacklist.getConfig().WblackList.remove(qq);
                    WhiteListPlugin.instance.wblacklist.saveConfig();
                    arg0.sendMessage("已将QQ["+arg3[1]+"]移出管制名单.");

                }catch(Throwable e) {e.printStackTrace();arg0.sendMessage("出现异常，堆栈记录已打印至控制台");}
            }
            return true;
        }
        return false;
    }

     
    @Override
    public String getUsage() {
        return "/wl unlimit";
    }

     
    @Override
    public Vector<Class<?>> getArgumentsTypes()
    {
        return VectorUtil.toVector(long.class);
    }

     
    @Override
    public Vector<String> getArgumentsDescriptions()
    {
        return VectorUtil.toVector("wblacklist");
    }

     
    @Override
    public String getPermission()
    {
        return "whitelist.unlimit";
    }
     
    @Override
    public String getDescription(){
        return "移出管制名单.";
    }
}
