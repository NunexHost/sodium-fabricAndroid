package me.jellysquid.mods.sodium.mixin.workarounds.event_loop;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.Tessellator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {
    
    @Shadow private static void pollEvents() {}
    
    private static void removeFirstPoll() {
        // noop
        // should fix some bugs with minecraft polling events twice for some reason (why does it do that in the first place?)
    }

    @Overwrite(remap = false)
    public static void flipFrame(long window) {
        // new code added here
        pollEvents();
        RenderSystem.replayQueue();
        Tessellator.getInstance().getBuffer().clear();
        pollEvents();
    }
}
