package com.kneelawk.kgui.core.api.client.widget;

import org.jetbrains.annotations.Nullable;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

import com.kneelawk.kgui.engine.api.prop.SimpleSocket;
import com.kneelawk.kgui.engine.api.prop.Socket;

public class KButton extends KAbstractButton {
    private final Socket<@Nullable Runnable> onPress = SimpleSocket.ofWritable(null);

    public KButton() {
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.setColor(1f, 1f, 1f, getAlpha());
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        // TODO: theming
        guiGraphics.blitSprite(SPRITES.get(active, isHoveredOrFocused()), getX(), getY(), getWidth(), getHeight());
        guiGraphics.setColor(1f, 1f, 1f, 1f);
        int color = active ? 0xFFFFFF : 0xA0A0A0;
        renderString(guiGraphics, minecraft.font, color | Mth.ceil(getAlpha() * 255f) << 24);
    }

    public void renderString(GuiGraphics guiGraphics, Font font, int color) {
        renderScrollingString(guiGraphics, font, 2, color);
    }

    public Socket<@Nullable Runnable> socketOnPress() {
        return onPress;
    }

    public void setOnPress(@Nullable Runnable onPress) {
        this.onPress.set(onPress);
    }

    @Override
    public void firePress() {
        Runnable press = onPress.get();
        if (press != null) press.run();
    }
}
