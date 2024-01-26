package me.jellysquid.mods.sodium.mixin.workarounds.context_creation;

import me.jellysquid.mods.sodium.client.compatibility.checks.ModuleScanner;
import me.jellysquid.mods.sodium.client.compatibility.checks.LateDriverScanner;
import net.minecraft.client.WindowEventHandler;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.util.MonitorTracker;
import net.minecraft.client.util.Window;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class WindowMixin {
    @Shadow
    @Final
    private static Logger LOGGER;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwCreateWindow(IILjava/lang/CharSequence;JJ)J"))
    private void postWindowCreated(WindowEventHandler eventHandler, MonitorTracker monitorTracker, WindowSettings settings, String videoMode, String title, CallbackInfo ci) {
        LateDriverScanner.onContextInitialized();
        ModuleScanner.checkModules();
    }

    @Inject(method = "swapBuffers", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;flipFrame(J)V", shift = At.Shift.AFTER))
    private void preSwapBuffers(CallbackInfo ci) {
        LOGGER.warn("GLFW-related features are not available for PojavLauncher.");
    }
}
