package View;

import Controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
/**
 * The Score class is responsible for displaying scores, messages, and game-over/win screens in the game.
 */
public class Score {
    /**
     * Displays a score at the specified position on the game view.
     *
     * @param x     The X-coordinate for displaying the score.
     * @param y     The Y-coordinate for displaying the score.
     * @param score The score value to be displayed.
     * @param view  The View object representing the game view.
     */
    public void show(final double x, final double y, int score, final View view) {
        String sign = score >= 0 ? "+" : "";
        final Label label = new Label(sign + score);
        label.setTranslateX(x);
        label.setTranslateY(y);

        view.addToRoot(label);

        Timeline timeline = new Timeline();
        for (int i = 0; i <= 20; i++) {
            int finalI = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(15 * i), e -> {
                label.setScaleX(finalI);
                label.setScaleY(finalI);
                label.setOpacity((20 - finalI) / 20.0);
            }));
        }
        timeline.play();
    }
    /**
     * Displays a message at a specific position on the game view.
     *
     * @param message The message to be displayed.
     * @param view    The View object representing the game view.
     */
    public void showMessage(String message, final View view) {
        final Label label = new Label(message);
        label.setTranslateX(220);
        label.setTranslateY(340);

        view.addToRoot(label);

        Timeline timeline = new Timeline();
        for (int i = 0; i <= 20; i++) {
            int index = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(15 * i), e -> {
                label.setScaleX(Math.abs(index - 10));
                label.setScaleY(Math.abs(index - 10));
                label.setOpacity((20 - index) / 20.0);
            }));
        }
        timeline.play();
    }

    /**
     * Displays the game-over screen with an option to restart the game.
     *
     * @param controller The Controller object managing the game logic.
     * @param view       The View object representing the game view.
     */

    public void showGameOver(final Controller controller , final View view) {
        Platform.runLater(() -> {


            Button restart = new Button("Restart");
            restart.setTranslateX(200);
            restart.setTranslateY(500);
            restart.getStyleClass().add("button-style");
            restart.setOnAction(event -> controller.restartGame());
            view.removeAllElementsFromRoot();
            view.addStyleClassToRoot("gameOverRoot");
            view.addToRoot(restart);

        });
    }
    /**
     * Displays the win screen.
     *
     * @param view The View object representing the game view.
     */

    public void showWin(final View view) {
        Platform.runLater(() -> {
            view.removeAllElementsFromRoot();
            view.addStyleClassToRoot("winRoot");
        });
    }
}