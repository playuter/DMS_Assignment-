# COMP2042 - DMS Assignment (Tetris Game)

## Table of Contents
- [GitHub](#github)
- [Repository Structure](#repository-structure)
- [Compilation Instructions](#compilation-instructions)
  - [requirements](#requirements)
- [how to build](#how-to-build)
- [how to run](#how-to-run)
- [progress](#progress)
  - [Implemented and Working Properly](#implemented-and-working-properly)
  - [Code Refactoring](#code-refactoring)
  - [Implemented but Not Working Properly](#implemented-but-not-working-properly)
  - [Features Not Implemented](#features-not-implemented)
  - [New Java Classes](#new-java-classes)
  - [Modified Java Classes](#modified-java-classes)
  - [Unexpected Problems](#unexpected-problems)
  - [References and Credits](#references-and-credits)

## GitHub

Forked from kooitt/CW2025, https://github.com/kooitt/CW2025 <br>
My Repository: https://github.com/playuter/DMS_Assignment-.git

## Repository Structure


- **Controller Package** (`com.comp2042.controller`): Contains all game control logic, input handling, sound management, and menu navigation.
- **Model Package** (`com.comp2042.model`): Houses game state, business logic, score tracking, and core board mechanics.
- **View Package** (`com.comp2042.view`): Manages all UI components, panels (Pause, Game Over), and visual rendering logic.
- **Logic Package** (`com.comp2042.logic`): Contains core game mechanics, brick implementations, leaderboard logic, and SRS wall kick data.
- **GameLogic Package** (`com.comp2042.gameLogic`): Encapsulates specific game rules like rotation algorithms, matrix operations, and lock delays.
- **Events Package** (`com.comp2042.events`): Defines the event system for handling user inputs and game state changes.
- **Data Package** (`com.comp2042.data`): Contains immutable data transfer objects (DTOs) for passing information between layers.
- **Constants Package** (`com.comp2042.constants`): Centralized configuration and game constants (dimensions, speeds, scoring).

## Compilation Instructions

this project uses **maven** and **JavaFX**

### requirements

Java 21 or later <br>
Maven wrapper (included in the repo)<br>
JavaFx (done by maven)

## how to run

### Clone the repository <br>
https://github.com/playuter/DMS_Assignment

### Command Line

#### How to Build
run this in the terminal inside the project directory:<br>
`./mvnw.cmd clean compile`

#### How to Run
`./mvnw.cmd javafx:run`

### IntelliJ IDEA

#### How to Build
- Open the maven window using the letter m shape on the right sidebar
- Choose Execute Maven Goal
- Search for mvn clean and double click it 
- Search for mvn compile and double click it

#### OR
- Open the maven window using the letter m shape on the right sidebar
- Under m demo3 choose lifecycle 
- then clean 
- then compile

#### How to Run
- Open the maven window using the letter m shape on the right sidebar
- Choose Execute Maven Goal
- Search for mvn javafx:run and double click it 

#### OR 
- Open the maven window using the letter m shape on the right sidebar
- Under m demo3 choose plugins
- then javafx 
- then javafx:run 



## progress

### Implemented and Working Properly

This project implements a complete Tetris experience with several modern enhancements and a robust architecture.

**1. Main Menu and Navigation**
The application launches into a polished main menu that serves as the central hub. From here, players can:
- **Create a Profile**: Enter a username which is used to track high scores.
- **Select Difficulty**: Choose from Normal, Extra, or Insane difficulty levels.
- **Access Settings**: Adjust global volume for music and sound effects.
- **View Leaderboards**: Check the top 50 scores achieved locally.

**2. Game Modes and Progression**
The game features three distinct difficulty levels to cater to different player skills:
- **Normal Mode**: A standard experience with a 10x20 board and a moderate speed (600ms per drop).
- **Extra Mode**: A slightly faster challenge with a 400ms drop speed.
- **Insane Mode**: A unique challenge featuring a **double-width board (20x20)**. The game speed is dynamic, starting at 200ms and linearly increasing to 100ms over two minutes, testing survival skills on a massive grid.

**3. Advanced Gameplay Mechanics**
To ensure a fluid and competitive feel, several advanced systems were implemented:
- **Super Rotation System (SRS)**: The game strictly follows standard Tetris rotation rules. This includes "wall kicks," allowing pieces to rotate into tight spaces by testing alternative positions when blocked.
- **Lock Delay**: A "Move Reset" lock delay gives players a 0.5-second grace period after a piece lands to slide or rotate it. This allows for precise placement and tactical maneuvers like T-spins.
- **Ghost Piece**: A shadow at the bottom of the board shows exactly where the current piece will land, improving placement accuracy.
- **Next Piece Preview**: A look-ahead system displays the next three upcoming blocks, allowing for better strategic planning.

**4. Visual and Audio Experience**
- **Dynamic Layout**: The game window and live video background automatically resize to fit the screen. In Insane Mode, the UI rearranges itself to accommodate the wider board without overlapping.
- **Immersive Overlays**: Pause and Game Over screens are full-screen overlays that dim the background, providing a clean and modern interface.
- **Audio System**: A `SoundManager` handles background music (looping and switching) and sound effects (movement, rotation, clears). A global volume slider is available in the settings.

**5. Data and Architecture**
- **Persistence**: High scores are serialized and saved to a local file, preserving the leaderboard between sessions.
- **MVC Architecture**: The codebase is strictly organized into Model, View, and Controller packages, promoting separation of concerns.
- **Event-Driven Input**: A dedicated input handler manages keyboard events and dispatches abstract game commands, keeping the controller logic clean.

### Code Refactoring
- **Package Organization**: Restructured the monolithic codebase into a clean MVC architecture with distinct packages (`controller`, `model`, `view`, `logic`, `data`, `events`) to improve maintainability and navigation.
- **GameConstants**: Centralized magic numbers (dimensions, speeds, IDs) into a dedicated `GameConstants` class.
- **GuiController Decomposition**: Extracted responsibilities to reduce class size and complexity:
  - `InputHandler`: Offloaded all keyboard event processing and state checking.
  - `ColorMapper`: Centralized block color logic, removing switch statements from the controller.
  - `ScoreView`: Encapsulated score display, high score tracking, and milestone animations.
  - `PreviewPanel`: Managed the "Next Bricks" preview display.
- **AnimationController**: Enhanced to encapsulate "Insane Mode" speed ramping logic, removing game rule logic from the view controller.
- **LockDelayManager**: Encapsulated the lock delay timer logic into a dedicated class in `com.comp2042.gameLogic`, removing complexity from `GameController`.
- **Decoupled Scoring Logic**: Moved score calculation out of `ClearRow` and `MatrixOperations` (Model/Data) into `GameController` and `ScoreView`, ensuring that data objects remain lightweight and logic stays in the controller.

### Implemented but Not Working Properly

(No known issues at the moment)

### Features Not Implemented

The following features were planned but could not be implemented within the current scope. They are marked for future development:

- **Multiplayer Mode**: A competitive mode allowing two players to play against each other locally or online.
- **Online Leaderboard**: Integration with a backend service to store and display global high scores.
- **Custom Keybindings**: A configuration menu allowing players to customize their controls.
- **Bonus Mechanic (Revival Mode)**: An Insane Mode feature where collecting a special heart block grants a second chance upon game over, allowing players to clear lines to survive.

### New Java Classes

**LeaderboardManager.java** (`src/main/java/com/comp2042/logic/leaderboard/LeaderboardManager.java`)
- **Purpose**: Manages the persistent leaderboard state
- **Responsibilities**:
  - Loads/saves scores to disk
  - Maintains top 50 high scores
  - Provides methods to add and retrieve scores
- **Benefits**: Encapsulates file I/O and score logic

**PlayerScore.java** (`src/main/java/com/comp2042/logic/leaderboard/PlayerScore.java`)
- **Purpose**: Data class representing a single score entry
- **Responsibilities**: Stores name, score, and sorting logic (Comparable)

**MainMenuController.java** (`src/main/java/com/comp2042/controller/MainMenuController.java`)
- **Purpose**: Controls the main menu logic
- **Responsibilities**: 
  - Loads the game scene when "Start Game" is clicked
  - Exits the application when "Exit" is clicked
  - Handles player name input before starting game
  - Displays the leaderboard via a dialog
  - Allows difficulty level selection (Normal/Extra/Insane) via a dialog
  - Displays game instructions and controls via a dialog
  - Animates cleared rows before removing them
  - Animates score display on hitting milestones
  - **Insane Mode Logic**: Dynamically adjusts the game board dimensions (20 columns) and window size when "Insane" difficulty is selected. Sets a flag for dynamic speed adjustment.
- **Benefits**: 
  - Provides a clear entry point for the user
  - Separates menu logic from game logic

**InputHandler.java** (`src/main/java/com/comp2042/controller/InputHandler.java`)
- **Purpose**: Handles all keyboard input processing for the Tetris game
- **Responsibilities**: 
  - Processes key press events (LEFT, RIGHT, UP, DOWN, N keys)
  - Checks game state (paused/game over) before processing input
  - Delegates game logic events to GameController
  - Updates display via callback interface (BrickDisplayUpdater)
  - Handles pause game request ('P' key)
  - Handles hard drop request ('SPACE' key)
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
  - Provides navigation buttons (Resume, Restart, Main Menu)
  - Extends `BorderPane` for consistent layout
  - **Updated**: Now styles the entire panel with a semi-transparent background to create a full-screen overlay effect.
- **Benefits**: 
  - Dedicated view component for pause state
  - Reuses existing styles for visual consistency

**SoundManager.java** (`src/main/java/com/comp2042/controller/SoundManager.java`)
- **Purpose**: Centralized audio management for the game.
- **Responsibilities**:
  - Preloads all sound effects and music tracks.
  - Handles one-shot sound playback (e.g., "bop", "clear").
  - Manages background music tracks, ensuring only one plays at a time.
  - Controls volume levels for specific sounds (e.g., boosted "bop" sound).
- **Benefits**:
  - Decouples sound logic from game controllers.
  - Prevents sound overlap issues.
  - Simplifies adding or modifying audio assets.

### Modified Java Classes

**GameController.java** (`src/main/java/com/comp2042/controller/GameController.java`)
- **Changes**: 
  - Removed logic that incremented score by 1 for every manual down movement
  - Updated constructor to accept variable board dimensions (rows/cols)
- **Impact**: 
  - Prevents players from farming score by repeatedly tapping down key; score now reflects only cleared lines
  - Enables different board sizes for different difficulty levels (e.g., wider board for Insane mode)

**SimpleBoard.java** (`src/main/java/com/comp2042/model/SimpleBoard.java`)
- **Changes**: 
  - Fixed initial block spawn position
  - Implemented `calculateShadowY()` to determine ghost piece position
  - Implemented `hardDrop()` to instantly move the brick to the shadow position
  - Updated `getViewData()` to include shadow position
  - **Implemented Super Rotation System (SRS)**: Updated `rotateLeftBrick` to use wall kick data, allowing pieces to rotate in tight spaces
- **Impact**: Blocks spawn correctly, hard drop functionality added, shadow blocks rendered, and rotation is much smoother and forgiving

**ViewData.java** (`src/main/java/com/comp2042/view/ViewData.java`)
- **Changes**: 
  - Added `shadowY` field and constructor parameter
  - Added `nextBricksData` list to hold matrices for upcoming pieces
- **Impact**: Carries shadow position and next pieces queue from Model to View

**BrickGenerator.java / RandomBrickGenerator.java**
- **Changes**: 
  - Updated interface to include `getNextBricks()`
  - Implemented queue-based brick generation (maintains a buffer of upcoming bricks)
- **Impact**: Enables the game to look ahead and display multiple upcoming pieces

**Brick Classes (TBrick, JBrick, LBrick, SBrick, ZBrick, IBrick, OBrick)**
- **Changes**:
  - Redefined all shape matrices to match the Standard Tetris Guideline (SRS) orientations.
  - Orientation 0 is now "Up" (or "Flat" for I), and rotations proceed Clockwise (Up->Right->Down->Left).
- **Impact**: Ensures that the standard Wall Kick data is applied correctly, fixing issues where pieces would jump erratically during rotation.

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
  - Implemented `bindScore` method to update the score display in the UI
  - Added `shadowPanel` and rendering logic for shadow blocks
  - Added `nextBrickPanel` and logic to render the next 3 upcoming bricks
  - Added logic to save player scores to the leaderboard on game over
  - Added real-time high score tracking and display
  - Added support for variable game speeds (difficulty levels)
  - **UI Update**: Modified `pausePanel` and `gameOverPanel` integration to add them to the root `StackPane`, allowing them to overlay the entire game window.
  - **Functionality Update**: Implemented actions for new Game Over buttons (Restart, Main Menu, Leaderboard, Save & Quit).
- **Rationale**: 
  - Follows Single Responsibility Principle - GuiController now focuses solely on display/UI coordination
  - Input handling, animation control, and color mapping are separated into their own classes
  - Reduces complexity of GuiController class significantly
  - Each extracted class can be tested and modified independently
  - Better foundation for adding features like difficulty levels (via AnimationController speed control)
- **Impact**: 
  - Much cleaner code structure with clear separation of concerns
  - GuiController is now more maintainable and easier to understand
  - Individual components (input, animation, colors) can be modified without affecting others
  - Better foundation for adding features like difficulty levels (via AnimationController speed control)

**Main.java** (`src/main/java/com/comp2042/Main.java`)
- **Changes**: Updated `start()` method to load `MainMenu.fxml` instead of `gameLayout.fxml` on startup
- **Impact**: Application now launches into the main menu

**GameOverPanel.java** (`src/main/java/com/comp2042/view/GameOverPanel.java`)
- **Changes**: 
  - Updated styling to include a semi-transparent background for full-screen overlay effect.
  - Added buttons for Restart, Main Menu, Leaderboard, and Save & Quit.

**Package Reorganization**
- **Before**: All classes were in a single `com.comp2042` package
- **After**: Classes organized into logical packages:
  - `controller/` - GameController, GuiController, InputHandler, SoundManager
  - `model/` - Board, SimpleBoard, Score
  - `view/` - ViewData, GameOverPanel, NotificationPanel, PausePanel, ColorMapper
  - `events/` - MoveEvent, EventType, EventSource, InputEventListener
  - `data/` - DownData, ClearRow, NextShapeInfo
  - `gameLogic/` - MatrixOperations, BrickRotator, WallKickData
  - `logic/bricks/` - All brick type classes (already existed)
  - `logic/leaderboard/` - LeaderboardManager, PlayerScore
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
playable box in insane level overlaps the next bricks preview
when fixing the center alignment of the spawning bricks in the insane level the other 2 levels alignment broke

### References and Credits
- **Original Repository**: Forked from [kooitt/CW2025](https://github.com/kooitt/CW2025)
- **Super Rotation System (SRS)**: Implementation details and wall kick data adapted from [Tetris Wiki - Super Rotation System](https://tetris.wiki/Super_Rotation_System). The detailed offset tables and rotation logic explanation were really useful.
- **Lock Delay**: Implemented based on the mechanics described in [Tetris Wiki - Lock Delay](https://tetris.wiki/Lock_delay), specifically following the "Move Reset" behavior where the lock timer resets on successful movement or rotation.
