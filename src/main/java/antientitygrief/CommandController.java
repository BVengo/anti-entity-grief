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

				String mobId = EntityType.getKey(entity).toString();
				setEntityGriefing(source, mobId, value);

				return 1;
		}))));
	}

	private static void setEntityGriefing(CommandSourceStack source, String mobId, boolean value) {

		// Logic to toggle mob griefing for the specified mob
		// This is a placeholder. Actual implementation depends on the game's API and mod capabilities.
		// Typically, you would set the mobGriefing rule for the specified mob.
		source.sendSystemMessage(Component.literal("Mob griefing for " + mobId + " set to " + value));
	}
}