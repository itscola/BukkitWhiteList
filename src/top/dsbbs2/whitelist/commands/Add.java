package top.dsbbs2.whitelist.commands;

import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import top.dsbbs2.whitelist.WhiteListPlugin;
import top.dsbbs2.whitelist.util.CommandUtil;
import top.dsbbs2.whitelist.util.MsgUtil;
import top.dsbbs2.whitelist.util.PlayerUtil;
import top.dsbbs2.whitelist.util.VectorUtil;

public class Add implements IChildCommand {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		class InsideMsgUtil {
			public void makeDebugMsgAndSend(String msg) {
				MsgUtil.sendDebugMessage(msg,arg3,arg1.getName(),new Throwable().getStackTrace()[1].toString());
			}
		}
		InsideMsgUtil imu = new InsideMsgUtil();
		if(arg3.length<=3 && arg3.length>=2) {
			imu.makeDebugMsgAndSend("arg3.length<=3 && arg3.length>=2 are true");
			long QQ = -1;
			if (arg3.length >= 2) {
				if(MsgUtil.hasNullString(arg3,arg0,true)){
					return true;
				}
				imu.makeDebugMsgAndSend("arg3.length==3 is true");
				if(arg3.length==3) {
					if (!CommandUtil.ArgumentUtil.isLong(arg3[2])) {
						imu.makeDebugMsgAndSend("!CommandUtil.ArgumentUtil.isLong(arg3[2]) is true");
						arg0.sendMessage("QQ�ű�����һ������");
						return true;
					}

				imu.makeDebugMsgAndSend("!CommandUtil.ArgumentUtil.isLong(arg3[2]) is false");
				QQ = Long.parseLong(arg3[2]);

				if(WhiteListPlugin.instance.isSameList.contains(QQ) || WhiteListPlugin.instance.wblacklist.getConfig().WblackList.contains(QQ)){
					arg0.sendMessage("��Ӱ�����ʧ��,���ѱ���������������������.");
					return true;
				}
				if (PlayerUtil.getWLPlayerByQQ(QQ) != null) {
					imu.makeDebugMsgAndSend("PlayerUtil.getWLPlayerByQQ(QQ)!=null is true");
					arg0.sendMessage("��QQ�Ѱ�");
					return true;
				}
				}
				Boolean mode = null;
				if(WhiteListPlugin.instance.whitelist.con.isOnlineServer != null  && (!CommandUtil.isnull(WhiteListPlugin.instance.whitelist.con.forceOnline) &&WhiteListPlugin.instance.whitelist.con.forceOnline.equalsIgnoreCase("Offline"))){
					//offline
					mode = false;
					imu.makeDebugMsgAndSend("ǿ��ģʽ���� ʹ�õ���ģʽ����");
				}else if(WhiteListPlugin.instance.whitelist.con.isOnlineServer != null  && (!CommandUtil.isnull(WhiteListPlugin.instance.whitelist.con.forceOnline) &&WhiteListPlugin.instance.whitelist.con.forceOnline.equalsIgnoreCase("Online"))){
					//online
					mode = true;
					MsgUtil.makeDebugMsgAndSend("ǿ��ģʽ���� ʹ������ģʽ����");
				}else{
					MsgUtil.makeDebugMsgAndSend("ǿ��ģʽû�п���!!!!!!!!!!!!!!!!!!!!1");
				}

				Boolean boo = false;
				if(mode == null){
					boo = PlayerUtil.checkThenAddtoWhitelist(arg0,arg3[1],QQ,Bukkit.getServer().getOnlineMode());
					MsgUtil.makeDebugMsgAndSend("û�п���ǿ��ģʽ!!! �Զ���ⴢ��");
				}else{
					boo = PlayerUtil.checkThenAddtoWhitelist(arg0,arg3[1],QQ,mode);
					MsgUtil.makeDebugMsgAndSend("������ǿ��ģʽ!!! ");
				}
				if(boo){
					imu.makeDebugMsgAndSend("boo is true");
				}else{
					imu.makeDebugMsgAndSend("boo is false");
				}
				return boo;

			}


		}
		return false;

	}







	@Override
	public String getUsage() {
		return "/wl add <player_name> [QQ]";
	}

	@Override
	public Vector<Class<?>> getArgumentsTypes()
	{
		return VectorUtil.toVector(String.class,long.class);
	}

	@Override
	public Vector<String> getArgumentsDescriptions()
	{
		return VectorUtil.toVector("unwhitelisted_player","qq");
	}

	@Override
	public String getPermission()
	{
		return "whitelist.add";
	}

	@Override
	public String getDescription(){
		return "����û���������";
	}




}
