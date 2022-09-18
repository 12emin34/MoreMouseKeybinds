package me._12emin34.moremousekeybinds.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class MoreMouseKeybindsClient implements ClientModInitializer {
    private static KeyBinding holdAttack;
    private static KeyBinding holdUse;
    private static KeyBinding periodicAttack;
    boolean shouldHoldAttack = false;
    boolean shouldHoldUse = false;
    boolean shouldPeriodicAttack = false;
//    int periodicAttackCounter = 0;

    @Override
    public void onInitializeClient() {
        holdAttack = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.moremousekeybinds.holdattack",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.moremousekeybinds.general"
        ));

        holdUse = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.moremousekeybinds.holduse",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                "category.moremousekeybinds.general"
        ));

        periodicAttack = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.moremousekeybinds.periodicattack",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                "category.moremousekeybinds.general"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            GameOptions options = client.options;
            KeyBinding attackKeybinding = options.attackKey;
            KeyBinding useKeybinding = options.useKey;

            while (holdAttack.wasPressed()) {
                shouldHoldAttack = !shouldHoldAttack;
                attackKeybinding.setPressed(shouldHoldAttack);
            }

            while (holdUse.wasPressed()) {
                shouldHoldUse = !shouldHoldUse;
                useKeybinding.setPressed(shouldHoldUse);
            }

            while (periodicAttack.wasPressed()) {
                shouldPeriodicAttack = !shouldPeriodicAttack;
            }

//            if (periodicAttackCounter > 40) {
//                periodicAttackCounter = 0;
//            } else if (shouldPeriodicAttack) {
//                periodicAttackCounter++;
//            }

            if (client.player != null && (shouldPeriodicAttack && client.player.getAttackCooldownProgress(0.0F) == 1.0F)) {
                KeyBinding.onKeyPressed(attackKeybinding.getDefaultKey());
            }
        });
    }
}
