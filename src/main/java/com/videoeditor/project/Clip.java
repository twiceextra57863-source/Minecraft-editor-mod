package com.videoeditor.project;

import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.List;

public class Clip {
    private final String id;
    private String name;
    private ClipType type;
    private List<CameraFrame> frames;
    private long duration;
    
    public enum ClipType {
        CAMERA,
        SCREEN_RECORDING,
        IMAGE
    }
    
    public Clip(String name, ClipType type) {
        this.id = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.frames = new ArrayList<>();
        this.duration = 0;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public ClipType getType() {
        return type;
    }
    
    public List<CameraFrame> getFrames() {
        return frames;
    }
    
    public void addFrame(CameraFrame frame) {
        this.frames.add(frame);
    }
    
    public long getDuration() {
        return duration;
    }
    
    public void setDuration(long duration) {
        this.duration = duration;
    }
    
    public static class CameraFrame {
        public Vec3d position;
        public float yaw;
        public float pitch;
        public long timestamp;
        
        public CameraFrame(Vec3d position, float yaw, float pitch, long timestamp) {
            this.position = position;
            this.yaw = yaw;
            this.pitch = pitch;
            this.timestamp = timestamp;
        }
    }
}
