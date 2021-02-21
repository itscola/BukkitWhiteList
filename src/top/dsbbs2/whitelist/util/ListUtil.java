package top.dsbbs2.whitelist.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
	@SafeVarargs
	public static <T> ArrayList<T> toList(T... o)
	{
		return new ArrayList<T>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8461643752862517970L;

			{
				for(T i : o)
				  this.add(i);
			}
		};
	}
	public static <T> List<T> append(List<T> l, T... o)
	{
		List<T> temp=new ArrayList<>(l);
		for(T i : o)
			temp.add(i);
		return temp;
	}
	public static <T> T[] toArray(List<T> l,Class<T> clazz)
	{
		T[] ret=(T[])java.lang.reflect.Array.newInstance(clazz,l.size());
		for(int i=0;i<l.size();i++)
			ret[i]=l.get(i);
		return ret;
	}
	public static List<String> toLowerCaseList(List<String> l)
	{
		List<String> ret=new ArrayList<>();
		l.forEach(i->ret.add(i.toLowerCase()));
		return ret;
	}
	public static <T> boolean hasDuplicatedElement(List<T> l)
	{
		for(int i=0;i<l.size();i++)
			for(int i2=i+1;i2<l.size();i2++)
			{
				if(l.get(i)==null && l.get(i2)==null)
					return true;
				if(l.get(i)==null)
					continue;
				if(l.get(i2)==null)
					continue;
				if(l.get(i).equals(l.get(i2)))
					return true;
			}
		return false;
	}
}
