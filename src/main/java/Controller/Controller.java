package Controller;
import Model.Model;
import View.View;
import View.Score;
import brickGame.*;
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
import brickGame.Block;



/**
 * The Controller class responsible for handling user input and managing the game.
 * Extends Application to create a JavaFX application and implements EventHandler for key events
 * and GameEngine.OnAction for game actions.
 */
public class Controller extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {
    private ExhaustTail exhaustTail;
    private Soundeffects Soundeffects;
    /**
     * The width of the break.
     */
    public static int breakWidth = 200;

    /**
     * The height of the break.
     */
    public static int breakHeight = 30;

    /**
     * The width of the scene.
     */
    public static int sceneWidth = 500;

    /**
     * The height of the scene.
     */
    public static int sceneHeigt = 700;

    /**
     * Represents the left direction.
     */
    public static final int LEFT = 1;

    /**
     * Represents the right direction.
     */
    public static final int RIGHT = 2;


    private Ball ball;

    private boolean isGoldStauts      = false;
    private boolean isExistHeartBlock = false;

    private Break rect;


    private long time     = 0;
    private long goldTime = 0;

    private GameEngine engine;
    /**
     * The file path for saving and loading game data.
     */
    public static String savePath = "D:/save/save.mdds";

    /**
     * The directory path for saving and loading game data.
     */
    public static String savePathDir = "D:/save/";



    private final CopyOnWriteArrayList<Bonus> chocos = new CopyOnWriteArrayList<>();

    private final CopyOnWriteArrayList<Bonus> mysteries = new CopyOnWriteArrayList<>();

    private boolean loadFromSave = false;

    private boolean isNextLevelCalled = false;


    Stage  primaryStage;

    private View view;
    Model model = new Model();

    /**
     * The entry point for the JavaFX application. Initializes the game by creating instances of various game elements,
     * setting up the game world, and starting the game engine.
     *
     * @param primaryStage The primary stage for the application.
     * @throws Exception If an exception occurs during the initialization of the game.
     */



    @Override
    //here is entry point
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Soundeffects = new Soundeffects();
        isNextLevelCalled = false;  // Reset the flag

        if (!loadFromSave) {
            model.incLevel();
            System.out.println("level now is " + model.getLevel());
            if (model.getLevel() > 1) {
                new Score().showMessage("Level Up :)", view);
            }
            if (model.getLevel() == 9) {
                Soundeffects.playWin();
                new Score().showWin(view);
                return;
            }

            initBall();
            initBreak();
            initBoard();

        }
        view = new View(loadFromSave , ball , rect, model.getLevel() , exhaustTail , model.getBlocks());

        //go to handle method
        view.getScene().setOnKeyPressed(this);

        // Setup scene and show
        primaryStage.setTitle("Game");
        primaryStage.setScene(view.getScene());
        primaryStage.show();

        if (!loadFromSave) {
            //level 2-17
            if (model.getLevel() > 1 && model.getLevel() < 18) {
                view.setLoadButtonVisibility(false);
                view.setNewGameButtonVisibility(false);
                // Start the game engine and initialize event handlers

                engine = new GameEngine();
                engine.setOnAction(this);
                engine.setFps(120);
                engine.start();
            }
            //level 1 main menu
            view.getLoadButton().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    loadGame();
                    view.setLoadButtonVisibility(false);
                    view.setNewGameButtonVisibility(false);
                }
            });

            view.getNewGameButton().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    engine = new GameEngine();
                    engine.setOnAction(Controller.this);
                    engine.setFps(120);
                    engine.start();
                    view.setLoadButtonVisibility(false);
                    view.setNewGameButtonVisibility(false);
                }
            });


        } else {
            engine = new GameEngine();
            engine.setOnAction(this);
            engine.setFps(120);
            engine.start();
            loadFromSave = false;
        }

    }

    private void initBoard() {
        model.setUpBoard();
    }



    private boolean isGamePaused = false;

    /**
     * Handles user input events, such as key presses. Adjusts game elements and triggers specific actions based on the input.
     *
     * @param event The KeyEvent representing the user's input.
     */

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                Break.move(LEFT);
                break;
            case RIGHT:

                Break.move(RIGHT);
                break;
            case SPACE:
                if (!isGamePaused) {
                    engine.stop(); // Stop the game engine
                    isGamePaused = true; // Set the flag to indicate the game is paused
                    view.setPauseLabelLabelVisibility(true);
                } else {
                    engine.start(); // Restart the game engine
                    isGamePaused = false; // Set the flag to indicate the game is running
                    view.setPauseLabelLabelVisibility(false);
                }

                break;
            case S:
                saveGame();
                break;
        }
    }

    private boolean isPenaltyActive = false;
    /**
     * Updates the game state and the graphical representation of the game during the game loop.
     * Responsible for handling collisions, updating scores, and managing various game elements.
     */

    private void handlePenalty() {
        if (isPenaltyActive) {
            // If a penalty is already active, don't start a new one
            return;
        }

        isPenaltyActive = true;
        view.addStyleClassToRoot("freezeRoot");

        view.setPenaltyLabelVisibility(true);

        final int countdownTime = 4; // although is 4s but the display is delayed a little , so what the user see is 3s
        final int[] remainingTime = {countdownTime}; // Start from countdownTime

        Timeline penaltyTimeline = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(1), e -> {
            remainingTime[0]--; // Decrement remaining time
            view.updatePenaltyTime(remainingTime[0]);

            // Update label on JavaFX Application Thread
            if (remainingTime[0] <= 0) {
                penaltyTimeline.stop();
                view.setPenaltyLabelVisibility(false);
                view.removeStyleClassFromRoot("freezeRoot");
                isPenaltyActive = false; // Reset the penalty flag
                breakStopped = false; // Resume break movement after the penalty duration
            }
        });

        penaltyTimeline.getKeyFrames().add(frame);
        penaltyTimeline.setCycleCount(countdownTime);
        penaltyTimeline.play();
    }

    /**
     * Flag indicating whether the break is stopped.
     */
    public static boolean breakStopped = false;

    private void initBall() {
        ball = new Ball(Ball.ballRadius, (double) sceneWidth /2, 360);
        exhaustTail = new ExhaustTail(ball);
    }


    private void initBreak() {
        rect = new Break(170 , 640 , breakWidth , breakHeight);
    }

    /**
     * Saves the current game state to a file. This method runs in a separate thread to prevent blocking the main application.
     * It creates the necessary directories, writes game-related data (such as level, score, heart count, etc.) to the specified
     * file path, and serializes the relevant objects using ObjectOutputstream. Additionally, it displays a "Game Saved" message
     * on the UI.
     */

    private void saveGame() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new File(savePathDir).mkdirs();
                // Create a File object for the save file

                File file = new File(savePath);
                ObjectOutputStream outputStream = null;
                try {
                    // Write game-related data to the save file

                    outputStream = new ObjectOutputStream(new FileOutputStream(file));

                    outputStream.writeInt(model.getLevel());
                    outputStream.writeInt(model.getScore());
                    outputStream.writeInt(model.getHeart());
                    outputStream.writeInt(model.getDestroyedBlockCount());


                    outputStream.writeDouble(ball.getBallX());
                    outputStream.writeDouble(ball.getBallY());
                    outputStream.writeDouble(Break.getxBreak());
                    outputStream.writeDouble(Break.getyBreak());
                    outputStream.writeDouble(Break.getCenterBreakx());
                    outputStream.writeLong(time);
                    outputStream.writeLong(goldTime);
                    outputStream.writeDouble(5);


                    outputStream.writeBoolean(isExistHeartBlock);
                    outputStream.writeBoolean(isGoldStauts);
                    outputStream.writeBoolean(ball.isGoDownBall());
                    outputStream.writeBoolean(ball.isGoRightBall());
                    outputStream.writeBoolean(ball.isColideToRightBlock());
                    outputStream.writeBoolean(ball.isColideToBottomBlock());
                    outputStream.writeBoolean(ball.isColideToLeftBlock());
                    outputStream.writeBoolean(ball.isColideToTopBlock());

                    // Serialize the list of non-destroyed blocks
                    ArrayList<BlockSerializable> blockSerializables = new ArrayList<BlockSerializable>();
                    for (Block block : model.getBlocks()) {
                        if (block.isDestroyed) {
                            continue;
                        }
                        blockSerializables.add(new BlockSerializable(block.row, block.column, block.type , block.direction));
                    }

                    outputStream.writeObject(blockSerializables);

                    new Score().showMessage("Game Saved", view);


                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private void loadGame() {

        LoadSave loadSave = new LoadSave();
        loadSave.read();

        //Assigning Loaded Values to Local Variables:
        isExistHeartBlock = loadSave.isExistHeartBlock;
        isGoldStauts = loadSave.isGoldStauts;
        ball.setGoDownBall(loadSave.goDownBall);
        ball.setGoRightBall(loadSave.goRightBall);
        ball.setColideToRightBlock(loadSave.colideToRightBlock);
        ball.setColideToBottomBlock(loadSave.colideToBottomBlock);
        ball.setColideToLeftBlock(loadSave.colideToLeftBlock);
        ball.setColideToTopBlock(loadSave.colideToTopBlock);
        model.setLevel(loadSave.level);
        model.setScore(loadSave.score);
        model.setHeart(loadSave.heart);
        model.setDestroyedBlockCount(0);
        ball.setCenterX(loadSave.xBall);
        ball.setCenterY(loadSave.yBall);
        Break.setxBreak(loadSave.xBreak);
        Break.setyBreak(loadSave.yBreak);
        Break.setCenterBreakx(loadSave.centerBreakX);
        time = loadSave.time;
        goldTime = loadSave.goldTime;
        //Clearing Existing Blocks and chocos and mysteries:
        model.clearBlocks();
        chocos.clear();
        mysteries.clear();
        //Repopulating the blocks from the Loaded Data:
        model.repopulateBlocks(loadSave.blocks);
        try {
            loadFromSave = true;
            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    /**
     * Initiates the transition to the next game level. Resets game state variables, clears existing blocks,
     * and restarts the game on the JavaFX Application Thread.
     */
    private void nextLevel() {
        if (isNextLevelCalled) {
            return;
        }
        isNextLevelCalled = true;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Reset various game state variables for the next level

                    ball.resetColideFlags();
                    ball.setGoDownBall(true);
                    isGoldStauts = false;
                    isExistHeartBlock = false;
                    time = 0;
                    goldTime = 0;
                    // Stop the game engine

                    engine.stop();
                    // Clear existing blocks, chocos, and mysteries

                    model.clearBlocks();
                    chocos.clear();
                    mysteries.clear();
                    model.setDestroyedBlockCount(0);
                    // Restart the game for the next level

                    start(primaryStage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * Restarts the game, resetting all game-related variables to their initial values and initiating a new game session.
     * This method sets the initial level, heart count, score, and destroyed block count. Additionally, it resets ball
     * movement and collision flags, clears existing blocks, chocos, and mysteries, and starts a new game on the JavaFX
     * Application Thread.
     */

    public void restartGame() {

        try {
            // Reset game variables to their initial values
            model.setLevel(0);
            model.setHeart(3);
            model.setScore(0);
            model.setDestroyedBlockCount(0);
            ball.resetColideFlags();
            ball.setGoDownBall(true);

            isGoldStauts = false;
            isExistHeartBlock = false;
            time = 0;
            goldTime = 0;
            // Clear existing blocks, chocos, and mysteries

            model.clearBlocks();
            chocos.clear();
            mysteries.clear();
            // Start a new game on the JavaFX Application Thread

            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Handles updates during the game loop. Updates the user interface (UI) elements, checks for collisions
     * with the ball, and processes various in-game events based on the collisions.
     */
    @Override
    public void onUpdate() {

        // Update UI on the JavaFX Application Thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {


                view.updateScore(model.getScore());
                view.updateHeart(model.getHeart());
                rect.setX(Break.getxBreak());
                rect.setY(Break.getyBreak());
                ball.setCenterX(ball.getBallX());
                ball.setCenterY(ball.getBallY());
                view.updateExhaustTail();
                model.moveImpenetrableBlock();
                // Update positions of bonus items (chocos and mysteries)

                for (Bonus choco : chocos) {
                    choco.block.setY(choco.y);
                }
                for (Bonus mystery : mysteries) {
                    mystery.block.setY(mystery.y);
                }
            }
        });

        // Check for collisions with the ball
        if (ball.getBallY() >= Block.getPaddingTop() && ball.getBallY() <= (Block.getHeight() * (10)) + Block.getPaddingTop()) {
            for (final Block block : model.getBlocks()) {
                int hitCode = block.checkHitToBlock(ball.getBallX(), ball.getBallY());
                if (hitCode != Block.NO_HIT) {
                    // Handle collision with a block

                    if(block.type != BLOCK_IMPENETRABLE){
                        Soundeffects.playBlockHit();
                        model.incScore();
                        new Score().show(block.x, block.y, 1, view);
                        // Spawn a new spooky block if the hit block is of type BLOCK_SPOOKY
                        if(block.type == BLOCK_SPOOKY){
                            Block newBlock = new Block(new Random().nextInt(10), new Random().nextInt(5), Block.BLOCK_SPOOKED, 0);
                            Soundeffects.playSpooky();
                            model.addBlocks(newBlock);
                            view.addBlockToRoot(newBlock);
                        }
                        block.rect.setVisible(false);
                        block.isDestroyed = true;
                        model.incDestroyedBlockCount();

                    }else{
                        //just play blockhitSound when hit
                        Soundeffects.playBlockHit();
                    }

                    ball.resetColideFlags();
                    if (block.type == Block.BLOCK_CHOCO) {
                        final Bonus choco = new Bonus(block.row, block.column , block.type);
                        choco.timeCreated = time;
                        view.addToRoot(choco.block);
                        chocos.add(choco);
                    }
                    // Process bonus items based on the type of the hit block

                    if (block.type == Block.BLOCK_MYSTERY) {
                        final Bonus mystery = new Bonus(block.row, block.column , block.type);
                        mystery.timeCreated = time;
                        view.addToRoot(mystery.block);
                        mysteries.add(mystery);
                    }
                    if (block.type == Block.BLOCK_STAR) {
                        Soundeffects.playGoldBlockHit();
                        goldTime = time;
                        ball.setFill(new ImagePattern(new Image("goldball.png")));
                        System.out.println("gold ball");
                        view.addStyleClassToRoot("goldRoot");
                        isGoldStauts = true;
                    }
                    if (block.type == Block.BLOCK_HEART) {
                        model.incHeart();
                        Soundeffects.playHeartUp();
                    }

                    ball.onUpdateBall(hitCode);
                }
            }
        }
    }


    @Override
    public void onInit() {

    }
    /**
     * Handles physics-related updates during the game loop. Manages ball movement, collisions, and bonus items.
     */
    @Override
    public void onPhysicsUpdate() {
        //if all destroyed move to next lvl
        if(model.allBlocksDestroyed()){
            nextLevel();
        }
        ball.setPhysicsToBall();
        if (ball.hasHitSceneBottom()) {
            // Handle the scenario when the ball hits the bottom
            if (!isGoldStauts) {
                model.decHeart();
                Soundeffects.playHeartDown();
                // Display a heart-down message on the UI

                new Score().show((double) sceneWidth / 2, (double) sceneHeigt / 2, -1, view);
                // Check if the player has run out of hearts and end the game if true

                if (model.getHeart() == 0) {
                    Soundeffects.playGameOver();
                    // Display a game-over message on the UI

                    new Score().showGameOver(this ,view);
                    engine.stop();
                }
            }
            ball.resetHitBottomFlag(); // Reset the flag after handling
        }
        // Reset the gold ball effect after 5s
        if (time - goldTime > 5000) {
            ball.setFill(new ImagePattern(new Image("ball.png")));
            view.removeStyleClassFromRoot("goldRoot");
            isGoldStauts = false;
        }
        // Handle bonuses (chocos) falling and check for collision with the paddle
        for (Bonus choco : chocos) {
            if (choco.y > sceneHeigt || choco.taken) {
                continue;
            }
            //Check for collision with break
            if (choco.y >= Break.getyBreak() && choco.y <= Break.getyBreak() + breakHeight && choco.x >= Break.getxBreak() && choco.x <= Break.getxBreak() + breakWidth) {
                Soundeffects.playItemCatch();
                System.out.println("You Got it and +3 score for you");
                choco.taken = true;
                model.inc3Score();
                // Remove the bonus block from the view
                Platform.runLater(() -> {
                    choco.block.setVisible(false);
                    // Display a score increase message on the UI
                    new Score().show(choco.x, choco.y, 3, view);
                });
                new Score().show(choco.x, choco.y, 3, view);
            }
            //gravity
            choco.y += ((time - choco.timeCreated) / 1000.000) + 1.000;
        }
        for (Bonus mystery : mysteries) {
            if (mystery.y > sceneHeigt || mystery.taken) {
                continue;
            }
            //Check for collision with break
            if (mystery.y >= Break.getyBreak() && mystery.y <= Break.getyBreak() + breakHeight && mystery.x >= Break.getxBreak() && mystery.x <= Break.getxBreak() + breakWidth) {
                Soundeffects.playItemCatch();
                System.out.println("Mystery Gift!");
                mystery.taken = true;
                Platform.runLater(() -> {
                    mystery.block.setVisible(false);
                });
                if (Math.random() < 0.5) {
                    model.inc5Score();
                    new Score().show(mystery.x, mystery.y, 5, view);
                }else {
                    System.out.println("Oh No ! FREEZE for 3s");
                    Soundeffects.playFreezeTime();
                    breakStopped = true;
                    handlePenalty();
                }


            }
            //gravity
            mystery.y += ((time - mystery.timeCreated) / 1000.000) + 1.000;
        }


    }

    /**
     * Handles time-related events during the game loop.
     *
     * @param time The current time in milliseconds.
     */
    @Override
    public void onTime(long time) {
        this.time = time;
    }

}