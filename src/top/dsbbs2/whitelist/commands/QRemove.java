package top.dsbbs2.whitelist.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.dsbbs2.whitelist.WhiteListPlugin;
import top.dsbbs2.whitelist.util.*;

import java.util.Vector;

public class QRemove implements IChildCommand {

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
					if(PlayerUtil.getWLPlayerByQQ(Long.parseLong(arg3[1]))==null){
						arg0.sendMessage("������Ч,��QQ"+arg3[1]+"��û�����������!");
						return true;
					}
					if(Long.parseLong(arg3[1])==-1){
						arg0.sendMessage("������Ч,������ɾ��-1QQ,�������addʱû�м�QQ��,����ʹ��wl remove <id>");
						return true;
					}
					String name = PlayerUtil.getWLPlayerByQQ(Long.parseLong(arg3[1])).name;
					if(ServerUtil.isOnlineStorageMode()) {
						OfflinePlayer op=DedicatedMethods.tryGetOfflinePlayerByQQ(Long.parseLong(arg3[1]),arg0);
						if(op==null) {
							return true;
						}
						PlayerUtil.removeFromWhiteListAndSave(op.getUniqueId());
					}else{
						PlayerUtil.removeFromWhiteListAndSave(name);
					}
					Player p = Bukkit.getPlayer(name);
					if(p!=null) {
						if (WhiteListPlugin.instance.whitelist.con.unCongratulate != null) {
							p.sendMessage(WhiteListPlugin.instance.whitelist.con.unCongratulate);
						} else {
							p.sendMessage("��ʧȥ�˰�����!");
						}
					}
					arg0.sendMessage("�����ɹ�,�ɹ���"+name+"["+arg3[1]+"]�Ƴ�������!");
					if(PlayerUtil.kickPlayerIfIs(Bukkit.getPlayer(arg3[1]))){
						arg0.sendMessage("���ڷ���������û�а������޷������ģʽ,���߳����"+arg3[1]);
					}
				}catch(Throwable e) {e.printStackTrace();arg0.sendMessage("�����쳣����ջ��¼�Ѵ�ӡ������̨");}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public String getUsage() {
		return "/wl qremove <qq>";
	}
	
	@Override
	public Vector<Class<?>> getArgumentsTypes()
	{
		return VectorUtil.toVector(long.class);
	}
	
	@Override
	public Vector<String> getArgumentsDescriptions()
	{
		return VectorUtil.toVector("qq");
	}
	
	@Override
	public String getPermission()
	{
		return "whitelist.remove";
	}
	@Override
	public String getDescription(){
		return "ͨ��qq�Ƴ�������";
	}
}
