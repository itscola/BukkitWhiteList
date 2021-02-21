package top.dsbbs2.whitelist.config.struct;

import java.util.UUID;
import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import top.dsbbs2.whitelist.WhiteListPlugin;
import top.dsbbs2.whitelist.util.ServerUtil;

public class WhiteListConfig {
	public static class WLPlayer{
		public UUID uuid;
		public String name;
		public long QQ;

		public WLPlayer(UUID uuid,String name,long QQ)
		{
			this.uuid=uuid;
			this.name = name;
			this.QQ=QQ;
		}
		public WLPlayer(String name,long QQ)
		{
			this.name = name;
			this.QQ=QQ;
		}



		public Player toPlayer()
		{
			if(Bukkit.getServer().getOnlineMode() || WhiteListPlugin.instance.whitelist.getConfig().forceOnline.equalsIgnoreCase("Online") ) {
				return Bukkit.getPlayer(this.uuid);
			}else{
				return Bukkit.getPlayer(this.name);
			}
		}
		@Deprecated
		public OfflinePlayer toOfflinePlayerOld()
		{
			if(this.uuid==null){
				return Bukkit.getOfflinePlayer(this.name);
			}
			return Bukkit.getOfflinePlayer(this.uuid);
		}
		public OfflinePlayer toOfflinePlayer()
		{
			if(ServerUtil.isOnlineStorageMode()) {
				return Bukkit.getOfflinePlayer(this.uuid);
			}else{
				return Bukkit.getOfflinePlayer(this.name);
			}
		}
		public static WLPlayer fromPlayer(Player p,long QQ)
		{
			return new WLPlayer(p.getUniqueId(),p.getName(),QQ);
		}
		public static WLPlayer fromOfflinePlayer(OfflinePlayer p,long QQ)
		{
			return new WLPlayer(p.getUniqueId(),p.getName(),QQ);
		}
	}
	public String mess = "你还不在白名单中,在此之前你将不能进行任何对此服务器的实质性动作";
	public String PlayerCantJoinMSG = "您目前没有白名单,无法进入,请先申请白名单!";
	public String congratulate = "恭喜您获得白名单!";
	public String unCongratulate = "您失去了白名单!";
	public String on_UUID_Is_Right_But_Name = "检测到您的白名单中的Name错误,请在群里 '@机器人+验证',如果服务器没有对接群,请找管理员输入/wl confirm <您的QQ号>";
	public String on_Name_Is_Right_But_UUID = "检测到您的白名单中的UUID错误,请在群里 '@机器人+验证',如果服务器没有对接群,请找管理员输入/wl confirm <您的QQ号>";
	public String isOnlineServer = Bukkit.getServer().getOnlineMode()==true?"正版服(自动检测,更改无效)":"盗版服(自动检测,更改无效)";
	public String forceOnline = "No";
	public boolean useBlackList = true;
	/*
		No = 不开启强制
		Online = "强制设置模式为online"
		Offline = "强制设置模式为Offline"
	 */
	public boolean uesLoginPluginOrNot = true;
	public boolean useSkinonWLList = false;
	public boolean debugMode = false;
	public boolean canNoWhitePlayerGetIn = true;
	public boolean enableTabIntercept = false;
	public boolean antiNPCBug = true;
	public Vector<WLPlayer> players=new Vector<>();


}
