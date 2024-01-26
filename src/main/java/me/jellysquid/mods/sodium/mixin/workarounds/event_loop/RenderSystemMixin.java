package me.jellysquid.mods.sodium.mixin.workarounds.event_loop;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.Tessellator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {

    // Instead of overwriting, inject code at a suitable point
    @Inject(method = "flipFrame", at = @At("HEAD"), cancellable = true)
    private static void injectFrameHandling(long window, CallbackInfo ci) {
        // Perform essential actions from flipFrame:
        RenderSystem.replayQueue();

        // Cancel the original flipFrame method
        ci.cancel();
    }
}
