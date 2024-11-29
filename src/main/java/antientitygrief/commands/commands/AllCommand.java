package antientitygrief.commands.commands;

import antientitygrief.commands.SuggestionController;
import antientitygrief.commands.resources.CapabilityResource;
import antientitygrief.commands.resources.ValueResource;
import antientitygrief.config.Configs;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import static antientitygrief.commands.commands.CommandHelper.setEntityCapability;

public class AllCommand {
	/**
	 * Register the entityGriefingAll command for setting a capability on all entities.
	 */
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("entityGriefingAll").requires(CommandHelper.isOp())
				.then(CapabilityResource.request()
						.then(ValueResource.request()
								.executes(AllCommand::set))));  // entityGriefingAll <capability> <value>
	}

	private static int set(CommandContext<ServerCommandSource> context) {
		// Given a capability and value, set the capability value for all entities.
		String capability = CapabilityResource.extract(context);
		boolean value = ValueResource.extract(context);

		String capabilityText = capability.equals(SuggestionController.ALL_SYMBOL) ? "All capabilities" : capability;

		Configs.getConfigDict().keySet().forEach(id -> setEntityCapability(id, capability, value));
		CommandHelper.message(context, capabilityText + " for all entities is now " + value);

		return 1;
	}
}
