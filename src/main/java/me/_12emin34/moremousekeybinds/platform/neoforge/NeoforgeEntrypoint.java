package me._12emin34.moremousekeybinds.platform.neoforge;

//? neoforge {

/*import me._12emin34.moremousekeybinds.MoreMouseKeybinds;
import me._12emin34.moremousekeybinds.platform.Platform;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@Mod(MoreMouseKeybinds.MOD_ID)
@EventBusSubscriber(modid = MoreMouseKeybinds.MOD_ID)
public class NeoforgeEntrypoint {

	@SubscribeEvent
	private static void onCommonSetup(FMLCommonSetupEvent event) {
		MoreMouseKeybinds.onInitialize();
	}

	@SubscribeEvent
	private static void initCommonResources(AddPackFindersEvent event) {
		event.addPackFinders(
				MoreMouseKeybinds.id(MoreMouseKeybinds.xplat().packPath(Platform.VersionedPackType.DATA)),
				PackType.SERVER_DATA,
				Component.literal("Mod " + MoreMouseKeybinds.xplat().mcVersion() + " Data Pack"),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
	}
}
*///?}
