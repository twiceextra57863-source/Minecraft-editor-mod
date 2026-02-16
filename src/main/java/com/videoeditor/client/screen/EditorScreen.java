package com.videoeditor.client.screen;

import com.videoeditor.project.Clip;
import com.videoeditor.project.Project;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class EditorScreen extends Screen {
    private final Project project;
    private boolean isPlaying = false;
    private boolean isPreviewFullscreen = false;
    private int selectedClipIndex = -1;
    
    private static final int PREVIEW_HEIGHT = 200;
    private static final int TIMELINE_HEIGHT = 150;
    private static final int CONTROL_BAR_HEIGHT = 40;
    
    public EditorScreen(Project project) {
        super(Text.literal("Editor - " + project.getName()));
        this.project = project;
    }
    
    @Override
    protected void init() {
        super.init();
        
        if (!isPreviewFullscreen) {
            initNormalMode();
        } else {
            initFullscreenMode();
        }
    }
    
    private void initNormalMode() {
        int controlY = PREVIEW_HEIGHT + 10;
        int buttonWidth = 60;
        int buttonHeight = 20;
        int centerX = this.width / 2;
        
        // Play/Pause button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal(isPlaying ? "⏸ Pause" : "▶ Play"), 
            button -> {
                isPlaying = !isPlaying;
                clearAndInit();
            })
        .dimensions(centerX - buttonWidth - 5, controlY, buttonWidth, buttonHeight)
        .build());
        
        // Stop button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("⏹ Stop"), button -> {
            isPlaying = false;
            clearAndInit();
        })
        .dimensions(centerX + 5, controlY, buttonWidth, buttonHeight)
        .build());
        
        // Fullscreen toggle button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("⛶ Full"), button -> {
            isPreviewFullscreen = true;
            clearAndInit();
        })
        .dimensions(this.width - 70, 10, 60, 20)
        .build());
        
        // Add Camera Clip button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("+ Camera Clip"), button -> {
            if (this.client != null) {
                this.client.setScreen(new CameraRecordingScreen(this, project));
            }
        })
        .dimensions(10, controlY + 30, 120, 20)
        .build());
        
        // Back to Dashboard button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("← Dashboard"), button -> {
            if (this.client != null) {
                this.client.setScreen(new DashboardScreen());
            }
        })
        .dimensions(10, this.height - 30, 100, 20)
        .build());
    }
    
    private void initFullscreenMode() {
        // Exit fullscreen button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Exit Fullscreen"), button -> {
            isPreviewFullscreen = false;
            clearAndInit();
        })
        .dimensions(this.width - 120, 10, 110, 20)
        .build());
        
        // Play/Pause in fullscreen
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal(isPlaying ? "⏸ Pause" : "▶ Play"), 
            button -> {
                isPlaying = !isPlaying;
                clearAndInit();
            })
        .dimensions(this.width / 2 - 30, this.height - 40, 60, 20)
        .build());
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (isPreviewFullscreen) {
            renderFullscreenPreview(context);
        } else {
            renderNormalMode(context, mouseX, mouseY);
        }
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    private void renderNormalMode(DrawContext context, int mouseX, int mouseY) {
        // Background
        context.fill(0, 0, this.width, this.height, 0xFF1A1A1A);
        
        // Preview area (top half)
        renderPreviewArea(context, 0, 0, this.width, PREVIEW_HEIGHT);
        
        // Control bar
        int controlY = PREVIEW_HEIGHT + 10;
        context.fill(0, controlY + CONTROL_BAR_HEIGHT, this.width, controlY + CONTROL_BAR_HEIGHT + 1, 0xFF444444);
        
        // Timeline area (bottom half)
        renderTimeline(context, 0, PREVIEW_HEIGHT + CONTROL_BAR_HEIGHT + 20, this.width, TIMELINE_HEIGHT);
        
        // Project info
        context.drawTextWithShadow(this.textRenderer, "Project: " + project.getName(), 
                                  10, 10, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, 
                                  project.getQuality() + "p @ " + project.getFps() + " FPS", 
                                  10, 25, 0xAAAAAA);
    }
    
    private void renderPreviewArea(DrawContext context, int x, int y, int width, int height) {
        // Preview background
        context.fill(x + 10, y + 40, x + width - 10, y + height - 10, 0xFF000000);
        
        // Preview border
        context.drawBorder(x + 10, y + 40, width - 20, height - 50, 0xFF333333);
        
        // Preview content
        String previewText = isPlaying ? "Playing..." : "Preview (No clip selected)";
        if (project.getClips().size() > 0 && selectedClipIndex >= 0) {
            Clip clip = project.getClips().get(selectedClipIndex);
            previewText = "Clip: " + clip.getName();
        }
        
        int textX = x + width / 2 - this.textRenderer.getWidth(previewText) / 2;
        int textY = y + height / 2;
        context.drawTextWithShadow(this.textRenderer, previewText, textX, textY, 0x888888);
    }
    
    private void renderFullscreenPreview(DrawContext context) {
        // Fullscreen black background
        context.fill(0, 0, this.width, this.height, 0xFF000000);
        
        // Center preview text
        String previewText = "Fullscreen Preview Mode";
        if (isPlaying && project.getClips().size() > 0) {
            previewText = "Playing: " + project.getClips().get(0).getName();
        }
        
        int textX = this.width / 2 - this.textRenderer.getWidth(previewText) / 2;
        int textY = this.height / 2;
        context.drawTextWithShadow(this.textRenderer, previewText, textX, textY, 0xFFFFFF);
        
        // Show hint
        context.drawTextWithShadow(this.textRenderer, 
                                  "Camera clips will play here in fullscreen", 
                                  this.width / 2 - 100, textY + 20, 0x888888);
    }
    
    private void renderTimeline(DrawContext context, int x, int y, int width, int height) {
        int timelineY = y + 10;
        
        // Timeline background
        context.fill(x + 10, timelineY, x + width - 10, timelineY + height - 20, 0xFF2A2A2A);
        
        // Timeline label
        context.drawTextWithShadow(this.textRenderer, "Timeline", x + 20, timelineY - 15, 0xAAAAAA);
        
        // Render clips in timeline
        if (project.getClips().size() > 0) {
            int clipWidth = 100;
            int clipHeight = 60;
            int clipX = x + 20;
            int clipY = timelineY + 10;
            
            for (int i = 0; i < project.getClips().size(); i++) {
                Clip clip = project.getClips().get(i);
                boolean selected = i == selectedClipIndex;
                
                // Clip box
                context.fill(clipX, clipY, clipX + clipWidth, clipY + clipHeight, 
                           selected ? 0xFF4444FF : 0xFF444444);
                context.drawBorder(clipX, clipY, clipWidth, clipHeight, 
                                 selected ? 0xFFFFFF00 : 0xFF666666);
                
                // Clip name
                String clipName = clip.getName();
                if (clipName.length() > 12) {
                    clipName = clipName.substring(0, 12) + "...";
                }
                context.drawTextWithShadow(this.textRenderer, clipName, clipX + 5, clipY + 5, 0xFFFFFF);
                
                // Clip type
                context.drawTextWithShadow(this.textRenderer, clip.getType().toString(), 
                                         clipX + 5, clipY + 20, 0xAAAAAA);
                
                clipX += clipWidth + 10;
            }
        } else {
            context.drawTextWithShadow(this.textRenderer, "No clips added yet", 
                                     x + 20, timelineY + 30, 0x666666);
        }
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // Handle clip selection in timeline
        if (!isPreviewFullscreen) {
            int timelineY = PREVIEW_HEIGHT + CONTROL_BAR_HEIGHT + 30;
            int clipWidth = 100;
            int clipHeight = 60;
            int clipX = 20;
            int clipY = timelineY + 10;
            
            for (int i = 0; i < project.getClips().size(); i++) {
                if (mouseX >= clipX && mouseX <= clipX + clipWidth &&
                    mouseY >= clipY && mouseY <= clipY + clipHeight) {
                    selectedClipIndex = i;
                    return true;
                }
                clipX += clipWidth + 10;
            }
        }
        
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    private void clearAndInit() {
        this.clearChildren();
        this.init();
    }
    
    @Override
    public boolean shouldPause() {
        return false;
    }
                                                  }
