package me._12emin34.moremousekeybinds.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(KeyMapping.class)
public interface KeyMappingAccessor {
	@Accessor("key")
	InputConstants.Key getKey();
}
