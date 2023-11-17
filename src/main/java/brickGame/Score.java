package brickGame;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import sun.plugin2.message.Message;

public class Score {
    public void show(final double x, final double y, int score, final Main main) {
        String sign;
        if (score >= 0) {
            sign = "+";
        } else {
            sign = "";
        }
        final Label label = new Label(sign + score);
        label.setTranslateX(x);
        label.setTranslateY(y);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                main.root.getChildren().add(label);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 21; i++) {
                    try {
                        final int finalI = i;
                        Platform.runLater(() -> {
                            label.setScaleX(finalI);
                            label.setScaleY(finalI);
                            label.setOpacity((20 - finalI) / 20.0);
                        });
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public void showMessage(String message, final Main main) {
        final Label label = new Label(message);
        label.setTranslateX(220);
        label.setTranslateY(340);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                main.root.getChildren().add(label);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 21; i++) {
                    final int index = i;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            label.setScaleX(Math.abs(index - 10));
                            label.setScaleY(Math.abs(index - 10));
                            label.setOpacity((20 - index) / 20.0);
                        }
                    });

                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    public void showGameOver(final Main main) {
        Platform.runLater(() -> {
            // Display the final score
            Label scoreLabel = new Label("Your Score: " + main.score);
            scoreLabel.setTranslateX(220);
            scoreLabel.setTranslateY(200);
            scoreLabel.setScaleX(3);
            scoreLabel.setScaleY(3);

            // Game Over label
            Label gameOverLabel = new Label("Game Over :(");
            gameOverLabel.setTranslateX(220);
            gameOverLabel.setTranslateY(250);
            gameOverLabel.setScaleX(2);
            gameOverLabel.setScaleY(2);

            // Restart button
            Button restart = new Button("Restart");
            restart.setTranslateX(240);
            restart.setTranslateY(300);
            restart.setOnAction(event -> main.restartGame());

            main.root.getChildren().addAll(scoreLabel, gameOverLabel, restart);
        });
    }


    public void showWin(final Main main) {
        Platform.runLater(() -> {
            // Display the final score
            Label scoreLabel = new Label("Your Score: " + main.score);
            scoreLabel.setTranslateX(220);
            scoreLabel.setTranslateY(200);
            scoreLabel.setScaleX(3);
            scoreLabel.setScaleY(3);

            // Win label
            Label winLabel = new Label("You Win :)");
            winLabel.setTranslateX(220);
            winLabel.setTranslateY(250);
            winLabel.setScaleX(2);
            winLabel.setScaleY(2);

            main.root.getChildren().addAll(scoreLabel, winLabel);
        });
    }
}

