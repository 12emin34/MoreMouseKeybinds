package me._12emin34.moremousekeybinds;

import com.mojang.blaze3d.platform.InputConstants;
import me._12emin34.moremousekeybinds.config.ModClientConfig;
import me._12emin34.moremousekeybinds.mixin.KeyMappingAccessor;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.HitResult;
import org.lwjgl.glfw.GLFW;

//? fabric {
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
//?}

public class MoreMouseKeybindsClient {
	public static final Identifier CLIENT_CONFIG_ID = MoreMouseKeybinds.id("client_config");
	private static final KeyMapping.Category GENERAL_MOD_CATEGORY = KeyMapping.Category.register(MoreMouseKeybinds.id("general"));
	public static ModClientConfig CLIENT_CONFIG = ConfigApiJava.registerAndLoadConfig(ModClientConfig::new, RegisterType.CLIENT);
	public static boolean shouldCancelSwingWhenCoolingDown = false;
	public static boolean shouldCancelSwingWhenNoTarget = false;
	public static boolean swingCancelledWhenCoolingDown = false;
	public static boolean swingCancelledWhenNoTarget = false;
	static boolean shouldHoldAttack = false;
	static boolean shouldHoldUse = false;
	static boolean shouldPeriodicAttack = false;
	static boolean shouldHoldKeyToAttack = false;
	static int periodicAttackCounter = 0;

	static KeyMapping holdAttack = new KeyMapping(
			"key.moremousekeybinds.holdattack",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_UNKNOWN,
			GENERAL_MOD_CATEGORY
	);
	static KeyMapping holdUse = new KeyMapping(
			"key.moremousekeybinds.holduse",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_UNKNOWN,
			GENERAL_MOD_CATEGORY
	);
	static KeyMapping periodicAttack = new KeyMapping(
			"key.moremousekeybinds.periodicattack",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_UNKNOWN,
			GENERAL_MOD_CATEGORY
	);
	static KeyMapping toggleHoldToAttack = new KeyMapping(
			"key.moremousekeybinds.toggleholdtoattack",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_UNKNOWN,
			GENERAL_MOD_CATEGORY
	);
	static KeyMapping toggleCancelSwingWhenCoolingDown = new KeyMapping(
			"key.moremousekeybinds.togglecancelswingwhencoolingdown",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_UNKNOWN,
			GENERAL_MOD_CATEGORY
	);

	static KeyMapping toggleCancelSwingWhenNoTarget = new KeyMapping(
			"key.moremousekeybinds.togglecancelswingwhennotarget",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_UNKNOWN,
			GENERAL_MOD_CATEGORY
	);

	public static void registerKeyMappings() {
		//? fabric {
		KeyBindingHelper.registerKeyBinding(holdAttack);
		KeyBindingHelper.registerKeyBinding(holdUse);
		KeyBindingHelper.registerKeyBinding(periodicAttack);
		KeyBindingHelper.registerKeyBinding(toggleHoldToAttack);
		KeyBindingHelper.registerKeyBinding(toggleCancelSwingWhenCoolingDown);
		KeyBindingHelper.registerKeyBinding(toggleCancelSwingWhenNoTarget);
		//?}
	}

	public static void init() {
		//? fabric {
		ClientTickEvents.START_CLIENT_TICK.register(MoreMouseKeybindsClient::onStartTick);
		ClientTickEvents.END_CLIENT_TICK.register(MoreMouseKeybindsClient::onEndTick);
		//?}
		registerKeyMappings();
	}

	private static void sendToggleMessage(boolean optionToCheck, String message, Minecraft client) {
		if (client.player == null) return;

		client.player.displayClientMessage(Component.literal(message + ((optionToCheck) ? "ON" : "OFF")), true);
	}

	private static void onStartTick(Minecraft client) {
		if (shouldHoldKeyToAttack && (client.player != null && client.player.getAttackStrengthScale(0.0F) == 1.0F) && client.options.keyAttack.isDown()) {
			KeyMapping.click(((KeyMappingAccessor) client.options.keyAttack).getKey());
		}

		swingCancelledWhenCoolingDown = shouldCancelSwingWhenCoolingDown && (client.player != null && client.player.getAttackStrengthScale(0.0F) != 1.0F);
		swingCancelledWhenNoTarget = shouldCancelSwingWhenNoTarget && (client.hitResult != null && client.hitResult.getType() == HitResult.Type.MISS);
	}

	private static void onEndTick(Minecraft client) {
		Options options = client.options;
		KeyMapping attackKeybinding = options.keyAttack;
		KeyMapping useKeybinding = options.keyUse;

		while (holdAttack.consumeClick()) {
			shouldHoldAttack = !shouldHoldAttack;
			attackKeybinding.setDown(shouldHoldAttack);
			sendToggleMessage(shouldHoldAttack, "Hold attack button: ", client);
		}

		while (holdUse.consumeClick()) {
			shouldHoldUse = !shouldHoldUse;
			useKeybinding.setDown(shouldHoldUse);
			sendToggleMessage(shouldHoldUse, "Hold use button: ", client);
		}

		while (periodicAttack.consumeClick()) {
			shouldPeriodicAttack = !shouldPeriodicAttack;
			periodicAttackCounter = 0;
			sendToggleMessage(shouldPeriodicAttack, "Periodic attack: ", client);
		}

		while (toggleHoldToAttack.consumeClick()) {
			shouldHoldKeyToAttack = !shouldHoldKeyToAttack;
			sendToggleMessage(shouldHoldKeyToAttack, "Hold key to attack: ", client);
		}

		while (toggleCancelSwingWhenCoolingDown.consumeClick()) {
			shouldCancelSwingWhenCoolingDown = !shouldCancelSwingWhenCoolingDown;
			sendToggleMessage(shouldCancelSwingWhenCoolingDown, "Attack only when cooldown full: ", client);
		}

		while (toggleCancelSwingWhenNoTarget.consumeClick()) {
			shouldCancelSwingWhenNoTarget = !shouldCancelSwingWhenNoTarget;
			sendToggleMessage(shouldCancelSwingWhenNoTarget, "Prevent swinging in air: ", client);
		}

		if (CLIENT_CONFIG.periodicAttackMatchCooldownSpeed) {
			if (client.player != null && (shouldPeriodicAttack && client.player.getAttackStrengthScale(0.0F) == 1.0F)) {
				KeyMapping.click(((KeyMappingAccessor) attackKeybinding).getKey());
			}
		} else {
			if (periodicAttackCounter > CLIENT_CONFIG.periodicAttackDelay) {
				periodicAttackCounter = 0;
				KeyMapping.click(((KeyMappingAccessor) attackKeybinding).getKey());
			} else if (shouldPeriodicAttack) {
				periodicAttackCounter++;
			}
		}
	}
}
