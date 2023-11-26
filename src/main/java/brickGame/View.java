package brickGame;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;

import static brickGame.Main.*;

public class View {
    private Pane root;
    private Label scoreLabel, heartLabel, levelLabel, pauseLabel , penaltyLabel;
    private Button load, newGame;
    private Scene scene;
    private int score, heart, level;


    public View(boolean loadFromSave, Ball ball, Break rect,int level) {
        initUI(loadFromSave , ball , rect);
        updateLevel(level);
    }

    public void initUI(boolean loadFromSave, Ball ball, Break rect) {
        penaltyLabel = new Label("PENALTY TIME");
        penaltyLabel.setTranslateX(200); // Set X position
        penaltyLabel.setTranslateY(300);
        penaltyLabel.setVisible(false);
        // Initialize buttons
        load = new Button("Load Game");
        load.setTranslateX(220);
        load.setTranslateY(300);

        newGame = new Button("Start New Game");
        newGame.setTranslateX(220);
        newGame.setTranslateY(340);

        // Initialize labels
        pauseLabel = new Label("\t Game is Paused \n Press Space to Resume!");
        pauseLabel.setTranslateX(160);
        pauseLabel.setTranslateY(300);
        pauseLabel.setVisible(false);

        scoreLabel = new Label("Score: " + score);
        levelLabel = new Label("Level: " + level);
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + heart);
        heartLabel.setTranslateX(sceneWidth  - 70);

        // Add elements to root pane
        root = new Pane();
        if (loadFromSave == false) {
            root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel, pauseLabel ,newGame, load ,penaltyLabel);
        } else {
            root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel , pauseLabel, penaltyLabel );
        }
        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }
        scene = new Scene(root, sceneWidth , sceneHeigt );
        scene.getStylesheets().add("style.css");
    }
    

    public Scene getScene() {
        return scene;
    }
    public Button getLoadButton() {
        return load;
    }

    public Button getNewGameButton() {
        return newGame;
    }
    public void setPenaltyLabelVisibility(boolean visible) {
        penaltyLabel.setVisible(visible);
    }
    public void setPauseLabelLabelVisibility(boolean visible) {
        pauseLabel.setVisible(visible);
    }
    public void setLoadButtonVisibility(boolean visible) {
        load.setVisible(visible);
    }

    public void setNewGameButtonVisibility(boolean visible) {
        newGame.setVisible(visible);
    }
    public void addToRoot(Node node) {
        Platform.runLater(() -> root.getChildren().add(node));
    }
    public void removeFromRoot(Node node) {
        Platform.runLater(() -> root.getChildren().remove(node));
    }
    public void removeStyleClassFromRoot(String styleClass) {
        Platform.runLater(() -> root.getStyleClass().remove(styleClass));
    }

    public void addStyleClassToRoot(String styleClass) {
        Platform.runLater(() -> root.getStyleClass().add(styleClass));
    }






    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void updateHeart(int heart) {
        heartLabel.setText("Heart: " + heart);
    }

    public void updateLevel(int level) {
        levelLabel.setText("Level: " + level);
    }
    public void updatePenaltyTime(int i) {
        penaltyLabel.setText("Penalty: " + i + " seconds");
    }

    // Methods to control visibility of labels
    public void setScoreVisibility(boolean visible) {
        scoreLabel.setVisible(visible);
    }

    public void setHeartVisibility(boolean visible) {
        heartLabel.setVisible(visible);
    }

    public void setLevelVisibility(boolean visible) {
        levelLabel.setVisible(visible);
    }

    public void setPauseVisibility(boolean visible) {
        pauseLabel.setVisible(visible);
    }
    public void setOnKeyPressed(EventHandler<KeyEvent> handler) {
        scene.setOnKeyPressed(handler);
    }


}
