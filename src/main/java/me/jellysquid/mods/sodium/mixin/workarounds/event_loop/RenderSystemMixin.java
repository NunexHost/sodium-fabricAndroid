package me.jellysquid.mods.sodium.mixin.workarounds.event_loop;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {

    private static boolean firstPollRemoved = false; // Flag to track removal

    @Redirect(method = "flipFrame", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;pollEvents()V", ordinal = 0))
    private static void removeFirstPollIfNecessary() {
        if (!firstPollRemoved) {
            firstPollRemoved = true; // Prevent further removals
            return; // Skip the first poll
        }

        // Call custom poll events method
        pollEvents();
    }

    private static void pollEvents() {
        // Simulate the behavior of the original pollEvents() method
        // ...
    }
}
