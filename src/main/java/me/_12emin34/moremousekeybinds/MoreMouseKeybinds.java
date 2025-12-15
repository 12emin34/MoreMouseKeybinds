package me._12emin34.moremousekeybinds;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me._12emin34.moremousekeybinds.platform.Platform;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? fabric {
import me._12emin34.moremousekeybinds.platform.fabric.FabricPlatform;
//?} neoforge {
/*import me._12emin34.moremousekeybinds.platform.neoforge.NeoforgePlatform;
*///?}

@SuppressWarnings("LoggingSimilarMessage")
public class MoreMouseKeybinds {

	public static final String MOD_ID = /*$ mod_id*/ "moremousekeybinds";
	public static final String MOD_VERSION = /*$ mod_version*/ "3.0.0";
	public static final String MOD_FRIENDLY_NAME = /*$ mod_name*/ "MoreMouseKeybinds";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Identifier CONFIG_ID = id("config");
	private static final Platform PLATFORM = createPlatformInstance();

	public static void onInitialize() {
	}

	public static void onInitializeClient() {
		MoreMouseKeybindsClient.init();
	}

	public static Platform xplat() {
		return PLATFORM;
	}

	private static Platform createPlatformInstance() {
		//? fabric {
		return new FabricPlatform();
		//?} neoforge {
		/*return new NeoforgePlatform();
		*///?}
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static void debugLog(String message, Object ... args) {
		if (PLATFORM.isDebug()) LOGGER.info(message, args);
	}
}
