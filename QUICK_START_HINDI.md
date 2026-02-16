# Minecraft Video Editor Mod - Quick Start (Hindi/Hinglish)

## Installation Steps (Kaise Install Karein)

### 1. Requirements Check Karo
- Java 21 installed hona chahiye
- Minecraft 1.21.4 hona chahiye  
- Fabric Loader install hona chahiye

### 2. Mod Build Karo

**Linux/Mac:**
```bash
cd minecraft-editor-mod
./gradlew build
```

**Windows:**
```bash
cd minecraft-editor-mod
gradlew.bat build
```

### 3. JAR File Copy Karo
- `build/libs/` folder me jaao
- `minecraft-video-editor-1.0.0.jar` file copy karo
- `.minecraft/mods/` folder me paste karo

### 4. Game Start Karo!
Minecraft launch karo aur game me jaao.

## Kaise Use Karein (How to Use)

### Dashboard Kholna
1. Game me **T** key press karo
2. Dashboard khul jayega

### Project Banana
1. Top-left corner me **☰** (3 line) icon pe click karo
2. **"Create Project"** pe click karo
3. Form fill karo:
   - **Name**: Apna project ka naam
   - **Description**: Kuch description likho
   - **Quality**: Resolution (jaise 1080)
   - **FPS**: Frame rate (jaise 60)
4. **"Create"** button pe click karo

### Camera Clip Add Karna
1. Dashboard se apna project kholo
2. **"+ Camera Clip"** button pe click karo
3. **"⏺ Start Recording"** pe click karo
4. Duniya me ghumo - tumhari camera movement record hogi
5. **ESC** press karo ya **"⏹ Stop Recording"** pe click karo
6. **"▶ Preview"** pe click karke dekhlo recording
7. **"✓ Save"** pe click karke clip project me add karo

### Editor Use Karna
- **Preview Area**: Upar wale section me video preview dikhega
- **Timeline**: Niche wale section me sare clips dikhenge
- **Controls**: Play/Pause buttons se playback control karo
- **Fullscreen**: **"⛶ Full"** pe click karke fullscreen me dekho

## Important Features

### Abhi Available Hai:
✅ Dashboard with menu
✅ Project creation form
✅ Camera clip recording
✅ Timeline view
✅ Preview area with fullscreen
✅ Play/Pause controls

### Future me Aayega:
⏳ Actual video rendering
⏳ More clip types
⏳ Transitions aur effects
⏳ Audio support
⏳ Video export

## Common Problems & Solutions

### Problem: Build nahi ho raha
**Solution:**
- Java 21 installed hai check karo
- `./gradlew clean` run karo
- Phir `./gradlew build` run karo

### Problem: Mod load nahi ho raha
**Solution:**
- Fabric Loader install hai check karo
- Minecraft version 1.21.4 hai confirm karo
- Fabric API mod folder me hai check karo

### Problem: T key kaam nahi kar raha
**Solution:**
- Minecraft settings me keybind conflicts check karo
- `.minecraft/logs/latest.log` me errors dekho

## File Structure Samajhna

```
minecraft-editor-mod/
├── src/main/java/           # Java code yaha hai
│   └── com/videoeditor/
│       ├── VideoEditorMod.java      # Main mod file
│       ├── client/                   # Client-side code
│       │   ├── KeyBindings.java     # T key handler
│       │   └── screen/              # UI screens
│       ├── manager/                 # Project management
│       └── project/                 # Project/Clip classes
└── src/main/resources/      # Resources (config, lang files)
```

## Tips & Tricks

1. **Multiple Projects**: Tum multiple projects bana sakte ho aur switch kar sakte ho
2. **Timeline**: Clips ko timeline me click karke select karo
3. **Recording**: Recording ke time pe normal jaise ghoom sakte ho, bas break/use nahi kar sakte
4. **Fullscreen**: Preview fullscreen me dekhna better experience deta hai

## Help Chahiye?

Agar koi problem aa rahi hai to:
1. README.md file padho (English me detailed info hai)
2. Logs check karo: `.minecraft/logs/latest.log`
3. GitHub pe issue create karo (agar GitHub pe upload kiya to)

## Credits

Made with ❤️ for Minecraft modding community!

Enjoy editing! 🎬🎮
