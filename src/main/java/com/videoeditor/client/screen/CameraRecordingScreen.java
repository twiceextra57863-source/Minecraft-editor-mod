package com.videoeditor.client.screen;

import com.videoeditor.project.Clip;
import com.videoeditor.project.Project;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class CameraRecordingScreen extends Screen {
    private final Screen parent;
    private final Project project;
    private boolean isRecording = false;
    private boolean isPlayingBack = false;
    private Clip currentClip;
    private long recordingStartTime;
    private int recordedFrames = 0;
    
    public CameraRecordingScreen(Screen parent, Project project) {
        super(Text.literal("Camera Recording"));
        this.parent = parent;
        this.project = project;
    }
    
    @Override
    protected void init() {
        super.init();
        
        if (!isPlayingBack) {
            initRecordingMode();
        } else {
            initPlaybackMode();
        }
    }
    
    private void initRecordingMode() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        
        if (!isRecording && currentClip == null) {
            // Start Recording button
            this.addDrawableChild(ButtonWidget.builder(Text.literal("⏺ Start Recording"), button -> {
                startRecording();
            })
            .dimensions(centerX - 60, centerY + 30, 120, 20)
            .build());
        } else if (isRecording) {
            // Stop Recording button
            this.addDrawableChild(ButtonWidget.builder(Text.literal("⏹ Stop Recording"), button -> {
                stopRecording();
            })
            .dimensions(centerX - 60, centerY + 30, 120, 20)
            .build());
        } else if (currentClip != null) {
            // Preview recording button
            this.addDrawableChild(ButtonWidget.builder(Text.literal("▶ Preview"), button -> {
                isPlayingBack = true;
                clearAndInit();
            })
            .dimensions(centerX - 130, centerY + 30, 80, 20)
            .build());
            
            // Save button
            this.addDrawableChild(ButtonWidget.builder(Text.literal("✓ Save"), button -> {
                saveClip();
            })
            .dimensions(centerX - 40, centerY + 30, 80, 20)
            .build());
            
            // Discard button
            this.addDrawableChild(ButtonWidget.builder(Text.literal("✗ Discard"), button -> {
                currentClip = null;
                clearAndInit();
            })
            .dimensions(centerX + 50, centerY + 30, 80, 20)
            .build());
        }
        
        // Back button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("← Back"), button -> {
            if (this.client != null) {
                this.client.setScreen(parent);
            }
        })
        .dimensions(10, this.height - 30, 80, 20)
        .build());
    }
    
    private void initPlaybackMode() {
        // Exit playback button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Exit Preview"), button -> {
            isPlayingBack = false;
            if (this.client != null) {
                this.client.setScreen(null);
            }
        })
        .dimensions(this.width - 110, 10, 100, 20)
        .build());
    }
    
    private void startRecording() {
        isRecording = true;
        currentClip = new Clip("Camera Clip " + (project.getClips().size() + 1), Clip.ClipType.CAMERA);
        recordingStartTime = System.currentTimeMillis();
        recordedFrames = 0;
        clearAndInit();
        
        // Close the screen to allow free movement
        if (this.client != null) {
            this.client.setScreen(null);
        }
    }
    
    private void stopRecording() {
        isRecording = false;
        if (currentClip != null) {
            long duration = System.currentTimeMillis() - recordingStartTime;
            currentClip.setDuration(duration);
        }
        clearAndInit();
    }
    
    private void saveClip() {
        if (currentClip != null) {
            project.addClip(currentClip);
            if (this.client != null) {
                this.client.setScreen(parent);
            }
        }
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (isPlayingBack) {
            renderPlaybackMode(context);
        } else {
            renderRecordingMode(context);
        }
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    private void renderRecordingMode(DrawContext context) {
        // Semi-transparent background
        context.fill(0, 0, this.width, this.height, 0xDD000000);
        
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        
        // Title
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, centerX, 20, 0xFFFFFF);
        
        if (isRecording) {
            // Recording indicator
            context.fill(centerX - 150, centerY - 80, centerX + 150, centerY - 20, 0x88FF0000);
            context.drawCenteredTextWithShadow(this.textRenderer, "⏺ RECORDING", centerX, centerY - 60, 0xFFFFFF);
            context.drawCenteredTextWithShadow(this.textRenderer, "Move around to record camera movement", 
                                             centerX, centerY - 40, 0xCCCCCC);
            context.drawCenteredTextWithShadow(this.textRenderer, "Frames: " + recordedFrames, 
                                             centerX, centerY - 20, 0xCCCCCC);
        } else if (currentClip != null) {
            // Recording complete
            context.fill(centerX - 150, centerY - 80, centerX + 150, centerY - 20, 0x8800FF00);
            context.drawCenteredTextWithShadow(this.textRenderer, "Recording Complete!", centerX, centerY - 60, 0xFFFFFF);
            context.drawCenteredTextWithShadow(this.textRenderer, "Frames: " + currentClip.getFrames().size(), 
                                             centerX, centerY - 40, 0xCCCCCC);
            context.drawCenteredTextWithShadow(this.textRenderer, 
                                             "Duration: " + (currentClip.getDuration() / 1000.0) + "s", 
                                             centerX, centerY - 20, 0xCCCCCC);
        } else {
            // Ready to record
            context.drawCenteredTextWithShadow(this.textRenderer, "Ready to record camera movement", 
                                             centerX, centerY - 40, 0xCCCCCC);
            context.drawCenteredTextWithShadow(this.textRenderer, 
                                             "Click 'Start Recording' and move around in the world", 
                                             centerX, centerY - 20, 0xAAAAAA);
        }
    }
    
    private void renderPlaybackMode(DrawContext context) {
        // Fullscreen black background
        context.fill(0, 0, this.width, this.height, 0xFF000000);
        
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        
        context.drawCenteredTextWithShadow(this.textRenderer, "Camera Playback Preview", centerX, centerY - 40, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, "Camera movement will play here", 
                                         centerX, centerY - 20, 0xAAAAAA);
        
        if (currentClip != null) {
            context.drawCenteredTextWithShadow(this.textRenderer, 
                                             "Recorded " + currentClip.getFrames().size() + " frames", 
                                             centerX, centerY + 20, 0x888888);
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Record frames while recording
        if (isRecording && this.client != null && this.client.player != null) {
            Vec3d pos = this.client.player.getPos();
            float yaw = this.client.player.getYaw();
            float pitch = this.client.player.getPitch();
            long timestamp = System.currentTimeMillis() - recordingStartTime;
            
            if (currentClip != null) {
                currentClip.addFrame(new Clip.CameraFrame(pos, yaw, pitch, timestamp));
                recordedFrames++;
            }
        }
    }
    
    protected void clearAndInit() {
        this.clearChildren();
        this.init();
    }
    
    @Override
    public boolean shouldPause() {
        return !isRecording && !isPlayingBack;
    }
    
    @Override
    public boolean shouldCloseOnEsc() {
        if (isRecording) {
            stopRecording();
            return false;
        }
        return super.shouldCloseOnEsc();
    }
  }

