package com.kneelawk.kgui.example.properties;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class ClientEntrypoint implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(PropertiesMod.OPEN_GUI);

        PropertiesMod.initClient();

        ClientTickEvents.END_CLIENT_TICK.register(PropertiesMod::endClientTick);
    }
}
