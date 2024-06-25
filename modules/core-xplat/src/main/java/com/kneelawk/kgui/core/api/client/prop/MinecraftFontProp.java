package com.kneelawk.kgui.core.api.client.prop;

import java.util.function.Consumer;

import org.jetbrains.annotations.UnknownNullability;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

import com.kneelawk.kgui.engine.api.prop.ListenerSet;
import com.kneelawk.kgui.engine.api.prop.Prop;

public class MinecraftFontProp implements Prop<Font> {
    private static final MinecraftFontProp INSTANCE = new MinecraftFontProp();

    public static MinecraftFontProp getInstance() {
        return INSTANCE;
    }

    private final ListenerSet<Font> listeners = new ListenerSet<>();

    private MinecraftFontProp() {}

    @Override
    public @UnknownNullability Font get() {
        return Minecraft.getInstance().font;
    }

    @Override
    public void addWeakListener(Consumer<Font> listener) {
    }

    @Override
    public void addStrongListener(Consumer<Font> listener) {
    }

    @Override
    public void removeListener(Object listener) {
    }
}
