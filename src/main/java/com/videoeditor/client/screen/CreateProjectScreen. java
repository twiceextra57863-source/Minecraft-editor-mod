package com.videoeditor.client.screen;

import com.videoeditor.VideoEditorMod;
import com.videoeditor.manager.ProjectManager;
import com.videoeditor.project.Project;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class CreateProjectScreen extends Screen {
    private final Screen parent;
    private final ProjectManager projectManager;
    
    private TextFieldWidget nameField;
    private TextFieldWidget descriptionField;
    private TextFieldWidget qualityField;
    private TextFieldWidget fpsField;
    
    public CreateProjectScreen(Screen parent) {
        super(Text.literal("Create New Project"));
        this.parent = parent;
        this.projectManager = VideoEditorMod.getInstance().getProjectManager();
    }
    
    @Override
    protected void init() {
        super.init();
        
        int centerX = this.width / 2;
        int startY = 60;
        int fieldWidth = 300;
        int fieldHeight = 20;
        int spacing = 35;
        
        // Name field
        nameField = new TextFieldWidget(this.textRenderer, centerX - fieldWidth / 2, startY, 
                                       fieldWidth, fieldHeight, Text.literal("Project Name"));
        nameField.setMaxLength(50);
        nameField.setText("My Project");
        this.addDrawableChild(nameField);
        
        // Description field
        descriptionField = new TextFieldWidget(this.textRenderer, centerX - fieldWidth / 2, 
                                              startY + spacing, fieldWidth, fieldHeight, 
                                              Text.literal("Description"));
        descriptionField.setMaxLength(200);
        descriptionField.setText("Project description");
        this.addDrawableChild(descriptionField);
        
        // Quality field
        qualityField = new TextFieldWidget(this.textRenderer, centerX - fieldWidth / 2, 
                                          startY + spacing * 2, fieldWidth, fieldHeight, 
                                          Text.literal("Quality"));
        qualityField.setMaxLength(4);
        qualityField.setText("1080");
        this.addDrawableChild(qualityField);
        
        // FPS field
        fpsField = new TextFieldWidget(this.textRenderer, centerX - fieldWidth / 2, 
                                      startY + spacing * 3, fieldWidth, fieldHeight, 
                                      Text.literal("FPS"));
        fpsField.setMaxLength(3);
        fpsField.setText("60");
        this.addDrawableChild(fpsField);
        
        // Create button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Create"), button -> {
            createProject();
        })
        .dimensions(centerX - 100, startY + spacing * 4 + 20, 90, 20)
        .build());
        
        // Cancel button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Cancel"), button -> {
            if (this.client != null) {
                this.client.setScreen(parent);
            }
        })
        .dimensions(centerX + 10, startY + spacing * 4 + 20, 90, 20)
        .build());
    }
    
    private void createProject() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        
        int quality;
        int fps;
        
        try {
            quality = Integer.parseInt(qualityField.getText());
            fps = Integer.parseInt(fpsField.getText());
        } catch (NumberFormatException e) {
            // Default values if parsing fails
            quality = 1080;
            fps = 60;
        }
        
        Project project = projectManager.createProject(name, description, quality, fps);
        
        if (this.client != null) {
            this.client.setScreen(new EditorScreen(project));
        }
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Render background
        context.fill(0, 0, this.width, this.height, 0xCC000000);
        
        // Render title
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        
        // Render labels
        int centerX = this.width / 2;
        int startY = 60;
        int spacing = 35;
        int labelOffset = -150 - 60;
        
        context.drawTextWithShadow(this.textRenderer, "Name:", centerX + labelOffset, startY + 6, 0xAAAAAA);
        context.drawTextWithShadow(this.textRenderer, "Description:", centerX + labelOffset, startY + spacing + 6, 0xAAAAAA);
        context.drawTextWithShadow(this.textRenderer, "Quality (p):", centerX + labelOffset, startY + spacing * 2 + 6, 0xAAAAAA);
        context.drawTextWithShadow(this.textRenderer, "FPS:", centerX + labelOffset, startY + spacing * 3 + 6, 0xAAAAAA);
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    @Override
    public boolean shouldPause() {
        return false;
    }
}

