# COMP2042 - DMS Assignment (Tetris Game)

## GitHub

Forked from kooitt/CW2025, https://github.com/kooitt/CW2025 <br>
My Repository: https://github.com/playuter/DMS_Assignment-.git

## Compilation Instructions

this project uses **maven** and **JavaFX**

### requirements

Java 21 <br>
Maven wrapper (included in the repo)<br>
JavaFx (done by maven)

## how to build

run this in the terminal inside the project directory:<br>
`./mvnw.cmd clean compile`

## how to run

`./mvnw.cmd javafx:run`

## progress

### Implemented and Working Properly

Base Tetris project forked and cloned locally<br>
got familiar with .md files<br>
changed poml.xml file to use java version 21 instead of 23<br>
Project compiles successfully with Java 21<br>
Maven + JavaFX environment correctly set up<br>
Game window launches<br>
Bricks spawn, fall, and render<br>
Controllers load without errors<br>
Game board is now centered and stable when resizing or entering fullscreen (via updated FXML layout) <br>
Background image restored after layout update <br>
Fixed Tetris blocks spawning position - blocks now fall from the top of the container instead of appearing partway down <br>
Refactored classes into logical packages (controller, model, view, events, data, gameLogic) <br>
Extracted input handling logic from GuiController into separate InputHandler class<br>
Centralized block color mapping via new ColorMapper utility (removes color logic from GuiController)<br>
Extracted animation/timeline management from GuiController into separate AnimationController class<br>
Implemented pause game functionality using 'P' key and new PausePanel<br>



### Implemented but Not Working Properly

**Live Wallpaper Resizing**:
- The live video background (`liveWallpaper.mp4`) plays correctly but does not automatically resize when the game window is resized.
- **Issue**: `MediaView` does not automatically scale to fit its parent container like CSS background images do.
- **Potential Fix**: Bind `fitWidth` and `fitHeight` of the `MediaView` to the scene or root container properties in `GuiController.initialize()`.

### Features Not Implemented

(Will list non-implemented assignment features as work progresses)

### New Java Classes

**InputHandler.java** (`src/main/java/com/comp2042/controller/InputHandler.java`)
- **Purpose**: Handles all keyboard input processing for the Tetris game
- **Responsibilities**: 
  - Processes key press events (LEFT, RIGHT, UP, DOWN, N keys)
  - Checks game state (paused/game over) before processing input
  - Delegates game logic events to GameController
  - Updates display via callback interface (BrickDisplayUpdater)
  - Handles pause game request ('P' key)
- **Design Pattern**: Uses callback interface pattern to avoid circular dependencies between InputHandler and GuiController
- **Benefits**: Separates input handling from display logic, follows Single Responsibility Principle, improves testability

**ColorMapper.java** (`src/main/java/com/comp2042/view/ColorMapper.java`)
- **Purpose**: Central utility to map block integers (0-7) to their JavaFX `Paint` colors
- **Responsibilities**:
  - Provides `getColorForValue(int value)` for brick rendering and board background
  - Offers `getTransparentColor()` helper for empty cells
- **Benefits**: Removes color-switch logic from `GuiController`, keeps theme/color updates in one place, simplifies future refactors and testing

**AnimationController.java** (`src/main/java/com/comp2042/controller/AnimationController.java`)
- **Purpose**: Manages the automatic falling animation timeline for Tetris pieces
- **Responsibilities**:
  - Encapsulates JavaFX Timeline creation and management
  - Controls start/stop of automatic piece falling animation
  - Configurable fall speed (default 400ms, can be changed for difficulty levels)
  - Executes callback on each animation tick
- **Design Pattern**: Uses callback pattern (Runnable) to execute game logic on each tick
- **Benefits**: 
  - Separates animation control from display logic
  - Makes fall speed configurable (useful for difficulty levels)
  - Follows Single Responsibility Principle
  - Animation logic can be tested independently

**PausePanel.java** (`src/main/java/com/comp2042/view/PausePanel.java`)
- **Purpose**: Displays a "PAUSED" message when the game is paused
- **Responsibilities**: 
  - Creates a UI panel with styled "PAUSED" text
  - Extends `BorderPane` for consistent layout
- **Benefits**: 
  - Dedicated view component for pause state
  - Reuses existing styles for visual consistency

### Modified Java Classes

**SimpleBoard.java** (`src/main/java/com/comp2042/model/SimpleBoard.java`)
- Fixed initial block spawn position in `createNewBrick()` method
- Changed initial y-position from `10` to `2` (line 88)
- Blocks now spawn at the top of the visible game container instead of appearing partway down

**GuiController.java** (`src/main/java/com/comp2042/controller/GuiController.java`)
- **Refactoring**: Extracted multiple responsibilities into separate classes (InputHandler, ColorMapper, AnimationController)
- **Changes Made**:
  - Removed keyboard event handling code from `initialize()` method (previously lines 68-93)
  - Now delegates keyboard input to `InputHandler` instance
  - Implements `InputHandler.BrickDisplayUpdater` interface to receive display update callbacks
  - Removed Timeline management code - now uses `AnimationController` for automatic falling
  - Removed inline color switch and now uses `ColorMapper.getColorForValue(...)` for all rectangles
  - Simplified `initialize()` method to only set up delegation to specialized classes
  - Replaced direct Timeline calls (`timeLine.play()`, `timeLine.stop()`) with `AnimationController` methods
  - Added `PausePanel` and implemented pause logic (toggle state, show/hide panel, stop/start animation)
  - Added `pauseGame` method to handle pause requests
  - Integrated `MediaView` to play `liveWallpaper.mp4` as the game background
- **Rationale**: 
  - Follows Single Responsibility Principle - GuiController now focuses solely on display/UI coordination
  - Input handling, animation control, and color mapping are separated into their own classes
  - Reduces complexity of GuiController class significantly
  - Each extracted class can be tested and modified independently
- **Impact**: 
  - Much cleaner code structure with clear separation of concerns
  - GuiController is now more maintainable and easier to understand
  - Individual components (input, animation, colors) can be modified without affecting others
  - Better foundation for adding features like difficulty levels (via AnimationController speed control)

**Package Reorganization**
- **Before**: All classes were in a single `com.comp2042` package
- **After**: Classes organized into logical packages:
  - `controller/` - GameController, GuiController, InputHandler
  - `model/` - Board, SimpleBoard, Score
  - `view/` - ViewData, GameOverPanel, NotificationPanel
  - `events/` - MoveEvent, EventType, EventSource, InputEventListener
  - `data/` - DownData, ClearRow, NextShapeInfo
  - `gameLogic/` - MatrixOperations, BrickRotator
  - `logic/bricks/` - All brick type classes (already existed)
- **Rationale**: 
  - Improves code organization and maintainability
  - Makes it easier to locate related classes
  - Follows standard Java package organization practices
- **Impact**: 
  - Better code structure and navigation
  - Clearer separation of concerns
  - Easier for other developers to understand the codebase

### Unexpected Problems

JavaFX + Maven setup required fixing compiler target compatibility

Java path / JAVA_HOME configuration issues resolved

Background completely broke when changing to live background
when resizing the background does not resize along with the game window