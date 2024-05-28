package antientitygrief;

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
    private static final List<EntityType<? extends Entity>> ENTITY_SELECTION = List.of(
        EntityType.BLAZE,  // Set fires
        EntityType.CREEPER,  // Destroy blocks
        EntityType.ENDERMAN,  // Pick up / place blocks
        EntityType.ENDER_DRAGON,  // Destroy blocks
        EntityType.END_CRYSTAL,  // Destroy blocks
        EntityType.FROG,  // Place eggs
        EntityType.GHAST,  // Destroy blocks, set fires
        EntityType.PLAYER,  // All the things, just for laughs
        EntityType.SILVERFISH,  // Destroy blocks
        EntityType.SNOW_GOLEM,  // Leave snow trails
        EntityType.TURTLE,  // Place eggs
        EntityType.TNT,  // Destroy blocks - also impacts TNT minecarts
        EntityType.VILLAGER,  // Farm crops
        EntityType.WITHER  // Destroy blocks
    );

    public static final SuggestionProvider<CommandSourceStack> ENTITY_SUGGESTIONS = SuggestionProviders.register(
        new ResourceLocation("griefing_entities"), 
        (commandContext, suggestionsBuilder) -> SharedSuggestionProvider.suggestResource(
            BuiltInRegistries.ENTITY_TYPE.stream().filter(entityType -> ENTITY_SELECTION.contains(entityType)),
            suggestionsBuilder,
            EntityType::getKey,
            entityType -> Component.translatable(Util.makeDescriptionId("entity", EntityType.getKey(entityType)))));
}
