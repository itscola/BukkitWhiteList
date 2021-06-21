package top.dsbbs2.whitelist.commands;

import java.util.Vector;

import org.bukkit.command.CommandExecutor;

public interface IChildCommand extends CommandExecutor {
	public default String getUsage() {return "";}
	public default Vector<Class<?>> getArgumentsTypes(){return new Vector<>();}
	public default Vector<String> getArgumentsDescriptions(){return new Vector<>();}
	public default String getPermission() {return "";}
	public default String getClassName() {
		return this.getClass().getSimpleName();
	}
	public default String getDescription(){return "";};
}
