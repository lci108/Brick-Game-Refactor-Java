
package brickGame;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;


public class GameView {
    // GameView Variables
    private Pane root;
    private Label scoreLabel;
    private Label heartLabel;
    private Label levelLabel;
    private Label pauseLabel;
    private Stage primaryStage;
    private Button loadButton;
    private Button newGameButton;
    private Rectangle rect; // Representation of the paddle
    private Circle ball; // Representation of the ball
    private int sceneWidth ;
    private int sceneHeight;
    private Rectangle paddle;
    public GameView{
        paddle = new Rectangle();

    }
    public int getSceneWidth() {
        return sceneWidth;
    }

    public int getSceneHeight() {
        return sceneHeight;
    }

    public void animatePaddleMovement(double newX) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(200), paddle);
        transition.setToX(newX - paddle.getTranslateX()); // Calculate the new translation
        transition.play();
    }

}

