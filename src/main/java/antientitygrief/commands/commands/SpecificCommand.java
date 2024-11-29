package antientitygrief.commands.commands;

import antientitygrief.Utils;
import antientitygrief.commands.SuggestionController;
import antientitygrief.commands.resources.CapabilityResource;
import antientitygrief.commands.resources.EntityResource;
import antientitygrief.commands.resources.ValueResource;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import static antientitygrief.commands.commands.CommandHelper.setEntityCapability;

public class SpecificCommand {
	/**
	 * Register the entityGriefing command for setting and printing a capability on a specific entity.
	 */
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("entityGriefing").requires(CommandHelper.isOp())
				.then(EntityResource.request()
						.then(CapabilityResource.request()
								.then(ValueResource.request()
										.executes(SpecificCommand::set))  // entityGriefing <entity> <capability> <value>
								.executes(SpecificCommand::print))));  // entityGriefing <entity> <capability>
	}

	private static int set(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		// Given an entity, capability, and value, set the capability value.
		String entityId = EntityResource.extract(context);
		String capability = CapabilityResource.extract(context);
		boolean value = ValueResource.extract(context);

		String capabilityText = capability.equals(SuggestionController.ALL_SYMBOL) ? "All capabilities" : capability;

		setEntityCapability(entityId, capability, value);
		CommandHelper.message(context, capabilityText + " for " + entityId + " is now " + value);

		return 1;
	}

	private static int print(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		// Given just an entity and capability, print the capability value.
		String entityId = EntityResource.extract(context);
		String capability = CapabilityResource.extract(context);

		ServerCommandSource source = context.getSource();

		if(entityId.equals(SuggestionController.ALL_SYMBOL)) {
			Configs.getConfigDict().keySet().forEach(id -> printEntityCapability(source, id, capability, true));
		} else {
			// Print one entity
			printEntityCapability(source, entityId, capability, false);
		}

		return 1;
	}

	private static void printEntityCapability(ServerCommandSource source, String entityId, String capabilityString, boolean skipInvalid) {
		// Assumes inputs have already been validated

		if(capabilityString.equals(SuggestionController.ALL_SYMBOL)) {
			// Print all capabilities
			Configs.getConfigDict().get(entityId).getCapabilities().forEach((capability, value) -> {
				printEntityCapability(source, entityId, capability.name(), true);
			});
		} else {
			Capabilities capability = Utils.getCapability(capabilityString);

			if(skipInvalid && !Configs.getConfigDict().get(entityId).getAvailableCapabilities().contains(capability)) {
				return;
			}

			CommandHelper.message(source, entityId + " has capability " + capabilityString + " set to " + Configs.getGriefingOption(entityId, capability));
		}
	}
}