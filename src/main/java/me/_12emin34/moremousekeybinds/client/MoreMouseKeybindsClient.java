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

    @Override
    public void onInitializeClient() {
        boolean useLegacyText = MinecraftVersion.CURRENT.getName().startsWith("1.18");
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
                if (client.player != null) {
                    if (useLegacyText) {
                        if (shouldHoldAttack) {
                            client.player.sendMessage((Text) TextHack.literal("Hold attack button: ON"), true);
                        } else {
                            client.player.sendMessage((Text) TextHack.literal("Hold attack button: OFF"), true);
                        }
                    } else {
                        if (shouldHoldAttack) {
                            client.player.sendMessage(Text.literal("Hold attack button: ON"), true);
                        } else {
                            client.player.sendMessage(Text.literal("Hold attack button: OFF"), true);
                        }
                    }


                }
            }

            while (holdUse.wasPressed()) {
                shouldHoldUse = !shouldHoldUse;
                useKeybinding.setPressed(shouldHoldUse);
                if (client.player != null) {
                    if (useLegacyText) {
                        if (shouldHoldUse) {
                            client.player.sendMessage((Text) TextHack.literal("Hold use button: ON"), true);
                        } else {
                            client.player.sendMessage((Text) TextHack.literal("Hold use button: OFF"), true);
                        }
                    } else {
                        if (shouldHoldUse) {
                            client.player.sendMessage(Text.literal("Hold use button: ON"), true);
                        } else {
                            client.player.sendMessage(Text.literal("Hold use button: OFF"), true);
                        }
                    }
                }
            }

            while (periodicAttack.wasPressed()) {
                shouldPeriodicAttack = !shouldPeriodicAttack;
                periodicAttackCounter = 0;
                if (client.player != null) {
                    if (useLegacyText) {
                        if (shouldPeriodicAttack) {
                            client.player.sendMessage((Text) TextHack.literal("Periodic attack: ON"), true);
                        } else {
                            client.player.sendMessage((Text) TextHack.literal("Periodic attack: OFF"), true);
                        }
                    } else {
                        if (shouldPeriodicAttack) {
                            client.player.sendMessage(Text.literal("Periodic attack: ON"), true);
                        } else {
                            client.player.sendMessage(Text.literal("Periodic attack: OFF"), true);
                        }
                    }
                }
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
}
