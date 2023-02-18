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
import net.minecraft.MinecraftVersion;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class MoreMouseKeybindsClient implements ClientModInitializer {
    boolean shouldHoldAttack = false;
    boolean shouldHoldUse = false;
    boolean shouldPeriodicAttack = false;
    int periodicAttackCounter = 0;

    boolean useLegacyText = MinecraftVersion.CURRENT.getName().startsWith("1.18");

    @Override
    public void onInitializeClient() {
        MidnightConfig.init("moremousekeybinds", ModConfig.class);

        KeyBinding holdAttack = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.moremousekeybinds.holdattack",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                ModConstants.keybindingCategory
        ));

        KeyBinding holdUse = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.moremousekeybinds.holduse",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                ModConstants.keybindingCategory
        ));

        KeyBinding periodicAttack = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.moremousekeybinds.periodicattack",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                ModConstants.keybindingCategory
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
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
                    KeyBinding.onKeyPressed(attackKeybinding.getDefaultKey());
                }
            } else {
                if (periodicAttackCounter > ModConfig.periodicAttackDelay) {
                    periodicAttackCounter = 0;
                    KeyBinding.onKeyPressed(attackKeybinding.getDefaultKey());
                } else if (shouldPeriodicAttack) {
                    periodicAttackCounter++;
                }
            }
        });
    }

    public void sendToggleMessage(boolean optionToCheck, String message, MinecraftClient client) {
        if (client.player == null) return;
        String tmp = optionToCheck ? "ON" : "OFF";
        if (useLegacyText) {
            client.player.sendMessage((Text) TextHack.literal(message + tmp), true);
        } else {
            client.player.sendMessage(Text.literal(message + tmp), true);
        }
    }
}
