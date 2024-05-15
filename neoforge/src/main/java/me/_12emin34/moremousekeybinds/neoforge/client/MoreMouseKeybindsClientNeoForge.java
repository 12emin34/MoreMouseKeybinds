package me._12emin34.moremousekeybinds.neoforge.client;

import me._12emin34.moremousekeybinds.client.MoreMouseKeybindsClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(MoreMouseKeybindsClient.MOD_ID)
@OnlyIn(Dist.CLIENT)
public class MoreMouseKeybindsClientNeoForge {
    public MoreMouseKeybindsClientNeoForge() {
        MoreMouseKeybindsClient.initClient();
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void leftClick(InputEvent.InteractionKeyMappingTriggered event) {
        if (MoreMouseKeybindsClient.swingCancelledWhenCoolingDown || MoreMouseKeybindsClient.swingCancelledWhenNoTarget) {
            event.setSwingHand(false);
            event.setCanceled(true);
        }
    }
}
