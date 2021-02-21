package top.dsbbs2.whitelist.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import top.dsbbs2.whitelist.WhiteListPlugin;
import top.dsbbs2.whitelist.config.struct.WhiteListConfig;
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
				arg0.sendMessage("QQ号必须是一个整数");
				return true;
			}else {
				try {
					if(PlayerUtil.getWLPlayerByQQ(Long.parseLong(arg3[1]))==null){
						arg0.sendMessage("操作无效,此QQ"+arg3[1]+"并没有申请白名单!");
						return true;
					}
					if(Long.parseLong(arg3[1])==-1){
						arg0.sendMessage("操作无效,不允许删除-1QQ,您如果在add时没有加QQ号,可以使用wl remove <id>");
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
							p.sendMessage("您失去了白名单!");
						}
					}
					arg0.sendMessage("操作成功,成功将"+name+"["+arg3[1]+"]移出白名单!");
					if(PlayerUtil.kickPlayerIfIs(Bukkit.getPlayer(arg3[1]))){
						arg0.sendMessage("由于服务器开启没有白名单无法进入的模式,将踢出玩家"+arg3[1]);
					}
				}catch(Throwable e) {e.printStackTrace();arg0.sendMessage("出现异常，堆栈记录已打印至控制台");}
			}
			return true;
		}
		return false;
	}
	
	@NotNull
	@Override
	public String getUsage() {
		return "/wl qremove <qq>";
	}
	
	@NotNull
	@Override
	public Vector<Class<?>> getArgumentsTypes()
	{
		return VectorUtil.toVector(long.class);
	}
	
	@NotNull
	@Override
	public Vector<String> getArgumentsDescriptions()
	{
		return VectorUtil.toVector("qq");
	}
	
	@NotNull
	@Override
	public String getPermission()
	{
		return "whitelist.remove";
	}
	@NotNull
	@Override
	public String getDescription(){
		return "通过qq移除白名单";
	}
}
