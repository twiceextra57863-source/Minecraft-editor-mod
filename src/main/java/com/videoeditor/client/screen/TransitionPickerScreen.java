package com.videoeditor.client.screen;

import com.videoeditor.project.Project;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class TransitionPickerScreen extends Screen {
    private final Screen parent;
    private final Project project;
    private final int clipIndex;
    
    private static final String[] TRANSITIONS = {
        "Fade", "Dissolve", "Wipe", "Slide",
        "Zoom", "Spin", "Blur", "Pixelate"
    };
    
    public TransitionPickerScreen(Screen parent, Project project, int clipIndex) {
        super(Text.literal("Choose Transition"));
        this.parent = parent;
        this.project = project;
        this.clipIndex = clipIndex;
    }
    
    @Override
    protected void init() {
        super.init();
        
        int startX = 50;
        int startY = 60;
        int buttonWidth = 150;
        int buttonHeight = 30;
        int spacing = 10;
        
        for (int i = 0; i < TRANSITIONS.length; i++) {
            final String transition = TRANSITIONS[i];
            int row = i / 4;
            int col = i % 4;
            
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("🔄 " + transition), 
                button -> applyTransition(transition)
            )
            .dimensions(
                startX + col * (buttonWidth + spacing), 
                startY + row * (buttonHeight + spacing), 
                buttonWidth, 
                buttonHeight
            )
            .build());
        }
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("← Back"), button -> {
            if (this.client != null) this.client.setScreen(parent);
        })
        .dimensions(10, this.height - 30, 80, 20)
        .build());
    }
    
    private void applyTransition(String transition) {
        if (this.client != null) {
            this.client.setScreen(parent);
        }
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xCC000000);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, 
            "Add transition between clips", this.width / 2, 40, 0xAAAAAA);
        super.render(context, mouseX, mouseY, delta);
    }
    
    @Override
    public boolean shouldPause() { return false; }
}
