package com.videoeditor.client.screen;

import com.videoeditor.project.Clip;
import com.videoeditor.project.Project;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class TimelineClipEditor extends Screen {
    private final Screen parent;
    private final Project project;
    private final Clip clip;
    private final int clipIndex;
    
    private TextFieldWidget durationField;
    private TextFieldWidget nameField;
    private int newDuration;
    
    public TimelineClipEditor(Screen parent, Project project, Clip clip, int clipIndex) {
        super(Text.literal("Edit Clip - " + clip.getName()));
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
        int startY = 60;
        
        // Name field
        nameField = new TextFieldWidget(this.textRenderer, centerX - 150, startY, 300, 20,
                                       Text.literal("Clip Name"));
        nameField.setMaxLength(50);
        nameField.setText(clip.getName());
        this.addDrawableChild(nameField);
        
        // Duration field (in seconds)
        durationField = new TextFieldWidget(this.textRenderer, centerX - 150, startY + 40, 300, 20,
                                           Text.literal("Duration (seconds)"));
        durationField.setMaxLength(6);
        durationField.setText(String.valueOf(newDuration));
        this.addDrawableChild(durationField);
        
        // Quick duration buttons
        int buttonY = startY + 80;
        this.addDrawableChild(ButtonWidget.builder(Text.literal("+1s"), 
            button -> adjustDuration(1))
        .dimensions(centerX - 150, buttonY, 60, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("+5s"), 
            button -> adjustDuration(5))
        .dimensions(centerX - 80, buttonY, 60, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("+10s"), 
            button -> adjustDuration(10))
        .dimensions(centerX - 10, buttonY, 60, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("-1s"), 
            button -> adjustDuration(-1))
        .dimensions(centerX + 60, buttonY, 60, 20).build());
        
        // Clip operations
        int opsY = startY + 130;
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("✂ Split Clip"), 
            button -> splitClip())
        .dimensions(centerX - 150, opsY, 95, 25).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("📋 Duplicate"), 
            button -> duplicateClip())
        .dimensions(centerX - 45, opsY, 95, 25).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("🗑 Delete"), 
            button -> deleteClip())
        .dimensions(centerX + 60, opsY, 95, 25).build());
        
        // Move clip buttons
        int moveY = opsY + 35;
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("← Move Left"), 
            button -> moveClip(-1))
        .dimensions(centerX - 100, moveY, 95, 25).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Move Right →"), 
            button -> moveClip(1))
        .dimensions(centerX + 5, moveY, 95, 25).build());
        
        // Save and Cancel
        this.addDrawableChild(ButtonWidget.builder(Text.literal("✓ Save Changes"), 
            button -> saveChanges())
        .dimensions(centerX - 80, this.height - 60, 160, 30).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("← Cancel"), 
            button -> {
                if (this.client != null) this.client.setScreen(parent);
            })
        .dimensions(10, this.height - 35, 80, 25).build());
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
    
    private void splitClip() {
        // Split clip into two at midpoint
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
    
    private void saveChanges() {
        clip.setName(nameField.getText());
        
        try {
            int duration = Integer.parseInt(durationField.getText());
            clip.setDuration(duration * 1000L);
        } catch (NumberFormatException e) {
            // Keep original duration
        }
        
        if (this.client != null) this.client.setScreen(parent);
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xCC000000);
        
        // Title
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, 
                                         this.width / 2, 20, 0xFFFFFF);
        
        // Labels
        int centerX = this.width / 2;
        context.drawTextWithShadow(this.textRenderer, "Clip Name:", 
                                  centerX - 150, 50, 0xAAAAAA);
        context.drawTextWithShadow(this.textRenderer, "Duration (seconds):", 
                                  centerX - 150, 90, 0xAAAAAA);
        context.drawTextWithShadow(this.textRenderer, "Quick Adjust:", 
                                  centerX - 150, 110, 0x888888);
        context.drawTextWithShadow(this.textRenderer, "Clip Operations:", 
                                  centerX - 150, 170, 0xAAAAAA);
        
        // Clip preview
        int previewY = 250;
        context.fill(centerX - 100, previewY, centerX + 100, previewY + 60, 0xFF2A2A2A);
        context.drawBorder(centerX - 100, previewY, 200, 60, 0xFF444444);
        
        String icon = clip.getType() == Clip.ClipType.CAMERA ? "📹" : 
                     (clip.getType() == Clip.ClipType.IMAGE ? "🖼" : "🖥");
        context.drawCenteredTextWithShadow(this.textRenderer, icon, centerX, previewY + 10, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, clip.getType().toString(), 
                                         centerX, previewY + 30, 0xAAAAAA);
        context.drawCenteredTextWithShadow(this.textRenderer, 
                                         clip.getFrames().size() + " frames", 
                                         centerX, previewY + 45, 0x888888);
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    @Override
    public boolean shouldPause() { return false; }
                                   }
