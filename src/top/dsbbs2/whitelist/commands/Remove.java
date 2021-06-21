package top.dsbbs2.whitelist.commands;

import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import top.dsbbs2.whitelist.util.MsgUtil;
import top.dsbbs2.whitelist.util.PlayerUtil;
import top.dsbbs2.whitelist.util.ServerUtil;
import top.dsbbs2.whitelist.util.VectorUtil;

public class Remove implements IChildCommand {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg3.length==2)
		{
			if(MsgUtil.hasNullString(arg3,arg0,false)){
				return true;
			}
			OfflinePlayer op=Bukkit.getOfflinePlayer(arg3[1]);
			if(ServerUtil.isOnlineStorageMode()) {
				if(!PlayerUtil.isInWhiteList(arg3[1])){
					if (!PlayerUtil.isInWhiteList(op.getUniqueId())) {
						arg0.sendMessage("�ڰ��������Ҳ��������["+arg3[1]+"]:"+op.getUniqueId());
						return true;
					}
				}
			}else{
				if (!PlayerUtil.isInWhiteList(arg3[1])) {
					arg0.sendMessage("�ڰ��������Ҳ��������:"+arg3[1]);
					return true;
				}
			}
			try {
				long QQ = 0;
				if(ServerUtil.isOnlineStorageMode()) {
					if(PlayerUtil.getWLPlayerByUUID(op.getUniqueId())!=null){
						QQ = PlayerUtil.getWLPlayerByUUID(op.getUniqueId()).QQ;
						PlayerUtil.removeFromWhiteListAndSave(op.getUniqueId());
					}else if(PlayerUtil.getWLPlayerByName(arg3[1])!=null){
						QQ = PlayerUtil.getWLPlayerByName(arg3[1]).QQ;
						PlayerUtil.removeFromWhiteListAndSave(arg3[1]);
					}

				}else{
					QQ = PlayerUtil.getWLPlayerByName(arg3[1]).QQ;
					PlayerUtil.removeFromWhiteListAndSave(arg3[1]);
				}
				arg0.sendMessage("�����ɹ�,�ɹ���"+arg3[1]+"["+QQ+"]�Ƴ�������!");
				if(PlayerUtil.kickPlayerIfIs(Bukkit.getPlayer(arg3[1]))){
					arg0.sendMessage("���ڷ���������û�а������޷������ģʽ,���߳����"+arg3[1]);
				}
			}catch(Throwable e) {e.printStackTrace();arg0.sendMessage("�����쳣����ջ��¼�Ѵ�ӡ������̨");}
			return true;
		}
		return false;
	}

	@Override
	public String getUsage() {
		return "/wl remove <player_name>";
	}

	@Override
	public Vector<Class<?>> getArgumentsTypes()
	{
		return VectorUtil.toVector(String.class);
	}

	 
	@Override
	public Vector<String> getArgumentsDescriptions()
	{
		return VectorUtil.toVector("whitelisted_player");
	}

	 
	@Override
	public String getPermission()
	{
		return "whitelist.remove";
	}
	 
	@Override
	public String getDescription(){
		return "ͨ��id�Ƴ��û�������";
	}
}
