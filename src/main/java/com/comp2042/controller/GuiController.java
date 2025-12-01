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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

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

public class GuiController implements Initializable, InputHandler.BrickDisplayUpdater {

    private static final int BRICK_SIZE = 20;
    private String playerName = "Guest";

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
    
    @FXML
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
    private long initialFallSpeed = 400;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();
    
    private InputHandler inputHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        
        // Initialize input handler (will be set up after eventListener is set)
        // The input handler will be created in setEventListener() method
        
        gamePanel.setOnKeyPressed(keyEvent -> {
            if (inputHandler != null) {
                inputHandler.handleKeyPress(keyEvent);
            }
        });
        
        gameOverPanel.setVisible(false);
        pausePanel = new PausePanel();
        pausePanel.setVisible(false);
        groupNotification.getChildren().add(pausePanel);
        
        // Initialize Live Wallpaper
        try {
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
        
        // Initialize High Score
        int currentHighScore = LeaderboardManager.getInstance().getHighestScore();
        if (highScoreValue != null) {
            highScoreValue.setText(String.valueOf(currentHighScore));
        }
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
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

        refreshNextBricks(brick);
        
        // Initialize animation controller for automatic falling
        animationController = new AnimationController(
            () -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD)),
            initialFallSpeed
        );
        animationController.start();
    }


    /**
     * Refreshes the brick display with new position and shape data.
     * Part of the BrickDisplayUpdater interface.
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
            refreshNextBricks(brick);
        }
    }
    
    private void refreshNextBricks(ViewData brick) {
        if (nextBrickPanel == null) return;
        nextBrickPanel.getChildren().clear();
        
        java.util.List<int[][]> nextBricks = brick.getNextBricksData();
        if (nextBricks == null) return;
        
        for (int k = 0; k < nextBricks.size(); k++) {
            int[][] nextBrickData = nextBricks.get(k);
            GridPane singleBrickContainer = new GridPane();
            singleBrickContainer.setHgap(1);
            singleBrickContainer.setVgap(1);
            
            for (int i = 0; i < nextBrickData.length; i++) {
                for (int j = 0; j < nextBrickData[i].length; j++) {
                    if (nextBrickData[i][j] != 0) {
                        Rectangle rectangle = new Rectangle(15, 15); // Smaller size for preview
                        rectangle.setFill(ColorMapper.getColorForValue(nextBrickData[i][j]));
                        rectangle.setArcWidth(5);
                        rectangle.setArcHeight(5);
                        singleBrickContainer.add(rectangle, j, i);
                    }
                }
            }
            nextBrickPanel.add(singleBrickContainer, 0, k);
        }
    }
    
    /**
     * Handles downward movement of the brick.
     * Part of the BrickDisplayUpdater interface.
     */
    @Override
    public void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel(
                        "+" + downData.getClearRow().getScoreBonus());
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
        animationController.start();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }

    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(ColorMapper.getColorForValue(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }


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

    public void bindScore(IntegerProperty integerProperty) {
        scoreValue.textProperty().bind(integerProperty.asString());
        
        // Add listener to update high score in real-time if beaten
        integerProperty.addListener((obs, oldVal, newVal) -> {
            int currentScore = newVal.intValue();
            int highScore = Integer.parseInt(highScoreValue.getText());
            if (currentScore > highScore) {
                highScoreValue.setText(String.valueOf(currentScore));
            }
        });
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setInitialFallSpeed(long speed) {
        this.initialFallSpeed = speed;
    }

    public void gameOver() {
        animationController.stop();
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);
        
        // Save score to leaderboard
        try {
            int finalScore = Integer.parseInt(scoreValue.getText());
            LeaderboardManager.getInstance().addScore(playerName, finalScore);
            System.out.println("Score saved for " + playerName + ": " + finalScore);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing score for leaderboard: " + e.getMessage());
        }
    }

    /**
     * Public method for FXML button binding (if needed).
     * Delegates to the newGame() method from BrickDisplayUpdater interface.
     */
    public void newGame(ActionEvent actionEvent) {
        newGame();
    }

    @Override
    public void pauseGame() {
        if (isGameOver.getValue() == Boolean.FALSE) {
            if (isPause.getValue() == Boolean.FALSE) {
                animationController.stop();
                pausePanel.setVisible(true);
                isPause.setValue(Boolean.TRUE);
            } else {
                pausePanel.setVisible(false);
                animationController.start();
                isPause.setValue(Boolean.FALSE);
            }
        }
        gamePanel.requestFocus();
    }

    public void pauseGame(ActionEvent actionEvent) {
        pauseGame();
    }
}
