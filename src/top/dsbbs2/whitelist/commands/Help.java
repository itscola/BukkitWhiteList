package top.dsbbs2.whitelist.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import top.dsbbs2.whitelist.WhiteListPlugin;
import top.dsbbs2.whitelist.util.MsgUtil;
import top.dsbbs2.whitelist.util.VectorUtil;

import java.util.Vector;

public class Help implements IChildCommand{

    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        if (arg3.length >= 1 && arg3.length <= 2) {
            int page = 0;
            try {
                if (arg3.length >= 2)
                    page = Integer.valueOf(arg3[1]) - 1;
            } catch (Throwable e) {
                arg0.sendMessage("[����]ҳ����������");
                return true;
            }
            if (page < 0) {
                arg0.sendMessage("[����]��Чҳ��");
                return true;
            }
            if (Math.ceil(WhiteListPlugin.instance.childCmds.size() / (double) 5) < page) {
                arg0.sendMessage("[����]��Чҳ��");
                return true;
            }
            arg0.sendMessage(MsgUtil.getPage(WhiteListPlugin.instance.childCmds,page).toString());
            return true;
        }
        return false;
    }
    @Override
    public String getUsage() {
        return "/wl help <num>";
    }

    @Override
    public  Vector<Class<?>> getArgumentsTypes() {
        return VectorUtil.toVector(int.class);
    }

    @Override
    public Vector<String> getArgumentsDescriptions() {
        return VectorUtil.toVector("num");
    }

    @Override
    public String getPermission() {
        return "whitelist.help";
    }
    @Override
    public String getDescription(){
        return "��ȡ����";
    }
}
