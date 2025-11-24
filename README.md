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
Centralized block color mapping via new ColorMapper utility (removes color logic from GuiController)

### Implemented but Not Working Properly

(Will update as development continues)

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
- **Design Pattern**: Uses callback interface pattern to avoid circular dependencies between InputHandler and GuiController
- **Benefits**: Separates input handling from display logic, follows Single Responsibility Principle, improves testability

**ColorMapper.java** (`src/main/java/com/comp2042/view/ColorMapper.java`)
- **Purpose**: Central utility to map block integers (0-7) to their JavaFX `Paint` colors
- **Responsibilities**:
  - Provides `getColorForValue(int value)` for brick rendering and board background
  - Offers `getTransparentColor()` helper for empty cells
- **Benefits**: Removes color-switch logic from `GuiController`, keeps theme/color updates in one place, simplifies future refactors and testing

### Modified Java Classes

**SimpleBoard.java** (`src/main/java/com/comp2042/model/SimpleBoard.java`)
- Fixed initial block spawn position in `createNewBrick()` method
- Changed initial y-position from `10` to `2` (line 88)
- Blocks now spawn at the top of the visible game container instead of appearing partway down

**GuiController.java** (`src/main/java/com/comp2042/controller/GuiController.java`)
- **Refactoring**: Extracted keyboard input handling logic and color mapping logic into InputHandler class and ColorMapper class
- **Changes Made**:
  - Removed keyboard event handling code from `initialize()` method (previously lines 68-93)
  - Now delegates keyboard input to `InputHandler` instance
  - Implements `InputHandler.BrickDisplayUpdater` interface to receive display update callbacks
  - Simplified `initialize()` method to only set up input handler delegation
  - Removed inline color switch and now uses `ColorMapper.getColorForValue(...)` for all rectangles
- **Rationale**: 
  - Follows Single Responsibility Principle - GuiController now focuses on display/UI logic
  - Input handling is separated into its own class for better organization and testability
  - Reduces complexity of GuiController class and delegates color concerns to ColorMapper
- **Impact**: 
  - Cleaner code structure with better separation of concerns
  - Input handling logic can now be tested independently
  - Easier to modify input behavior or color themes without touching display code

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
