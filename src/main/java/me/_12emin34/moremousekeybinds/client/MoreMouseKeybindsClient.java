package me._12emin34.moremousekeybinds.client;

import eu.midnightdust.lib.config.MidnightConfig;
import me._12emin34.moremousekeybinds.ModConstants;
import me._12emin34.moremousekeybinds.compat.TextHack;
import me._12emin34.moremousekeybinds.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class MoreMouseKeybindsClient implements ClientModInitializer {
    final boolean useLegacyText = shouldUseLegacyText();
    boolean shouldHoldAttack = false;
    boolean shouldHoldUse = false;
    boolean shouldPeriodicAttack = false;
    int periodicAttackCounter = 0;
    KeyBinding holdAttack = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.moremousekeybinds.holdattack",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            ModConstants.KEYBINDING_CATEGORY
    ));

    KeyBinding holdUse = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.moremousekeybinds.holduse",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            ModConstants.KEYBINDING_CATEGORY
    ));

    KeyBinding periodicAttack = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.moremousekeybinds.periodicattack",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_N,
            ModConstants.KEYBINDING_CATEGORY
    ));

    @Override
    public void onInitializeClient() {
        MidnightConfig.init("moremousekeybinds", ModConfig.class);
        ClientTickEvents.END_CLIENT_TICK.register(this::onEndTick);
    }

    public void sendToggleMessage(boolean optionToCheck, String message, MinecraftClient client) {
        if (client.player == null) return;

        if (useLegacyText) {
            client.player.sendMessage((Text) TextHack.literal(message + ((optionToCheck) ? "ON" : "OFF")), true);
        } else {
            client.player.sendMessage(Text.literal(message + ((optionToCheck) ? "ON" : "OFF")), true);
        }
    }

    public boolean shouldUseLegacyText() {
        try {
            Class.forName("net.minecraft.class_2561").getMethod("method_43470", String.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            return true;
        }
        return false;
    }

    private void onEndTick(MinecraftClient client) {
        GameOptions options = client.options;
        KeyBinding attackKeybinding = options.attackKey;
        KeyBinding useKeybinding = options.useKey;

        while (holdAttack.wasPressed()) {
            shouldHoldAttack = !shouldHoldAttack;
            attackKeybinding.setPressed(shouldHoldAttack);
            sendToggleMessage(shouldHoldAttack, "Hold attack button: ", client);
        }

        while (holdUse.wasPressed()) {
            shouldHoldUse = !shouldHoldUse;
            useKeybinding.setPressed(shouldHoldUse);
            sendToggleMessage(shouldHoldUse, "Hold use button: ", client);
        }

        while (periodicAttack.wasPressed()) {
            shouldPeriodicAttack = !shouldPeriodicAttack;
            periodicAttackCounter = 0;
            sendToggleMessage(shouldPeriodicAttack, "Periodic attack: ", client);
        }

        if (ModConfig.periodicAttackMatchCooldownSpeed) {
            if (client.player != null && (shouldPeriodicAttack && client.player.getAttackCooldownProgress(0.0F) == 1.0F)) {
                KeyBinding.onKeyPressed(KeyBindingHelper.getBoundKeyOf(attackKeybinding));
            }
        } else {
            if (periodicAttackCounter > ModConfig.periodicAttackDelay) {
                periodicAttackCounter = 0;
                KeyBinding.onKeyPressed(KeyBindingHelper.getBoundKeyOf(attackKeybinding));
            } else if (shouldPeriodicAttack) {
                periodicAttackCounter++;
            }
        }
    }
}
