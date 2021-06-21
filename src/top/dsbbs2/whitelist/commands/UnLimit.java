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
                arg0.sendMessage("QQ�ű�����һ������");
                return true;
            }else {
                try {
                    long qq = Long.parseLong(arg3[1]);
                    if(!WhiteListPlugin.instance.wblacklist.getConfig().WblackList.contains(qq)){
                        arg0.sendMessage("û���ڹ����������ҵ�QQ["+arg3[1]+"]");
                        return true;
                    }
                    WhiteListPlugin.instance.wblacklist.getConfig().WblackList.remove(qq);
                    WhiteListPlugin.instance.wblacklist.saveConfig();
                    arg0.sendMessage("�ѽ�QQ["+arg3[1]+"]�Ƴ���������.");

                }catch(Throwable e) {e.printStackTrace();arg0.sendMessage("�����쳣����ջ��¼�Ѵ�ӡ������̨");}
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
        return "�Ƴ���������.";
    }
}
