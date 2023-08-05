package me._12emin34.moremousekeybinds.forge.client;

import dev.architectury.platform.forge.EventBuses;
import me._12emin34.moremousekeybinds.client.MoreMouseKeybindsClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MoreMouseKeybindsClient.MOD_ID)
@OnlyIn(Dist.CLIENT)
public class MoreMouseKeybindsClientForge {
    public MoreMouseKeybindsClientForge() {
        EventBuses.registerModEventBus(MoreMouseKeybindsClient.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        MoreMouseKeybindsClient.initClient();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void leftClick(InputEvent.InteractionKeyMappingTriggered event) {
        if (MoreMouseKeybindsClient.swingCancelledWhenCoolingDown || MoreMouseKeybindsClient.swingCancelledWhenNoTarget) {
            event.setSwingHand(false);
            event.setCanceled(true);
        }
    }
}
