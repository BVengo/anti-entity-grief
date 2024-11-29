package antientitygrief.commands.resources;

import antientitygrief.Utils;
import antientitygrief.commands.SuggestionController;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.RegistryEntryReferenceArgumentType;
import net.minecraft.entity.EntityType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class EntityResource {
	public static RequiredArgumentBuilder<ServerCommandSource, EntitySelector> request() {
		return CommandManager.argument("entity", EntityArgumentType.entities())
				.suggests(SuggestionController.ENTITY_SUGGESTIONS);
	}

	public static String extract(CommandContext<ServerCommandSource> commandContext) throws CommandSyntaxException {
		EntityType<?> entityType = RegistryEntryReferenceArgumentType.getEntityType(commandContext, "entity").value();
		return Utils.getEntityId(entityType);
	}
}