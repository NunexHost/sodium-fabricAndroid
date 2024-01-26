package me.jellysquid.mods.sodium.mixin.workarounds.event_loop;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {

    @Overwrite(remap = false)
    public static void flipFrame(long window) {
        pollEvents();
        RenderSystem.replayQueue();
        Tesselator.getInstance().getBuilder().clear();
        pollEvents();

    }
}
