package me._12emin34.moremousekeybinds.platform.neoforge;

//? neoforge {

/*import me._12emin34.moremousekeybinds.MoreMouseKeybinds;
import me._12emin34.moremousekeybinds.platform.Platform;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@EventBusSubscriber(modid = MoreMouseKeybinds.MOD_ID, value = Dist.CLIENT)
public class NeoforgeClientEventSubscriber {

	@SubscribeEvent
	public static void onClientSetup(final FMLClientSetupEvent event) {
		MoreMouseKeybinds.onInitializeClient();
	}

	@SubscribeEvent
	private static void initClientResources(AddPackFindersEvent event) {
		event.addPackFinders(
				MoreMouseKeybinds.id(MoreMouseKeybinds.xplat().packPath(Platform.VersionedPackType.ASSETS)),
				PackType.CLIENT_RESOURCES,
				Component.literal("Mod " + MoreMouseKeybinds.xplat().mcVersion() + " Resource Pack"),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
	}
}
*///?}
