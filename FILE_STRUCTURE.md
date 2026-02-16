# Complete File Structure Overview

## Directory Tree

```
minecraft-editor-mod/
│
├── build.gradle                    # Main build configuration
├── gradle.properties               # Version info (Minecraft, Fabric, Mod versions)
├── settings.gradle                 # Gradle project settings
├── gradlew                        # Gradle wrapper (Linux/Mac)
├── gradlew.bat                    # Gradle wrapper (Windows)
├── README.md                      # Main documentation (English)
├── QUICK_START_HINDI.md          # Quick start guide (Hindi/Hinglish)
│
└── src/
    └── main/
        │
        ├── java/                   # All Java source code
        │   └── com/
        │       └── videoeditor/
        │           │
        │           ├── VideoEditorMod.java              # [MAIN] Mod entry point
        │           │   - Initializes the mod
        │           │   - Registers keybindings
        │           │   - Creates ProjectManager
        │           │
        │           ├── client/                          # Client-side code
        │           │   │
        │           │   ├── KeyBindings.java            # Handles T key press
        │           │   │   - Registers T keybind
        │           │   │   - Opens DashboardScreen
        │           │   │
        │           │   └── screen/                     # All GUI screens
        │           │       │
        │           │       ├── DashboardScreen.java   # Main dashboard UI
        │           │       │   - Shows menu with project list
        │           │       │   - 3-line menu icon
        │           │       │   - Create/select projects
        │           │       │
        │           │       ├── CreateProjectScreen.java  # Project creation form
        │           │       │   - Input fields: name, description, quality, fps
        │           │       │   - Creates new Project
        │           │       │   - Opens EditorScreen after creation
        │           │       │
        │           │       ├── EditorScreen.java       # Main editor interface
        │           │       │   - Preview area (top)
        │           │       │   - Timeline (bottom)
        │           │       │   - Play/Pause controls
        │           │       │   - Fullscreen toggle
        │           │       │   - Add clip button
        │           │       │
        │           │       └── CameraRecordingScreen.java  # Camera recording
        │           │           - Start/Stop recording
        │           │           - Records camera frames
        │           │           - Preview recorded clip
        │           │           - Save/Discard options
        │           │
        │           ├── manager/                        # Management classes
        │           │   │
        │           │   └── ProjectManager.java        # Manages all projects
        │           │       - Creates projects
        │           │       - Stores project list
        │           │       - Current project tracking
        │           │       - Add clips to projects
        │           │
        │           ├── project/                       # Data classes
        │           │   │
        │           │   ├── Project.java              # Project data model
        │           │   │   - ID, name, description
        │           │   │   - Quality, FPS settings
        │           │   │   - List of clips
        │           │   │   - Creation timestamp
        │           │   │
        │           │   └── Clip.java                 # Clip data model
        │           │       - ID, name, type (CAMERA/SCREEN/IMAGE)
        │           │       - List of camera frames
        │           │       - Duration
        │           │       - CameraFrame inner class
        │           │
        │           └── mixin/                        # Mixin classes
        │               │
        │               └── MinecraftClientMixin.java  # Client tick hook
        │                   - Can be used for future recording features
        │
        └── resources/                                # Resource files
            │
            ├── fabric.mod.json                      # Mod metadata
            │   - Mod ID, name, version
            │   - Entry points
            │   - Dependencies
            │
            ├── videoeditor.mixins.json             # Mixin configuration
            │   - Lists all mixin classes
            │
            └── assets/
                └── videoeditor/
                    └── lang/
                        └── en_us.json              # English translations
                            - Keybind names
                            - Category names
```

## Key File Descriptions

### Build Files
- **build.gradle**: Configures how the mod is built, dependencies, Java version
- **gradle.properties**: Stores version numbers for Minecraft, Fabric, and the mod
- **settings.gradle**: Basic Gradle project settings

### Main Code Files

#### Core
- **VideoEditorMod.java**: The starting point of the mod. Runs when Minecraft starts.

#### Client Package
- **KeyBindings.java**: Listens for T key and opens the dashboard

#### Screen Package (All UI)
- **DashboardScreen.java**: First screen you see when pressing T
- **CreateProjectScreen.java**: Form to create a new project
- **EditorScreen.java**: Main editing workspace
- **CameraRecordingScreen.java**: Recording and previewing camera movement

#### Manager Package
- **ProjectManager.java**: Keeps track of all projects and which one is active

#### Project Package
- **Project.java**: Stores info about a video project
- **Clip.java**: Stores info about a single video clip

#### Mixin Package
- **MinecraftClientMixin.java**: Hooks into Minecraft's code for special features

### Resource Files
- **fabric.mod.json**: Tells Fabric about your mod (name, version, etc.)
- **videoeditor.mixins.json**: Tells the game which mixins to use
- **en_us.json**: English text for keybinds

## How They Work Together

```
Player presses T
    ↓
KeyBindings.java catches it
    ↓
Opens DashboardScreen.java
    ↓
Player clicks "Create Project"
    ↓
Opens CreateProjectScreen.java
    ↓
Player fills form and clicks Create
    ↓
ProjectManager.java creates new Project
    ↓
Opens EditorScreen.java
    ↓
Player clicks "Add Camera Clip"
    ↓
Opens CameraRecordingScreen.java
    ↓
Player records camera movement
    ↓
CameraFrame objects stored in Clip
    ↓
Clip added to Project
    ↓
Shows in Timeline on EditorScreen
```

## Do You Need Two Structure Ways?

**Answer: NO!**

You don't need separate `main/java` and `client/java` folders. 

**Fabric handles client/server separation differently:**

1. **Single source folder**: All code goes in `src/main/java/`

2. **Client-only mod**: In `fabric.mod.json`, we set:
   ```json
   "environment": "client"
   ```
   This tells Fabric the ENTIRE mod is client-only.

3. **Client-specific code**: We organize client code in packages:
   ```
   com/videoeditor/client/     # Client-only stuff
   com/videoeditor/project/    # Shared data (works everywhere)
   ```

4. **Entry points**: In `fabric.mod.json`:
   ```json
   "entrypoints": {
     "client": ["com.videoeditor.VideoEditorMod"]
   }
   ```
   This runs the mod only on client side.

**So the structure is:**
```
src/
  main/
    java/              # All your Java code (both client & shared)
    resources/         # All resources (textures, configs, etc.)
```

No need for `client/java/` folder!

## What's Missing?

To make this work, you still need:
1. **Gradle wrapper JAR**: Download from Gradle or copy from another project
2. **Icon file**: Create a 256x256 PNG icon at `src/main/resources/assets/videoeditor/icon.png`

But the code structure is complete and will compile!
