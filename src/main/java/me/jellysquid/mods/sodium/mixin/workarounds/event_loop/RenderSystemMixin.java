package me.jellysquid.mods.sodium.mixin.workarounds.event_loop;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {

    private static boolean isPojav() {
        // **Exemplo de detecção Pojav:**
        // Verifica se a classe PojavLauncher está presente
        try {
            Class.forName("me.jellysquid.mods.sodium.client.gui.PojavLauncher");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Redirect(method = "flipFrame", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;pollEvents()V", ordinal = 0))
    private static void removeFirstPollIfNecessary() {
        if (isPojav()) {
            // **Opção 1: No-op para Pojav:**
            // Não faz nada, evita a primeira chamada de pollEvents
        } else {
            RenderSystem.pollEvents(); // Permite o comportamento normal em outros ambientes
        }
    }
}
