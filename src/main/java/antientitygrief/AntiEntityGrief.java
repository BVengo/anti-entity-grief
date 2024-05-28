package antientitygrief;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntiEntityGrief implements ModInitializer {
	public static final String MOD_ID = "antientitygrief";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
				CommandController.register(dispatcher, registryAccess));

		LOGGER.info(LOGGER.getName() + " loaded.");
	}
}