package me._12emin34.moremousekeybinds.fabric.client;

import me._12emin34.moremousekeybinds.client.MoreMouseKeybindsClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.HitResult;

@Environment(EnvType.CLIENT)
public class MoreMouseKeybindsClientFabric implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        MoreMouseKeybindsClient.initClient();
        ClientPreAttackCallback.EVENT.register(this::onPreAttack);
    }

    private boolean onPreAttack(Minecraft minecraft, LocalPlayer localPlayer, int i) {
        if (MoreMouseKeybindsClient.shouldCancelSwingWhenCoolingDown && (localPlayer != null && localPlayer.getAttackStrengthScale(0.0F) != 1.0F)) {
            return true;
        } else {
            return MoreMouseKeybindsClient.shouldCancelSwingWhenNoTarget && (minecraft.hitResult != null && minecraft.hitResult.getType() == HitResult.Type.MISS);
        }
    }
}
