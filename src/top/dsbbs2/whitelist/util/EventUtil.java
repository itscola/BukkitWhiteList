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
            MsgUtil.makeDebugMsgAndSend("发现NPC 取消检查");
            e.setCancelled(false);
            return true;
        }



        try {
            if(WhiteListPlugin.instance.CNCU.containsValue(p.getName())){
                e.setCancelled(true);
//                PlayerUtil.setInv(p, true);
                    if(PlayerUtil.isMaxMarkInPlayerInteract(p.getName())){
                        p.kickPlayer("请勿频繁操作,请先申请白名单!");
                        PlayerUtil.removePlayerFromPlayerInteract(p.getName());
                    }





                String messa = WhiteListPlugin.instance.whitelist.con.on_Name_Is_Right_But_UUID;
                if (messa != null && !messa.trim().equals("")){
                    p.sendMessage(messa);
                }else{
                    p.sendMessage("检测到您的白名单中的UUID错误,请在群里 '.验证',如果服务器没有对接群,请找管理员输入/wl confirm <您的QQ号>");
                }
                return true;
            }else if(WhiteListPlugin.instance.CUCN.containsValue(p.getUniqueId())){
                e.setCancelled(true);


                    if(PlayerUtil.isMaxMarkInPlayerInteract(p.getName())){
                        Bukkit.broadcastMessage("§b没有白名单的玩家"+p.getName()+"在服务器频繁操作,已将其踢出游戏!");
                        p.kickPlayer("请勿频繁操作,请先申请白名单!");
                        PlayerUtil.removePlayerFromPlayerInteract(p.getName());
                    }


//                PlayerUtil.setInv(p, true);
                String messa = WhiteListPlugin.instance.whitelist.con.on_UUID_Is_Right_But_Name;
                if (messa != null && !messa.trim().equals("")){
                    p.sendMessage(messa);
                }else{
                    p.sendMessage("检测到您的白名单中的Name错误,请在群里 '.验证',如果服务器没有对接群,请找管理员输入/wl confirm <您的QQ号>");
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
            MsgUtil.makeDebugMsgAndSend("发现NPC 抛出 e2");
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
                Bukkit.broadcastMessage("§b没有白名单的玩家"+p.getName()+"在服务器频繁操作,已将其踢出游戏!");
                p.kickPlayer("请勿频繁操作,请先申请白名单!");
                PlayerUtil.removePlayerFromPlayerInteract(p.getName());
            }
        }


//                    PlayerUtil.setInv(p, true);
        if (mess != null && !mess.trim().equals(""))
            p.sendMessage(mess);
        return true;
    }

}
