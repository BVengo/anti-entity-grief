package antientitygrief.commands.commands;

import antientitygrief.Utils;
import antientitygrief.commands.SuggestionController;
import antientitygrief.commands.resources.CapabilityResource;
import antientitygrief.commands.resources.EntityResource;
import antientitygrief.commands.resources.ValueResource;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static antientitygrief.commands.commands.CommandHelper.setEntityCapability;

public class SpecificCommand {
	/**
	 * Register the entityGriefing command for setting and printing a capability on a specific entity.
	 */
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess) {
		dispatcher.register(CommandManager.literal("entityGriefing").requires(CommandHelper.isOp())
				.then(EntityResource.request(commandRegistryAccess)
						.then(CapabilityResource.request()
								.then(ValueResource.request()
										.executes(SpecificCommand::set))  // entityGriefing <entity> <capability> <value>
								.executes(SpecificCommand::print))  // entityGriefing <entity> <capability>
						.executes(SpecificCommand::printAll)));  // entityGriefing <entity>
	}

	private static int set(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		// Given an entity, capability, and value, set the capability value.
		String entityId = EntityResource.extract(context);
		String capability = CapabilityResource.extract(context);
		boolean value = ValueResource.extract(context);

		String capabilityString = capability.equals(SuggestionController.ALL_SYMBOL) ? "All capabilities" : capability;

		boolean success = setEntityCapability(entityId, capability, value);

		if(success) {
			CommandHelper.message(context, (
					Text.literal("").styled(style -> style.withColor(Formatting.GRAY))
							.append(Text.literal(entityId).styled(style -> style.withColor(Formatting.YELLOW)))
							.append(Text.literal(" now has "))
							.append(Text.literal(capabilityString).styled(style -> style.withColor(Formatting.AQUA)))
							.append(Text.literal(" set to "))
							.append(Text.literal(value ? "true" : "false").styled(style -> style.withColor(Formatting.GREEN)))
			));
			return 1;
		} else {
			String correctCommand = "/entityGriefing " + entityId;
			CommandHelper.message(context, (
					Text.literal("").styled(style -> style.withColor(Formatting.RED))
							.append(Text.literal("Failed to set "))
							.append(Text.literal(capabilityString).styled(style -> style.withColor(Formatting.AQUA)))
							.append(Text.literal(" for "))
							.append(Text.literal(entityId).styled(style -> style.withColor(Formatting.YELLOW)))
							.append(Text.literal(". Is that a valid capability for this entity? ("))
							.append(Text.literal("Click here or type '" + correctCommand + "' to view capabilities")
									.styled(style -> style.withColor(Formatting.GRAY)
											.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, correctCommand))))
							.append(Text.literal(")"))
			));
			return 0;
		}
	}

	private static int printAll(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		// Given an entity, print all capabilities.
		String entityId = EntityResource.extract(context);
		ServerCommandSource source = context.getSource();

		if(entityId.equals(SuggestionController.ALL_SYMBOL)) {
			Configs.getConfigDict().keySet().forEach(id -> printEntityCapability(source, id, SuggestionController.ALL_SYMBOL, true));
		} else {
			// Print one entity
			printEntityCapability(source, entityId, SuggestionController.ALL_SYMBOL, false);
		}

		return 1;
	}

	private static int print(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		// Given just an entity and capability, print the capability value.
		String entityId = EntityResource.extract(context);
		String capability = CapabilityResource.extract(context);

		ServerCommandSource source = context.getSource();

		if(entityId.equals(SuggestionController.ALL_SYMBOL)) {
			Configs.getConfigDict().keySet().forEach(id -> printEntityCapability(source, id, capability, true));
		} else {
			// Print one entity
			printEntityCapability(source, entityId, capability, false);
		}

		return 1;
	}

	private static void printEntityCapability(ServerCommandSource source, String entityId, String capabilityString, boolean skipInvalid) {
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

			boolean value = Configs.getGriefingOption(entityId, capability);

			CommandHelper.message(source, (
					Text.literal("").styled(style -> style.withColor(Formatting.GRAY))
							.append(Text.literal(entityId).styled(style -> style.withColor(Formatting.YELLOW)))
							.append(Text.literal(" has "))
							.append(Text.literal(capabilityString).styled(style -> style.withColor(Formatting.AQUA)))
							.append(Text.literal(" currently set to "))
							.append(Text.literal(value ? "true" : "false").styled(style -> style.withColor(value ? Formatting.GREEN : Formatting.RED)))
			));
		}
	}
}