package antientitygrief.commands;

import antientitygrief.AntiEntityGrief;
import antientitygrief.commands.resources.EntityResource;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.registry.Registries;
import net.minecraft.entity.EntityType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


public class SuggestionController {
    public static final String ALL_SYMBOL = "ALL";

    public static final List<EntityType<?>> ENTITY_SELECTION = Configs.getEntityTypes();

    public static final SuggestionProvider<ServerCommandSource> ENTITY_SUGGESTIONS = SuggestionProviders.register(
        Identifier.of(AntiEntityGrief.MOD_ID, "griefing_entities"),
        (context, builder) -> CommandSource.suggestFromIdentifier(
            Registries.ENTITY_TYPE.stream().filter(ENTITY_SELECTION::contains),
            builder,
            EntityType::getId,
            entityType -> Text.translatable(Util.createTranslationKey("entity", EntityType.getId(entityType)))
        ));

    public static CompletableFuture<Suggestions> suggestEntityCapabilities(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        // Dynamically suggest capabilities based on the provided entity
        String entityId = EntityResource.extract(context);

        List<Capabilities> capabilities;

        if(entityId.equals(ALL_SYMBOL)) {
            capabilities = Arrays.asList(Capabilities.values());
        } else {
            capabilities = Configs.getEntityCapabilities(entityId);
        }

        List<String> capabilityNames = capabilities.stream().map(Capabilities::name).collect(Collectors.toList());
        capabilityNames.add(0, ALL_SYMBOL);

        return CommandSource.suggestMatching(capabilityNames, builder);
    }

    public static CompletableFuture<Suggestions> suggestCapabilities(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        // Suggest capabilities for all entities
        List<String> capabilityNames = Arrays.stream(Capabilities.values()).map(Capabilities::name).collect(Collectors.toList());
        capabilityNames.add(0, ALL_SYMBOL);

        return CommandSource.suggestMatching(capabilityNames, builder);
    }
}
