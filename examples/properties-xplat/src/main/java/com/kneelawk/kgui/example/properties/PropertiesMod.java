package com.kneelawk.kgui.example.properties;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class PropertiesMod {
    public static final String MOD_ID = "kgui_example_properties";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final KeyMapping OPEN_GUI = new KeyMapping("key." + MOD_ID + ".open_properties",
        InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_P, "category." + MOD_ID + ".keys");

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static MutableComponent tt(String prefix, String suffix, Object... args) {
        return Component.translatable(prefix + "." + MOD_ID + "." + suffix, args);
    }

    public static void initClient() {
        LOGGER.info("Initializing KGUI Properties Example...");
    }

    public static void endClientTick(Minecraft client) {
        while (OPEN_GUI.consumeClick()) {
            if (!(client.screen instanceof PropertiesScreen)) {
                client.setScreen(new PropertiesScreen());
            }
        }
    }
}
