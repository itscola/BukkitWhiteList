package top.dsbbs2.whitelist.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.spigotmc.WatchdogThread;
import top.dsbbs2.common.file.FileUtils;
import top.dsbbs2.whitelist.config.MojangJson;
import top.dsbbs2.whitelist.util.PlayerUtil;
import top.dsbbs2.whitelist.util.ServerUtil;
import top.dsbbs2.whitelist.util.VectorUtil;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class Import implements IChildCommand {
    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        try {
                long s1 = System.currentTimeMillis();
                String mojangWhitelistJson = FileUtils.readTextFile(new File(".\\whitelist.json"), StandardCharsets.UTF_8);

                Gson g =new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().setLenient().create();
                Vector<MojangJson> v = g.fromJson(mojangWhitelistJson,new TypeToken<Vector<MojangJson>>(){}.getType());
                if(v==null){
                    arg0.sendMessage("����ʧ��,�޷���json�ļ�ת��Ϊ����!");
                    return true;
                }
                for(MojangJson mj : v){
                    try {
                        WatchdogThread.tick();
                    }catch(Throwable e){}
                    PlayerUtil.checkThenAddtoWhitelist(arg0,mj.name,-1, ServerUtil.isOnlineStorageMode());
                }
                long s2 = System.currentTimeMillis();
                arg0.sendMessage("�������,��ʱ"+(s2-s1)+"����!");


        }catch(Throwable e){throw new RuntimeException(e);}
        return true;
    }

    @Override
    public String getUsage() {
        return "/wl import";
    }

    @Override
    public Vector<Class<?>> getArgumentsTypes()
    {
        return VectorUtil.toVector(String.class);
    }

    @Override
    public Vector<String> getArgumentsDescriptions()
    {
        return VectorUtil.toVector("Mojang");
    }

    @Override
    public String getPermission()
    {
        return "whitelist.import";
    }
    @Override
    public String getDescription(){
        return "��mojang/�ɰ� �������[��ʱֻ֧�ִӹٷ�����������!]";
    }
}
