package top.dsbbs2.whitelist.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.dsbbs2.whitelist.WhiteListPlugin;
import top.dsbbs2.whitelist.config.struct.WhiteListConfig;
import top.dsbbs2.whitelist.util.*;

import java.util.UUID;
import java.util.Vector;

public class Confirm implements IChildCommand{
    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        if(!ServerUtil.isOnlineStorageMode()){
            arg0.sendMessage("������ֻ�����洢��ģʽ����!");
            return true;
        }
        for(int i=0;i<arg3.length;i++){
            if(arg3[i].equals("")){
                arg0.sendMessage("��"+(i+1)+"������,����Ϊ��!");
                return true;
            }
        }
        if(arg3.length==2){
            if(MsgUtil.hasNullString(arg3,arg0,false)){
                return true;
            }
            if(!CommandUtil.ArgumentUtil.isLong(arg3[1])){
                arg0.sendMessage("QQ�ű�����һ������!");
                return true;
            }
            long QQ = Long.parseLong(arg3[1]);
            if(PlayerUtil.getWLPlayerByQQ(QQ)==null){
                arg0.sendMessage("�ڰ�������û���ҵ�����QQ,�������������!");
                return true;
            }
            Player p = Bukkit.getPlayer(PlayerUtil.getWLPlayerByQQ(QQ).uuid);
            if(p==null){
                p = Bukkit.getPlayer(PlayerUtil.getWLPlayerByQQ(QQ).name);
            }
            if(p==null||!p.isOnline()){
                arg0.sendMessage("���������߲��ܽ�����֤!");
                return true;
            }
            if(WhiteListPlugin.CUCN.containsKey(QQ)){
                try{
                    arg0.sendMessage("��֤�ɹ�,��ʼΪ������Name!");
                    UUID uuid = WhiteListPlugin.instance.CUCN.get(QQ);
                    WhiteListConfig.WLPlayer wlp = PlayerUtil.getWLPlayerByUUID(uuid);
                    updateHisWhitelist(wlp,p,arg0,"Name");
                    WhiteListPlugin.instance.CUCN.remove(QQ);
                    if(p!=null&&p.isOnline()){
                        p.sendMessage("��a��ϲ����֤�ɹ�!");
                        PlayerUtil.setInv(p,false);
                    }
                    return true;
                }catch (Throwable e){
                    arg0.sendMessage(e.getStackTrace()+"! ����Nameʧ��,����ϵ����Ա,������־�Ѿ��ڿ���̨��ӡ!");
                    e.printStackTrace();
                }
            }
            if(WhiteListPlugin.CNCU.containsKey(QQ)){
                try {
                    arg0.sendMessage("��֤�ɹ�,��ʼΪ������UUID!");
                    String name = WhiteListPlugin.instance.CNCU.get(QQ);
                    WhiteListConfig.WLPlayer wlp = PlayerUtil.getWLPlayerByName(name);
                    updateHisWhitelist(wlp,p,arg0,"UUID");
                    WhiteListPlugin.instance.CNCU.remove(QQ);
                    if(p!=null&&p.isOnline()){
                        p.sendMessage("��a��ϲ����֤�ɹ�!");
                        PlayerUtil.setInv(p,false);
                    }
                    return true;
                }catch (Throwable e){
                    arg0.sendMessage(e.getStackTrace()+"! ����ʧ��,����ϵ����Ա,������־�Ѿ��ڷ���������̨��ӡ!");
                    e.printStackTrace();
                }
            }
            arg0.sendMessage("��֤��Ϣʧ��,��Ŀǰ������֤!");
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
        arg0.sendMessage("��ʱ: "+(s2-s1)+"����!");
    }

    @Override
    public String getUsage() {
        return "/wl confirm <QQ>";
    }

    @Override
    public Vector<Class<?>> getArgumentsTypes() {
        return VectorUtil.toVector(long.class);
    }

    @Override
    public Vector<String> getArgumentsDescriptions() {
        return VectorUtil.toVector("qq");
    }

    @Override
    public String getPermission() {

        return "whitelist.confirm";
    }

    @Override
    public String getDescription(){
        return "���û�������֤";
    }
}
