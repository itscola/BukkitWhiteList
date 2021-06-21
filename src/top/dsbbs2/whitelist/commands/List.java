package top.dsbbs2.whitelist.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.dsbbs2.whitelist.gui.WhiteListGUI;
import top.dsbbs2.whitelist.util.CommandUtil;
import top.dsbbs2.whitelist.util.MsgUtil;
import top.dsbbs2.whitelist.util.VectorUtil;

import java.util.Vector;

public class List implements IChildCommand {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg3.length>=1 && arg3.length<=2)
		{
			int page=0;
			if(arg3.length==2) {
				if (MsgUtil.hasNullString(arg3, arg0, false)) {
					return true;
				}
				if (!CommandUtil.ArgumentUtil.isInteger(arg3[1])) {
					arg0.sendMessage("页数必须为整数.");
					return true;
				} else page = Integer.valueOf(arg3[1]) - 1;
			}
			if(page<0) {
				arg0.sendMessage("无效的页数");
				return true;
			}
			if(!(arg0 instanceof Player))
			{
				arg0.sendMessage("此命令只能由玩家来执行");
				return true;
			}
			new WhiteListGUI((Player)arg0,page).open();
			return true;
		}
		return false;
	}

	@Override
	public String getUsage() {
		return "/wl list [num]";
	}

	@Override
	public Vector<Class<?>> getArgumentsTypes()
	{
		return VectorUtil.toVector(int.class);
	}

	@Override
	public Vector<String> getArgumentsDescriptions()
	{
		return VectorUtil.toVector();
	}

	@Override
	public String getPermission()
	{
		return "whitelist.list";
	}

	@Override
	public String getDescription(){
		return "可视化查看拥有白名单的玩家的列表";
	}
}
