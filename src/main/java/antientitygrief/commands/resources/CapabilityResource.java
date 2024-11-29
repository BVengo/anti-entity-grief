package antientitygrief.commands.resources;

import antientitygrief.commands.SuggestionController;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class CapabilityResource {
	public static RequiredArgumentBuilder<ServerCommandSource, String> request() {
		return CommandManager.argument("capability", StringArgumentType.string())
				.suggests(SuggestionController::suggestEntityCapabilities);
	}

	public static String extract(CommandContext<ServerCommandSource> commandContext) {
		return StringArgumentType.getString(commandContext, "capability");
	}
}