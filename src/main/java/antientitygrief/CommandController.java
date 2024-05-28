package antientitygrief;

import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.registries.Registries;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;

public class CommandController {
	public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher, CommandBuildContext commandBuildContext) {
		commandDispatcher.register(Commands.literal("entityGriefing")
			.requires(source -> source.hasPermission(2))
			.then(Commands.argument("entity", ResourceArgument.resource(commandBuildContext, Registries.ENTITY_TYPE))
			.suggests(SuggestionController.ENTITY_SUGGESTIONS)
			.then(Commands.argument("value", BoolArgumentType.bool())
			.executes(commandContext -> {
				CommandSourceStack source = commandContext.getSource();

				EntityType<?> entity = ResourceArgument.getEntityType(commandContext, "entity").value();
				boolean value = BoolArgumentType.getBool(commandContext, "value");

				setGriefingOption(source, entity, value);
				return 1;
			}))
			.executes(commandContext -> {
				CommandSourceStack source = commandContext.getSource();

				EntityType<?> entity = ResourceArgument.getEntityType(commandContext, "entity").value();

				printGriefingOption(source, entity);
				return 1;
			})));
	}

	private static void setGriefingOption(CommandSourceStack source, EntityType<?> entity, boolean value) {
		String entityId = Utils.getEntityId(entity);
		AntiEntityGrief.CONFIGS.setGriefingOption(entityId, value);
		source.sendSystemMessage(Component.literal("Griefing for " + entityId + " set to " + value));
	}

	private static void printGriefingOption(CommandSourceStack source, EntityType<?> entity) {
		String entityId = Utils.getEntityId(entity);
		boolean currentValue = AntiEntityGrief.CONFIGS.getGriefingOption(entityId);
		source.sendSystemMessage(Component.literal("Griefing for " + entityId + " is currently " + currentValue));
	}
}