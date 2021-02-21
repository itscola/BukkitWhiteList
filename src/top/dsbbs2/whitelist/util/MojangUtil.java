package top.dsbbs2.whitelist.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.dsbbs2.whitelist.config.struct.UUIDJson;

import java.util.UUID;

public class MojangUtil {
    public static final String uuid_mojang = "https://api.mojang.com/users/profiles/minecraft/<name>";
    public static final String backup_uuid_mojang = "https://api.ashcon.app/mojang/v2/user/<name>";
    public static UUID getUUIDFromMojang(String name) throws Throwable{
        String content = UrlUtil.readURL(uuid_mojang.replace("<name>", name));
        Gson g = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().setLenient().create();
        UUIDJson uc = g.fromJson(content, UUIDJson.class);
        String beforeUUID = uc.id;
        String uuid = beforeUUID.substring(0, 8) +"-"+ beforeUUID.substring(9, 13)+"-"+ beforeUUID.substring(14, 18) +"-"+ beforeUUID.substring(19, 23) +"-"+ beforeUUID.substring(24);
        return UUID.fromString(uuid);
    }

}
