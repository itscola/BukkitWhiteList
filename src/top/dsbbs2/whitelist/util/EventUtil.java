package top.dsbbs2.whitelist.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerPickupItemEvent;
import top.dsbbs2.whitelist.WhiteListPlugin;

public class EventUtil {
    public static boolean checkAndCancel(Cancellable e, Player p, String mess) throws Throwable
    {
        if(p==null){
            MsgUtil.makeDebugMsgAndSend("����NPC ȡ�����");
            e.setCancelled(false);
            return true;
        }



        try {
            if(WhiteListPlugin.instance.CNCU.containsValue(p.getName())){
                e.setCancelled(true);
//                PlayerUtil.setInv(p, true);
                    if(PlayerUtil.isMaxMarkInPlayerInteract(p.getName())){
                        p.kickPlayer("����Ƶ������,�������������!");
                        PlayerUtil.removePlayerFromPlayerInteract(p.getName());
                    }





                String messa = WhiteListPlugin.instance.whitelist.con.on_Name_Is_Right_But_UUID;
                if (messa != null && !messa.trim().equals("")){
                    p.sendMessage(messa);
                }else{
                    p.sendMessage("��⵽���İ������е�UUID����,����Ⱥ�� '.��֤',���������û�жԽ�Ⱥ,���ҹ���Ա����/wl confirm <����QQ��>");
                }
                return true;
            }else if(WhiteListPlugin.instance.CUCN.containsValue(p.getUniqueId())){
                e.setCancelled(true);


                    if(PlayerUtil.isMaxMarkInPlayerInteract(p.getName())){
                        Bukkit.broadcastMessage("��bû�а����������"+p.getName()+"�ڷ�����Ƶ������,�ѽ����߳���Ϸ!");
                        p.kickPlayer("����Ƶ������,�������������!");
                        PlayerUtil.removePlayerFromPlayerInteract(p.getName());
                    }


//                PlayerUtil.setInv(p, true);
                String messa = WhiteListPlugin.instance.whitelist.con.on_UUID_Is_Right_But_Name;
                if (messa != null && !messa.trim().equals("")){
                    p.sendMessage(messa);
                }else{
                    p.sendMessage("��⵽���İ������е�Name����,����Ⱥ�� '.��֤',���������û�жԽ�Ⱥ,���ҹ���Ա����/wl confirm <����QQ��>");
                }
                return true;
            }

            if(ServerUtil.isOnlineStorageMode()) {
                if (!PlayerUtil.isInWhiteList(p.getUniqueId())&&!PlayerUtil.isInWhiteList(p.getName())) {
                    cancleThat(e,p,mess);
                }

            }else{
                if (!PlayerUtil.isInWhiteList(p.getName())) {
                    cancleThat(e,p,mess);
                }
            }
        }
        catch(Throwable e2)
        {
            MsgUtil.makeDebugMsgAndSend("����NPC �׳� e2");
            throw e2;
        }
        return false;


    }

    public static boolean checkAndCancel(Cancellable e, Player p) throws Throwable{

        return checkAndCancel(e, p, PlayerUtil.informMess);

    }

    public static boolean cancleThat(Cancellable e, Player p, String mess){
        e.setCancelled(true);

        if(!(e instanceof PlayerPickupItemEvent)){
            PlayerUtil.addMarkToPlayerInteract(p.getName());

            if(PlayerUtil.isMaxMarkInPlayerInteract(p.getName())){
                Bukkit.broadcastMessage("��bû�а����������"+p.getName()+"�ڷ�����Ƶ������,�ѽ����߳���Ϸ!");
                p.kickPlayer("����Ƶ������,�������������!");
                PlayerUtil.removePlayerFromPlayerInteract(p.getName());
            }
        }


//                    PlayerUtil.setInv(p, true);
        if (mess != null && !mess.trim().equals(""))
            p.sendMessage(mess);
        return true;
    }

}
