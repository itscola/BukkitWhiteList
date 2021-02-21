package top.dsbbs2.whitelist.util;

import java.util.UUID;
import java.util.Vector;

import org.bukkit.Bukkit;

import top.dsbbs2.whitelist.commands.IChildCommand;

public class CommandUtil {
	public static Vector<String> commandListToCommandNameList(Vector<IChildCommand> v)
	{
		Vector<String> ret=new Vector<>();
		for(IChildCommand i : v)
		{
			ret.add(i.getClass().getSimpleName().toLowerCase());
		}
		return ret;
	}
	public static IChildCommand getCommand(Vector<IChildCommand> l,String cmd)
	{
		for(IChildCommand i : l)
		{
			if(i.getClass().getSimpleName().equalsIgnoreCase(cmd))
				return i;
		}
		return null;
	}
	public static class ArgumentUtil{
		public static boolean isOnlinePlayerExists(UUID uuid)
		{
			return Bukkit.getPlayer(uuid)!=null;
		}
		public static boolean isOnlinePlayerExists(String name)
		{
			return Bukkit.getPlayer(name)!=null;
		}
		public static boolean isDouble(String s)
		{
			try {
			return !Double.valueOf(s).equals(Double.NaN);
			}catch(Throwable e) {return false;}
		}
		public static boolean isLong(String s)
		{
			try {
			Long.valueOf(s);
			return true;
			}catch(Throwable e) {return false;}
		}
		public static boolean isInteger(String s)
		{
			try {
				Integer.valueOf(s);
				return true;
			}catch(Throwable e) {return false;}
		}
		public static boolean isUUID(String s)
		{
			try {
				UUID.fromString(s);
				return true;
			}catch(Throwable e) {return false;}
		}

	}
	public static boolean isnull(String str){
		if(str!=null&&!str.equals("")){
			MsgUtil.makeDebugMsgAndSend((str!=null)+" 111");
			MsgUtil.makeDebugMsgAndSend(!(str.equals(""))+" 222");
			return false;
		}
		//System.out.println("str!=null is"+str!=null);
		//System.out.println(str.equals(""));
		return true;
	}
}
