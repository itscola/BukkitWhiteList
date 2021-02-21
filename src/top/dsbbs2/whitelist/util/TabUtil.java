package top.dsbbs2.whitelist.util;

import java.util.ArrayList;
import java.util.List;

public class TabUtil {
    public static List<String> betterGetStartsWithList(List<String> o, String s)
    {
        List<String> temp=getStartsWithList(o,s,false);
        if(ListUtil.hasDuplicatedElement(ListUtil.toLowerCaseList(temp)))
            return getStartsWithList(o,s,true);
        return temp;
    }
    public static List<String> getStartsWithList(List<String> o, String s)
    {
       return getStartsWithList(o,s,false);
    }
    public static List<String> getStartsWithList(List<String> o, String s,boolean caseSensitive)
    {
        List<String> ret=new ArrayList<>();
        if(caseSensitive)
        {
            o.forEach(i->{
                if(i!=null&&i.startsWith(s))
                    ret.add(i);
            });
        }else{
            o.forEach(i->{
                if(i!=null&&i.toLowerCase().startsWith(s.toLowerCase()))
                    ret.add(i);
            });
        }
        return ret;
    }
}
