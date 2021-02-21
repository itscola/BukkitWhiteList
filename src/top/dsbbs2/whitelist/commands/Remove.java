package top.dsbbs2.whitelist.commands;

import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.jetbrains.annotations.NotNull;

import top.dsbbs2.whitelist.WhiteListPlugin;
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
						arg0.sendMessage("在白名单中找不到该玩家["+arg3[1]+"]:"+op.getUniqueId());
						return true;
					}
				}
			}else{
				if (!PlayerUtil.isInWhiteList(arg3[1])) {
					arg0.sendMessage("在白名单中找不到该玩家:"+arg3[1]);
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
				arg0.sendMessage("操作成功,成功将"+arg3[1]+"["+QQ+"]移出白名单!");
				if(PlayerUtil.kickPlayerIfIs(Bukkit.getPlayer(arg3[1]))){
					arg0.sendMessage("由于服务器开启没有白名单无法进入的模式,将踢出玩家"+arg3[1]);
				}
			}catch(Throwable e) {e.printStackTrace();arg0.sendMessage("出现异常，堆栈记录已打印至控制台");}
			return true;
		}
		return false;
	}

	@NotNull
	@Override
	public String getUsage() {
		return "/wl remove <player_name>";
	}

	@NotNull
	@Override
	public Vector<Class<?>> getArgumentsTypes()
	{
		return VectorUtil.toVector(String.class);
	}

	@NotNull
	@Override
	public Vector<String> getArgumentsDescriptions()
	{
		return VectorUtil.toVector("whitelisted_player");
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
		return "通过id移除用户白名单";
	}
}
