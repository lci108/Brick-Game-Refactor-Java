package brickGame;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


import java.util.Random;

import static brickGame.Block.*;


/**
 * The Main class is the entry point for the Brick Game application.
 * It extends the JavaFX Application class and initializes the game by creating
 * an instance of the Controller class and starting the game.
 *
 */
public class Main extends Application {

    /**
     * The main method launches the JavaFX application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start method of the Application class.
     * Initializes the game by creating a Controller instance and starting the game.
     *
     * @param stage The primary stage for the application.
     * @throws Exception If an exception occurs during the initialization of the game.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Create an instance of the Controller class to manage the game
        Controller controller = new Controller();

        // Start the game by invoking the start method of the Controller
        controller.start(stage);
    }
}



 