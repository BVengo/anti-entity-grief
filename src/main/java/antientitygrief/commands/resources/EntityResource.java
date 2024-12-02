package antientitygrief.commands.resources;

import antientitygrief.Utils;
import antientitygrief.commands.SuggestionController;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.RegistryEntryArgumentType;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class EntityResource {
	public static RequiredArgumentBuilder<ServerCommandSource, RegistryEntry.Reference<EntityType<?>>> request(CommandRegistryAccess commandRegistryAccess) {
		return CommandManager.argument("entity", RegistryEntryArgumentType.registryEntry(commandRegistryAccess, RegistryKeys.ENTITY_TYPE)).suggests(SuggestionController.ENTITY_SUGGESTIONS);
	}

	public static String extract(CommandContext<ServerCommandSource> commandContext) throws CommandSyntaxException {
		return Utils.getEntityId(RegistryEntryArgumentType.getEntityType(commandContext, "entity").value());
	}
}