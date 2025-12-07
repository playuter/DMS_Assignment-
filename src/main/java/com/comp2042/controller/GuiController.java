package com.comp2042.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

import com.comp2042.data.DownData;
import com.comp2042.events.EventSource;
import com.comp2042.events.EventType;
import com.comp2042.events.InputEventListener;
import com.comp2042.events.MoveEvent;
import com.comp2042.view.ColorMapper;
import com.comp2042.view.GameOverPanel;
import com.comp2042.view.NotificationPanel;
import com.comp2042.view.PausePanel;
import com.comp2042.view.ViewData;
import com.comp2042.logic.leaderboard.LeaderboardManager;
import com.comp2042.view.SettingsDialog;
import com.comp2042.logic.leaderboard.PlayerScore;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import java.io.IOException;

import com.comp2042.constants.GameConstants;

import com.comp2042.view.components.PreviewPanel;
import com.comp2042.view.components.ScoreView;

/**
 * Controller for the main game GUI (JavaFX).
 * Manages the display of the game board, bricks, score, next pieces, and overlays.
 * It delegates input handling to InputHandler and game logic events to GameController.
 */
public class GuiController implements Initializable, InputHandler.BrickDisplayUpdater {

    private static final int BRICK_SIZE = GameConstants.BRICK_SIZE;
    private String playerName = "Guest";

    @FXML
    private StackPane rootStackPane;

    @FXML
    private javafx.scene.layout.Pane gameBoardContainer;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GridPane shadowPanel;

    @FXML
    private GridPane nextBrickPanel;
    
    private GameOverPanel gameOverPanel;

    @FXML
    private MediaView backgroundMediaView;

    @FXML
    private Text scoreValue;

    @FXML
    private Text highScoreValue;

    private PausePanel pausePanel;

    private Rectangle[][] displayMatrix;

    private InputEventListener eventListener;

    private Rectangle[][] rectangles;
    private Rectangle[][] shadowRectangles;

    private AnimationController animationController;
    private long initialFallSpeed = GameConstants.INITIAL_FALL_SPEED;
    private boolean isInsaneMode = false;
    
    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();
    
    private InputHandler inputHandler;
    
    private ScoreView scoreView;
    private PreviewPanel previewPanel;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * 
     * @param location The location used to resolve relative paths for the root object.
     * @param resources The resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        
        // Initialize Components
        scoreView = new ScoreView(scoreValue, highScoreValue, null); // VBox container passed as null for now if unused
        previewPanel = new PreviewPanel(nextBrickPanel);
        
        // Initialize input handler (will be set up after eventListener is set)
        // The input handler will be created in setEventListener() method
        
        gamePanel.setOnKeyPressed(keyEvent -> {
            if (inputHandler != null) {
                inputHandler.handleKeyPress(keyEvent);
            }
        });
        
        gameOverPanel = new GameOverPanel();
        gameOverPanel.setVisible(false);
        
        // Wire up Game Over Panel buttons
        gameOverPanel.getRestartButton().setOnAction(e -> newGame());
        gameOverPanel.getMainMenuButton().setOnAction(e -> returnToMainMenu());
        gameOverPanel.getLeaderboardButton().setOnAction(e -> showLeaderboard());
        gameOverPanel.getQuitButton().setOnAction(e -> quitGame());

        pausePanel = new PausePanel();
        pausePanel.setVisible(false);
        
        // Wire up Pause Panel buttons
        pausePanel.getResumeButton().setOnAction(e -> pauseGame());
        pausePanel.getRestartButton().setOnAction(e -> newGame());
        pausePanel.getSettingsButton().setOnAction(e -> SettingsDialog.show());
        pausePanel.getMainMenuButton().setOnAction(e -> returnToMainMenu());
        
        if (rootStackPane != null) {
            rootStackPane.getChildren().addAll(gameOverPanel, pausePanel);
        } else {
            System.err.println("rootStackPane is null! Fallback to groupNotification.");
            groupNotification.getChildren().addAll(gameOverPanel, pausePanel);
        }
        
        // Initialize Live Wallpaper
        try {
            // Bind MediaView to StackPane size to ensure full coverage
            if (backgroundMediaView != null && rootStackPane != null) {
                backgroundMediaView.fitWidthProperty().bind(rootStackPane.widthProperty());
                backgroundMediaView.fitHeightProperty().bind(rootStackPane.heightProperty());
            }

            URL mediaUrl = getClass().getClassLoader().getResource("liveWallpaper.mp4");
            if (mediaUrl != null) {
                Media media = new Media(mediaUrl.toExternalForm());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setAutoPlay(true);
                backgroundMediaView.setMediaPlayer(mediaPlayer);
            } else {
                System.err.println("Could not find liveWallpaper.mp4");
            }
        } catch (Exception e) {
            System.err.println("Error loading live wallpaper: " + e.getMessage());
            e.printStackTrace();
        }

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
        
        // High Score initialization moved to ScoreView
    }

    /**
     * Initializes the game view with the board matrix and current brick.
     * Sets up the grid of rectangles for the board and the falling brick.
     * 
     * @param boardMatrix The current state of the game board.
     * @param brick The current falling brick view data.
     */
    public void initGameView(int[][] boardMatrix, ViewData brick) {
        // Adjust game board container size based on matrix width
        if (gameBoardContainer != null) {
            // Calculate required width: Matrix width + padding for margins (approx 60px: 40px left + 20px right)
            // Score panel has been moved to the side panel, so less padding is needed here.
            double requiredWidth = boardMatrix[0].length * BRICK_SIZE + 60;
            gameBoardContainer.setMinWidth(requiredWidth);
            gameBoardContainer.setPrefWidth(requiredWidth);
            gameBoardContainer.setMaxWidth(requiredWidth);
        }

        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
            for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(ColorMapper.getTransparentColor());
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        shadowRectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(ColorMapper.getColorForValue(brick.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
                
                // Init shadow rectangles
                Rectangle shadowRec = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                shadowRec.setFill(ColorMapper.getColorForValue(brick.getBrickData()[i][j]));
                shadowRec.setOpacity(0.3); // Make it semi-transparent
                shadowRectangles[i][j] = shadowRec;
                shadowPanel.add(shadowRec, j, i);
            }
        }
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap()
                + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap()
                + brick.getyPosition() * BRICK_SIZE);
                
        // Set initial shadow position
        shadowPanel.setLayoutX(brickPanel.getLayoutX());
        shadowPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getShadowY() * brickPanel.getHgap()
                + brick.getShadowY() * BRICK_SIZE);

        if (previewPanel != null) {
            previewPanel.refreshNextBricks(brick);
        }
        
        // startTime managed in AnimationController now
        
        animationController = new AnimationController(
            () -> {
                moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD));
            },
            initialFallSpeed
        );
        if (isInsaneMode) {
            animationController.setInsaneMode(true);
        }
        animationController.start();
    }


    /**
     * Refreshes the brick display with new position and shape data.
     * Part of the BrickDisplayUpdater interface.
     * 
     * @param brick The updated view data for the falling brick.
     */
    @Override
    public void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap()
                    + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap()
                    + brick.getyPosition() * BRICK_SIZE);
            
            // Update shadow position and shape
            shadowPanel.setLayoutX(brickPanel.getLayoutX());
            shadowPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getShadowY() * brickPanel.getHgap()
                    + brick.getShadowY() * BRICK_SIZE);
                    
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
                    setRectangleData(brick.getBrickData()[i][j], shadowRectangles[i][j]);
                }
            }
            if (previewPanel != null) {
                previewPanel.refreshNextBricks(brick);
            }
        }
    }
    
    // refreshNextBricks delegated to PreviewPanel
    
    /**
     * Handles downward movement of the brick.
     * Part of the BrickDisplayUpdater interface.
     * 
     * @param event The move event triggering the down movement.
     */
    @Override
    public void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                 int scoreBonus = GameConstants.SCORE_PER_LINE * downData.getClearRow().getLinesRemoved() * downData.getClearRow().getLinesRemoved();
                NotificationPanel notificationPanel = new NotificationPanel(
                        "+" + scoreBonus);
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }
    
    /**
     * Starts a new game.
     * Part of the BrickDisplayUpdater interface.
     */
    @Override
    public void newGame() {
        animationController.stop();
        gameOverPanel.setVisible(false);
        pausePanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        animationController.resetStartTime(); // Reset timer
        if (isInsaneMode) {
            animationController.setFallSpeed(initialFallSpeed); // Reset speed
            animationController.setInsaneMode(true);
        } else {
            animationController.setInsaneMode(false);
        }
        animationController.start();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }

    /**
     * Refreshes the game board background to reflect the current state of locked bricks.
     * 
     * @param board The matrix representing the game board.
     */
    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // Reset opacity in case it was faded out
                displayMatrix[i][j].setOpacity(1.0);
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    /**
     * Animates the clearing of rows.
     * 
     * @param rows The list of row indices to clear.
     * @param onFinished A runnable to execute after the animation completes.
     */
    public void animateClearRows(java.util.List<Integer> rows, Runnable onFinished) {
        // Play clear sound for single line, or doubleLineClear for multiple lines
        if (rows.size() > 1) {
            SoundManager.play("clear"); // "clear" key is mapped to doubleLineClear.mp3
        } 
        
        javafx.animation.ParallelTransition transition = new javafx.animation.ParallelTransition();
        
        for (Integer row : rows) {
            if (row < 2) continue; // Skip hidden rows
            
            for (int col = 0; col < displayMatrix[row].length; col++) {
                Rectangle rect = displayMatrix[row][col];
                javafx.animation.FadeTransition ft = new javafx.animation.FadeTransition(
                    javafx.util.Duration.millis(300), rect
                );
                ft.setFromValue(1.0);
                ft.setToValue(0.0);
                transition.getChildren().add(ft);
            }
        }
        
        transition.setOnFinished(e -> {
            if (onFinished != null) {
                onFinished.run();
            }
        });
        transition.play();
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(ColorMapper.getColorForValue(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }


    /**
     * Sets the event listener for handling game logic events.
     * Also initializes the InputHandler.
     * 
     * @param eventListener The event listener to set.
     */
    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
        // Create input handler after event listener is set
        this.inputHandler = new InputHandler(
            eventListener, 
            isPause, 
            isGameOver, 
            this  // GuiController implements BrickDisplayUpdater
        );
    }

    /**
     * Binds the score property to the ScoreView.
     * 
     * @param integerProperty The score property to bind.
     */
    public void bindScore(IntegerProperty integerProperty) {
        if (scoreView != null) {
            scoreView.bindScore(integerProperty);
        }
    }

    // isMilestone delegated to ScoreView

    /**
     * Triggers the score animation.
     */
    public void animateScore() {
        if (scoreView != null) {
            scoreView.animateScore();
        }
    }

    // animateScore(Text target) delegated to ScoreView

    /**
     * Sets the player's name.
     * 
     * @param playerName The player's name.
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Sets the initial fall speed for the game.
     * 
     * @param speed The initial fall speed in milliseconds.
     */
    public void setInitialFallSpeed(long speed) {
        this.initialFallSpeed = speed;
    }

    /**
     * Sets whether the game is in Insane Mode.
     * 
     * @param insaneMode True if Insane Mode is enabled, false otherwise.
     */
    public void setInsaneMode(boolean insaneMode) {
        this.isInsaneMode = insaneMode;
    }

    /**
     * Handles the Game Over state.
     * Stops the game, plays sound, shows the Game Over panel, and saves the score.
     */
    public void gameOver() {
        animationController.stop();
        SoundManager.play("gameover");
        gameOverPanel.setVisible(true);
        gameOverPanel.toFront(); // Ensure Game Over panel is on top
        isGameOver.setValue(Boolean.TRUE);
        
        // Save score to leaderboard
        try {
            int finalScore = 0;
            if (scoreView != null) {
                finalScore = scoreView.getCurrentScore();
            } else {
                finalScore = Integer.parseInt(scoreValue.getText());
            }
            LeaderboardManager.getInstance().addScore(playerName, finalScore);
            System.out.println("Score saved for " + playerName + ": " + finalScore);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing score for leaderboard: " + e.getMessage());
        }
    }

    /**
     * Public method for FXML button binding (if needed).
     * Delegates to the newGame() method from BrickDisplayUpdater interface.
     * 
     * @param actionEvent The action event triggering the new game.
     */
    public void newGame(ActionEvent actionEvent) {
        newGame();
    }

    private String previousMusicTrack;

    private long pauseStartTime;

    /**
     * Toggles the game pause state.
     * Stops/resumes the animation and music, and shows/hides the pause panel.
     * Part of the BrickDisplayUpdater interface.
     */
    @Override
    public void pauseGame() {
        if (isGameOver.getValue() == Boolean.FALSE) {
            if (isPause.getValue() == Boolean.FALSE) {
                // Pause Game
                previousMusicTrack = SoundManager.getCurrentTrack();
                SoundManager.playBackgroundMusic("pause");
                
                pauseStartTime = System.currentTimeMillis(); // Record pause start time
                
                animationController.stop();
                pausePanel.setVisible(true);
                // Ensure pausePanel is on top
                pausePanel.toFront();
                isPause.setValue(Boolean.TRUE);
            } else {
                // Resume Game
                if (previousMusicTrack != null) {
                    SoundManager.playBackgroundMusic(previousMusicTrack);
                } else {
                    SoundManager.stopBackgroundMusic(); // Stop pause music if no previous track
                }
                
                long pauseDuration = System.currentTimeMillis() - pauseStartTime;
                animationController.adjustStartTime(pauseDuration); // Shift start time
                
                pausePanel.setVisible(false);
                animationController.start();
                isPause.setValue(Boolean.FALSE);
            }
        }
        gamePanel.requestFocus();
    }

    /**
     * Public method for FXML button binding to pause/resume the game.
     * 
     * @param actionEvent The action event.
     */
    public void pauseGame(ActionEvent actionEvent) {
        pauseGame();
    }

    private void returnToMainMenu() {
        try {
            animationController.stop();
            SoundManager.stopBackgroundMusic(); // Stop current game music
            SoundManager.playBackgroundMusic("menu"); // Restart menu music
            
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MainMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) gamePanel.getScene().getWindow();
            stage.setScene(new Scene(root, 300, 510));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void showLeaderboard() {
        List<PlayerScore> scores = LeaderboardManager.getInstance().getScores();
        StringBuilder sb = new StringBuilder();
        if (scores.isEmpty()) {
            sb.append("No scores yet!");
        } else {
            for (int i = 0; i < scores.size(); i++) {
                PlayerScore ps = scores.get(i);
                sb.append(String.format("%d. %s: %d\n", i + 1, ps.getName(), ps.getScore()));
            }
        }
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Leaderboard");
        alert.setHeaderText("Top 50 Scores");
        alert.setContentText(sb.toString());
        alert.showAndWait();
    }
    
    private void quitGame() {
        System.exit(0);
    }
}
