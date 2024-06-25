package com.kneelawk.kgui.core.api.client.widget;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;

import com.kneelawk.kgui.engine.api.prop.SimpleSocket;
import com.kneelawk.kgui.engine.api.prop.Socket;

public class KLabel extends KAbstractStringWidget {
    private final Socket<Float> alignX = SimpleSocket.ofWritable(0.5f);
    private final Socket<Float> alignY = SimpleSocket.ofWritable(0.5f);
    private final Socket<Boolean> dropShadow = SimpleSocket.ofWritable(false);

    public KLabel() {
    }

    public KLabel(int x, int y, Component message, Font font) {
        this(x, y, font.width(message.getVisualOrderText()), font.lineHeight, message);
        setFont(font);
    }

    public KLabel(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    public Socket<Float> socketAlignX() {
        return alignX;
    }

    public Socket<Float> socketAlignY() {
        return alignY;
    }

    public Socket<Boolean> socketDropShadow() {
        return dropShadow;
    }

    public float getAlignX() {
        return alignX.get();
    }

    public void setAlignX(float alignX) {
        this.alignX.set(alignX);
    }

    public float getAlignY() {
        return alignY.get();
    }

    public void setAlignY(float alignY) {
        this.alignY.set(alignY);
    }

    public boolean isDropShadow() {
        return dropShadow.get();
    }

    public void setDropShadow(boolean dropShadow) {
        this.dropShadow.set(dropShadow);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Component component = getMessage();
        Font font = getFont();
        int width = getWidth();
        int fullTextWidth = font.width(component);

        FormattedCharSequence text;
        if (fullTextWidth > width) {
            text = clipText(component, width, font);
        } else {
            text = component.getVisualOrderText();
        }

        int textWidth = font.width(text);
        int height = getHeight();
        int textHeight = font.lineHeight;
        int x = getX() + Math.round(getAlignX() * (float) (width - textWidth));
        int y = getY() + Math.round(getAlignY() * (float) (height - textHeight));

        guiGraphics.drawString(font, text, x, y, getColor(), isDropShadow());
    }

    private static FormattedCharSequence clipText(Component message, int width, Font font) {
        FormattedText formatted = font.substrByWidth(message, width - font.width(CommonComponents.ELLIPSIS));
        return Language.getInstance().getVisualOrder(FormattedText.composite(formatted, CommonComponents.ELLIPSIS));
    }
}
