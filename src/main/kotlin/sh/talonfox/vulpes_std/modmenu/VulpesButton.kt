package sh.talonfox.vulpes_std.modmenu

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import kotlin.math.abs
import kotlin.math.sin

class VulpesButton(x: Int, y: Int, width: Int, height: Int, text: Component, press: OnPress) : Button(x,y,width,height,text,press,DEFAULT_NARRATION) {
    private companion object {
        @JvmField
        var ticks: Long = 0
    }

    private val vulpes_logo = ResourceLocation("vulpes:textures/vulpes.png")

    override fun renderWidget(gfx: GuiGraphics, mouseX: Int, mouseY: Int, d: Float) {
        if (mouseX >= x && (mouseX <= x + width) and (mouseY >= y) && mouseY <= y + height) {
            val intensity = (abs(sin(Math.toRadians((ticks * 9).toDouble()))) * 128).toInt()
            gfx.fill(
                x,
                y,
                x + width,
                y + height,
                (0x80000000.toUInt() or (intensity.toUInt() shl 16) or (intensity.toUInt() shl 8) or intensity.toUInt()).toInt()
            )
        } else {
            ticks = 5
            gfx.fill(
                x,
                y,
                x + width,
                y + height,
                0x80000000.toInt()
            )
        }
        gfx.blit(
            vulpes_logo,
            x,
            y,
            height,
            height,
            0f,
            0f,
            512,
            512,
            512,
            512
        )
        gfx.drawCenteredString(
            Minecraft.getInstance().font,
            message,
            x + (width/2),
            y + ((height/2)-(9/2)),
            0xffffff
        )
    }
}