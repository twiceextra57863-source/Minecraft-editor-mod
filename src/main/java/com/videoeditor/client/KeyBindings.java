package com.videoeditor.client;

import com.videoeditor.client.screen.DashboardScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    private static KeyBinding openDashboard;
    
    public static void register() {
        openDashboard = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.videoeditor.open_dashboard",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_T,
            "category.videoeditor.general"
        ));
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openDashboard.wasPressed()) {
                if (client.player != null) {
                    client.setScreen(new DashboardScreen());
                }
            }
        });
    }
}
