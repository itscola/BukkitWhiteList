package top.dsbbs2.whitelist.commands;

import java.util.UUID;
import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import top.dsbbs2.whitelist.util.*;

public class NoNameRemove implements IChildCommand {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        if(!ServerUtil.isOnlineStorageMode()){
            arg0.sendMessage("���洢��ģʽ�޷�ʹ�ô�����!");
            return true;
        }
        if(arg3.length==2)
        {
            if(MsgUtil.hasNullString(arg3,arg0,false)){
                return true;
            }
            if(!CommandUtil.ArgumentUtil.isUUID(arg3[1]))
            {
                arg0.sendMessage("��Ч��UUID");
                return true;
            }
            OfflinePlayer op=Bukkit.getOfflinePlayer(UUID.fromString(arg3[1]));
            if(!PlayerUtil.isInWhiteList(op))
            {
                arg0.sendMessage("�ڰ��������Ҳ��������");
                return true;
            }
            String name = PlayerUtil.getWLPlayerByUUID(UUID.fromString(arg3[1])).name;
            long QQ = PlayerUtil.getWLPlayerByUUID(UUID.fromString(arg3[1])).QQ;
            try {
                PlayerUtil.removeFromWhiteListAndSave(op.getUniqueId());
                arg0.sendMessage("�����ɹ�,�ɹ���"+name+"["+QQ+"]["+arg3[1]+"]�Ƴ�������!");
            }catch(Throwable e) {e.printStackTrace();arg0.sendMessage("�����쳣����ջ��¼�Ѵ�ӡ������̨");}
            return true;
        }
        return false;
    }
    @Override
    public String getUsage() {
        return "/wl nonameremove <player_uuid>";
    }

    @Override
    public Vector<Class<?>> getArgumentsTypes()
    {
        return VectorUtil.toVector(String.class);
    }

    @Override
    public Vector<String> getArgumentsDescriptions()
    {
        return VectorUtil.toVector("noname_player");
    }

    @Override
    public String getPermission()
    {
        return "whitelist.remove";
    }
    @Override
    public String getDescription(){
        return "ͨ��UUIDɾ�����[�����������]";
    }
}
