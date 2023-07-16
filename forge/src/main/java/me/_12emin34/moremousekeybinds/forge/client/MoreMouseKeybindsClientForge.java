package me._12emin34.moremousekeybinds.forge.client;

import dev.architectury.platform.forge.EventBuses;
import me._12emin34.moremousekeybinds.client.MoreMouseKeybindsClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MoreMouseKeybindsClient.MOD_ID)
@OnlyIn(Dist.CLIENT)
public class MoreMouseKeybindsClientForge {
    public MoreMouseKeybindsClientForge() {
        EventBuses.registerModEventBus(MoreMouseKeybindsClient.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        MoreMouseKeybindsClient.initClient();
    }
}
