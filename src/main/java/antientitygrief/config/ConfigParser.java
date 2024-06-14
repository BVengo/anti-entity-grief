package antientitygrief.config;

import antientitygrief.AntiEntityGrief;
import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class ConfigParser {
	private static final File file = new File(FabricLoader.getInstance().getConfigDir().toFile(), AntiEntityGrief.MOD_ID + ".json");
	private static final Gson gson = new GsonBuilder()
			.registerTypeAdapter(Configs.class, new ConfigsSerializer())
			.setPrettyPrinting().create();

	public static void loadConfig() {
		if (!file.exists()) {
			AntiEntityGrief.LOGGER.info("Config file not found. Creating a new one.");
			saveConfig();
			return;
		}

		try (Reader reader = new FileReader(file)) {
			gson.fromJson(reader, Configs.class);
			saveConfig();
		} catch (Exception e) {
			AntiEntityGrief.LOGGER.error("Error reading config file, creating a new one.", e);
			moveOldConfig();
			saveConfig();
		}
	}

	public static void saveConfig() {
		try (Writer writer = new FileWriter(file)) {
			gson.toJson(Configs.getInstance(), writer);
		} catch (IOException e) {
			AntiEntityGrief.LOGGER.error("Unable to save configs to file.", e);
		}
	}

	private static void moveOldConfig() {
		File oldFile = new File(file.getParentFile(), "old." + file.getName());
		if (file.renameTo(oldFile)) {
            AntiEntityGrief.LOGGER.info("Renamed old config file to {}", oldFile.getName());
		} else {
			AntiEntityGrief.LOGGER.error("Failed to rename old config file.");
		}
	}
}