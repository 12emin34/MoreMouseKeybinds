package me._12emin34.moremousekeybinds.fabric.client;

import me._12emin34.moremousekeybinds.client.MoreMouseKeybindsClient;
import net.fabricmc.api.ClientModInitializer;

public class MoreMouseKeybindsClientFabric implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        MoreMouseKeybindsClient.initClient();
    }
}
