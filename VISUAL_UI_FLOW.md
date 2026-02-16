# Visual UI Flow & Screenshots (Text Representation)

## Main UI Flow Diagram

```
                           ┌──────────────────┐
                           │  MINECRAFT GAME  │
                           │   (Press T Key)  │
                           └────────┬─────────┘
                                    │
                                    ▼
                    ┌───────────────────────────────┐
                    │     DASHBOARD SCREEN          │
                    │  ┌──────────────────────┐    │
                    │  │ ☰ Video Editor       │    │
                    │  └──────────────────────┘    │
                    │                               │
                    │  [Menu Open/Closed]           │
                    │                               │
                    │  If Menu Open:                │
                    │  ┌─────────────┐             │
                    │  │ Projects:   │             │
                    │  │ • Project 1 │◄───Click────┼──┐
                    │  │ • Project 2 │             │  │
                    │  │             │             │  │
                    │  │ + New       │◄───Click────┼──┼──┐
                    │  └─────────────┘             │  │  │
                    └───────────────────────────────┘  │  │
                                                       │  │
                     ┌─────────────────────────────────┘  │
                     │                                    │
                     ▼                                    ▼
           ┌─────────────────┐              ┌────────────────────┐
           │  EDITOR SCREEN  │              │ CREATE PROJECT     │
           │                 │              │     SCREEN         │
           │ ┌─────────────┐ │              │                    │
           │ │   PREVIEW   │ │              │ Name: [______]     │
           │ │    AREA     │ │  [⛶ Full]   │ Desc: [______]     │
           │ │             │ │              │ Quality: [1080]    │
           │ └─────────────┘ │              │ FPS: [60]          │
           │                 │              │                    │
           │ [▶] [⏹] [Full] │              │ [Create] [Cancel]  │
           │                 │              └──────────┬─────────┘
           │ [+ Camera Clip]─┼──┐                     │
           │                 │  │                     │
           │ Timeline:       │  │         Create Success
           │ ┌───┐ ┌───┐   │  │                     │
           │ │C1 │ │C2 │   │  │                     │
           │ └───┘ └───┘   │  │                     ▼
           │                 │  │         Opens Editor Screen
           │ [← Dashboard]   │  │         (Same as left)
           └─────────────────┘  │
                                │
                                ▼
                 ┌──────────────────────────┐
                 │ CAMERA RECORDING SCREEN  │
                 │                          │
                 │  Ready to Record         │
                 │                          │
                 │  [⏺ Start Recording]    │
                 │                          │
                 │  [← Back]                │
                 └────────┬─────────────────┘
                          │
                          │ Click Start
                          ▼
                 ┌──────────────────────────┐
                 │   RECORDING MODE         │
                 │   (Screen Closes)        │
                 │                          │
                 │   Player moves freely    │
                 │   Camera frames saved    │
                 │                          │
                 │   Press ESC to stop      │
                 └────────┬─────────────────┘
                          │
                          │ Recording Done
                          ▼
                 ┌──────────────────────────┐
                 │  Recording Complete!     │
                 │                          │
                 │  Frames: 450             │
                 │  Duration: 15.0s         │
                 │                          │
                 │ [▶ Preview] [✓ Save]     │
                 │ [✗ Discard]              │
                 └────┬──────────┬──────────┘
                      │          │
              Preview │          │ Save
                      │          │
                      ▼          ▼
         ┌─────────────────┐  Returns to
         │ FULLSCREEN      │  Editor Screen
         │ PLAYBACK        │  with new clip
         │                 │  in timeline
         │ [Exit Preview]  │
         └─────────────────┘
```

## Dashboard Screen Layout

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ ┌──┐ Video Editor Dashboard           [Close] ┃
┃ │☰ │                                           ┃
┃ └──┘                                           ┃
┃                                                ┃
┃  ┌─────────────────┐                          ┃
┃  │ Menu (if open)  │                          ┃
┃  │                 │   Main Content Area:     ┃
┃  │ Projects:       │                          ┃
┃  │ ┌─────────────┐ │   No project selected    ┃
┃  │ │ My Video    │ │   Click menu to create   ┃
┃  │ └─────────────┘ │                          ┃
┃  │ ┌─────────────┐ │                          ┃
┃  │ │ Tutorial    │ │                          ┃
┃  │ └─────────────┘ │                          ┃
┃  │                 │                          ┃
┃  │ ┌─────────────┐ │                          ┃
┃  │ │ + New       │ │                          ┃
┃  │ └─────────────┘ │                          ┃
┃  └─────────────────┘                          ┃
┃                                                ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```

## Create Project Screen Layout

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃         Create New Project                     ┃
┃                                                ┃
┃                                                ┃
┃    Name:        [My Project_____________]      ┃
┃                                                ┃
┃    Description: [Project description____]      ┃
┃                                                ┃
┃    Quality (p): [1080___________________]      ┃
┃                                                ┃
┃    FPS:         [60_____________________]      ┃
┃                                                ┃
┃                                                ┃
┃           [Create]      [Cancel]               ┃
┃                                                ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```

## Editor Screen Layout (Normal Mode)

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ Project: My Video    1080p @ 60 FPS   [⛶ Full] ┃
┃ ┌──────────────────────────────────────────┐  ┃
┃ │                                          │  ┃
┃ │           PREVIEW AREA                   │  ┃
┃ │     (Camera POV will play here)          │  ┃
┃ │                                          │  ┃
┃ └──────────────────────────────────────────┘  ┃
┃                                                ┃
┃     [▶ Play]  [⏹ Stop]    [+ Camera Clip]     ┃
┃ ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ ┃
┃                                                ┃
┃  Timeline:                                     ┃
┃  ┌─────────┐ ┌─────────┐ ┌─────────┐         ┃
┃  │ Clip 1  │ │ Clip 2  │ │ Clip 3  │         ┃
┃  │ CAMERA  │ │ CAMERA  │ │ CAMERA  │         ┃
┃  └─────────┘ └─────────┘ └─────────┘         ┃
┃                                                ┃
┃ [← Dashboard]                                  ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```

## Editor Screen Layout (Fullscreen Preview)

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                                [Exit Fullscreen] ┃
┃                                                  ┃
┃                                                  ┃
┃                                                  ┃
┃                                                  ┃
┃              FULLSCREEN PREVIEW                  ┃
┃           (Camera playback here)                 ┃
┃                                                  ┃
┃                                                  ┃
┃                                                  ┃
┃                                                  ┃
┃                  [▶ Play]                        ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```

## Camera Recording Screen States

### State 1: Ready to Record
```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃           Camera Recording                      ┃
┃                                                 ┃
┃                                                 ┃
┃      Ready to record camera movement            ┃
┃   Click 'Start Recording' and move around       ┃
┃                                                 ┃
┃          [⏺ Start Recording]                    ┃
┃                                                 ┃
┃                                                 ┃
┃ [← Back]                                        ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```

### State 2: Recording (Screen closes, player moves freely)

### State 3: Recording Complete
```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃           Camera Recording                      ┃
┃                                                 ┃
┃      ┌─────────────────────────────┐           ┃
┃      │  Recording Complete! ✓      │           ┃
┃      │                              │           ┃
┃      │  Frames: 450                 │           ┃
┃      │  Duration: 15.0s             │           ┃
┃      └─────────────────────────────┘           ┃
┃                                                 ┃
┃     [▶ Preview]  [✓ Save]  [✗ Discard]         ┃
┃                                                 ┃
┃ [← Back]                                        ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```

## Color Scheme (As Implemented)

- **Background**: Dark (#000000, #1A1A1A, #2A2A2A)
- **Menu**: Darker gray (#222222)
- **Buttons**: Medium gray (#333333)
- **Hover**: Blue (#4444FF) or Green (#44FF44)
- **Borders**: Light gray (#444444, #666666)
- **Text**: White (#FFFFFF), Light gray (#AAAAAA, #CCCCCC)
- **Recording**: Red tint (#FF0000 with alpha)
- **Selection**: Yellow border (#FFFF00)

## User Interaction Flow

### Opening Dashboard
```
Player in Game
    │
    ├─► Press T
    │
    └─► Dashboard opens
         Menu closed by default
```

### Creating First Project
```
Dashboard (no projects)
    │
    ├─► Click ☰ menu
    │
    ├─► Click "Create Project"
    │
    ├─► Fill form
    │
    ├─► Click "Create"
    │
    └─► Editor opens with empty timeline
```

### Adding Camera Clip
```
Editor Screen
    │
    ├─► Click "+ Camera Clip"
    │
    ├─► Recording screen opens
    │
    ├─► Click "⏺ Start Recording"
    │
    ├─► Screen closes, player moves
    │
    ├─► Press ESC
    │
    ├─► Recording screen shows "Complete!"
    │
    ├─► (Optional) Click "▶ Preview"
    │
    ├─► Click "✓ Save"
    │
    └─► Returns to Editor
         Clip appears in timeline
```

### Playing Clips
```
Editor Screen
    │
    ├─► Click clip in timeline (selects it)
    │
    ├─► Click "▶ Play"
    │
    ├─► Preview shows camera playback
    │
    ├─► (Optional) Click "⛶ Full"
    │
    └─► Fullscreen preview mode
         Click "Exit Fullscreen" to return
```

## Keyboard Shortcuts

- **T** - Open Dashboard (in-game)
- **ESC** - Close current screen / Stop recording
- **Mouse Click** - Select clips, click buttons, open menu

## Visual Indicators

✅ Green - Save/Success actions
❌ Red - Delete/Discard actions  
⏺ Red - Recording indicator
▶ White - Play action
⏹ White - Stop action
☰ White - Menu toggle
⛶ White - Fullscreen toggle

---

This visual guide helps understand the complete UI flow without running the mod!
