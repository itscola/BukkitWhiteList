package top.dsbbs2.whitelist.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import top.dsbbs2.whitelist.WhiteListPlugin;
import top.dsbbs2.whitelist.util.*;

import java.util.Vector;

public class QBan implements IChildCommand {

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
			}
			if(Long.parseLong(arg3[1])==-1){
				arg0.sendMessage("������Ч,������ban-1QQ,�������addʱû�м�QQ��,����ʹ��wl remove <id>");
				return true;
			}

				if(PlayerUtil.getWLPlayerByQQ(Long.parseLong(arg3[1]))==null){
					arg0.sendMessage("������Ч,�޷������QQ["+arg3[1]+"],���QQ��û�������������!");
					return true;
				}

				String name = PlayerUtil.getWLPlayerByQQ(Long.parseLong(arg3[1])).name;
				try {
					BanUtil.ban(name,"��c���ѱ����");

					WhiteListPlugin.instance.wblacklist.getConfig().WblackList.add(Long.parseLong(arg3[1]));
					WhiteListPlugin.instance.wblacklist.saveConfig();
					if(ServerUtil.isOnlineStorageMode()) {
						PlayerUtil.removeFromWhiteListAndSave(PlayerUtil.getWLPlayerByQQ(Long.parseLong(arg3[1])).uuid);
					}else{
						PlayerUtil.removeFromWhiteListAndSave(name);
					}
					arg0.sendMessage("�����ɹ�,�ɹ���"+name+"["+arg3[1]+"]�Ƴ�������,���ҷ��!");
					arg0.sendMessage("�ѽ�"+name+"["+arg3[1]+"]����������������.");
					return true;
				}catch(Throwable e) {e.printStackTrace();arg0.sendMessage("�����쳣����ջ��¼�Ѵ�ӡ������̨");}





				//
				/*
				OfflinePlayer op=DedicatedMethods.tryGetOfflinePlayerByQQ(Long.parseLong(arg3[1]),arg0);
				if(op==null)
					return true;

				op.banPlayer("���ѱ����,ԭ��:���߳�Ⱥ�򱻹���Ա���!");
*/
				 //
				/*
				if(PlayerUtil.getWLPlayerByQQ(Long.parseLong(arg3[1]))==null){
					arg0.sendMessage("������Ч,�޷������QQ["+arg3[1]+"],���QQ��û��û�������������!");
					return true;
				}

				String name = PlayerUtil.getWLPlayerByQQ(Long.parseLong(arg3[1])).name;
				Bukkit.getScheduler().runTask(WhiteListPlugin.instance,()->{
					Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),"ban "+name);
				});
				Bukkit.getScheduler().runTask(WhiteListPlugin.instance,()->{
					Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),"ban-ip "+name);
				});
				try {
					if(Bukkit.getServer().getOnlineMode()) {
						PlayerUtil.removeFromWhiteListAndSave(PlayerUtil.getWLPlayerByQQ(Long.parseLong(arg3[1])).uuid);
					}else{
						PlayerUtil.removeFromWhiteListAndSave(name);
					}
					arg0.sendMessage("�����ɹ�,�ɹ���"+name+"["+arg3[1]+"]�Ƴ�������,���ҷ��!");
				}catch(Throwable e) {e.printStackTrace();arg0.sendMessage("�����쳣����ջ��¼�Ѵ�ӡ������̨");}
				*/

			return true;
		}
		return false;
	}
	
	@Override
	public String getUsage() {
		return "/wl qban <qq>";
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
		return "whitelist.ban";
	}
	@Override
	public String getDescription(){
		return "ͨ��QQ ban���";
	}
}
