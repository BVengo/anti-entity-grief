package antientitygrief.commands;

import antientitygrief.Utils;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.registries.Registries;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandController {
	public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher, CommandBuildContext commandBuildContext) {
		registerEntityGriefing(commandDispatcher, commandBuildContext);
		registerEntityGriefingAll(commandDispatcher, commandBuildContext);
		registerEntityGriefingReset(commandDispatcher, commandBuildContext);
	}

	private static void registerEntityGriefing(CommandDispatcher<CommandSourceStack> commandDispatcher, CommandBuildContext commandBuildContext) {
		/**
		 * Command to get or set the griefing option for a specific entity type.
		 * Formats:
		 * 	/entityGriefing <entity> [value] - Get (or set) all griefing capabilities for the specified entity type.
		 * 	/entityGriefing <entity> <capability> [value] - Get (or set) the specified capability for the specified entity type.
		 */
		commandDispatcher.register(Commands.literal("entityGriefing")
			.requires(source -> source.hasPermission(2))
			// Select the entity
			.then(Commands.argument("entity", ResourceArgument.resource(commandBuildContext, Registries.ENTITY_TYPE))
			.suggests(SuggestionController.ENTITY_SUGGESTIONS)
			// Select the capability
			.then(Commands.argument("capability", StringArgumentType.string())
			.suggests((context, builder) -> {
				// Suggest all capabilities for the specified entity type.
				EntityType<?> entity = ResourceArgument.getEntityType(context, "entity").value();

				List<Capabilities> capabilities = Configs.getEntityCapabilities(entity);
				List<String> capabilityNames = capabilities.stream().map(Capabilities::name).collect(Collectors.toList());
				return SharedSuggestionProvider.suggest(capabilityNames, builder);
			})
			// Set the capability value
			.then(Commands.argument("value", BoolArgumentType.bool())
			// Set the value for the specified capability type and entity.
			.executes(commandContext -> {
				CommandSourceStack source = commandContext.getSource();

				EntityType<?> entity = ResourceArgument.getEntityType(commandContext, "entity").value();
				String capability = StringArgumentType.getString(commandContext, "capability");
				boolean value = BoolArgumentType.getBool(commandContext, "value");

				setGriefingOption(source, entity, capability, value);
				return 1;
			}))
			// Get the value for the specified capability type and entity.
			.executes(commandContext -> {
				CommandSourceStack source = commandContext.getSource();

				EntityType<?> entity = ResourceArgument.getEntityType(commandContext, "entity").value();
				String capabilityString = StringArgumentType.getString(commandContext, "capability");

				Capabilities capability = Utils.getCapability(capabilityString);
				printGriefingOption(source, entity, capability);
				return 1;
			}))
			// List all capabilities for the specified entity.
			.executes(commandContext -> {
				CommandSourceStack source = commandContext.getSource();

				EntityType<?> entity = ResourceArgument.getEntityType(commandContext, "entity").value();

				printGriefingOption(source, entity, null);
				return 1;
			})));
	}

	private static void registerEntityGriefingAll(CommandDispatcher<CommandSourceStack> commandDispatcher, CommandBuildContext commandBuildContext) {
		/**
		 * Command to get or set a specific griefing capability for all entities (where applicable).
		 * Format:
		 * 	/entityGriefingAll <capability> <value> - Get (or set) the specified capability for all entities.
		 */
		commandDispatcher.register(Commands.literal("entityGriefingAll")
			.requires(source -> source.hasPermission(2))
			.then(Commands.argument("capability", StringArgumentType.string())
			.suggests((context, builder) -> {
				// Suggest all capabilities for all entities.
				List<String> capabilityNames = Arrays.stream(Capabilities.values()).map(Capabilities::name).collect(Collectors.toList());
				return SharedSuggestionProvider.suggest(capabilityNames, builder);
			})
			.then(Commands.argument("value", BoolArgumentType.bool())
			.executes(commandContext -> {
				CommandSourceStack source = commandContext.getSource();

				String capability = StringArgumentType.getString(commandContext, "capability");
				boolean value = BoolArgumentType.getBool(commandContext, "value");

				setGriefingOption(source, capability, value);
				return 1;
			}))));
	}

	private static void registerEntityGriefingReset(CommandDispatcher<CommandSourceStack> commandDispatcher, CommandBuildContext commandBuildContext) {
		/**
		 * Command to reset all griefing capabilities to their default values.
		 * Format:
		 * 	/entityGriefingReset - Reset all griefing capabilities to their default values.
		 */
		commandDispatcher.register(Commands.literal("entityGriefingReset")
			.requires(source -> source.hasPermission(2))
			.executes(commandContext -> {
				CommandSourceStack source = commandContext.getSource();

				Configs.resetCapabilities();
				source.sendSystemMessage(Component.literal("All griefing capabilities have been reset to their default values."));
				return 1;
			}));
	}

	private static void setGriefingOption(CommandSourceStack source, EntityType<?> entityType, String capability, boolean value) {
		String entityId = Utils.getEntityId(entityType);
		Configs.setGriefingOption(entityId, capability, value);
		source.sendSystemMessage(Component.literal(capability + " for " + entityId + " is now " + value));
	}

	private static void setGriefingOption(CommandSourceStack source, String capability, boolean value) {
		Configs.getConfigDict().keySet().forEach(entityId -> {
			Configs.setGriefingOption(entityId, capability, value);
		});

		source.sendSystemMessage(Component.literal(capability + " for all applicable entities is now " + value));
	}

	private static void printGriefingOption(CommandSourceStack source, EntityType<?> entity, @Nullable Capabilities capability) {
		if(capability == null) {
			List<Capabilities> capabilities = Configs.getEntityCapabilities(entity);
			for (Capabilities c : capabilities) {
				printGriefingOption(source, entity, c);
			}
			return;
		}

		boolean currentValue = Configs.getGriefingOption(entity, capability);
		String entityId = Utils.getEntityId(entity);
		String capabilityName = capability.name();

		source.sendSystemMessage(Component.literal(capabilityName + " for " + entityId + " is currently " + currentValue));
	}
}