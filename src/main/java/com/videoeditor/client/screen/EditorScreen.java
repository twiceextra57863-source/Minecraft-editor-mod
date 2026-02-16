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
    private String selectedTool = "none"; // none, model, particle, effect, transition
    
    private static final int PREVIEW_HEIGHT = 200;
    private static final int TIMELINE_HEIGHT = 180;
    private static final int CONTROL_BAR_HEIGHT = 40;
    private static final int TOOLBAR_HEIGHT = 60;
    
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
            Text.literal(isPlaying ? "⏸" : "▶"), 
            button -> {
                isPlaying = !isPlaying;
                clearAndInit();
            })
        .dimensions(centerX - 90, controlY, buttonWidth, buttonHeight)
        .build());
        
        // Stop button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("⏹"), button -> {
            isPlaying = false;
            clearAndInit();
        })
        .dimensions(centerX - 25, controlY, buttonWidth, buttonHeight)
        .build());
        
        // Fullscreen toggle
        this.addDrawableChild(ButtonWidget.builder(Text.literal("⛶"), button -> {
            isPreviewFullscreen = true;
            clearAndInit();
        })
        .dimensions(centerX + 40, controlY, buttonWidth, buttonHeight)
        .build());
        
        // TOOLBAR BUTTONS (Bottom)
        int toolbarY = this.height - TOOLBAR_HEIGHT + 5;
        int toolbarButtonWidth = 100;
        int toolbarSpacing = 5;
        int startX = 20;
        
        // Add Camera Clip
        this.addDrawableChild(ButtonWidget.builder(Text.literal("📹 Camera"), button -> {
            if (this.client != null) {
                this.client.setScreen(new CameraRecordingScreen(this, project));
            }
        })
        .dimensions(startX, toolbarY, toolbarButtonWidth, 20)
        .build());
        
        // Add Model
        this.addDrawableChild(ButtonWidget.builder(Text.literal("🧊 Add Model"), button -> {
            selectedTool = "model";
            addModelToTimeline();
        })
        .dimensions(startX + (toolbarButtonWidth + toolbarSpacing), toolbarY, toolbarButtonWidth, 20)
        .build());
        
        // Add Particles
        this.addDrawableChild(ButtonWidget.builder(Text.literal("✨ Particles"), button -> {
            selectedTool = "particle";
            addParticlesToTimeline();
        })
        .dimensions(startX + 2 * (toolbarButtonWidth + toolbarSpacing), toolbarY, toolbarButtonWidth, 20)
        .build());
        
        // Add Effect
        this.addDrawableChild(ButtonWidget.builder(Text.literal("🎨 Effect"), button -> {
            selectedTool = "effect";
            if (this.client != null) {
                this.client.setScreen(new EffectPickerScreen(this, project, selectedClipIndex));
            }
        })
        .dimensions(startX + 3 * (toolbarButtonWidth + toolbarSpacing), toolbarY, toolbarButtonWidth, 20)
        .build());
        
        // Add Transition
        this.addDrawableChild(ButtonWidget.builder(Text.literal("🔄 Transition"), button -> {
            selectedTool = "transition";
            if (this.client != null) {
                this.client.setScreen(new TransitionPickerScreen(this, project, selectedClipIndex));
            }
        })
        .dimensions(startX + 4 * (toolbarButtonWidth + toolbarSpacing), toolbarY, toolbarButtonWidth, 20)
        .build());
        
        // Add Text
        this.addDrawableChild(ButtonWidget.builder(Text.literal("📝 Text"), button -> {
            selectedTool = "text";
            if (this.client != null) {
                this.client.setScreen(new TextEditorScreen(this, project));
            }
        })
        .dimensions(startX + 5 * (toolbarButtonWidth + toolbarSpacing), toolbarY, toolbarButtonWidth, 20)
        .build());
        
        // Toolbar second row
        int toolbarY2 = toolbarY + 25;
        
        // Add Audio
        this.addDrawableChild(ButtonWidget.builder(Text.literal("🔊 Audio"), button -> {
            selectedTool = "audio";
            // Future: Audio clip
        })
        .dimensions(startX, toolbarY2, toolbarButtonWidth, 20)
        .build());
        
        // Add Image
        this.addDrawableChild(ButtonWidget.builder(Text.literal("🖼 Image"), button -> {
            selectedTool = "image";
            // Future: Image clip
        })
        .dimensions(startX + (toolbarButtonWidth + toolbarSpacing), toolbarY2, toolbarButtonWidth, 20)
        .build());
        
        // Add Animation
        this.addDrawableChild(ButtonWidget.builder(Text.literal("🎭 Animation"), button -> {
            selectedTool = "animation";
            if (this.client != null) {
                this.client.setScreen(new AnimationPickerScreen(this, project, selectedClipIndex));
            }
        })
        .dimensions(startX + 2 * (toolbarButtonWidth + toolbarSpacing), toolbarY2, toolbarButtonWidth, 20)
        .build());
        
        // Export button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("💾 Export"), button -> {
            if (this.client != null) {
                this.client.setScreen(new ExportScreen(this, project));
            }
        })
        .dimensions(this.width - 110, toolbarY2, 100, 20)
        .build());
        
        // Back to Dashboard
        this.addDrawableChild(ButtonWidget.builder(Text.literal("← Back"), button -> {
            if (this.client != null) {
                this.client.setScreen(new DashboardScreen());
            }
        })
        .dimensions(10, this.height - TOOLBAR_HEIGHT - 30, 80, 20)
        .build());
    }
    
    private void initFullscreenMode() {
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Exit Fullscreen"), button -> {
            isPreviewFullscreen = false;
            clearAndInit();
        })
        .dimensions(this.width - 120, 10, 110, 20)
        .build());
        
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
        // Dark background
        context.fill(0, 0, this.width, this.height, 0xFF0A0A0A);
        
        // Preview area
        renderPreviewArea(context, 0, 0, this.width, PREVIEW_HEIGHT);
        
        // Control bar separator
        int controlY = PREVIEW_HEIGHT + 10;
        context.fill(0, controlY + CONTROL_BAR_HEIGHT, this.width, controlY + CONTROL_BAR_HEIGHT + 2, 0xFF333333);
        
        // Timeline area
        renderTimeline(context, 0, PREVIEW_HEIGHT + CONTROL_BAR_HEIGHT + 20, this.width, TIMELINE_HEIGHT);
        
        // Toolbar separator
        context.fill(0, this.height - TOOLBAR_HEIGHT - 2, this.width, this.height - TOOLBAR_HEIGHT, 0xFF333333);
        
        // Toolbar background
        context.fill(0, this.height - TOOLBAR_HEIGHT, this.width, this.height, 0xFF1A1A1A);
        
        // Project info (top left)
        context.drawTextWithShadow(this.textRenderer, "Project: " + project.getName(), 
                                  10, 10, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, 
                                  project.getQuality() + "p @ " + project.getFps() + " FPS", 
                                  10, 25, 0xAAAAAA);
        
        // Show selected tool
        if (!selectedTool.equals("none")) {
            context.drawTextWithShadow(this.textRenderer, 
                                      "Selected: " + selectedTool.toUpperCase(), 
                                      this.width - 150, 10, 0xFFFF00);
        }
    }
    
    private void renderPreviewArea(DrawContext context, int x, int y, int width, int height) {
        // Preview background
        context.fill(x + 10, y + 40, x + width - 10, y + height - 10, 0xFF000000);
        
        // Preview border with glow effect
        context.drawBorder(x + 10, y + 40, width - 20, height - 50, isPlaying ? 0xFF00FF00 : 0xFF444444);
        
        // Preview content
        String previewText = isPlaying ? "▶ PLAYING..." : "Preview";
        if (project.getClips().size() > 0 && selectedClipIndex >= 0 && selectedClipIndex < project.getClips().size()) {
            Clip clip = project.getClips().get(selectedClipIndex);
            previewText = "Clip: " + clip.getName() + " (" + clip.getType() + ")";
            
            // Show clip details
            int detailY = y + height / 2 + 20;
            context.drawCenteredTextWithShadow(this.textRenderer, 
                "Frames: " + clip.getFrames().size(), 
                x + width / 2, detailY, 0x888888);
            context.drawCenteredTextWithShadow(this.textRenderer, 
                "Duration: " + (clip.getDuration() / 1000.0) + "s", 
                x + width / 2, detailY + 15, 0x888888);
        }
        
        int textX = x + width / 2 - this.textRenderer.getWidth(previewText) / 2;
        int textY = y + height / 2 - 10;
        context.drawTextWithShadow(this.textRenderer, previewText, textX, textY, 
                                  isPlaying ? 0x00FF00 : 0xCCCCCC);
    }
    
    private void renderFullscreenPreview(DrawContext context) {
        context.fill(0, 0, this.width, this.height, 0xFF000000);
        
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        
        context.drawCenteredTextWithShadow(this.textRenderer, "FULLSCREEN PREVIEW", centerX, centerY - 40, 0xFFFFFF);
        
        if (isPlaying && project.getClips().size() > 0) {
            context.drawCenteredTextWithShadow(this.textRenderer, 
                                             "Playing: " + project.getClips().get(0).getName(), 
                                             centerX, centerY - 20, 0x00FF00);
        }
        
        context.drawCenteredTextWithShadow(this.textRenderer, 
                                         "Camera playback & effects render here", 
                                         centerX, centerY + 20, 0x888888);
    }
    
    private void renderTimeline(DrawContext context, int x, int y, int width, int height) {
        int timelineY = y + 10;
        
        // Timeline background with gradient effect
        context.fill(x + 10, timelineY, x + width - 10, timelineY + height - 20, 0xFF1A1A1A);
        context.drawBorder(x + 10, timelineY, width - 20, height - 20, 0xFF333333);
        
        // Timeline label with effects count
        String timelineLabel = "Timeline (" + project.getClips().size() + " clips)";
        context.drawTextWithShadow(this.textRenderer, timelineLabel, x + 20, timelineY - 15, 0xFFFFFF);
        
        // Time ruler
        renderTimeRuler(context, x + 20, timelineY + 5, width - 40);
        
        // Render clips in timeline with effects
        if (project.getClips().size() > 0) {
            int clipWidth = 120;
            int clipHeight = 80;
            int clipX = x + 20;
            int clipY = timelineY + 25;
            int scrollOffset = 0; // Future: add scrolling
            
            for (int i = 0; i < project.getClips().size(); i++) {
                Clip clip = project.getClips().get(i);
                boolean selected = i == selectedClipIndex;
                
                // Clip container
                int clipColor = getClipColor(clip.getType());
                context.fill(clipX, clipY, clipX + clipWidth, clipY + clipHeight, 
                           selected ? 0xFF5555FF : clipColor);
                
                // Clip border with glow if selected
                int borderColor = selected ? 0xFFFFFF00 : 0xFF666666;
                context.drawBorder(clipX, clipY, clipWidth, clipHeight, borderColor);
                if (selected) {
                    context.drawBorder(clipX - 1, clipY - 1, clipWidth + 2, clipHeight + 2, 0xFFFFFF00);
                }
                
                // Clip icon/thumbnail
                String icon = getClipIcon(clip.getType());
                context.drawCenteredTextWithShadow(this.textRenderer, icon, 
                                                 clipX + clipWidth / 2, clipY + 5, 0xFFFFFF);
                
                // Clip name
                String clipName = clip.getName();
                if (clipName.length() > 14) {
                    clipName = clipName.substring(0, 14) + "...";
                }
                context.drawTextWithShadow(this.textRenderer, clipName, clipX + 5, clipY + 25, 0xFFFFFF);
                
                // Clip type & duration
                context.drawTextWithShadow(this.textRenderer, clip.getType().toString(), 
                                         clipX + 5, clipY + 40, 0xAAAAAA);
                context.drawTextWithShadow(this.textRenderer, 
                                         (clip.getDuration() / 1000.0) + "s", 
                                         clipX + 5, clipY + 52, 0x888888);
                
                // Effects indicator
                if (clip.getType() == Clip.ClipType.CAMERA) {
                    int effectCount = clip.getFrames().size() / 30; // Simulated
                    if (effectCount > 0) {
                        context.drawTextWithShadow(this.textRenderer, 
                                                 "✨ " + effectCount + " fx", 
                                                 clipX + 5, clipY + 64, 0xFFFF00);
                    }
                }
                
                clipX += clipWidth + 15;
                
                // Wrap to next row if needed
                if (clipX + clipWidth > x + width - 20) {
                    clipX = x + 20;
                    clipY += clipHeight + 10;
                }
            }
        } else {
            // Empty timeline message
            context.drawCenteredTextWithShadow(this.textRenderer, 
                                             "Timeline is empty - Add clips using toolbar below", 
                                             x + width / 2, timelineY + height / 2 - 10, 0x666666);
            context.drawCenteredTextWithShadow(this.textRenderer, 
                                             "📹 Camera | 🧊 Model | ✨ Particles | 🎨 Effects | 🔄 Transitions", 
                                             x + width / 2, timelineY + height / 2 + 10, 0x444444);
        }
    }
    
    private void renderTimeRuler(DrawContext context, int x, int y, int width) {
        // Time markers
        for (int i = 0; i <= 10; i++) {
            int markerX = x + (i * width / 10);
            context.fill(markerX, y, markerX + 1, y + 5, 0xFF666666);
            String timeLabel = i + "s";
            context.drawTextWithShadow(this.textRenderer, timeLabel, markerX - 5, y + 6, 0x666666);
        }
    }
    
    private int getClipColor(Clip.ClipType type) {
        return switch (type) {
            case CAMERA -> 0xFF2A2A4A;
            case SCREEN_RECORDING -> 0xFF4A2A2A;
            case IMAGE -> 0xFF2A4A2A;
        };
    }
    
    private String getClipIcon(Clip.ClipType type) {
        return switch (type) {
            case CAMERA -> "📹";
            case SCREEN_RECORDING -> "🖥";
            case IMAGE -> "🖼";
        };
    }
    
    private void addModelToTimeline() {
        // Add a model clip to timeline
        Clip modelClip = new Clip("3D Model", Clip.ClipType.IMAGE);
        modelClip.setDuration(3000); // 3 seconds
        project.addClip(modelClip);
        selectedClipIndex = project.getClips().size() - 1;
    }
    
    private void addParticlesToTimeline() {
        // Add particle effect clip
        Clip particleClip = new Clip("Particles", Clip.ClipType.SCREEN_RECORDING);
        particleClip.setDuration(2000); // 2 seconds
        project.addClip(particleClip);
        selectedClipIndex = project.getClips().size() - 1;
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!isPreviewFullscreen) {
            int timelineY = PREVIEW_HEIGHT + CONTROL_BAR_HEIGHT + 30;
            int clipWidth = 120;
            int clipHeight = 80;
            int clipX = 20;
            int clipY = timelineY + 25;
            
            for (int i = 0; i < project.getClips().size(); i++) {
                if (mouseX >= clipX && mouseX <= clipX + clipWidth &&
                    mouseY >= clipY && mouseY <= clipY + clipHeight) {
                    selectedClipIndex = i;
                    return true;
                }
                clipX += clipWidth + 15;
                
                if (clipX + clipWidth > this.width - 20) {
                    clipX = 20;
                    clipY += clipHeight + 10;
                }
            }
        }
        
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    protected void clearAndInit() {
        this.clearChildren();
        this.init();
    }
    
    @Override
    public boolean shouldPause() {
        return false;
    }
    }
