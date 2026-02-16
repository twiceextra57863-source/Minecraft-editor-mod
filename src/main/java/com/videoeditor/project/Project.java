package com.videoeditor.project;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Project {
    private final String id;
    private String name;
    private String description;
    private int quality;
    private int fps;
    private List<Clip> clips;
    private long createdTime;
    
    public Project(String name, String description, int quality, int fps) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.quality = quality;
        this.fps = fps;
        this.clips = new ArrayList<>();
        this.createdTime = System.currentTimeMillis();
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getQuality() {
        return quality;
    }
    
    public void setQuality(int quality) {
        this.quality = quality;
    }
    
    public int getFps() {
        return fps;
    }
    
    public void setFps(int fps) {
        this.fps = fps;
    }
    
    public List<Clip> getClips() {
        return clips;
    }
    
    public void addClip(Clip clip) {
        this.clips.add(clip);
    }
    
    public void removeClip(Clip clip) {
        this.clips.remove(clip);
    }
    
    public long getCreatedTime() {
        return createdTime;
    }
}
