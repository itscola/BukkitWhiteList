package top.dsbbs2.whitelist;

import io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import top.dsbbs2.whitelist.com.comphenix.tinyprotocol.TinyProtocol;
import top.dsbbs2.whitelist.commands.*;
import top.dsbbs2.whitelist.config.SimpleConfig;
import top.dsbbs2.whitelist.config.struct.BlackList;
import top.dsbbs2.whitelist.config.struct.ExtraConfig;
import top.dsbbs2.whitelist.config.struct.WhiteListConfig;
import top.dsbbs2.whitelist.listeners.PlayerListener;
import top.dsbbs2.whitelist.util.*;

import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class WhiteListPlugin extends JavaPlugin {
	public static HashMap<String,Integer> playerInteract = new HashMap<>();
	public static Vector<String> blackUUIDList = new Vector<>();
	public static Vector<Long> isSameList = new Vector<>();
	// /\ blackList
	public static boolean isSameMode = true;
	public static volatile WhiteListPlugin instance=null;
	public volatile SimpleConfig<WhiteListConfig> whitelist=new SimpleConfig<>(this.getDataFolder()+"/whitelist.json","UTF8",WhiteListConfig.class);
    public volatile SimpleConfig<ExtraConfig> extraConfig =new SimpleConfig<>(this.getDataFolder()+"/ExtraConfig.json","UTF8",ExtraConfig.class);
	public volatile SimpleConfig<BlackList> wblacklist =new SimpleConfig<>(this.getDataFolder()+"/WblackList.json","UTF8",BlackList.class);
	public volatile Vector<IChildCommand> childCmds=new Vector<>();
	public volatile TinyProtocol protocol;
	public void registerListeners()
	{
		registerListener(new PlayerListener());
	}
	public void registerListener(Listener lis)
	{
		Bukkit.getPluginManager().registerEvents(lis, this);
	}
	public static ConcurrentHashMap<Long,String> CNCU = new ConcurrentHashMap<>();//confirm name,to change uuid. UUID错误问题
	public static ConcurrentHashMap<Long,UUID> CUCN = new ConcurrentHashMap<>();    //confirm uuid,to change name. 玩家改名
	public static boolean debugMode = false;
    public static boolean forceLowerMode = false;


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length>0)
		{
			IChildCommand c=CommandUtil.getCommand(this.childCmds,args[0]);
			if(c==null)
			{

				sender.sendMessage("命令 "+args[0]+" 不存在");
			}else {
				if(!c.getPermission().trim().equals("") && !sender.hasPermission(c.getPermission()))
				{
					sender.sendMessage("你没有权限这么做,你需要"+c.getPermission()+"权限!");
					return true;
				}
				if(!c.onCommand(sender, command, label, args))
				{
					String usage=c.getUsage();
					if(!usage.trim().equals(""))
						sender.sendMessage(usage);
				}
			}
			return true;
		}
		return super.onCommand(sender, command, label, args);
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(args.length>0)
			return TabUtil.betterGetStartsWithList(realOnTabComplete(sender,command,alias,args),args[args.length-1]);
		else
			return realOnTabComplete(sender,command,alias,args);
	}
	public List<String> realOnTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(args.length<=1)
			return VectorUtil.toArrayList(CommandUtil.commandListToCommandNameList(childCmds));
		if(args.length>1)
		{
			IChildCommand c=CommandUtil.getCommand(childCmds, args[0]);
			if(c!=null && sender.hasPermission(c.getPermission()))
			{
				Vector<Class<?>> cats=c.getArgumentsTypes();
				if(cats.size()>args.length-2)
				{
					Class<?> argType=cats.get(args.length-2);
					Vector<String> des=c.getArgumentsDescriptions();
					String desc=null;
					if(des.size()>args.length-2)
						desc=des.get(args.length-2);
					if(desc==null)
					{
						return ListUtil.toList(argType.getSimpleName());
					}else if(desc.equals("player"))
					{
						return PlayerUtil.getOfflinePlayersNameList();
					}else if(desc.equals("unwhitelisted_player"))
					{
						return PlayerUtil.getUnwhitelistedOfflinePlayersNameList();
					}else if(desc.equals("whitelisted_player")){
						return PlayerUtil.whiteListPlayerListToNameList(this.whitelist.con.players);
					}else if(desc.equals("qq")){
						return VectorUtil.toArrayList(VectorUtil.toStringVector(PlayerUtil.getQQList()));
					}else if(desc.equals("noname_player")){
						return VectorUtil.toArrayList(PlayerUtil.getNoNameWhiteListPlayerUUIDString());
					}else if(desc.contains("/")){
						return ListUtil.toList(desc.split("/"));
					}else if(desc.contains("wblacklist")){
						return WhiteListPlugin.instance.wblacklist.getConfig().WblackList.stream().map(i->i+"").collect(Collectors.toList());
					}
					else{
						return ListUtil.toList(desc);
					}
				}
			}
		}
		return new ArrayList<>();
	}
	public void initChildCommands()
	{
		addChildCmd(new Add());
		addChildCmd(new Remove());
		addChildCmd(new QRemove());
		addChildCmd(new QBan());
		addChildCmd(new top.dsbbs2.whitelist.commands.List());
		addChildCmd(new NoNameRemove());
		addChildCmd(new Reload());
		addChildCmd(new Import());
		addChildCmd(new Get());
		addChildCmd(new Confirm());
		addChildCmd(new Debug());
		addChildCmd(new Help());
		addChildCmd(new UnLimit());
	}
	public void addChildCmd(IChildCommand c)
	{
		this.childCmds.add(c);
	}
	@Override
	public void onLoad()
	{
		instance=this;
		try {
			whitelist.loadConfig();
			extraConfig.loadConfig();
			wblacklist.loadConfig();
            PluginUtil.checkConfigFileAndRecover();


		}catch(Throwable e) {throw new RuntimeException(e);}
		initChildCommands();
//		try{
//			AsyncCatcher.enabled = false;
//		}catch (Throwable e){e.printStackTrace();}

	}
	@Override
	public void onEnable()
	{
		registerListeners();
		if(this.whitelist.con.enableTabIntercept) {
			try {
				protocol = new TinyProtocol(this) {
					@Override
					public Object onPacketInAsync(Player sender, Channel channel, Object packet) {
						if (WhiteListPlugin.instance != null && WhiteListPlugin.instance.isEnabled() && packet.getClass().getSimpleName().equals("PacketPlayInTabComplete")) {
							try {
								if (!PlayerUtil.isInWhiteList(sender)) {
									PlayerUtil.setInv(sender, true);
									return null;
								}
							} catch (Throwable e2) {
								e2.printStackTrace();
								return null;
							}
						}

						return super.onPacketInAsync(sender, channel, packet);
					}

					@Override
					public Object onPacketOutAsync(Player reciever, Channel channel, Object packet) {
						if (WhiteListPlugin.instance != null && WhiteListPlugin.instance.isEnabled() && packet.getClass().getSimpleName().equals("PacketPlayOutTabComplete")) {
							try {
								if (!PlayerUtil.isInWhiteList(reciever)) {
									PlayerUtil.setInv(reciever, true);
									return null;
								}
							} catch (Throwable e2) {
								e2.printStackTrace();
								return null;
							}
						}

						return super.onPacketOutAsync(reciever, channel, packet);
					}
				};
			} catch (Throwable e) {
				getLogger().warning("警告,由于您的服务端过新,TinyProtocol暂时不支持新版,所以您将无法开启无白名单玩家的tab拦截功能!");
				getLogger().warning("对其他功能没有任何影响,不影响正常使用(就算不拦截tab 没白名单的玩家也无法发送个命令,除了兼容登录插件的功能[可关闭])");
			}
		}
		MsgUtil.makeDebugMsgAndSend("开始比较模式...");
		try {
			compareOnlineMode();
		}catch (Throwable e){
			MsgUtil.makeDebugMsgAndSend("无法检测正盗版 报错内容已打印 ");
			e.printStackTrace();
		}
		ServerUtil.updateJson();

		updateOnlineModeinWhiteListJson();
		if(ServerUtil.isOnlineStorageMode()) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				PlayerUtil.checkUUIDAndName(player);
			}
			System.out.println("§a[WL]已检测全部玩家的UUID和Name!");
		}
		if(MsgUtil.getDebugMode()){
			MsgUtil.makeDebugMsgAndSend("[BukkitWhitelist]已开启debug模式!");
		}
		PlayerUtil.updateSameList();
//		PlayerUtil.updateBlackUUID();
		System.out.println(PluginUtil.getPluginVersion());
	}


	public static void updateOnlineModeinWhiteListJson(){

		if(!isSameMode){
			MsgUtil.makeDebugMsgAndSend("isSameMode 为 false");
			if (ServerUtil.isOnlineStorageMode()) {
				System.out.println("[whitelist]检测到服务器被从盗版服更改到正版服!");
				System.out.println("[whitelist]开始转换whitelist.json内容!");
				new Thread(()->{

					convertOnlineOrOfflineJsonMode(true);
				}).start();

			} else {
				MsgUtil.makeDebugMsgAndSend("从正版转换为盗版");
				MsgUtil.makeDebugMsgAndSend("Actual: "+Bukkit.getServer().getOnlineMode()+"");
				MsgUtil.makeDebugMsgAndSend((!CommandUtil.isnull(WhiteListPlugin.instance.whitelist.con.forceOnline)&&WhiteListPlugin.instance.whitelist.con.forceOnline.equalsIgnoreCase("Online"))+"");
				MsgUtil.makeDebugMsgAndSend(!CommandUtil.isnull(WhiteListPlugin.instance.whitelist.con.forceOnline)+"");
				MsgUtil.makeDebugMsgAndSend(WhiteListPlugin.instance.whitelist.con.forceOnline.equalsIgnoreCase("Online")+"");
				System.out.println("[whitelist]检测到服务器被从正版服更改到盗版服!");
				System.out.println("[whitelist]开始转换whitelist.json内容!");
				new Thread(()->{

					convertOnlineOrOfflineJsonMode(false);
				}).start();
			}

		}
	}

	public static void convertOnlineOrOfflineJsonMode(boolean isOnline){
		//ture is online
		//false is offline
		long s = System.currentTimeMillis();
		int index = WhiteListPlugin.instance.whitelist.con.players.size();
		for(int i=0;i<index;i++){
			WhiteListConfig.WLPlayer wlp = WhiteListPlugin.instance.whitelist.con.players.get(0);
			//||((!CommandUtil.isnull(WhiteListPlugin.instance.whitelist.con.forceOnline)))&&WhiteListPlugin.instance.whitelist.con.forceOnline.equalsIgnoreCase("")
			if(!isOnline ) {
				WhiteListPlugin.instance.whitelist.con.players.add(new WhiteListConfig.WLPlayer(wlp.name, wlp.QQ));
				System.out.println("§a§l[" + wlp.name + "][Online] -> [" + wlp.name + "][Offline]");
			}else{
				OfflinePlayer op = null;
				try {
					op = Bukkit.getOfflinePlayer(MojangUtil.getUUIDFromMojang(wlp.name));
				} catch (Throwable e) {
					System.out.println("通过Mojang服务器获取UUID操作失败,将开始通过本地获取!");
					op = Bukkit.getOfflinePlayer(wlp.name);
				}
				WhiteListPlugin.instance.whitelist.con.players.add(new WhiteListConfig.WLPlayer(op.getUniqueId(),wlp.name, wlp.QQ));
				System.out.println("§a§l[" + wlp.name + "][Offline] -> [" + wlp.name + "][Online]");
			}
			WhiteListPlugin.instance.whitelist.con.players.remove(0);
		}
		if(!isOnline) {
			WhiteListPlugin.instance.whitelist.con.isOnlineServer = "盗版服(自动检测,更改无效)";
		}else{
			WhiteListPlugin.instance.whitelist.con.isOnlineServer = "正版服(自动检测,更改无效)";
		}
		try {
			WhiteListPlugin.instance.whitelist.saveConfig();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("出现异常，堆栈记录已打印至控制台");
		}
		long s2 = System.currentTimeMillis();
		System.out.println("转换完成,耗时"+(s2-s)+"毫秒!");
	}

	public static void compareOnlineMode(){
		if(!(!(CommandUtil.isnull(WhiteListPlugin.instance.whitelist.con.forceOnline)) || WhiteListPlugin.instance.whitelist.con.forceOnline.equalsIgnoreCase("No"))) {
			MsgUtil.makeDebugMsgAndSend("强制模式没有设置");
			//MsgUtil.makeDebugMsgAndSend("!CommandUtil.isnull(WhiteListPlugin.instance.whitelist.con.forceOnline)=" + !CommandUtil.isnull(WhiteListPlugin.instance.whitelist.con.forceOnline));
			//MsgUtil.makeDebugMsgAndSend("!(CommandUtil.isnull(WhiteListPlugin.instance.whitelist.con.forceOnline)) || WhiteListPlugin.instance.whitelist.con.forceOnline.equalsIgnoreCase(No)= "+(!(CommandUtil.isnull(WhiteListPlugin.instance.whitelist.con.forceOnline)) || WhiteListPlugin.instance.whitelist.con.forceOnline.equalsIgnoreCase("No")));

			if (WhiteListPlugin.instance.whitelist.con.isOnlineServer != null && WhiteListPlugin.instance.whitelist.con.isOnlineServer.equals("正版服(自动检测,更改无效)")) {

				if (Bukkit.getServer().getOnlineMode()) {
					isSameMode = true;
					return;
				} else {
					isSameMode = false;
					return;
				}
			} else if (WhiteListPlugin.instance.whitelist.con.isOnlineServer != null && WhiteListPlugin.instance.whitelist.con.isOnlineServer.equals("盗版服(自动检测,更改无效)") ) {

				if (!Bukkit.getServer().getOnlineMode()) {
					isSameMode = true;
					return;
				} else {
					isSameMode = false;
					return;
				}
			}
		}else{
			MsgUtil.makeDebugMsgAndSend("强制模式已经设置");
			if(WhiteListPlugin.instance.whitelist.con.isOnlineServer != null && WhiteListPlugin.instance.whitelist.con.isOnlineServer.equals("正版服(自动检测,更改无效)")  && (!CommandUtil.isnull(WhiteListPlugin.instance.whitelist.con.forceOnline) &&WhiteListPlugin.instance.whitelist.con.forceOnline.equalsIgnoreCase("Offline"))){
				MsgUtil.makeDebugMsgAndSend("强制模式设置为了盗版 但是服务器之前是正版 isSamemode 设置为 false");
				isSameMode = false;
				return;
			}
			if(WhiteListPlugin.instance.whitelist.con.isOnlineServer != null && WhiteListPlugin.instance.whitelist.con.isOnlineServer.equals("盗版服(自动检测,更改无效)")  && (!CommandUtil.isnull(WhiteListPlugin.instance.whitelist.con.forceOnline) &&WhiteListPlugin.instance.whitelist.con.forceOnline.equalsIgnoreCase("Online"))){
				MsgUtil.makeDebugMsgAndSend("强制模式设置为了正版 但是服务器之前是盗版 isSamemode 设置为 false");
				isSameMode = false;
				return;
			}
			MsgUtil.makeDebugMsgAndSend((WhiteListPlugin.instance.whitelist.con.isOnlineServer !=null)+"");
			MsgUtil.makeDebugMsgAndSend(WhiteListPlugin.instance.whitelist.con.isOnlineServer.equals("盗版服(自动检测,更改无效)")+"");
			MsgUtil.makeDebugMsgAndSend(!CommandUtil.isnull(WhiteListPlugin.instance.whitelist.con.forceOnline)+"");
			MsgUtil.makeDebugMsgAndSend(WhiteListPlugin.instance.whitelist.con.forceOnline.equalsIgnoreCase("Online")+"");
			MsgUtil.makeDebugMsgAndSend("isSamemode 设置为 true");
			isSameMode = true;
			return;
		}
	}



	@Override
	public void onDisable()
	{
		instance=null;
		if(protocol!=null)
			protocol.close();
		try {
			//closeServerSocket();
		} catch (Throwable e) {
			//System.out.println("尝试关闭服务端Socket时,出现错误:"+e.getStackTrace().toString()+",开始再次尝试!");
			e.printStackTrace();
			onDisable();
		}

	}
	/*
	public static void closeServerSocket() throws Throwable{
		String CraftBukkitPackage=Bukkit.getServer().getClass().getPackage().getName();
		String NMSPackage=CraftBukkitPackage.replace("org.bukkit.craftbukkit", "net.minecraft.server");
		Class<?> cls = Class.forName(NMSPackage+".RemoteControlSession");
		Constructor<?> con = cls.getConstructor();
//		Field serverSocket = cls.getDeclaredField("j");
//		serverSocket.setAccessible(true);
//		serverSocket = null;
		Method meth = cls.getDeclaredMethod("g");
		meth.setAccessible(true);
		//meth.invoke(cls.newInstance(new IMinecraftServer(),"",new Socket(127.0.0.1)));
		System.out.println("已关闭ServerSocket的值!(BukkitWhitelist针对1.14.4的bug修复功能)");	}
*/
}
