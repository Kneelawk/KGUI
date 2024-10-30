package com.kneelawk.kgui.core.api.client.widget;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;

import com.kneelawk.kgui.engine.api.prop.SimpleSocket;
import com.kneelawk.kgui.engine.api.prop.Socket;

public abstract class KAbstractWidget extends AbstractWidget
    implements KWidget, Renderable, GuiEventListener, LayoutElement, NarratableEntry {
    private final Socket<Integer> width;
    private final Socket<Integer> height;
    private final Socket<Integer> x;
    private final Socket<Integer> y;
    private final Socket<Component> message;
    private final Socket<Float> alpha;

    public KAbstractWidget() {
        this(0, 0, 0, 0, Component.empty());
    }

    public KAbstractWidget(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
        this.width = SimpleSocket.ofWritable(width);
        this.height = SimpleSocket.ofWritable(height);
        this.x = SimpleSocket.ofWritable(x);
        this.y = SimpleSocket.ofWritable(y);
        this.message = SimpleSocket.ofWritable(message);
        alpha = SimpleSocket.ofWritable(1.0f);

        this.width.addStrongListener(super::setWidth);
        this.height.addStrongListener(super::setHeight);
        alpha.addStrongListener(super::setAlpha);
    }

    public Socket<Integer> socketWidth() {
        return width;
    }

    public Socket<Integer> socketHeight() {
        return height;
    }

    public Socket<Integer> socketX() {
        return x;
    }

    public Socket<Integer> socketY() {
        return y;
    }

    public Socket<Component> socketMessage() {
        return message;
    }

    public Socket<Float> socketAlpha() {
        return alpha;
    }

    @Override
    public int getWidth() {
        return width.get();
    }

    @Override
    public void setWidth(int width) {
        this.width.set(width);
    }

    @Override
    public int getHeight() {
        return height.get();
    }

    @Override
    public void setHeight(int height) {
        this.height.set(height);
    }

    @Override
    public int getX() {
        return x.get();
    }

    @Override
    public void setX(int x) {
        this.x.set(x);
    }

    @Override
    public int getY() {
        return this.y.get();
    }

    @Override
    public void setY(int y) {
        this.y.set(y);
    }

    @Override
    public void setSize(int width, int height) {
        this.width.set(width);
        this.height.set(height);
    }

    @Override
    public Component getMessage() {
        return message.get();
    }

    @Override
    public void setMessage(Component message) {
        this.message.set(message);
    }

    public float getAlpha() {
        return alpha.get();
    }

    @Override
    public void setAlpha(float alpha) {
        this.alpha.set(alpha);
    }
}
