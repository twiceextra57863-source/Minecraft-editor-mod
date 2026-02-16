package com.videoeditor;

import com.videoeditor.client.KeyBindings;
import com.videoeditor.manager.ProjectManager;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VideoEditorMod implements ClientModInitializer {
    public static final String MOD_ID = "videoeditor";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    private static VideoEditorMod instance;
    private ProjectManager projectManager;

    @Override
    public void onInitializeClient() {
        instance = this;
        LOGGER.info("Initializing Minecraft Video Editor Mod");
        
        // Initialize project manager
        projectManager = new ProjectManager();
        
        // Register keybindings
        KeyBindings.register();
        
        LOGGER.info("Video Editor Mod initialized successfully!");
    }
    
    public static VideoEditorMod getInstance() {
        return instance;
    }
    
    public ProjectManager getProjectManager() {
        return projectManager;
    }
}
