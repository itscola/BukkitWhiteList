package top.dsbbs2.whitelist.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtil {
    public static String readURL(String Iurl) throws Throwable {
        URL url = new URL(Iurl);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setUseCaches(false);
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type","application/json");
        InputStream is = con.getInputStream();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        while(true)
        {
            int temp = is.read();
            if(temp!=-1)
            {
                bao.write(temp);
            }else{
                break;
            }
        }
        is.close();
        bao.close();
        return new java.lang.String(bao.toByteArray());


    }
}