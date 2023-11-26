package brickGame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Score {
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

    public void showGameOver(final Main main , final View view) {
        Platform.runLater(() -> {
            Label label = new Label("Game Over :(");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);

            Button restart = new Button("Restart");
            restart.setTranslateX(220);
            restart.setTranslateY(300);
            restart.setOnAction(event -> main.restartGame());
            view.addToRoot(label);
            view.addToRoot(restart);

        });
    }

    public void showWin(final View view) {
        Platform.runLater(() -> {
            Label label = new Label("You Win :)");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);
            view.addToRoot(label);

        });
    }
}