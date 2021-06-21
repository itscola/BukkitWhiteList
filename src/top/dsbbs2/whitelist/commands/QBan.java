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
				arg0.sendMessage("QQ号必须是一个整数");
				return true;
			}
			if(Long.parseLong(arg3[1])==-1){
				arg0.sendMessage("操作无效,不允许ban-1QQ,您如果在add时没有加QQ号,可以使用wl remove <id>");
				return true;
			}

				if(PlayerUtil.getWLPlayerByQQ(Long.parseLong(arg3[1]))==null){
					arg0.sendMessage("操作无效,无法封禁该QQ["+arg3[1]+"],这个QQ号没有申请过白名单!");
					return true;
				}

				String name = PlayerUtil.getWLPlayerByQQ(Long.parseLong(arg3[1])).name;
				try {
					BanUtil.ban(name,"§c您已被封禁");

					WhiteListPlugin.instance.wblacklist.getConfig().WblackList.add(Long.parseLong(arg3[1]));
					WhiteListPlugin.instance.wblacklist.saveConfig();
					if(ServerUtil.isOnlineStorageMode()) {
						PlayerUtil.removeFromWhiteListAndSave(PlayerUtil.getWLPlayerByQQ(Long.parseLong(arg3[1])).uuid);
					}else{
						PlayerUtil.removeFromWhiteListAndSave(name);
					}
					arg0.sendMessage("操作成功,成功将"+name+"["+arg3[1]+"]移出白名单,并且封禁!");
					arg0.sendMessage("已将"+name+"["+arg3[1]+"]加入白名单申请管制.");
					return true;
				}catch(Throwable e) {e.printStackTrace();arg0.sendMessage("出现异常，堆栈记录已打印至控制台");}





				//
				/*
				OfflinePlayer op=DedicatedMethods.tryGetOfflinePlayerByQQ(Long.parseLong(arg3[1]),arg0);
				if(op==null)
					return true;

				op.banPlayer("你已被封禁,原因:被踢出群或被管理员封禁!");
*/
				 //
				/*
				if(PlayerUtil.getWLPlayerByQQ(Long.parseLong(arg3[1]))==null){
					arg0.sendMessage("操作无效,无法封禁该QQ["+arg3[1]+"],这个QQ号没有没有申请过白名单!");
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
					arg0.sendMessage("操作成功,成功将"+name+"["+arg3[1]+"]移出白名单,并且封禁!");
				}catch(Throwable e) {e.printStackTrace();arg0.sendMessage("出现异常，堆栈记录已打印至控制台");}
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
		return "通过QQ ban玩家";
	}
}
