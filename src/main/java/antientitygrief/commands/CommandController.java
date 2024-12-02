package antientitygrief.commands;

import antientitygrief.commands.commands.AllCommand;
import antientitygrief.commands.commands.ResetCommand;
import antientitygrief.commands.commands.SpecificCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;


public class CommandController {
	public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher, CommandRegistryAccess commandRegistryAccess) {
		SpecificCommand.register(commandDispatcher, commandRegistryAccess);
		AllCommand.register(commandDispatcher);
		ResetCommand.register(commandDispatcher);
	}
}