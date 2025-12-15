package me._12emin34.moremousekeybinds.platform.fabric;

//? fabric {

import me._12emin34.moremousekeybinds.MoreMouseKeybinds;
import me._12emin34.moremousekeybinds.platform.Platform;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;

@SuppressWarnings("unused")
public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		MoreMouseKeybinds.onInitialize();
		initConditionalCommonResources();
	}

	private void initConditionalCommonResources() {
		FabricLoader.getInstance().getModContainer(MoreMouseKeybinds.MOD_ID).ifPresent(modContainer ->
				ResourceManagerHelper.registerBuiltinResourcePack(
						MoreMouseKeybinds.id(MoreMouseKeybinds.xplat().packPath(Platform.VersionedPackType.DATA)),
						modContainer,
						ResourcePackActivationType.ALWAYS_ENABLED
				)
		);
	}
}
//?}
