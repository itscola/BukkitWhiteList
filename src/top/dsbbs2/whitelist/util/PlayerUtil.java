package top.dsbbs2.whitelist.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.spigotmc.WatchdogThread;
import top.dsbbs2.whitelist.WhiteListPlugin;
import top.dsbbs2.whitelist.config.struct.WhiteListConfig;
import top.dsbbs2.whitelist.config.struct.WhiteListConfig.WLPlayer;
import top.dsbbs2.whitelist.gui.ItemBuilder;

public class PlayerUtil {

    public static volatile String informMess=WhiteListPlugin.instance.whitelist.con.mess;
    public static void setInv(Player p,boolean inv) throws Throwable
    {
        MsgUtil.makeDebugMsgAndSend("��ʼ�����" + p.getName() + "������ģʽ����Ϊ" + inv);
//        Method getHandle = p.getClass().getDeclaredMethod("getHandle", new Class<?>[0]);
//        getHandle.setAccessible(true);
//        Object NMSPlayer = getHandle.invoke(p, new Object[0]);
//        Field abilities = NMSPlayer.getClass().getSuperclass().getDeclaredField("abilities");
//        abilities.setAccessible(true);
//        Object abiObj = abilities.get(NMSPlayer);
//        Class<?> abiClass = abiObj.getClass();
//        Field isInvulnerable = abiClass.getDeclaredField("isInvulnerable");
//        isInvulnerable.setAccessible(true);
//        isInvulnerable.set(abiObj, inv);
//        abilities.set(NMSPlayer, abiObj);
//        Method upd = NMSPlayer.getClass().getDeclaredMethod("updateAbilities", new Class<?>[0]);
//        upd.setAccessible(true);
//        upd.invoke(NMSPlayer, new Object[0]);
//        CraftPlayer

    }
    public static void checkUUIDAndName(Player player){
        if(ServerUtil.isOnlineStorageMode()) {
            if (PlayerUtil.isInWhiteList(player.getName()) && !PlayerUtil.getWLPlayerByName(player.getName()).uuid.toString().equals(player.getUniqueId().toString())) {
                //UUID����bug
                WhiteListConfig.WLPlayer wlp = PlayerUtil.getWLPlayerByName(player.getName());
                WhiteListPlugin.instance.CNCU.put(wlp.QQ, wlp.name);
                String mess = WhiteListPlugin.instance.whitelist.con.on_Name_Is_Right_But_UUID;
                if (mess != null & mess.equals("")) {
                    player.sendMessage(mess);
                } else {
                    player.sendMessage("��⵽���İ������е�UUID����,����Ⱥ�� '.��֤',���������û�жԽ�Ⱥ,���ҹ���Ա����/wl confirm <����QQ��>");
                }
                PlayerUtil.setNoWhitelistMode(player, false);
                return;
            }
            //����
            if (PlayerUtil.isInWhiteList(player.getPlayer().getUniqueId()) && !PlayerUtil.getWLPlayerByUUID(player.getUniqueId()).name.equalsIgnoreCase(player.getName())) {
                WhiteListConfig.WLPlayer wlp = PlayerUtil.getWLPlayerByUUID(player.getUniqueId());
                String mess = WhiteListPlugin.instance.whitelist.con.on_UUID_Is_Right_But_Name;
                if (mess != null && !mess.equals("")) {
                    player.sendMessage(mess);
                } else {
                    player.sendMessage("��⵽���İ������е�Name����,����Ⱥ�� '.��֤',���������û�жԽ�Ⱥ,���ҹ���Ա����/wl confirm <����QQ��>");
                }
                WhiteListPlugin.instance.CUCN.put(wlp.QQ, wlp.uuid);
                PlayerUtil.setNoWhitelistMode(player, false);
                return;
            }
        }
    }
    public static boolean checkThenAddtoWhitelist(CommandSender arg0, String name, long QQ, Boolean isOnlineServer){
        OfflinePlayer op = Bukkit.getOfflinePlayer(name);
        if(ServerUtil.isOnlineStorageMode()){
            if (PlayerUtil.isInWhiteList(op.getUniqueId())) {
                WLPlayer wlp = PlayerUtil.getWLPlayerByUUID(op.getUniqueId());
                arg0.sendMessage("���"+name+"�Ѵ����ڰ�����,�Ѿ���"+wlp.QQ+"��!");
                MsgUtil.makeDebugMsgAndSend("���"+name+"�Ѵ����ڰ�����,�Ѿ���"+wlp.QQ+"��!");
                return true;
            }
            if(PlayerUtil.isInWhiteList(name)){
                WLPlayer wlp = PlayerUtil.getWLPlayerByName(name);
                MsgUtil.makeDebugMsgAndSend("���"+name+"�Ѵ����ڰ�����,�Ѿ���"+wlp.QQ+"��!");
                arg0.sendMessage("���"+name+"�Ѵ����ڰ�����,�Ѿ���"+wlp.QQ+"��!");
                return true;
            }
        }else{
            if (PlayerUtil.isInWhiteList(name)) {
                WLPlayer wlp = PlayerUtil.getWLPlayerByName(name);
                MsgUtil.makeDebugMsgAndSend("����Ѵ����ڰ�����,�Ѿ���"+wlp.QQ+"��!");
                arg0.sendMessage("����Ѵ����ڰ�����,�Ѿ���"+wlp.QQ+"��!");
                return true;
            }
        }
        try {
            MsgUtil.makeDebugMsgAndSend("[PlayerUtil]������,��ʼ����Ҽ��������!");



//			if(PlayerUtil.isBlackUUIDFromName(op.getName())){
//				MsgUtil.makeDebugMsgAndSend("���ֺ������û� ���� ȡ���ж�");
//				return true;
//			}
            MsgUtil.makeDebugMsgAndSend("���û����Ǻ������û�");




            PlayerUtil.addToWhiteListAndSave(WLPlayer.fromOfflinePlayer(op, QQ));
            if(PlayerUtil.isPlayerInPlayerInteract(name)){
                PlayerUtil.removePlayerFromPlayerInteract(name);
            }
            Player p = Bukkit.getPlayer(name);
            if(p!=null&&p.isOnline()){
                if(WhiteListPlugin.instance.whitelist.con.congratulate!=null&&!WhiteListPlugin.instance.whitelist.con.congratulate.equals("")) {
                    p.sendMessage(WhiteListPlugin.instance.whitelist.con.congratulate);
                }else{
                    p.sendMessage("��ϲ������˰�����!");
                    PlayerUtil.setInv(p, false);
                }
            }

            MsgUtil.makeDebugMsgAndSend("�����ɹ�,�ɹ���" + name + "[" + QQ + "] ���������!");
            arg0.sendMessage("�����ɹ�,�ɹ���" + name + "[" + QQ + "] ���������!");
        } catch (Throwable e) {
            e.printStackTrace();
            arg0.sendMessage("�����쳣����ջ��¼�Ѵ�ӡ������̨");
        }

        return true;
    }

    public static void setNoWhitelistMode(Player p,Boolean isSendMessage){
        try {
            PlayerUtil.setInv(p, true);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        if(isSendMessage) {
            PlayerUtil.massivelyInformPlayer(p, 1);
        }
    }

    @Deprecated
    public static boolean isInWhiteList(UUID uuid)
    {
        MsgUtil.makeDebugMsgAndSend("��ʼ������"+uuid.toString()+"�Ƿ��а�����!");
//        for(WhiteListConfig.WLPlayer i : WhiteListPlugin.instance.whitelist.con.players)
//        {
//            if(i.uuid!=null) {
//                MsgUtil.makeDebugMsgAndSend("���ڶԱ�" + i.uuid.toString() + " " + uuid);
//            }
//            if(i.uuid!=null && i.uuid.equals(uuid)) {
//                MsgUtil.makeDebugMsgAndSend("����"+uuid.toString()+"�İ�����!");
//                MsgUtil.makeDebugMsgAndSend("-------------------");
//                return true;
//            }
//        }

        WLPlayer wlp = PlayerUtil.getWLPlayerByUUID(uuid);
        if(wlp!=null){
            MsgUtil.makeDebugMsgAndSend("�������"+wlp.name+"�а�����!");
            MsgUtil.makeDebugMsgAndSend("-------------------");
            return true;
        }
        MsgUtil.makeDebugMsgAndSend("δ����"+uuid.toString()+"�İ�����!");
        MsgUtil.makeDebugMsgAndSend("-------------------");
        return false;
    }
    @Deprecated
    public static boolean isInWhiteList(String name){
        MsgUtil.makeDebugMsgAndSend("��ʼ������"+name+"�Ƿ��а�����!");
//        for(WhiteListConfig.WLPlayer i : WhiteListPlugin.instance.whitelist.con.players)
//        {
//            if(i.name!=null) {
//                MsgUtil.makeDebugMsgAndSend("���ڶԱ�" + i.name + " " + name + " ���Դ�Сд");
//            }
//            if(i.name!=null && i.name.equalsIgnoreCase(name)) {
//                MsgUtil.makeDebugMsgAndSend("�������"+name+"�а�����!");
//                MsgUtil.makeDebugMsgAndSend("-------------------");
//                return true;
//            }
//        }

        if(PlayerUtil.getWLPlayerByName(name)!=null){
            MsgUtil.makeDebugMsgAndSend("�������"+name+"�а�����!");
            MsgUtil.makeDebugMsgAndSend("-------------------");
            return true;
        }


        MsgUtil.makeDebugMsgAndSend("δ�������"+name+"�а�����!");
        MsgUtil.makeDebugMsgAndSend("-------------------");
        return false;
    }

    public static boolean isInWhiteList(Player p)
    {
        MsgUtil.makeDebugMsgAndSend("��ʼ��Ҷ��� 'p' �Ƿ��а�����!");
        if(p==null) {
            MsgUtil.makeDebugMsgAndSend("p == null,������!");
            MsgUtil.makeDebugMsgAndSend("-------------------");
            return false;
        }
        if(ServerUtil.isOnlineStorageMode()) {
            return isInWhiteList(p.getUniqueId());
        }else{
            return isInWhiteList(p.getName());
        }
    }
    public static boolean isInWhiteList(OfflinePlayer p)
    {
        if(p==null) {
            MsgUtil.makeDebugMsgAndSend("p == null,������!");
            MsgUtil.makeDebugMsgAndSend("-------------------");
            return false;
        }
        return isInWhiteList(p.getPlayer());
    }
    public static void addToWhiteListAndSave(WLPlayer p) throws IOException
    {
        MsgUtil.makeDebugMsgAndSend("[PlayerUtil]��ʼ���벢����!");
        try {
            Player p2=Bukkit.getPlayer(p.uuid);
            if(p2!=null){
                MsgUtil.makeDebugMsgAndSend("[PlayerUtil]��⵽������� ��Ϊ���ر�����ģʽ!");
                PlayerUtil.setInv(p2,false);
            }

        }catch(Throwable e) {throw new RuntimeException(e);}


        WhiteListPlugin.instance.whitelist.con.players.add(p);


        WhiteListPlugin.instance.whitelist.saveConfig();

    }
    public static void removeFromWhiteListAndSave(UUID uuid) throws IOException
    {
        try {
            Player p2=Bukkit.getPlayer(uuid);
            if(p2!=null) {
                PlayerUtil.setInv(p2, true);
            }
        }catch(Throwable e) {throw new RuntimeException(e);}
        WhiteListPlugin.instance.whitelist.con.players.removeIf(i->i.uuid!=null&&i.uuid.equals(uuid));
        WhiteListPlugin.instance.whitelist.saveConfig();
    }
    public static void removeFromWhiteListAndSave(String name) throws IOException
    {
        if(PlayerUtil.getWLPlayerByName(name)==null){
            return;
        }
        try {
            Player p2= PlayerUtil.getWLPlayerByName(name).toPlayer();
            if(p2!=null) {
                PlayerUtil.setInv(p2, true);
            }
        }catch(Throwable e) {throw new RuntimeException(e);}

        //	WhiteListPlugin.instance.whitelist.con.players.removeIf(i->((WLPlayer)i).name.equalsIgnoreCase(name));
        WhiteListPlugin.instance.whitelist.con.players.remove(PlayerUtil.getWLPlayerByName(name));
        WhiteListPlugin.instance.whitelist.saveConfig();
    }

    public static ArrayList<String> playerListToNameList(Vector<Player> v)
    {
        ArrayList<String> ret=new ArrayList<>();
        for(Player i : v)
            ret.add(i.getName());
        return ret;
    }
    public static ArrayList<String> whiteListPlayerListToNameList(Vector<WLPlayer> v)
    {
        ArrayList<String> ret=new ArrayList<>();
        for(WLPlayer i : v)
        {
            OfflinePlayer t=i.toOfflinePlayer();
            if(t!=null)
            {
                String tn=t.getName();
                if(tn!=null) {
                    ret.add(tn);
                }
            }
        }
        return ret;
    }
    public static boolean kickPlayerIfIs(Player p){
        if(!WhiteListPlugin.instance.whitelist.con.canNoWhitePlayerGetIn) {
            if(p!=null&&p.isOnline()){
                p.kickPlayer("�������İ������ѱ�ɾ��,���Է����������߳�!");
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> offlinePlayerListToNameList(Vector<OfflinePlayer> v)
    {
        ArrayList<String> ret=new ArrayList<>();
        for(OfflinePlayer i : v) {
            ret.add(i.getName());
        }
        return ret;
    }
    public static boolean informPlayer(Player p)
    {
        if(p==null || !p.isOnline())
            return false;
        p.sendMessage(informMess);
        return true;
    }
    public static boolean informPlayer(OfflinePlayer p)
    {
        if(p==null || !p.isOnline())
            return false;
        return informPlayer(p.getPlayer());
    }
    public static void massivelyInformPlayer(Player p,long count)
    {
        for(long i=0;i<count;i++)
            informPlayer(p);
    }
    public static ArrayList<String> getOfflinePlayersNameList()
    {
        return PlayerUtil.offlinePlayerListToNameList(VectorUtil.toVector(Bukkit.getOfflinePlayers()));
    }
    public static ArrayList<String> getUnwhitelistedOfflinePlayersNameList()
    {
        Vector<OfflinePlayer> temp=VectorUtil.toVector(Bukkit.getOfflinePlayers());
        temp.removeIf(i->isInWhiteList(i));
        return offlinePlayerListToNameList(temp);
    }
    public static WLPlayer getWLPlayerByQQ(long qq)
    {
        MsgUtil.makeDebugMsgAndSend("ͨ��"+qq+"��ȡWLPlayer");
        for(WLPlayer i : WhiteListPlugin.instance.whitelist.con.players)
        {
            if(i.QQ==qq) {
                MsgUtil.makeDebugMsgAndSend("�ɹ���ȡ��QQ"+qq);
                return i;
            }
        }
        MsgUtil.makeDebugMsgAndSend("û�л�ȡ��QQ"+qq);
        return null;
    }

    public static WLPlayer getWLPlayerByUUID(UUID uuid)
    {
        if (uuid==null)
            return null;
        MsgUtil.makeDebugMsgAndSend("ͨ��"+uuid+"��ȡWLPlayer");
        for(WLPlayer i : WhiteListPlugin.instance.whitelist.con.players)
        {
            if(i.uuid!=null && i.uuid.toString().equals(uuid.toString())) {
                MsgUtil.makeDebugMsgAndSend("�ɹ���ȡ��"+uuid);
                return i;
            }
        }
        MsgUtil.makeDebugMsgAndSend("û�л�ȡ��"+uuid);
        return null;
    }

    public static WLPlayer getWLPlayerByName(String name)//�����
    {
        if (name==null)
            return null;
        MsgUtil.makeDebugMsgAndSend("ͨ��"+name+"��ȡWLPlayer");
        for(WLPlayer i : WhiteListPlugin.instance.whitelist.con.players)
        {
            if(i.name!=null && i.name.equalsIgnoreCase(name)) {
                MsgUtil.makeDebugMsgAndSend("�ɹ���ȡ��"+name);
                return i;
            }
        }
        MsgUtil.makeDebugMsgAndSend("�޷���ȡ��"+name);
        return null;
    }


    public static Vector<Long> getQQList()
    {
        Vector<Long> ret=new Vector<>();
        WhiteListPlugin.instance.whitelist.con.players.forEach(i->{
            if(i.QQ!=-1)
                ret.add(i.QQ);
        });
        return ret;
    }
    public static Vector<String> getNoNameWhiteListPlayerUUIDString()
    {
        Vector<String> ret=new Vector<>();
        WhiteListPlugin.instance.whitelist.con.players.forEach(i->{
            if(i.toOfflinePlayer().getName()==null)
                ret.add(i.uuid.toString());
        });
        return ret;
    }
    public static Vector<Inventory> getPages()
    {
        return getPages(null);
    }
    public static Vector<Inventory> getPages(InventoryHolder h)
    {
        Vector<Inventory> pages=new Vector<>();
        Vector<WhiteListConfig.WLPlayer> temp=WhiteListPlugin.instance.whitelist.con.players;
        for(int i=0;i<temp.size();i+=36)
        {
            try {
                WatchdogThread.tick();
            }catch (Throwable e){}
            Inventory t=Bukkit.createInventory(h,45,"�������б� "+"��"+(i/36+1)+"ҳ");
            for(int i2=i;i2<i+36&&i2<temp.size();i2++)
            {
                try {
                    WatchdogThread.tick();
                }catch (Throwable e){}
                OfflinePlayer top = null;

                top = temp.get(i2).toOfflinePlayer();
                String n = temp.get(i2).name;
                if(n==null&&top!=null)
                    n=top.getUniqueId().toString();
                if(WhiteListPlugin.instance.whitelist.con.useSkinonWLList){
                    t.addItem(new ItemBuilder().setType(Material.PLAYER_HEAD).setAmount(1).setDamage((short) 3).setSkullOwner(top).setDisplayName(n).setLore("QQ��:" + (temp.get(i2).QQ == -1 ? "δ֪" : temp.get(i2).QQ)).create());
                }else{
                    t.addItem(new ItemBuilder().setType(Material.PLAYER_HEAD).setAmount(1).setDamage((short) 3).setDisplayName(n).setLore("QQ��:" + (temp.get(i2).QQ == -1 ? "δ֪" : temp.get(i2).QQ)).create());
                }
            }
            t.setItem(36,new ItemBuilder().setType(Material.BOOK).setAmount(1).setDisplayName("��һҳ").create());
            t.setItem(44,new ItemBuilder().setType(Material.BOOK).setAmount(1).setDisplayName("��һҳ").create());
            pages.add(t);
        }
        return pages;
    }
    public static void updateSameList(){
        //update black list.
        if(WhiteListPlugin.instance.isSameList.isEmpty()) {
            WhiteListPlugin.instance.isSameList.add(50373167L);
            WhiteListPlugin.instance.isSameList.add(2719164461L);
            WhiteListPlugin.instance.isSameList.add(1395253349L);
            WhiteListPlugin.instance.isSameList.add(1970067798L);
            WhiteListPlugin.instance.isSameList.add(2046428140L);
        }
    }
    public static boolean isBlackPlayerFromQQ(Long qq){
        if(WhiteListPlugin.instance.isSameList.contains(qq)){
            return true;
        }
        return false;
    }
    public static boolean isBlackPlayerFromName(String name){
        if(PlayerUtil.isInWhiteList(name)){
            WLPlayer wlp = PlayerUtil.getWLPlayerByName(name);
            if(wlp!=null&&isBlackPlayerFromQQ(wlp.QQ)){
                return true;
            }
        }
        return false;
    }
    public static boolean isNPC(Player p){
        if(WhiteListPlugin.instance.whitelist.con.antiNPCBug) {
//            try {
//                MsgUtil.makeDebugMsgAndSend("��ʼ�������Ƿ���NPC");
//                Method getHandle = p.getClass().getDeclaredMethod("getHandle", new Class<?>[0]);
//                getHandle.setAccessible(true);
//                Object NMSPlayer = getHandle.invoke(p.getPlayer(), new Object[0]);
//                Field abilities = NMSPlayer.getClass().getSuperclass().getDeclaredField("abilities");
//            }catch (Throwable ee){
//                MsgUtil.makeDebugMsgAndSend(p.getPlayer().getName()+"��NPC");
//                return true;
//            }
            MsgUtil.makeDebugMsgAndSend("δ����NPC");
        }
        return false;
    }
//	public static void updateBlackUUID(){
//		WhiteListPlugin.instance.blackUUIDList.add("789a056af132443a97c03fd6a2f0f0d8");
//	}
//	public static boolean isBlackUUIDFromName(String name){
//		Player p = Bukkit.getPlayer(name);
//		if(p.isOnline()){
//			if(WhiteListPlugin.instance.blackUUIDList.contains(p.getUniqueId().toString().replace("-",""))){
//				return true;
//			}
//		}
//		return false;
//	}


    public static void removePlayerFromPlayerInteract(String name){
        WhiteListPlugin.instance.playerInteract.remove(name);
    }

    public static boolean isPlayerInPlayerInteract(String name){
        return WhiteListPlugin.instance.playerInteract.containsKey(name);
    }

    public static void addMarkToPlayerInteract(String name){
        if(isPlayerInPlayerInteract(name)){
            Integer integer = WhiteListPlugin.instance.playerInteract.get(name);
            WhiteListPlugin.instance.playerInteract.put(name,integer+1);
        }else{
            WhiteListPlugin.instance.playerInteract.put(name,1);
        }
    }

    public static boolean isMaxMarkInPlayerInteract(String name){
        if(isPlayerInPlayerInteract(name)){
            if(WhiteListPlugin.instance.playerInteract.get(name)>20){
                if(PlayerUtil.isInWhiteList(name)){
                    removePlayerFromPlayerInteract(name);
                    return false;
                }
                return true;
            }
        }
        return false;
    }

}
