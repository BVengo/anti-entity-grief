package antientitygrief;

import antientitygrief.config.ConfigParser;
import antientitygrief.config.EntityConfigs;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntiEntityGrief implements ModInitializer {
	public static final String MOD_ID = "antientitygrief";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final EntityConfigs CONFIGS = EntityConfigs.getInstance();

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
				CommandController.register(dispatcher, registryAccess));

		ConfigParser.loadConfig();

		LOGGER.info(LOGGER.getName() + " loaded.");
	}
}