package sh.talonfox.vulpes_std.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import sh.talonfox.vulpes_std.modmenu.VulpesButton;
import sh.talonfox.vulpes_std.modmenu.VulpesModMenuScreen;

import java.util.List;
import java.util.Objects;

@Mixin(PauseScreen.class)
public class PauseScreenMixin extends Screen {

    protected PauseScreenMixin(Component $$0) {
        super($$0);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        VulpesButton.ticks += 1;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/GridLayout;visitWidgets(Ljava/util/function/Consumer;)V"), method = "createPauseMenu", locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void buttonOverride(CallbackInfo ci, GridLayout layout, GridLayout.RowHelper helper, Component text) {
        if (layout != null) {
            final List<LayoutElement> buttons = ((AccessorGridLayout)layout).getChildren();
            for (int i = 0; i < buttons.size(); i++) {
                LayoutElement widget = buttons.get(i);
                if(widget instanceof Button) {
                    if (Objects.equals(((Button) widget).getMessage().getString(), Component.translatable("menu.reportBugs").getString())) {
                        buttons.set(i, new VulpesButton(widget.getX(), widget.getY(), widget.getWidth(), widget.getHeight(), Component.literal("Mods"), (x) -> {
                            Minecraft.getInstance().setScreen(new VulpesModMenuScreen(((PauseScreen) (Object) this)));
                        }));
                    }
                }
            }
        }
    }

}
