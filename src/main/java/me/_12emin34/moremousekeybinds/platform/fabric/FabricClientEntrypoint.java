package me._12emin34.moremousekeybinds.platform.fabric;

//? fabric {

import me._12emin34.moremousekeybinds.MoreMouseKeybinds;
import me._12emin34.moremousekeybinds.MoreMouseKeybindsClient;
import me._12emin34.moremousekeybinds.platform.Platform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.HitResult;

@SuppressWarnings("unused")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		MoreMouseKeybinds.onInitializeClient();
		ClientPreAttackCallback.EVENT.register(this::onPreAttack);
		initConditionalClientResources();
	}

	private boolean onPreAttack(Minecraft minecraft, LocalPlayer localPlayer, int i) {
		if (MoreMouseKeybindsClient.shouldCancelSwingWhenCoolingDown && (localPlayer != null && localPlayer.getAttackStrengthScale(0.0F) != 1.0F)) {
			return true;
		} else {
			return MoreMouseKeybindsClient.shouldCancelSwingWhenNoTarget && (minecraft.hitResult != null && minecraft.hitResult.getType() == HitResult.Type.MISS);
		}
	}

	private void initConditionalClientResources() {
		FabricLoader.getInstance().getModContainer(MoreMouseKeybinds.MOD_ID).ifPresent(modContainer ->
				ResourceManagerHelper.registerBuiltinResourcePack(
						MoreMouseKeybinds.id(MoreMouseKeybinds.xplat().packPath(Platform.VersionedPackType.ASSETS)),
						modContainer,
						ResourcePackActivationType.ALWAYS_ENABLED
				)
		);
	}
}
//?}
