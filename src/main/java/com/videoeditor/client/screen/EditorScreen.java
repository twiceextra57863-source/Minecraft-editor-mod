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
    private boolean isPaused = false;
    private boolean isRecording = false;
    private boolean isPreviewFullscreen = false;
    private boolean isTimelineFullscreen = false;
    private boolean toolbarOpen = false;
    private int selectedClipIndex = -1;
    private String selectedTool = "none";
    
    private int timelineScrollOffset = 0;
    private int maxTimelineScroll = 0;
    
    private static final int PREVIEW_HEIGHT = 250;
    private static final int TIMELINE_HEIGHT_NORMAL = 280;
    private static final int CONTROL_BAR_HEIGHT = 50;
    private static final int TOOLBAR_WIDTH = 220;
    
    public EditorScreen(Project project) {
        super(Text.literal("Cinema Studio - " + project.getName()));
        this.project = project;
    }
    
    @Override
    protected void init() {
        super.init();
        
        if (isPreviewFullscreen) {
            initPreviewFullscreen();
        } else if (isTimelineFullscreen) {
            initTimelineFullscreen();
        } else {
            initNormalMode();
        }
    }
    
    private void initNormalMode() {
        int controlY = PREVIEW_HEIGHT + 10;
        int centerX = this.width / 2;
        
        // Recording controls (Left side - Cool design)
        if (isRecording) {
            // Stop Recording button (Red)
            this.addDrawableChild(ButtonWidget.builder(Text.literal("⏹ STOP REC"), button -> {
                isRecording = false;
                clearAndInit();
            })
            .dimensions(20, controlY, 100, 30)
            .build());
            
            // Pause/Resume (Yellow)
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal(isPaused ? "▶ RESUME" : "⏸ PAUSE"), 
                button -> {
                    isPaused = !isPaused;
                    clearAndInit();
                })
            .dimensions(130, controlY, 100, 30)
            .build());
        } else {
            // Start Recording button (Green)
            this.addDrawableChild(ButtonWidget.builder(Text.literal("⏺ REC"), button -> {
                isRecording = true;
                isPaused = false;
                clearAndInit();
            })
            .dimensions(20, controlY, 80, 30)
            .build());
            
            // Playback controls
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal(isPlaying ? "⏸" : "▶"), 
                button -> {
                    isPlaying = !isPlaying;
                    clearAndInit();
                })
            .dimensions(centerX - 60, controlY, 40, 30)
            .build());
            
            this.addDrawableChild(ButtonWidget.builder(Text.literal("⏹"), button -> {
                isPlaying = false;
                clearAndInit();
            })
            .dimensions(centerX - 10, controlY, 40, 30)
            .build());
            
            this.addDrawableChild(ButtonWidget.builder(Text.literal("⟲"), button -> {
                // Restart
            })
            .dimensions(centerX + 40, controlY, 40, 30)
            .build());
        }
        
        // Preview fullscreen toggle
        this.addDrawableChild(ButtonWidget.builder(Text.literal("⛶ Preview"), button -> {
            isPreviewFullscreen = true;
            clearAndInit();
        })
        .dimensions(this.width - 130, controlY, 120, 30)
        .build());
        
        // Timeline fullscreen toggle
        int timelineY = PREVIEW_HEIGHT + CONTROL_BAR_HEIGHT + 20;
        this.addDrawableChild(ButtonWidget.builder(Text.literal("⛶ Timeline"), button -> {
            isTimelineFullscreen = true;
            clearAndInit();
        })
        .dimensions(this.width - 130, timelineY - 25, 120, 20)
        .build());
        
        // Toolbar toggle (3-line menu at bottom left)
        this.addDrawableChild(ButtonWidget.builder(Text.literal("☰ Tools"), button -> {
            toolbarOpen = !toolbarOpen;
            clearAndInit();
        })
        .dimensions(10, this.height - 35, 80, 25)
        .build());
        
        // Back button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("← Dashboard"), button -> {
            if (this.client != null) {
                this.client.setScreen(new DashboardScreen());
            }
        })
        .dimensions(100, this.height - 35, 120, 25)
        .build());
        
        // Render toolbar menu if open
        if (toolbarOpen) {
            initToolbarMenu();
        }
    }
    
    private void initToolbarMenu() {
        int menuX = 10;
        int menuY = this.height - 360;
        int buttonWidth = 200;
        int buttonHeight = 25;
        int spacing = 5;
        int currentY = menuY;
        
        String[] tools = {
            "📹 Add Camera Clip",
            "🧊 Add 3D Model", 
            "✨ Add Particles",
            "🎨 Apply Effect",
            "🔄 Add Transition",
            "📝 Add Text",
            "🔊 Add Audio",
            "🖼 Add Image",
            "🎭 Add Animation",
            "💾 Export Project"
        };
        
        for (String tool : tools) {
            final String toolName = tool;
            this.addDrawableChild(ButtonWidget.builder(Text.literal(tool), button -> {
                handleToolClick(toolName);
            })
            .dimensions(menuX, currentY, buttonWidth, buttonHeight)
            .build());
            currentY += buttonHeight + spacing;
        }
    }
    
    private void handleToolClick(String tool) {
        if (this.client == null) return;
        
        if (tool.contains("Camera")) {
            this.client.setScreen(new CameraRecordingScreen(this, project));
        } else if (tool.contains("Model")) {
            addModelClip();
        } else if (tool.contains("Particles")) {
            addParticleClip();
        } else if (tool.contains("Effect")) {
            this.client.setScreen(new EffectPickerScreen(this, project, selectedClipIndex));
        } else if (tool.contains("Transition")) {
            this.client.setScreen(new TransitionPickerScreen(this, project, selectedClipIndex));
        } else if (tool.contains("Text")) {
            this.client.setScreen(new TextEditorScreen(this, project));
        } else if (tool.contains("Animation")) {
            this.client.setScreen(new AnimationPickerScreen(this, project, selectedClipIndex));
        } else if (tool.contains("Export")) {
            this.client.setScreen(new ExportScreen(this, project));
        }
    }
    
    private void initPreviewFullscreen() {
        // Exit fullscreen
        this.addDrawableChild(ButtonWidget.builder(Text.literal("✕ Exit Fullscreen"), button -> {
            isPreviewFullscreen = false;
            clearAndInit();
        })
        .dimensions(this.width - 150, 10, 140, 25)
        .build());
        
        // Playback controls in fullscreen
        int centerX = this.width / 2;
        int controlY = this.height - 50;
        
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal(isPlaying ? "⏸ Pause" : "▶ Play"), 
            button -> {
                isPlaying = !isPlaying;
                clearAndInit();
            })
        .dimensions(centerX - 50, controlY, 100, 30)
        .build());
    }
    
    private void initTimelineFullscreen() {
        // Exit timeline fullscreen
        this.addDrawableChild(ButtonWidget.builder(Text.literal("✕ Exit Timeline View"), button -> {
            isTimelineFullscreen = false;
            clearAndInit();
        })
        .dimensions(this.width - 180, 10, 170, 25)
        .build());
        
        // Toolbar toggle in timeline fullscreen
        this.addDrawableChild(ButtonWidget.builder(Text.literal("☰ Tools"), button -> {
            toolbarOpen = !toolbarOpen;
            clearAndInit();
        })
        .dimensions(10, 10, 80, 25)
        .build());
        
        if (toolbarOpen) {
            initToolbarMenu();
        }
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (isPreviewFullscreen) {
            renderPreviewFullscreen(context);
        } else if (isTimelineFullscreen) {
            renderTimelineFullscreen(context);
        } else {
            renderNormalMode(context, mouseX, mouseY);
        }
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    private void renderNormalMode(DrawContext context, int mouseX, int mouseY) {
        // Background
        context.fill(0, 0, this.width, this.height, 0xFF050505);
        
        // Preview area
        renderPreviewArea(context, 0, 0, this.width, PREVIEW_HEIGHT);
        
        // Control bar
        int controlY = PREVIEW_HEIGHT + 10;
        context.fill(0, controlY, this.width, controlY + CONTROL_BAR_HEIGHT, 0xFF1A1A1A);
        
        // Recording indicator
        if (isRecording) {
            renderRecordingIndicator(context);
        }
        
        // Timeline area (bigger now)
        int timelineY = PREVIEW_HEIGHT + CONTROL_BAR_HEIGHT + 20;
        renderTimeline(context, 0, timelineY, this.width, TIMELINE_HEIGHT_NORMAL);
        
        // Toolbar background if open
        if (toolbarOpen) {
            int menuHeight = 330;
            context.fill(5, this.height - menuHeight - 40, 215, this.height - 40, 0xEE1A1A1A);
            context.drawBorder(5, this.height - menuHeight - 40, 210, menuHeight, 0xFF00FF00);
        }
        
        // Project info overlay
        renderProjectInfo(context);
    }
    
    private void renderPreviewArea(DrawContext context, int x, int y, int width, int height) {
        // Preview background
        context.fill(x + 10, y + 40, x + width - 10, y + height - 10, 0xFF000000);
        
        // Border with status color
        int borderColor = isRecording ? 0xFFFF0000 : (isPlaying ? 0xFF00FF00 : 0xFF444444);
        context.drawBorder(x + 10, y + 40, width - 20, height - 50, borderColor);
        
        // Show actual Minecraft POV if available
        if (this.client != null && this.client.player != null && selectedClipIndex >= 0 
            && selectedClipIndex < project.getClips().size()) {
            
            Clip clip = project.getClips().get(selectedClipIndex);
            
            // Preview content
            String previewText = "Playing: " + clip.getName();
            context.drawCenteredTextWithShadow(this.textRenderer, previewText, 
                                             x + width / 2, y + height / 2 - 30, 0x00FF00);
            
            // Simulated POV display
            context.drawCenteredTextWithShadow(this.textRenderer, 
                "[ Minecraft POV View ]", x + width / 2, y + height / 2, 0xFFFFFF);
            context.drawCenteredTextWithShadow(this.textRenderer, 
                "Position: " + (int)this.client.player.getX() + ", " + 
                (int)this.client.player.getY() + ", " + (int)this.client.player.getZ(), 
                x + width / 2, y + height / 2 + 20, 0x888888);
                
        } else {
            String previewText = isRecording ? "⏺ RECORDING..." : 
                                (isPlaying ? "▶ PLAYING" : "Preview");
            context.drawCenteredTextWithShadow(this.textRenderer, previewText, 
                                             x + width / 2, y + height / 2, 
                                             isRecording ? 0xFF0000 : 0xCCCCCC);
            
            if (!isRecording && !isPlaying) {
                context.drawCenteredTextWithShadow(this.textRenderer, 
                    "Select a clip to preview or click ⏺ REC to record", 
                    x + width / 2, y + height / 2 + 20, 0x666666);
            }
        }
    }
    
    private void renderPreviewFullscreen(DrawContext context) {
        // Full black background
        context.fill(0, 0, this.width, this.height, 0xFF000000);
        
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        
        // Show Minecraft world view
        if (this.client != null && this.client.player != null) {
            // In actual implementation, render the game world here
            context.drawCenteredTextWithShadow(this.textRenderer, 
                "FULLSCREEN PREVIEW MODE", centerX, 30, 0xFFFFFF);
            
            context.drawCenteredTextWithShadow(this.textRenderer, 
                "[ Minecraft World View ]", centerX, centerY - 20, 0x00FF00);
            
            context.drawCenteredTextWithShadow(this.textRenderer, 
                "Move with WASD • Look with Mouse", centerX, centerY + 20, 0x888888);
                
            // Crosshair
            context.fill(centerX - 1, centerY - 10, centerX + 1, centerY + 10, 0xFFFFFFFF);
            context.fill(centerX - 10, centerY - 1, centerX + 10, centerY + 1, 0xFFFFFFFF);
        }
    }
    
    private void renderTimelineFullscreen(DrawContext context) {
        // Full timeline view
        context.fill(0, 0, this.width, this.height, 0xFF0A0A0A);
        
        // Title
        context.drawCenteredTextWithShadow(this.textRenderer, 
            "TIMELINE EDITOR - " + project.getName(), 
            this.width / 2, 20, 0xFFFFFF);
        
        // Big timeline
        renderTimeline(context, 20, 50, this.width - 40, this.height - 100);
        
        // Toolbar if open
        if (toolbarOpen) {
            int menuHeight = 330;
            context.fill(25, 60, 235, 60 + menuHeight, 0xEE1A1A1A);
            context.drawBorder(25, 60, 210, menuHeight, 0xFF00FF00);
        }
    }
    
    private void renderTimeline(DrawContext context, int x, int y, int width, int height) {
        // Timeline container
        context.fill(x + 10, y, x + width - 10, y + height, 0xFF1A1A1A);
        context.drawBorder(x + 10, y, width - 20, height, 0xFF333333);
        
        // Timeline header
        String headerText = "TIMELINE (" + project.getClips().size() + " clips)";
        context.drawTextWithShadow(this.textRenderer, headerText, x + 20, y + 10, 0x00FF00);
        
        // Scroll indicators
        if (maxTimelineScroll > 0) {
            context.drawTextWithShadow(this.textRenderer, 
                "↑ Scroll: " + timelineScrollOffset + "/" + maxTimelineScroll + " ↓", 
                x + width - 180, y + 10, 0xFFFF00);
        }
        
        // Time ruler
        renderTimeRuler(context, x + 20, y + 30, width - 40);
        
        // Clips area
        int clipsStartY = y + 50;
        int availableHeight = height - 60;
        
        renderClipsInTimeline(context, x + 20, clipsStartY, width - 40, availableHeight);
    }
    
    private void renderClipsInTimeline(DrawContext context, int x, int y, int width, int height) {
        if (project.getClips().isEmpty()) {
            context.drawCenteredTextWithShadow(this.textRenderer, 
                "Timeline is empty", x + width / 2, y + height / 2 - 20, 0x666666);
            context.drawCenteredTextWithShadow(this.textRenderer, 
                "Click ☰ Tools to add clips", x + width / 2, y + height / 2, 0x444444);
            return;
        }
        
        int clipWidth = 140;
        int clipHeight = 100;
        int spacing = 15;
        int clipsPerRow = Math.max(1, width / (clipWidth + spacing));
        
        int clipX = x;
        int clipY = y - timelineScrollOffset;
        int row = 0;
        
        for (int i = 0; i < project.getClips().size(); i++) {
            Clip clip = project.getClips().get(i);
            
            // Check if clip is visible
            if (clipY + clipHeight >= y && clipY < y + height) {
                renderClipBox(context, clip, clipX, clipY, clipWidth, clipHeight, i == selectedClipIndex);
            }
            
            // Move to next position
            if ((i + 1) % clipsPerRow == 0) {
                clipX = x;
                clipY += clipHeight + spacing;
                row++;
            } else {
                clipX += clipWidth + spacing;
            }
        }
        
        // Calculate max scroll
        int totalRows = (int) Math.ceil((double) project.getClips().size() / clipsPerRow);
        int totalHeight = totalRows * (clipHeight + spacing);
        maxTimelineScroll = Math.max(0, totalHeight - height);
    }
    
    private void renderClipBox(DrawContext context, Clip clip, int x, int y, int width, int height, boolean selected) {
        // Clip background
        int bgColor = getClipColor(clip.getType());
        context.fill(x, y, x + width, y + height, selected ? 0xFF4444FF : bgColor);
        
        // Border
        int borderColor = selected ? 0xFFFFFF00 : 0xFF555555;
        context.drawBorder(x, y, width, height, borderColor);
        
        if (selected) {
            context.drawBorder(x - 1, y - 1, width + 2, height + 2, 0xFFFFFF00);
            context.drawBorder(x - 2, y - 2, width + 4, height + 4, 0x88FFFF00);
        }
        
        // Icon
        String icon = getClipIcon(clip.getType());
        context.drawCenteredTextWithShadow(this.textRenderer, icon, x + width / 2, y + 8, 0xFFFFFF);
        
        // Name
        String name = clip.getName();
        if (name.length() > 16) name = name.substring(0, 16) + "...";
        context.drawTextWithShadow(this.textRenderer, name, x + 5, y + 28, 0xFFFFFF);
        
        // Type
        context.drawTextWithShadow(this.textRenderer, clip.getType().toString(), x + 5, y + 42, 0xAAAAAA);
        
        // Duration
        context.drawTextWithShadow(this.textRenderer, 
            String.format("%.1fs", clip.getDuration() / 1000.0), x + 5, y + 56, 0x888888);
        
        // Frame count
        context.drawTextWithShadow(this.textRenderer, 
            clip.getFrames().size() + " frames", x + 5, y + 70, 0x666666);
        
        // Effects badge
        if (clip.getFrames().size() > 10) {
            context.drawTextWithShadow(this.textRenderer, "✨ FX", x + 5, y + 84, 0xFFFF00);
        }
    }
    
    private void renderTimeRuler(DrawContext context, int x, int y, int width) {
        for (int i = 0; i <= 20; i++) {
            int markerX = x + (i * width / 20);
            boolean isMajor = i % 5 == 0;
            context.fill(markerX, y, markerX + 1, y + (isMajor ? 8 : 5), 0xFF888888);
            if (isMajor) {
                context.drawTextWithShadow(this.textRenderer, i + "s", markerX - 5, y + 10, 0x888888);
            }
        }
    }
    
    private void renderRecordingIndicator(DrawContext context) {
        // Pulsing red dot
        long time = System.currentTimeMillis();
        int alpha = (int)(Math.sin(time / 200.0) * 100 + 155);
        int color = (alpha << 24) | 0xFF0000;
        
        context.fill(this.width - 140, 15, this.width - 115, 40, color);
        context.drawTextWithShadow(this.textRenderer, "REC", this.width - 110, 22, 0xFFFFFF);
        
        if (isPaused) {
            context.drawTextWithShadow(this.textRenderer, "(PAUSED)", this.width - 70, 22, 0xFFFF00);
        }
    }
    
    private void renderProjectInfo(DrawContext context) {
        context.drawTextWithShadow(this.textRenderer, "Project: " + project.getName(), 10, 10, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, 
            project.getQuality() + "p @ " + project.getFps() + " FPS", 10, 25, 0xAAAAAA);
    }
    
    private int getClipColor(Clip.ClipType type) {
        return switch (type) {
            case CAMERA -> 0xFF2A2A5A;
            case SCREEN_RECORDING -> 0xFF5A2A2A;
            case IMAGE -> 0xFF2A5A2A;
        };
    }
    
    private String getClipIcon(Clip.ClipType type) {
        return switch (type) {
            case CAMERA -> "📹";
            case SCREEN_RECORDING -> "🖥";
            case IMAGE -> "🖼";
        };
    }
    
    private void addModelClip() {
        Clip clip = new Clip("3D Model", Clip.ClipType.IMAGE);
        clip.setDuration(3000);
        project.addClip(clip);
        selectedClipIndex = project.getClips().size() - 1;
    }
    
    private void addParticleClip() {
        Clip clip = new Clip("Particles", Clip.ClipType.SCREEN_RECORDING);
        clip.setDuration(2000);
        project.addClip(clip);
        selectedClipIndex = project.getClips().size() - 1;
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // Handle clip selection
        if (!isPreviewFullscreen && !isTimelineFullscreen) {
            handleClipSelection(mouseX, mouseY);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    private void handleClipSelection(double mouseX, double mouseY) {
        int timelineY = PREVIEW_HEIGHT + CONTROL_BAR_HEIGHT + 70;
        int clipWidth = 140;
        int clipHeight = 100;
        int spacing = 15;
        int clipsPerRow = Math.max(1, (this.width - 60) / (clipWidth + spacing));
        
        int clipX = 30;
        int clipY = timelineY - timelineScrollOffset;
        
        for (int i = 0; i < project.getClips().size(); i++) {
            if (mouseX >= clipX && mouseX <= clipX + clipWidth &&
                mouseY >= clipY && mouseY <= clipY + clipHeight &&
                clipY >= timelineY && clipY < timelineY + TIMELINE_HEIGHT_NORMAL - 60) {
                selectedClipIndex = i;
                return;
            }
            
            if ((i + 1) % clipsPerRow == 0) {
                clipX = 30;
                clipY += clipHeight + spacing;
            } else {
                clipX += clipWidth + spacing;
            }
        }
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        // Timeline scrolling
        if (!isPreviewFullscreen && mouseY > PREVIEW_HEIGHT + CONTROL_BAR_HEIGHT) {
            timelineScrollOffset = Math.max(0, Math.min(maxTimelineScroll, 
                timelineScrollOffset - (int)(verticalAmount * 20)));
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }
    
    protected void clearAndInit() {
        this.clearChildren();
        this.init();
    }
    
    @Override
    public boolean shouldPause() {
        return !isPreviewFullscreen;
    }
}
