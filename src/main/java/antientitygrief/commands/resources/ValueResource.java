package antientitygrief.commands.resources;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class ValueResource {
	public static RequiredArgumentBuilder<ServerCommandSource, Boolean> request() {
		return CommandManager.argument("value", BoolArgumentType.bool());
	}

	public static boolean extract(CommandContext<ServerCommandSource> commandContext) {
		return BoolArgumentType.getBool(commandContext, "value");
	}
}