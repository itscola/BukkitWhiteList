package top.dsbbs2.whitelist.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import top.dsbbs2.whitelist.util.*;

import java.util.Vector;

public class Get implements IChildCommand{


    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        if(arg3.length==2){
            if(MsgUtil.hasNullString(arg3,arg0,false)){
                return true;
            }
            Boolean boo = getIt(arg0,arg3[1], ServerUtil.isOnlineStorageMode());
            return boo;
        }
        return false;
    }
    public static boolean getIt(CommandSender arg0,String QQNumOrID,Boolean isOnlineServer) {
        if (CommandUtil.ArgumentUtil.isLong(QQNumOrID)) {
            long qq = Long.parseLong(QQNumOrID);
            if(PlayerUtil.getWLPlayerByQQ(qq)!=null) {
                String ID = PlayerUtil.getWLPlayerByQQ(qq).name;
                if (ID != null && !ID.equals("")) {
                    arg0.sendMessage("��ҵ�ID�� " + ID);
                    return true;
                }
            }
            arg0.sendMessage("û���ҵ���QQ�Ŷ�Ӧ��ID");
            return true;
        }
        long qq = 0;
        if (QQNumOrID != null && !QQNumOrID.equals("")&&PlayerUtil.getWLPlayerByName(QQNumOrID)!=null) {
            qq = PlayerUtil.getWLPlayerByName(QQNumOrID).QQ;
        }
        if(qq!=0) {
            arg0.sendMessage("���" + QQNumOrID + "��QQΪ" + qq + "!");
            return true;
        }
        arg0.sendMessage("û���ҵ������!");
        return true;
    }
    @Override
    public  String getUsage() {
        return "/wl get <QQNum>or<ID>";
    }

    @Override
    public  Vector<Class<?>> getArgumentsTypes() {
        return VectorUtil.toVector(String.class);
    }

    @Override
    public  Vector<String> getArgumentsDescriptions() {
        return VectorUtil.toVector("QQnum/ID");
    }

    @Override
    public String getPermission() {
        return "whitelist.get";
    }

    @Override
    public String getDescription(){
        return "ͨ��id/qq��ȡqq/id";
    }
}
