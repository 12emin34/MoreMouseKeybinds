package me._12emin34.moremousekeybinds.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import eu.midnightdust.lib.config.MidnightConfig;
import me._12emin34.moremousekeybinds.ModConstants;
import me._12emin34.moremousekeybinds.compat.ComponentHack;
import me._12emin34.moremousekeybinds.config.ModConfig;
import me._12emin34.moremousekeybinds.mixin.KeyMappingAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.HitResult;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class MoreMouseKeybindsClient {
    static final boolean SHOULD_USE_LEGACY_TEXT = shouldUseLegacyText();
    static boolean shouldHoldAttack = false;
    static boolean shouldHoldUse = false;
    static boolean shouldPeriodicAttack = false;
    static boolean shouldHoldKeyToAttack = false;
    static boolean shouldCancelSwingWhenCoolingDown = false;
    static boolean shouldCancelSwingWhenNoTarget = false;
    static int periodicAttackCounter = 0;
    static KeyMapping holdAttack = new KeyMapping(
            "key.moremousekeybinds.holdattack",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            ModConstants.KEYBINDING_CATEGORY
    );

    static KeyMapping holdUse = new KeyMapping(
            "key.moremousekeybinds.holduse",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            ModConstants.KEYBINDING_CATEGORY
    );

    static KeyMapping periodicAttack = new KeyMapping(
            "key.moremousekeybinds.periodicattack",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            ModConstants.KEYBINDING_CATEGORY
    );

    static KeyMapping toggleHoldToAttack = new KeyMapping(
            "key.moremousekeybinds.toggleholdtoattack",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            ModConstants.KEYBINDING_CATEGORY
    );

    static KeyMapping toggleCancelSwingWhenCoolingDown = new KeyMapping(
            "key.moremousekeybinds.togglecancelswingwhencoolingdown",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            ModConstants.KEYBINDING_CATEGORY
    );

    static KeyMapping toggleCancelSwingWhenNoTarget = new KeyMapping(
            "key.moremousekeybinds.togglecancelswingwhennotarget",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            ModConstants.KEYBINDING_CATEGORY
    );

    public static void registerKeyMappings() {
        KeyMappingRegistry.register(holdAttack);
        KeyMappingRegistry.register(holdUse);
        KeyMappingRegistry.register(periodicAttack);
        KeyMappingRegistry.register(toggleHoldToAttack);
        KeyMappingRegistry.register(toggleCancelSwingWhenCoolingDown);
        KeyMappingRegistry.register(toggleCancelSwingWhenNoTarget);
    }

    public static void initClient() {
        MidnightConfig.init("moremousekeybinds", ModConfig.class);
        ClientTickEvent.CLIENT_PRE.register(MoreMouseKeybindsClient::onStartTick);
        ClientTickEvent.CLIENT_POST.register(MoreMouseKeybindsClient::onEndTick);
        ClientRawInputEvent.MOUSE_CLICKED_PRE.register(MoreMouseKeybindsClient::onPreAttack);
        registerKeyMappings();
    }

    private static EventResult onPreAttack(Minecraft minecraft, int i, int i1, int i2) {
        if (shouldCancelSwingWhenCoolingDown && (minecraft.player != null && minecraft.player.getAttackStrengthScale(0.0F) != 1.0F)) {
            return EventResult.interruptFalse();
        } else if (shouldCancelSwingWhenNoTarget && (minecraft.hitResult != null && minecraft.hitResult.getType() == HitResult.Type.MISS)) {
            return EventResult.interruptFalse();
        }
        return EventResult.pass();
    }

    private static void sendToggleMessage(boolean optionToCheck, String message, Minecraft client) {
        if (client.player == null) return;

        if (SHOULD_USE_LEGACY_TEXT) {
            client.player.displayClientMessage((Component) ComponentHack.literal(message + ((optionToCheck) ? "ON" : "OFF")), true);
        } else {
            client.player.displayClientMessage(Component.literal(message + ((optionToCheck) ? "ON" : "OFF")), true);
        }
    }

    private static void onStartTick(Minecraft client) {
        if (shouldHoldKeyToAttack && (client.player != null && client.player.getAttackStrengthScale(0.0F) == 1.0F) && client.options.keyAttack.isDown()) {
            KeyMapping.click(((KeyMappingAccessor) client.options.keyAttack).getKey());
        }
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

        if (ModConfig.periodicAttackMatchCooldownSpeed) {
            if (client.player != null && (shouldPeriodicAttack && client.player.getAttackStrengthScale(0.0F) == 1.0F)) {
                KeyMapping.click(((KeyMappingAccessor) attackKeybinding).getKey());
            }
        } else {
            if (periodicAttackCounter > ModConfig.periodicAttackDelay) {
                periodicAttackCounter = 0;
                KeyMapping.click(((KeyMappingAccessor) attackKeybinding).getKey());
            } else if (shouldPeriodicAttack) {
                periodicAttackCounter++;
            }
        }
    }

    private static boolean shouldUseLegacyText() {
        try {
            Class.forName("net.minecraft.class_2561").getMethod("method_43470", String.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            return true;
        }
        return false;
    }
}
