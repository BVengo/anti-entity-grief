package antientitygrief;

import antientitygrief.config.Configs;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;

import java.util.List;

public class SuggestionController {
    public static final List<EntityType<?>> ENTITY_SELECTION = Configs.getEntityTypes();

    public static final SuggestionProvider<CommandSourceStack> ENTITY_SUGGESTIONS = SuggestionProviders.register(
        new ResourceLocation("griefing_entities"), 
        (commandContext, suggestionsBuilder) -> SharedSuggestionProvider.suggestResource(
            BuiltInRegistries.ENTITY_TYPE.stream().filter(ENTITY_SELECTION::contains),
            suggestionsBuilder,
            EntityType::getKey,
            entityType -> Component.translatable(Util.makeDescriptionId("entity", EntityType.getKey(entityType)))));
}
