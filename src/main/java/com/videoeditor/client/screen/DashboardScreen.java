package com.videoeditor.client.screen;

import com.videoeditor.VideoEditorMod;
import com.videoeditor.manager.ProjectManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class DashboardScreen extends Screen {
    private final ProjectManager projectManager;
    private boolean menuOpen = false;
    private static final int MENU_WIDTH = 200;
    private static final int MENU_ICON_SIZE = 30;
    
    public DashboardScreen() {
        super(Text.literal("Video Editor Dashboard"));
        this.projectManager = VideoEditorMod.getInstance().getProjectManager();
    }
    
    @Override
    protected void init() {
        super.init();
        
        // Add menu toggle button (3 lines icon) in top left
        this.addDrawableChild(ButtonWidget.builder(Text.literal("☰"), button -> {
            menuOpen = !menuOpen;
        })
        .dimensions(10, 10, MENU_ICON_SIZE, MENU_ICON_SIZE)
        .build());
        
        // Add close button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Close"), button -> {
            if (this.client != null) {
                this.client.setScreen(null);
            }
        })
        .dimensions(this.width - 110, this.height - 30, 100, 20)
        .build());
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Render dark background
        context.fill(0, 0, this.width, this.height, 0xCC000000);
        
        // Render title
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        
        // Render side menu if open
        if (menuOpen) {
            renderSideMenu(context, mouseX, mouseY);
        }
        
        // Render main content
        renderMainContent(context);
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    private void renderSideMenu(DrawContext context, int mouseX, int mouseY) {
        // Draw menu background
        context.fill(0, 0, MENU_WIDTH, this.height, 0xEE222222);
        
        int yOffset = 50;
        int buttonHeight = 30;
        int spacing = 10;
        
        // Draw menu items
        if (!projectManager.hasProjects()) {
            // Show "Create Project" option
            boolean hovered = mouseX >= 10 && mouseX <= MENU_WIDTH - 10 && 
                            mouseY >= yOffset && mouseY <= yOffset + buttonHeight;
            
            context.fill(10, yOffset, MENU_WIDTH - 10, yOffset + buttonHeight, 
                        hovered ? 0xFF4444FF : 0xFF333333);
            context.drawTextWithShadow(this.textRenderer, "Create Project", 20, yOffset + 10, 0xFFFFFF);
        } else {
            // Show project list and create new option
            context.drawTextWithShadow(this.textRenderer, "Projects:", 20, yOffset, 0xFFFFFF);
            yOffset += 30;
            
            for (int i = 0; i < projectManager.getProjects().size(); i++) {
                var project = projectManager.getProjects().get(i);
                boolean hovered = mouseX >= 10 && mouseX <= MENU_WIDTH - 10 && 
                                mouseY >= yOffset && mouseY <= yOffset + buttonHeight;
                
                context.fill(10, yOffset, MENU_WIDTH - 10, yOffset + buttonHeight, 
                            hovered ? 0xFF4444FF : 0xFF333333);
                context.drawTextWithShadow(this.textRenderer, project.getName(), 20, yOffset + 10, 0xFFFFFF);
                yOffset += buttonHeight + spacing;
            }
            
            // Add "New Project" button
            yOffset += 10;
            boolean hovered = mouseX >= 10 && mouseX <= MENU_WIDTH - 10 && 
                            mouseY >= yOffset && mouseY <= yOffset + buttonHeight;
            context.fill(10, yOffset, MENU_WIDTH - 10, yOffset + buttonHeight, 
                        hovered ? 0xFF44FF44 : 0xFF333333);
            context.drawTextWithShadow(this.textRenderer, "+ New Project", 20, yOffset + 10, 0xFFFFFF);
        }
    }
    
    private void renderMainContent(DrawContext context) {
        int contentX = menuOpen ? MENU_WIDTH + 20 : 20;
        int contentY = 60;
        
        if (projectManager.getCurrentProject() != null) {
            var project = projectManager.getCurrentProject();
            context.drawTextWithShadow(this.textRenderer, 
                "Current Project: " + project.getName(), contentX, contentY, 0xFFFFFF);
            contentY += 20;
            context.drawTextWithShadow(this.textRenderer, 
                "Quality: " + project.getQuality() + "p", contentX, contentY, 0xAAAAAA);
            contentY += 15;
            context.drawTextWithShadow(this.textRenderer, 
                "FPS: " + project.getFps(), contentX, contentY, 0xAAAAAA);
            contentY += 15;
            context.drawTextWithShadow(this.textRenderer, 
                "Clips: " + project.getClips().size(), contentX, contentY, 0xAAAAAA);
        } else {
            context.drawTextWithShadow(this.textRenderer, 
                "No project selected", contentX, contentY, 0x888888);
            contentY += 20;
            context.drawTextWithShadow(this.textRenderer, 
                "Click the menu icon to create a project", contentX, contentY, 0x888888);
        }
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (menuOpen && mouseX < MENU_WIDTH) {
            handleMenuClick(mouseX, mouseY);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    private void handleMenuClick(double mouseX, double mouseY) {
        int yOffset = 50;
        int buttonHeight = 30;
        int spacing = 10;
        
        if (!projectManager.hasProjects()) {
            // Check if "Create Project" was clicked
            if (mouseX >= 10 && mouseX <= MENU_WIDTH - 10 && 
                mouseY >= yOffset && mouseY <= yOffset + buttonHeight) {
                if (this.client != null) {
                    this.client.setScreen(new CreateProjectScreen(this));
                }
            }
        } else {
            yOffset += 30; // Skip "Projects:" label
            
            // Check project clicks
            for (int i = 0; i < projectManager.getProjects().size(); i++) {
                var project = projectManager.getProjects().get(i);
                if (mouseX >= 10 && mouseX <= MENU_WIDTH - 10 && 
                    mouseY >= yOffset && mouseY <= yOffset + buttonHeight) {
                    projectManager.setCurrentProject(project);
                    if (this.client != null) {
                        this.client.setScreen(new EditorScreen(project));
                    }
                    return;
                }
                yOffset += buttonHeight + spacing;
            }
            
            // Check "New Project" click
            yOffset += 10;
            if (mouseX >= 10 && mouseX <= MENU_WIDTH - 10 && 
                mouseY >= yOffset && mouseY <= yOffset + buttonHeight) {
                if (this.client != null) {
                    this.client.setScreen(new CreateProjectScreen(this));
                }
            }
        }
    }
    
    @Override
    public boolean shouldPause() {
        return false;
    }
                  }
