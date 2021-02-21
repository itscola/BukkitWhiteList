package top.dsbbs2.whitelist.util;

import org.bukkit.command.CommandSender;
import top.dsbbs2.whitelist.WhiteListPlugin;
import top.dsbbs2.whitelist.commands.IChildCommand;

import java.util.Vector;

public class MsgUtil {
    public static void makeDebugMsgAndSend(String msg){
        MsgUtil.sendDebugMessage(msg,new Throwable().getStackTrace()[1].toString());
    }

    public static void sendDebugMessage(String msg,String[] arg,String command,String callMsg){
        if(getDebugMode()){
            System.out.println("§l[Whitelist][debug]§d"+msg+"[Command:"+command+" "+getAllArr(arg)+"][CallMessage]: "+callMsg);
        }
    }
    public static void sendDebugMessage(String msg,String callMsg){
        if(getDebugMode()) {
            System.out.println("§l[Whitelist][debug]§d" + msg + "[CallMessage]: "+callMsg);
        }
    }
    public static String getAllArr(String[] arr){
        String All = "";
        for(String str : arr){
            All+=str+" ";
        }
        return All;
    }
    public static boolean getDebugMode(){
        return ((WhiteListPlugin.instance!=null&&WhiteListPlugin.instance.whitelist!=null&WhiteListPlugin.instance.whitelist.con.debugMode)||WhiteListPlugin.debugMode);
    }
    public static boolean hasNullString(String[] arg3, CommandSender arg0,Boolean allowLast){
        int index = allowLast==true?arg3.length:arg3.length-1;
        for(int i=0;i<index;i++){
            if(arg3[i].equals("")){
                if(arg0!=null) {
                    arg0.sendMessage("第" + (i + 1) + "个参数,不能为空! index :" + i + " 内容为 :" + arg3[1]);
                }
                return true;
            }
        }
        return false;
    }
    public static StringBuilder getPage(Vector<IChildCommand> commands, int page){
        StringBuilder ret=new StringBuilder();
        ret=ret.append("§e§l◎§b§lWhitelist§r------第§e§l"+(page+1)+"§r页--------------\n");
        for(int i=5*page;i<5*page+5&&i<commands.size();i++) {
            ret = ret.append("§r§a" + commands.get(i).getUsage() + "   " + commands.get(i).getDescription() + "\n");
        }
        ret=ret.append("§r----------§bwhite_cola,lvxinlei,mayukowo");
        return ret;
    }
}
