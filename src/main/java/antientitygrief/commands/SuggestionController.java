package antientitygrief.commands;

import antientitygrief.AntiEntityGrief;
import antientitygrief.Utils;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


public class SuggestionController {
    public static final String ALL_SYMBOL = "ALL";

    public static final List<EntityType<?>> ENTITY_SELECTION = Configs.getEntityTypes();

    public static final SuggestionProvider<CommandSourceStack> ENTITY_SUGGESTIONS = SuggestionProviders.register(
        ResourceLocation.fromNamespaceAndPath(AntiEntityGrief.MOD_ID, "griefing_entities"),
        (context, builder) -> SharedSuggestionProvider.suggestResource(
            BuiltInRegistries.ENTITY_TYPE.stream().filter(ENTITY_SELECTION::contains),
            builder,
            EntityType::getKey,
            entityType -> Component.translatable(Util.makeDescriptionId("entity", EntityType.getKey(entityType)))
        ));


    public static CompletableFuture<Suggestions> suggestEntityCapabilities(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        // Dynamically suggest capabilities based on the provided entity
        EntityType<?> entityType = ResourceArgument.getEntityType(context, "entity").value();
        String entityId = Utils.getEntityId(entityType);

        List<Capabilities> capabilities;

        if(entityId.equals(ALL_SYMBOL)) {
            capabilities = Arrays.asList(Capabilities.values());
        } else {
            capabilities = Configs.getEntityCapabilities(entityId);
        }

        List<String> capabilityNames = capabilities.stream().map(Capabilities::name).collect(Collectors.toList());
        capabilityNames.addFirst(ALL_SYMBOL);

        return SharedSuggestionProvider.suggest(capabilityNames, builder);
    }

    public static CompletableFuture<Suggestions> suggestCapabilities(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        // Suggest capabilities for all entities
        List<String> capabilityNames = Arrays.stream(Capabilities.values()).map(Capabilities::name).collect(Collectors.toList());
        capabilityNames.addFirst(ALL_SYMBOL);

        return SharedSuggestionProvider.suggest(capabilityNames, builder);
    }
}
