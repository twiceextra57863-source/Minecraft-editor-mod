package com.videoeditor.client.screen;

import com.videoeditor.project.Project;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class ExportScreen extends Screen {
    private final Screen parent;
    private final Project project;
    
    private TextFieldWidget filenameField;
    private int selectedQuality = 1080;
    private int selectedFps = 60;
    private String selectedFormat = "MP4";
    
    public ExportScreen(Screen parent, Project project) {
        super(Text.literal("Export Project"));
        this.parent = parent;
        this.project = project;
    }
    
    @Override
    protected void init() {
        super.init();
        
        int centerX = this.width / 2;
        int startY = 60;
        
        // Filename
        filenameField = new TextFieldWidget(this.textRenderer, centerX - 150, startY, 300, 20, 
                                           Text.literal("Filename"));
        filenameField.setMaxLength(50);
        filenameField.setText(project.getName());
        this.addDrawableChild(filenameField);
        
        // Quality options
        startY += 40;
        this.addDrawableChild(ButtonWidget.builder(Text.literal("720p"), 
            button -> selectedQuality = 720)
        .dimensions(centerX - 150, startY, 70, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("1080p"), 
            button -> selectedQuality = 1080)
        .dimensions(centerX - 70, startY, 70, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("1440p"), 
            button -> selectedQuality = 1440)
        .dimensions(centerX + 10, startY, 70, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("4K"), 
            button -> selectedQuality = 2160)
        .dimensions(centerX + 90, startY, 70, 20).build());
        
        // FPS options
        startY += 40;
        this.addDrawableChild(ButtonWidget.builder(Text.literal("30 FPS"), 
            button -> selectedFps = 30)
        .dimensions(centerX - 110, startY, 70, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("60 FPS"), 
            button -> selectedFps = 60)
        .dimensions(centerX - 30, startY, 70, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("120 FPS"), 
            button -> selectedFps = 120)
        .dimensions(centerX + 50, startY, 70, 20).build());
        
        // Format options
        startY += 40;
        this.addDrawableChild(ButtonWidget.builder(Text.literal("MP4"), 
            button -> selectedFormat = "MP4")
        .dimensions(centerX - 110, startY, 70, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("MOV"), 
            button -> selectedFormat = "MOV")
        .dimensions(centerX - 30, startY, 70, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("AVI"), 
            button -> selectedFormat = "AVI")
        .dimensions(centerX + 50, startY, 70, 20).build());
        
        // Export button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("💾 Export Video"), button -> {
            exportVideo();
        })
        .dimensions(centerX - 75, startY + 60, 150, 25)
        .build());
        
        // Back button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("← Back"), button -> {
            if (this.client != null) this.client.setScreen(parent);
        })
        .dimensions(10, this.height - 30, 80, 20)
        .build());
    }
    
    private void exportVideo() {
        // Future: Actual export logic
        if (this.client != null) {
            this.client.setScreen(parent);
        }
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xCC000000);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        
        // Labels
        context.drawTextWithShadow(this.textRenderer, "Filename:", this.width / 2 - 150, 50, 0xAAAAAA);
        context.drawTextWithShadow(this.textRenderer, "Quality:", this.width / 2 - 150, 90, 0xAAAAAA);
        context.drawTextWithShadow(this.textRenderer, "Frame Rate:", this.width / 2 - 150, 130, 0xAAAAAA);
        context.drawTextWithShadow(this.textRenderer, "Format:", this.width / 2 - 150, 170, 0xAAAAAA);
        
        // Current selection
        String exportInfo = String.format("Export as: %s.%s (%dp @ %d FPS)", 
                                         filenameField.getText(), selectedFormat, selectedQuality, selectedFps);
        context.drawCenteredTextWithShadow(this.textRenderer, exportInfo, 
                                         this.width / 2, 230, 0x00FF00);
        
        // Project info
        context.drawCenteredTextWithShadow(this.textRenderer, 
            "Project has " + project.getClips().size() + " clips", 
            this.width / 2, 250, 0x888888);
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    @Override
    public boolean shouldPause() { return false; }
}
