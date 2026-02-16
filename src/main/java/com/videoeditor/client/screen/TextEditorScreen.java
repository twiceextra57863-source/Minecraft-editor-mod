package com.videoeditor.client.screen;

import com.videoeditor.project.Clip;
import com.videoeditor.project.Project;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class TextEditorScreen extends Screen {
    private final Screen parent;
    private final Project project;
    
    private TextFieldWidget textField;
    private int selectedFont = 0;
    private int selectedColor = 0xFFFFFF;
    
    private static final String[] FONTS = {
        "Default", "Bold", "Italic", "Shadow"
    };
    
    private static final int[] COLORS = {
        0xFFFFFF, 0xFF0000, 0x00FF00, 0x0000FF, 
        0xFFFF00, 0xFF00FF, 0x00FFFF, 0xFFA500
    };
    
    public TextEditorScreen(Screen parent, Project project) {
        super(Text.literal("Add Text"));
        this.parent = parent;
        this.project = project;
    }
    
    @Override
    protected void init() {
        super.init();
        
        int centerX = this.width / 2;
        
        // Text input
        textField = new TextFieldWidget(this.textRenderer, centerX - 150, 60, 300, 20, 
                                       Text.literal("Text Content"));
        textField.setMaxLength(100);
        textField.setText("Your text here");
        this.addDrawableChild(textField);
        
        // Font buttons
        int fontY = 100;
        for (int i = 0; i < FONTS.length; i++) {
            final int fontIndex = i;
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal(FONTS[i]), 
                button -> selectedFont = fontIndex
            )
            .dimensions(centerX - 150 + (i * 80), fontY, 70, 20)
            .build());
        }
        
        // Color buttons
        int colorY = 140;
        for (int i = 0; i < COLORS.length; i++) {
            final int colorIndex = i;
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("■"), 
                button -> selectedColor = COLORS[colorIndex]
            )
            .dimensions(centerX - 150 + (i * 40), colorY, 30, 20)
            .build());
        }
        
        // Add text button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("✓ Add Text"), button -> {
            addTextClip();
        })
        .dimensions(centerX - 50, 200, 100, 20)
        .build());
        
        // Back button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("← Back"), button -> {
            if (this.client != null) this.client.setScreen(parent);
        })
        .dimensions(10, this.height - 30, 80, 20)
        .build());
    }
    
    private void addTextClip() {
        Clip textClip = new Clip(textField.getText(), Clip.ClipType.IMAGE);
        textClip.setDuration(3000);
        project.addClip(textClip);
        if (this.client != null) {
            this.client.setScreen(parent);
        }
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xCC000000);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        
        // Labels
        context.drawTextWithShadow(this.textRenderer, "Text:", this.width / 2 - 150, 50, 0xAAAAAA);
        context.drawTextWithShadow(this.textRenderer, "Font:", this.width / 2 - 150, 90, 0xAAAAAA);
        context.drawTextWithShadow(this.textRenderer, "Color:", this.width / 2 - 150, 130, 0xAAAAAA);
        
        // Preview
        context.drawCenteredTextWithShadow(this.textRenderer, "Preview:", this.width / 2, 170, 0xAAAAAA);
        context.drawCenteredTextWithShadow(this.textRenderer, textField.getText(), 
                                         this.width / 2, 185, selectedColor);
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    @Override
    public boolean shouldPause() { return false; }
}
