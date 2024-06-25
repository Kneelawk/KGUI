package com.kneelawk.kgui.example.properties;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import com.kneelawk.kgui.core.api.client.widget.KButton;
import com.kneelawk.kgui.core.api.client.widget.KLabel;
import com.kneelawk.kgui.engine.api.prop.Prop;
import com.kneelawk.kgui.engine.api.prop.SimpleWritableProp;
import com.kneelawk.kgui.engine.api.prop.WritableProp;

import static com.kneelawk.kgui.example.properties.PropertiesMod.tt;

public class PropertiesScreen extends Screen {
    private final WritableProp<Integer> pressCount = SimpleWritableProp.of(0);
    private final Prop<Component> buttonText =
        pressCount.map(count -> Component.literal("Button pressed " + count + " times"));
    private final Prop<Component> labelText = pressCount.map(count -> Component.literal("Count: " + count));

    public PropertiesScreen() {
        super(tt("menu", "properties"));
    }

    @Override
    protected void init() {
        KButton button = new KButton();
        button.setSize(100, 20);
        button.setPosition(0, 0);
        button.socketMessage().setProp(buttonText);
        button.setOnPress(() -> pressCount.set(pressCount.get() + 1));
        addRenderableWidget(button);

        KLabel label = new KLabel();
        label.setSize(100, 20);
        label.setPosition(0, 20);
        label.socketMessage().setProp(labelText);
        addRenderableWidget(label);
    }
}
