package com.videoeditor.recording;

import com.videoeditor.project.Clip;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.List;

public class RecordingManager {
    private static RecordingManager instance;
    private boolean isRecording = false;
    private boolean isPaused = false;
    private Clip currentClip;
    private long recordingStartTime;
    private long pausedDuration = 0;
    private long pauseStartTime = 0;
    private MinecraftClient client;
    
    // Recording settings
    private RecordingMode mode = RecordingMode.POV;
    
    public enum RecordingMode {
        POV,           // First person recording
        CINEMATIC,     // Ghost player with free camera
        REPLAY         // Full world recording (Replay mod style)
    }
    
    public static RecordingManager getInstance() {
        if (instance == null) {
            instance = new RecordingManager();
        }
        return instance;
    }
    
    private RecordingManager() {
        this.client = MinecraftClient.getInstance();
    }
    
    public void startRecording(String clipName, RecordingMode mode) {
        this.mode = mode;
        this.isRecording = true;
        this.isPaused = false;
        this.currentClip = new Clip(clipName, Clip.ClipType.CAMERA);
        this.recordingStartTime = System.currentTimeMillis();
        this.pausedDuration = 0;
    }
    
    public void stopRecording() {
        if (currentClip != null) {
            long duration = System.currentTimeMillis() - recordingStartTime - pausedDuration;
            currentClip.setDuration(duration);
        }
        this.isRecording = false;
        this.isPaused = false;
    }
    
    public void pauseRecording() {
        if (isRecording && !isPaused) {
            pauseStartTime = System.currentTimeMillis();
            isPaused = true;
        }
    }
    
    public void resumeRecording() {
        if (isRecording && isPaused) {
            pausedDuration += System.currentTimeMillis() - pauseStartTime;
            isPaused = false;
        }
    }
    
    public void tick() {
        if (isRecording && !isPaused && client.player != null) {
            recordFrame();
        }
    }
    
    private void recordFrame() {
        Vec3d pos = client.player.getPos();
        float yaw = client.player.getYaw();
        float pitch = client.player.getPitch();
        
        // For cinematic mode, camera is 3 blocks above
        if (mode == RecordingMode.CINEMATIC) {
            pos = pos.add(0, 3, 0);
            pitch -= 15;
        }
        
        long timestamp = System.currentTimeMillis() - recordingStartTime - pausedDuration;
        
        if (currentClip != null) {
            currentClip.addFrame(new Clip.CameraFrame(pos, yaw, pitch, timestamp));
        }
    }
    
    public boolean isRecording() { return isRecording; }
    public boolean isPaused() { return isPaused; }
    public Clip getCurrentClip() { return currentClip; }
    public RecordingMode getMode() { return mode; }
    
    public String getRecordingTime() {
        if (!isRecording) return "00:00";
        
        long elapsed = isPaused ? 
            (pauseStartTime - recordingStartTime - pausedDuration) :
            (System.currentTimeMillis() - recordingStartTime - pausedDuration);
            
        long minutes = elapsed / 60000;
        long seconds = (elapsed / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    
    public int getFrameCount() {
        return currentClip != null ? currentClip.getFrames().size() : 0;
    }
}
