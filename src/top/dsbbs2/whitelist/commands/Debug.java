package top.dsbbs2.whitelist.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import top.dsbbs2.whitelist.WhiteListPlugin;
import top.dsbbs2.whitelist.util.VectorUtil;

import java.util.Vector;

public class Debug implements IChildCommand{
    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        if(arg3.length==1){
            if(arg3[0].equalsIgnoreCase("debug")){
                if(WhiteListPlugin.instance.debugMode){
                    //����Ϊfalse
                    WhiteListPlugin.instance.debugMode = false;
                    arg0.sendMessage("��d��l�Ѿ�Ϊ���ر�debugģʽ!");
                    return true;
                }else{
                    //����Ϊtrue
                    WhiteListPlugin.instance.debugMode = true;
                    arg0.sendMessage("��d��l�Ѿ�Ϊ������debugģʽ!");
                    arg0.sendMessage("��d��lע��,debugģʽ�������������,��������������,��������ִ���,����û���κδ�����Ϣ,�뿪��������,Ȼ���Լ5���Ӻ�,���������߷�����־,�Ա��������!");
                    return true;
                }
            }
        }else if(arg3.length==2){
            if(arg3[1].equalsIgnoreCase("lowerMode")){
                if(!WhiteListPlugin.instance.forceLowerMode){
                    WhiteListPlugin.instance.forceLowerMode = true;
                    arg0.sendMessage("��Ϊ��ǿ�ƿ���������ģʽ,�����ģʽ��,��԰�����ϵ�в���Ĺ�����������.");
                    return true;
                }else{
                    WhiteListPlugin.instance.forceLowerMode = false;
                    arg0.sendMessage("��Ϊ��ǿ�ƹرյ�����ģʽ.");
                    return true;
                }
            }
        }
        arg0.sendMessage("��cdebug����ִ��ʧ��,��Ҫִ�е������ǲ��� '/wl debug' ?");
        return false;
    }
    @Override
    public String getUsage() {
        return "/wl debug";
    }

    @Override
    public Vector<Class<?>> getArgumentsTypes()
    {
        return VectorUtil.toVector(String.class);
    }

    @Override
    public Vector<String> getArgumentsDescriptions()
    {
        return VectorUtil.toVector("");
    }

    @Override
    public String getPermission()
    {
        return "whitelist.debug";
    }

    @Override
    public String getDescription(){
        return "����/�ر� debugģʽ";
    }

}
