package top.dsbbs2.whitelist.commands;

import java.util.Vector;

import org.bukkit.command.CommandExecutor;
import org.jetbrains.annotations.NotNull;

public interface IChildCommand extends CommandExecutor {
	@NotNull
	public default String getUsage() {return "";}
	@NotNull
	public default Vector<Class<?>> getArgumentsTypes(){return new Vector<>();}
	@NotNull
	public default Vector<String> getArgumentsDescriptions(){return new Vector<>();}
	@NotNull
	public default String getPermission() {return "";}
	@NotNull
	public default String getClassName() {
		return this.getClass().getSimpleName();
	}
	@NotNull
	public default String getDescription(){return "";};
}
