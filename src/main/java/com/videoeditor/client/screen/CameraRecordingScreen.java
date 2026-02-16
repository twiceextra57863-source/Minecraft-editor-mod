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
    private boolean isPaused = false;
    private boolean isPlayingBack = false;
    private boolean cinematicMode = false;
    private Clip currentClip;
    private long recordingStartTime;
    private long pausedDuration = 0;
    private long pauseStartTime = 0;
    private int recordedFrames = 0;
    
    private String recordingType = "POV"; // "POV" or "CINEMATIC"
    
    public CameraRecordingScreen(Screen parent, Project project) {
        super(Text.literal("Recording Studio"));
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
            // Recording type selection
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal(recordingType.equals("POV") ? "📹 POV Mode" : "🎬 Cinematic Mode"), 
                button -> {
                    recordingType = recordingType.equals("POV") ? "CINEMATIC" : "POV";
                    clearAndInit();
                })
            .dimensions(centerX - 100, centerY - 60, 200, 25)
            .build());
            
            // Start recording button
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("⏺ START RECORDING"), 
                button -> startRecording())
            .dimensions(centerX - 100, centerY - 20, 200, 35)
            .build());
            
            // Quick tips
            String tip = recordingType.equals("POV") ? 
                "POV: Records your gameplay view" : 
                "Cinematic: Adds camera above player";
            
        } else if (isRecording) {
            // Stop button
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("⏹ STOP RECORDING"), 
                button -> stopRecording())
            .dimensions(centerX - 100, centerY - 10, 95, 35)
            .build());
            
            // Pause/Resume button
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal(isPaused ? "▶ RESUME" : "⏸ PAUSE"), 
                button -> togglePause())
            .dimensions(centerX + 5, centerY - 10, 95, 35)
            .build());
            
        } else if (currentClip != null) {
            // Recording complete - show options
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("▶ Preview"), 
                button -> {
                    isPlayingBack = true;
                    clearAndInit();
                })
            .dimensions(centerX - 160, centerY + 20, 100, 25)
            .build());
            
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("✓ Save to Timeline"), 
                button -> saveClip())
            .dimensions(centerX - 50, centerY + 20, 130, 25)
            .build());
            
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("🗑 Discard"), 
                button -> {
                    currentClip = null;
                    clearAndInit();
                })
            .dimensions(centerX + 90, centerY + 20, 70, 25)
            .build());
            
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("⏺ Record New"), 
                button -> {
                    currentClip = null;
                    clearAndInit();
                })
            .dimensions(centerX - 75, centerY + 55, 150, 25)
            .build());
        }
        
        // Back button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("← Back to Editor"), button -> {
            if (this.client != null) {
                this.client.setScreen(parent);
            }
        })
        .dimensions(10, this.height - 30, 140, 20)
        .build());
    }
    
    private void initPlaybackMode() {
        // Fullscreen playback
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("✕ Exit Preview"), 
            button -> {
                isPlayingBack = false;
                clearAndInit();
            })
        .dimensions(this.width - 130, 10, 120, 25)
        .build());
        
        // Playback controls
        int centerX = this.width / 2;
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("⏹ Stop Preview"), 
            button -> {
                isPlayingBack = false;
                clearAndInit();
            })
        .dimensions(centerX - 75, this.height - 50, 150, 30)
        .build());
    }
    
    private void startRecording() {
        isRecording = true;
        isPaused = false;
        
        String clipName = recordingType.equals("POV") ? 
            "POV Recording " + (project.getClips().size() + 1) :
            "Cinematic Shot " + (project.getClips().size() + 1);
            
        currentClip = new Clip(clipName, Clip.ClipType.CAMERA);
        recordingStartTime = System.currentTimeMillis();
        pausedDuration = 0;
        recordedFrames = 0;
        cinematicMode = recordingType.equals("CINEMATIC");
        
        clearAndInit();
        
        // Close screen to allow free movement
        if (this.client != null) {
            this.client.setScreen(null);
        }
    }
    
    private void stopRecording() {
        isRecording = false;
        isPaused = false;
        
        if (currentClip != null) {
            long duration = System.currentTimeMillis() - recordingStartTime - pausedDuration;
            currentClip.setDuration(duration);
        }
        
        clearAndInit();
    }
    
    private void togglePause() {
        if (isPaused) {
            // Resume
            pausedDuration += System.currentTimeMillis() - pauseStartTime;
            isPaused = false;
        } else {
            // Pause
            pauseStartTime = System.currentTimeMillis();
            isPaused = true;
        }
        clearAndInit();
    }
    
    private void saveClip() {
        if (currentClip != null) {
            project.addClip(currentClip);
            
            // Add cinematic camera clip if in cinematic mode
            if (cinematicMode) {
                Clip cameraClip = new Clip("Camera Overlay", Clip.ClipType.IMAGE);
                cameraClip.setDuration(currentClip.getDuration());
                project.addClip(cameraClip);
            }
            
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
        // Background
        context.fill(0, 0, this.width, this.height, 0xDD000000);
        
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        
        // Title
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, centerX, 20, 0xFFFFFF);
        
        if (isRecording) {
            renderRecordingUI(context, centerX, centerY);
        } else if (currentClip != null) {
            renderCompletedUI(context, centerX, centerY);
        } else {
            renderSetupUI(context, centerX, centerY);
        }
    }
    
    private void renderSetupUI(DrawContext context, int centerX, int centerY) {
        // Mode selection display
        context.fill(centerX - 180, centerY - 120, centerX + 180, centerY - 70, 0xFF1A1A1A);
        context.drawBorder(centerX - 180, centerY - 120, 360, 50, 0xFF444444);
        
        String modeText = recordingType.equals("POV") ? 
            "📹 POV Recording Mode" : "🎬 Cinematic Camera Mode";
        context.drawCenteredTextWithShadow(this.textRenderer, modeText, centerX, centerY - 105, 0x00FF00);
        
        String modeDesc = recordingType.equals("POV") ? 
            "Records your player's view (First person)" : 
            "Adds floating camera above player (Third person)";
        context.drawCenteredTextWithShadow(this.textRenderer, modeDesc, centerX, centerY - 85, 0xAAAAAA);
        
        // Instructions
        context.drawCenteredTextWithShadow(this.textRenderer, 
            "Click mode button to switch • Ready to record!", 
            centerX, centerY + 30, 0x888888);
            
        // Feature info
        String[] features = {
            "✓ Move freely while recording",
            "✓ Pause/Resume anytime", 
            "✓ Preview before saving",
            "✓ Auto-add to timeline"
        };
        
        int featureY = centerY + 60;
        for (String feature : features) {
            context.drawCenteredTextWithShadow(this.textRenderer, feature, 
                                             centerX, featureY, 0x666666);
            featureY += 15;
        }
    }
    
    private void renderRecordingUI(DrawContext context, int centerX, int centerY) {
        // Recording indicator box
        context.fill(centerX - 200, centerY - 100, centerX + 200, centerY + 50, 0xAA000000);
        
        // Pulsing border
        long time = System.currentTimeMillis();
        int alpha = (int)(Math.sin(time / 200.0) * 100 + 155);
        int borderColor = (alpha << 24) | 0xFF0000;
        context.drawBorder(centerX - 200, centerY - 100, 400, 150, borderColor);
        
        // Recording status
        String status = isPaused ? "⏸ RECORDING PAUSED" : "⏺ RECORDING IN PROGRESS";
        int statusColor = isPaused ? 0xFFFF00 : 0xFF0000;
        context.drawCenteredTextWithShadow(this.textRenderer, status, centerX, centerY - 80, statusColor);
        
        // Mode indicator
        String mode = recordingType.equals("POV") ? "📹 POV Mode" : "🎬 Cinematic Mode";
        context.drawCenteredTextWithShadow(this.textRenderer, mode, centerX, centerY - 60, 0x00FF00);
        
        // Timer
        long elapsed = isPaused ? 
            (pauseStartTime - recordingStartTime - pausedDuration) :
            (System.currentTimeMillis() - recordingStartTime - pausedDuration);
        String timer = String.format("%02d:%02d.%d", 
            (elapsed / 60000), (elapsed / 1000) % 60, (elapsed / 100) % 10);
        context.drawCenteredTextWithShadow(this.textRenderer, timer, centerX, centerY - 30, 0xFFFFFF);
        
        // Frame count
        context.drawCenteredTextWithShadow(this.textRenderer, 
            "Frames: " + recordedFrames, centerX, centerY - 10, 0xCCCCCC);
        
        // Instructions
        if (cinematicMode) {
            context.drawCenteredTextWithShadow(this.textRenderer, 
                "Camera floating above player", centerX, centerY + 15, 0x00FFFF);
        }
        
        context.drawCenteredTextWithShadow(this.textRenderer, 
            isPaused ? "Click RESUME to continue" : "Move around to record camera movement", 
            centerX, centerY + 30, 0x888888);
    }
    
    private void renderCompletedUI(DrawContext context, int centerX, int centerY) {
        // Success box
        context.fill(centerX - 200, centerY - 100, centerX + 200, centerY + 95, 0xFF0A3A0A);
        context.drawBorder(centerX - 200, centerY - 100, 400, 195, 0xFF00FF00);
        
        // Success message
        context.drawCenteredTextWithShadow(this.textRenderer, 
            "✓ Recording Complete!", centerX, centerY - 80, 0x00FF00);
        
        // Clip name
        context.drawCenteredTextWithShadow(this.textRenderer, 
            currentClip.getName(), centerX, centerY - 60, 0xFFFFFF);
        
        // Stats
        String[] stats = {
            "Duration: " + String.format("%.1fs", currentClip.getDuration() / 1000.0),
            "Frames: " + currentClip.getFrames().size(),
            "Type: " + (cinematicMode ? "Cinematic Camera" : "POV Recording"),
            "Quality: " + project.getQuality() + "p @ " + project.getFps() + " FPS"
        };
        
        int statY = centerY - 35;
        for (String stat : stats) {
            context.drawCenteredTextWithShadow(this.textRenderer, stat, 
                                             centerX, statY, 0xAAAAAA);
            statY += 15;
        }
    }
    
    private void renderPlaybackMode(DrawContext context) {
        // Fullscreen black
        context.fill(0, 0, this.width, this.height, 0xFF000000);
        
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        
        // Playback UI
        context.drawCenteredTextWithShadow(this.textRenderer, 
            "PREVIEW PLAYBACK", centerX, 30, 0xFFFFFF);
        
        if (currentClip != null) {
            context.drawCenteredTextWithShadow(this.textRenderer, 
                currentClip.getName(), centerX, centerY - 40, 0x00FF00);
            
            // Simulated playback view
            context.drawCenteredTextWithShadow(this.textRenderer, 
                "[ Recording Playback View ]", centerX, centerY, 0xFFFFFF);
            
            if (cinematicMode) {
                context.drawCenteredTextWithShadow(this.textRenderer, 
                    "🎬 Cinematic Camera Angle", centerX, centerY + 20, 0x00FFFF);
            } else {
                context.drawCenteredTextWithShadow(this.textRenderer, 
                    "📹 POV Playback", centerX, centerY + 20, 0xFFFF00);
            }
            
            context.drawCenteredTextWithShadow(this.textRenderer, 
                currentClip.getFrames().size() + " frames | " + 
                String.format("%.1fs", currentClip.getDuration() / 1000.0), 
                centerX, centerY + 40, 0x888888);
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Record frames while recording and not paused
        if (isRecording && !isPaused && this.client != null && this.client.player != null) {
            Vec3d pos = this.client.player.getPos();
            float yaw = this.client.player.getYaw();
            float pitch = this.client.player.getPitch();
            
            // Adjust for cinematic camera (above player)
            if (cinematicMode) {
                pos = pos.add(0, 3, 0); // Camera 3 blocks above
                pitch = pitch - 15; // Look down slightly
            }
            
            long timestamp = System.currentTimeMillis() - recordingStartTime - pausedDuration;
            
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
