package brickGame;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.util.concurrent.CopyOnWriteArrayList;

import static brickGame.Controller.*;

public class View {
    private Pane root;
    private Label scoreLabel, heartLabel, levelLabel, pauseLabel , penaltyLabel;
    private Button load, newGame;
    private Scene scene;
    private int score, heart, level;
    private final ExhaustTail exhaustTail;
    private ImageView heartImageView;
    private ScaleTransition pulseAnimation;

    /**
     * Constructs a View object for the brick game.
     *
     * @param loadFromSave Flag indicating whether to load the game from a saved state.
     * @param ball         The game ball.
     * @param rect         The game paddle.
     * @param level        The current game level.
     * @param exhaustTail  The exhaust tail for visual effects.
     * @param blocks       The list of game blocks.
     */


    public View(boolean loadFromSave, Ball ball, Break rect, int level , ExhaustTail exhaustTail, CopyOnWriteArrayList<Block> blocks) {
        this.exhaustTail = exhaustTail;
        initUI(loadFromSave , ball , rect , blocks);
        updateLevel(level);
        initializePulseAnimation();

    }
    /**
     * Initializes the pulse animation for the heart image.
     */
    private void initializePulseAnimation() {
        pulseAnimation = new ScaleTransition(Duration.millis(500), heartImageView);
        pulseAnimation.setFromX(1.0);
        pulseAnimation.setFromY(1.0);
        pulseAnimation.setToX(1.2);
        pulseAnimation.setToY(1.2);
        pulseAnimation.setAutoReverse(true);
        pulseAnimation.setCycleCount(ScaleTransition.INDEFINITE);
    }
    /**
     * Initializes the UI elements, such as buttons, labels, and the game scene.
     *
     * @param loadFromSave Flag indicating whether to load the game from a saved state.
     * @param ball         The game ball.
     * @param rect         The game paddle.
     * @param blocks       The list of game blocks.
     */
    public void initUI(boolean loadFromSave, Ball ball, Break rect, CopyOnWriteArrayList<Block> blocks) {
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


        // Add elements to root pane
        root = new Pane();
        if (!loadFromSave) {
            root.getChildren().addAll((Node) rect, ball, scoreLabel, levelLabel, pauseLabel ,newGame, load ,penaltyLabel);

        } else {
            root.getChildren().addAll(rect, ball, scoreLabel, levelLabel , pauseLabel, penaltyLabel );

        }
        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }
        for (ImageView particle : exhaustTail.getParticles()) {
            root.getChildren().add(particle);
        }
        Image heart3Image = new Image("heart3.png");

        // Set initial heart image
        heartImageView = new ImageView(heart3Image);
        heartImageView.setFitWidth(80);
        heartImageView.setFitHeight(30);
        heartImageView.setTranslateX(sceneWidth - 95);
        heartImageView.setTranslateY(sceneHeigt - 690);



        root.getChildren().add(heartImageView);
        scene = new Scene(root, sceneWidth , sceneHeigt );
        scene.getStylesheets().add("style.css");
    }
    /**
     * Updates the exhaust tail visual effects.
     */
    public void updateExhaustTail() {
        exhaustTail.update(); // Update exhaust tail
        for (ImageView particle : exhaustTail.getParticles()) {
            if (!root.getChildren().contains(particle)) {
                root.getChildren().add(particle); // Add new particles to the root
            }
        }
    }
    /**
     * Adds a new block to the root pane.
     *
     * @param spook The block to add.
     */
    public void addBlockToRoot(Block spook) {
        Platform.runLater(() -> {
            root.getChildren().add(spook.rect);
        });
    }



    /**
     * Gets the game scene.
     *
     * @return The game scene.
     */
    public Scene getScene() {
        return scene;
    }
    /**
     * Gets the "Load Game" button.
     *
     * @return The "Load Game" button.
     */
    public Button getLoadButton() {
        return load;
    }
    /**
     * Gets the "Start New Game" button.
     *
     * @return The "Start New Game" button.
     */

    public Button getNewGameButton() {
        return newGame;
    }
    /**
     * Sets the visibility of the penalty label.
     *
     * @param visible True to make the penalty label visible, false otherwise.
     */
    public void setPenaltyLabelVisibility(boolean visible) {
        penaltyLabel.setVisible(visible);
    }
    /**
     * Sets the visibility of the pause label.
     *
     * @param visible True to make the pause label visible, false otherwise.
     */
    public void setPauseLabelLabelVisibility(boolean visible) {
        pauseLabel.setVisible(visible);
    }
    /**
     * Sets the visibility of the "Load Game" button.
     *
     * @param visible True to make the "Load Game" button visible, false otherwise.
     */
    public void setLoadButtonVisibility(boolean visible) {
        load.setVisible(visible);
    }
    /**
     * Sets the visibility of the "Start New Game" button.
     *
     * @param visible True to make the "Start New Game" button visible, false otherwise.
     */
    public void setNewGameButtonVisibility(boolean visible) {
        newGame.setVisible(visible);
    }
    /**
     * Adds a node to the root pane.
     *
     * @param node The node to add.
     */
    public void addToRoot(Node node) {
        Platform.runLater(() -> root.getChildren().add(node));
    }
    /**
     * Removes a style class from the root pane.
     *
     * @param styleClass The style class to remove.
     */
    public void removeStyleClassFromRoot(String styleClass) {
        Platform.runLater(() -> root.getStyleClass().remove(styleClass));
    }
    /**
     * Adds a style class to the root pane.
     *
     * @param styleClass The style class to add.
     */
    public void addStyleClassToRoot(String styleClass) {
        Platform.runLater(() -> root.getStyleClass().add(styleClass));
    }
    /**
     * Updates the player score display.
     *
     * @param score The updated score.
     */

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }
    /**
     * Updates the player heart display.
     *
     * @param heart The updated number of hearts.
     */

    public void updateHeart(int heart) {
        switch (heart) {
            case 3:
                heartImageView.setImage(new Image("heart3.png"));
                break;
            case 2:
                heartImageView.setImage(new Image("heart2.png"));
                break;
            case 1:
                heartImageView.setImage(new Image("heart1.png"));
                pulseAnimation.play();

                break;
            default:
                // Handle other cases or provide a default image
                break;
        }

    }
    /**
     * Updates the game level display.
     *
     * @param level The updated game level.
     */
    public void updateLevel(int level) {
        levelLabel.setText("Level: " + level);
    }
    /**
     * Updates the penalty time display.
     *
     * @param i The penalty time in seconds.
     */
    public void updatePenaltyTime(int i) {
        penaltyLabel.setText("Penalty: " + i + " seconds");
    }
    /**
     * Removes all elements from the root pane.
     */
    public void removeAllElementsFromRoot() {
        Platform.runLater(() -> {
            root.getChildren().clear(); // Remove all children nodes
            root.getStyleClass().clear(); // Clear all style classes
        });
    }




}
