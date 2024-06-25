package com.kneelawk.kgui.core.api.client.widget;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import com.kneelawk.kgui.core.api.client.prop.MinecraftFontProp;
import com.kneelawk.kgui.engine.api.prop.SimpleSocket;
import com.kneelawk.kgui.engine.api.prop.Socket;

public abstract class KAbstractStringWidget extends KAbstractWidget {
    private final Socket<Font> font = SimpleSocket.of(MinecraftFontProp.getInstance());
    private final Socket<Integer> color = SimpleSocket.ofWritable(0xFFFFFF);

    public KAbstractStringWidget() {
    }

    public KAbstractStringWidget(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    public Socket<Font> socketFont() {
        return font;
    }

    public Socket<Integer> socketColor() {
        return color;
    }

    public void setColor(int color) {
        this.color.set(color);
    }

    public int getColor() {
        return color.get();
    }

    public void setFont(Font font) {
        this.font.set(font);
    }

    public Font getFont() {
        return font.get();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }
}
