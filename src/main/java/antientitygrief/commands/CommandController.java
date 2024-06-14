package antientitygrief.commands;

import antientitygrief.Utils;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;

import java.util.List;


public class CommandController {
	public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher, CommandBuildContext commandBuildContext) {
		registerEntityGriefing(commandDispatcher, commandBuildContext);
		registerEntityGriefingReset(commandDispatcher);
		registerEntityGriefingAll(commandDispatcher);
	}

	private static void registerEntityGriefing(CommandDispatcher<CommandSourceStack> commandDispatcher, CommandBuildContext commandBuildContext) {
        commandDispatcher.register(Commands.literal("entityGriefing")
			.requires(source -> source.hasPermission(2))
			.then(Commands.argument("entity", ResourceArgument.resource(commandBuildContext, Registries.ENTITY_TYPE))
				.suggests(SuggestionController.ENTITY_SUGGESTIONS)
				.then(Commands.argument("capability", StringArgumentType.string())
					.suggests(SuggestionController::suggestEntityCapabilities)
					.then(Commands.argument("value", BoolArgumentType.bool())
						.executes(CommandController::setEntityCapability))  // entityGriefing <entity> <capability> <value>
					.executes(CommandController::printEntityCapability))));  // entityGriefing <entity> <capability>
	}

	private static void registerEntityGriefingAll(CommandDispatcher<CommandSourceStack> commandDispatcher) {
		commandDispatcher.register(Commands.literal("entityGriefingAll")
				.requires(source -> source.hasPermission(2))
				.then(Commands.argument("capability", StringArgumentType.string())
					.suggests(SuggestionController::suggestCapabilities)
					.then(Commands.argument("value", BoolArgumentType.bool())
						.executes(CommandController::setAllEntitiesCapability))));  // entityGriefingAll <capability> <value>
	}

	private static void registerEntityGriefingReset(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(Commands.literal("entityGriefingReset")
			.requires(source -> source.hasPermission(2))
			.executes(CommandController::resetAllEntityCapabilities));
	}

	private static int setEntityCapability(CommandContext<CommandSourceStack> commandContext) throws CommandSyntaxException {
		// Given an entity, capability, and value, set the capability value.
		CommandSourceStack source = commandContext.getSource();

		EntityType<?> entityType = ResourceArgument.getEntityType(commandContext, "entity").value();
		String entityId = Utils.getEntityId(entityType);

		String capability = StringArgumentType.getString(commandContext, "capability");
		boolean value = BoolArgumentType.getBool(commandContext, "value");

		String capabilityText = capability.equals(SuggestionController.ALL_SYMBOL) ? "All capabilities" : capability;

		setEntityCapability(entityId, capability, value);
		source.sendSystemMessage(Component.literal(capabilityText + " for " + entityId + " is now " + value));

		return 1;
	}

	private static int setAllEntitiesCapability(CommandContext<CommandSourceStack> commandContext) {
		// Given a capability and value, set the capability value for all entities.
		CommandSourceStack source = commandContext.getSource();

		String capability = StringArgumentType.getString(commandContext, "capability");
		boolean value = BoolArgumentType.getBool(commandContext, "value");

		String capabilityText = capability.equals(SuggestionController.ALL_SYMBOL) ? "All capabilities" : capability;

		Configs.getConfigDict().keySet().forEach(id -> setEntityCapability(id, capability, value));
		source.sendSystemMessage(Component.literal(capabilityText + " for all entities is now " + value));

		return 1;
	}

	private static void setEntityCapability(String entityId, String capability, boolean value) {
		// Assumes inputs have already been validated

		if(capability.equals(SuggestionController.ALL_SYMBOL)) {
			// Set all capabilities
			List<Capabilities> capabilities = Configs.getEntityCapabilities(entityId);
			for (Capabilities c : capabilities) {
				Configs.setGriefingOption(entityId, c.toString(), value);
			}
		} else {
			// Set one capability
			Configs.setGriefingOption(entityId, capability, value);
		}
	}

	private static int printEntityCapability(CommandContext<CommandSourceStack> commandContext) throws CommandSyntaxException {
		// Given just an entity and capability, print the capability value.
		CommandSourceStack source = commandContext.getSource();

		EntityType<?> entityType = ResourceArgument.getEntityType(commandContext, "entity").value();
		String entityId = Utils.getEntityId(entityType);
		String capabilityString = StringArgumentType.getString(commandContext, "capability");

		if(entityId.equals(SuggestionController.ALL_SYMBOL)) {
			Configs.getConfigDict().keySet().forEach(id -> printEntityCapability(source, id, capabilityString, true));
		} else {
			// Print one entity
			printEntityCapability(source, entityId, capabilityString, false);
		}

		return 1;
	}

	private static void printEntityCapability(CommandSourceStack source, String entityId, String capabilityString, boolean skipInvalid) {
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

			source.sendSystemMessage(Component.literal(entityId + " has capability " + capabilityString + " set to " + Configs.getGriefingOption(entityId, capability)));
		}
	}

	private static int resetAllEntityCapabilities(CommandContext<CommandSourceStack> commandContext) {
		Configs.resetCapabilities();
		commandContext.getSource().sendSystemMessage(Component.literal("All griefing capabilities have been reset to their default values."));

		return 1;
	}
}