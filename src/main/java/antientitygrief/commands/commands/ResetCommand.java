package antientitygrief.commands.commands;

import antientitygrief.config.Configs;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class ResetCommand {
	/**
	 * Register the entityGriefingReset command for resetting all entity capabilities.
	 */
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("entityGriefingReset").requires(CommandHelper.isOp())
				.executes(ResetCommand::reset));  // entityGriefingReset
	}

	private static int reset(CommandContext<ServerCommandSource> context) {
		// Reset all entity capabilities to their default values.
		Configs.resetCapabilities();
		CommandHelper.message(context, "All griefing capabilities have been reset to their default values.");

		return 1;
	}
}