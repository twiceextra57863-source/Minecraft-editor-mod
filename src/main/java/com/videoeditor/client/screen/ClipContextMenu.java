package com.videoeditor.client.screen;

import com.videoeditor.project.Clip;
import com.videoeditor.project.Project;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class ClipContextMenu extends Screen {
    private final Screen parent;
    private final Project project;
    private final Clip clip;
    private final int clipIndex;
    
    private TextFieldWidget durationField;
    private int newDuration;
    
    public ClipContextMenu(Screen parent, Project project, Clip clip, int clipIndex) {
        super(Text.literal("Edit Clip"));
        this.parent = parent;
        this.project = project;
        this.clip = clip;
        this.clipIndex = clipIndex;
        this.newDuration = (int)(clip.getDuration() / 1000);
    }
    
    @Override
    protected void init() {
        super.init();
        
        int centerX = this.width / 2;
        int startY = 80;
        
        // Duration field
        durationField = new TextFieldWidget(this.textRenderer, centerX - 100, startY, 200, 20,
                                           Text.literal("Duration"));
        durationField.setMaxLength(6);
        durationField.setText(String.valueOf(newDuration));
        this.addDrawableChild(durationField);
        
        // Quick adjust buttons
        int adjY = startY + 30;
        this.addDrawableChild(ButtonWidget.builder(Text.literal("-5s"), 
            button -> adjustDuration(-5))
        .dimensions(centerX - 100, adjY, 45, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("-1s"), 
            button -> adjustDuration(-1))
        .dimensions(centerX - 50, adjY, 45, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("+1s"), 
            button -> adjustDuration(1))
        .dimensions(centerX + 5, adjY, 45, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("+5s"), 
            button -> adjustDuration(5))
        .dimensions(centerX + 55, adjY, 45, 20).build());
        
        // Action buttons
        int btnY = startY + 80;
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("✓ Save Duration"), 
            button -> saveDuration())
        .dimensions(centerX - 120, btnY, 110, 25).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("✂ Split"), 
            button -> splitClip())
        .dimensions(centerX - 5, btnY, 110, 25).build());
        
        btnY += 35;
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("📋 Duplicate"), 
            button -> duplicateClip())
        .dimensions(centerX - 120, btnY, 110, 25).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("🗑 Delete"), 
            button -> deleteClip())
        .dimensions(centerX - 5, btnY, 110, 25).build());
        
        btnY += 35;
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("← Move Left"), 
            button -> moveClip(-1))
        .dimensions(centerX - 120, btnY, 110, 25).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Move Right →"), 
            button -> moveClip(1))
        .dimensions(centerX - 5, btnY, 110, 25).build());
        
        // Close button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("✕ Close"), button -> {
            if (this.client != null) this.client.setScreen(parent);
        })
        .dimensions(centerX - 50, this.height - 50, 100, 25).build());
    }
    
    private void adjustDuration(int seconds) {
        try {
            int current = Integer.parseInt(durationField.getText());
            newDuration = Math.max(1, current + seconds);
            durationField.setText(String.valueOf(newDuration));
        } catch (NumberFormatException e) {
            durationField.setText("5");
        }
    }
    
    private void saveDuration() {
        try {
            int duration = Integer.parseInt(durationField.getText());
            clip.setDuration(duration * 1000L);
        } catch (NumberFormatException e) {
            // Keep original
        }
        if (this.client != null) this.client.setScreen(parent);
    }
    
    private void splitClip() {
        Clip newClip = new Clip(clip.getName() + " (Part 2)", clip.getType());
        newClip.setDuration(clip.getDuration() / 2);
        clip.setDuration(clip.getDuration() / 2);
        project.getClips().add(clipIndex + 1, newClip);
        if (this.client != null) this.client.setScreen(parent);
    }
    
    private void duplicateClip() {
        Clip duplicate = new Clip(clip.getName() + " (Copy)", clip.getType());
        duplicate.setDuration(clip.getDuration());
        project.getClips().add(clipIndex + 1, duplicate);
        if (this.client != null) this.client.setScreen(parent);
    }
    
    private void deleteClip() {
        project.getClips().remove(clipIndex);
        if (this.client != null) this.client.setScreen(parent);
    }
    
    private void moveClip(int direction) {
        int newIndex = clipIndex + direction;
        if (newIndex >= 0 && newIndex < project.getClips().size()) {
            project.getClips().remove(clipIndex);
            project.getClips().add(newIndex, clip);
        }
        if (this.client != null) this.client.setScreen(parent);
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xDD000000);
        
        // Title
        context.drawCenteredTextWithShadow(this.textRenderer, 
            "Edit Clip: " + clip.getName(), this.width / 2, 30, 0xFFFFFF);
        
        // Labels
        context.drawTextWithShadow(this.textRenderer, "Duration (seconds):", 
                                  this.width / 2 - 100, 70, 0xAAAAAA);
        
        // Clip preview
        int prevY = this.height - 120;
        context.drawCenteredTextWithShadow(this.textRenderer, 
            "Type: " + clip.getType() + " | Frames: " + clip.getFrames().size(), 
            this.width / 2, prevY, 0x888888);
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    @Override
    public boolean shouldPause() { return false; }
}
