package antientitygrief.commands.commands;

import antientitygrief.commands.SuggestionController;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.List;
import java.util.function.Predicate;

public class CommandHelper {
	public static Predicate<ServerCommandSource> isOp() {
		return source -> source.hasPermissionLevel(2);
	}

	public static void message(ServerCommandSource source, Text message) {
		source.sendFeedback(() -> message, true);
	}

	public static void message(CommandContext<ServerCommandSource> context, Text message) {
		message(context.getSource(), message);
	}

	public static boolean setEntityCapability(String entityId, String capability, boolean value) {
		if(capability.equals(SuggestionController.ALL_SYMBOL)) {
			// Set all capabilities
			List<Capabilities> capabilities = Configs.getEntityCapabilities(entityId);
			for (Capabilities c : capabilities) {
				Configs.setGriefingOption(entityId, c.toString(), value);
			}
			return true;
		} else {
			// Set one capability
			return Configs.setGriefingOption(entityId, capability, value);
		}
	}
}
