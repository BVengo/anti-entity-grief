package antientitygrief;

import antientitygrief.commands.CommandController;
import antientitygrief.config.ConfigParser;
import antientitygrief.config.Configs;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.core.jmx.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntiEntityGrief implements ModInitializer {
	public static final String MOD_ID = "antientitygrief";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Level overworld;

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
				CommandController.register(dispatcher, registryAccess));

		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			overworld = server.getLevel(ServerLevel.OVERWORLD);
			Configs.applyCalculatedCapabilities();
		});

		ConfigParser.loadConfig();

        LOGGER.info("{} loaded.", LOGGER.getName());
	}
}