package me._12emin34.moremousekeybinds.mixin;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me._12emin34.moremousekeybinds.MoreMouseKeybinds;
import net.minecraft.client.gui.screens.packs.PackSelectionModel;
import net.minecraft.server.packs.repository.Pack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * This just hides the conditionally loaded resource packs from the resource pack and datapack selection UIs to prevent clutter.
 */
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(PackSelectionModel.class)
public class PackSelectionModelMixin {

    @Shadow @Final List<Pack> selected;
    @Shadow @Final List<Pack> unselected;

    @Inject(
            method = "findNewPacks",
            at = @At("TAIL")
    )
    private void filterPacks(CallbackInfo ci) {
        selected.removeIf(pack -> pack.getId().contains(MoreMouseKeybinds.MOD_ID));
        unselected.removeIf(pack -> pack.getId().contains(MoreMouseKeybinds.MOD_ID));
    }
}
