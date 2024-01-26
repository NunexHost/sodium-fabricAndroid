package me.jellysquid.mods.sodium.mixin.workarounds.event_loop;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.Tessellator
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {

    @Overwrite(remap = false)
    public static void flipFrame(long window) {
        RenderSystem.replayQueue();
        Tesselator.getInstance().getBuilder().clear();
    }
}
