package brickGame;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.concurrent.CopyOnWriteArrayList;


import java.io.*;
import java.util.ArrayList;
import java.util.Random;

import static brickGame.Ball.ballRadius;

public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {



    private Soundeffects Soundeffects;
    public static int level = 0;

    public static double xBreak = 0.0f;
    public static double yBreak = 640.0f;

    public static int breakWidth     = 200;
    private int breakHeight    = 30;

    public static int sceneWidth = 500;
    public static int sceneHeigt = 700;

    public static final int LEFT  = 1;
    public static final int RIGHT = 2;

    private Ball ball;
    private double xBall;
    private double yBall;

    private boolean isGoldStauts      = false;
    private boolean isExistHeartBlock = false;

    private Rectangle rect;

    private int destroyedBlockCount = 0;

    private double v = 1.000;

    private int  heart    = 30000;
    private int  score    = 0;
    private long time     = 0;
    private long goldTime = 0;

    private GameEngine engine;
    public static String savePath    = "D:/save/save.mdds";
    public static String savePathDir = "D:/save/";

    private CopyOnWriteArrayList<Block> blocks = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Bonus> chocos = new CopyOnWriteArrayList<>();

    private CopyOnWriteArrayList<Bonus> mysteries = new CopyOnWriteArrayList<>();
    private Color[]          colors = new Color[]{
            Color.MAGENTA,
            Color.RED,
            Color.GOLD,
            Color.CORAL,
            Color.AQUA,
            Color.VIOLET,
            Color.GREENYELLOW,
            Color.ORANGE,
            Color.PINK,
            Color.SLATEGREY,
            Color.YELLOW,
            Color.TOMATO,
            Color.TAN,
    };
    public  Pane             root;
    private Label            scoreLabel;
    private Label            heartLabel;
    private Label            levelLabel;

    private boolean loadFromSave = false;

    private Label pauseLabel;
    private boolean isNextLevelCalled = false;









    Stage  primaryStage;
    Button load    = null;
    Button newGame = null;


    @Override
    //here is entry point
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Soundeffects = new Soundeffects();
        isNextLevelCalled = false;  // Reset the flag



        if (loadFromSave == false) {
            level++;
            if (level > 1) {
                new Score().showMessage("Level Up :)", this);
            }
            if (level == 18) {
                new Score().showWin(this);
                return;
            }

            initBall();
            initBreak();
            initBoard();

            load = new Button("Load Game");
            newGame = new Button("Start New Game");
            load.setTranslateX(220);
            load.setTranslateY(300);
            newGame.setTranslateX(220);
            newGame.setTranslateY(340);
        }
        pauseLabel = new Label("\t Game is Paused \n Press Space to Resume!");
        pauseLabel.setTranslateX(160); // Adjust the position as needed
        pauseLabel.setTranslateY(300);
        pauseLabel.setVisible(false); // Initially invisible

        root = new Pane();
        scoreLabel = new Label("Score: " + score);
        levelLabel = new Label("Level: " + level);
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + heart);
        heartLabel.setTranslateX(sceneWidth - 70);
        if (loadFromSave == false) {
            root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel, pauseLabel ,newGame, load );
        } else {
            root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel , pauseLabel );
        }
        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }

        Scene scene = new Scene(root, sceneWidth, sceneHeigt);
        scene.getStylesheets().add("style.css");
        //go to handle method
        scene.setOnKeyPressed(this);

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();


        if (loadFromSave == false) {
            //level 2-17
            if (level > 1 && level < 18) {
                load.setVisible(false);
                newGame.setVisible(false);
                engine = new GameEngine();
                engine.setOnAction(this);
                engine.setFps(120);
                engine.start();
            }
            //level 1 main menu
            load.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
//                    loadGame();
                    load.setVisible(false);
                    newGame.setVisible(false);
                }
            });

            newGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    engine = new GameEngine();
                    engine.setOnAction(Main.this);
                    engine.setFps(120);
                    engine.start();

                    load.setVisible(false);
                    newGame.setVisible(false);
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
    //made the game easier for testing with this CHANCE
    private static final int CHANCE_DIVISOR = 500;
    private static final int CHOCO_CHANCE = 50; // 0-49 for chocolate (10%)
    private static final int HEART_CHANCE = 150; // 50-149 for heart (20%)
    private static final int STAR_CHANCE = 175; // 150-174 for star (5%)

    private static final int MYSTERY_CHANCE =200 ; //175 - 199 for mystery (5%)

    //refactored initBoard and changed the percentage
    private void initBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < level + 1; j++) {
                int randomChance = new Random().nextInt(CHANCE_DIVISOR);

                if (SkipBlockChance(randomChance)) {
                    continue;
                }

                int type = determineBlockType(randomChance);
                blocks.add(new Block(j, i, colors[randomChance % colors.length], type));
            }
        }
    }

    private boolean SkipBlockChance(int randomChance) {

        return randomChance % 5 == 0;
    }

    private int determineBlockType(int randomChance) {
        if (randomChance  < CHOCO_CHANCE) {
            return Block.BLOCK_CHOCO;
        } else if (randomChance  < HEART_CHANCE && !isExistHeartBlock) {
            isExistHeartBlock = true;
            return Block.BLOCK_HEART;
        } else if (randomChance  < STAR_CHANCE) {
            return Block.BLOCK_STAR;
        } else if(randomChance < MYSTERY_CHANCE) {
            return Block.BLOCK_MYSTERY;
        }else{
            return Block.BLOCK_NORMAL;
        }
    }
//end of initBoard

    private boolean isGamePaused = false;
    public static void main(String[] args) {
        launch(args);
    }

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
                    pauseLabel.setVisible(true);
                } else {
                    engine.start(); // Restart the game engine
                    isGamePaused = false; // Set the flag to indicate the game is running
                    pauseLabel.setVisible(false);
                }

                break;
            case S:
//                saveGame();
                break;
        }
    }
    private Label penaltyLabel;

    private boolean isPenaltyActive = false;

    private void handlePenalty() {
        if (isPenaltyActive) {
            // If a penalty is already active, don't start a new one
            return;
        }

        isPenaltyActive = true;
        Platform.runLater(() -> {
            root.getStyleClass().add("freezeRoot");

        });

        // Run the UI updates on the JavaFX Application Thread
        Platform.runLater(() -> {
            penaltyLabel = new Label();
            penaltyLabel.setTranslateX(200); // Set X position
            penaltyLabel.setTranslateY(300); // Set Y position
            Platform.runLater(() -> {
                root.getChildren().add(penaltyLabel);
            });
        });

        final int countdownTime = 5;
        final int[] remainingTime = {countdownTime}; // Start from countdownTime

        Timeline penaltyTimeline = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(1), e -> {
            remainingTime[0]--; // Decrement remaining time

            // Update label on JavaFX Application Thread
            Platform.runLater(() -> {
                penaltyLabel.setText("Penalty: " + remainingTime[0] + " seconds");
            });

            if (remainingTime[0] <= 0) {
                penaltyTimeline.stop();

                // Hide and remove the label on JavaFX Application Thread
                Platform.runLater(() -> {
                    penaltyLabel.setVisible(false);
                    root.getChildren().remove(penaltyLabel);
                });
                Platform.runLater(() -> {
                    root.getStyleClass().remove("freezeRoot");

                });

                isPenaltyActive = false; // Reset the penalty flag
                breakStopped = false; // Resume break movement after the penalty duration
            }
        });

        penaltyTimeline.getKeyFrames().add(frame);
        penaltyTimeline.setCycleCount(countdownTime); // Run for countdownTime seconds
        penaltyTimeline.play();
    }






    public static boolean breakStopped = false;
//    private void move(final int direction) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int sleepTime = 4;
//
//                if (breakStopped) {
//                    return;
//                }
//
//                for (int i = 0; i < 40; i++) {
//                    final double newX;
//                    if (xBreak < (sceneWidth - breakWidth) && direction == RIGHT) {
//                        newX = xBreak + 1;
//                    } else if (xBreak > 0 && direction == LEFT) {
//                        newX = xBreak - 1;
//                    } else {
//                        break;
//                    }
//
//                    final double newCenterX = newX + halfBreakWidth;
//
//
//                        xBreak = newX;
//                        centerBreakX = newCenterX;
//
//                    try {
//                        Thread.sleep(sleepTime);
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                    }
//
//
//                }
//            }
//        }).start();
//    }







    private void initBall() {
        // Calculate the total height occupied by the blocks
        int totalBlocksHeight = Block.getHeight() * (level + 1) + Block.getPaddingTop();

        // Set the ball's initial position
        xBall = sceneWidth / 2; // Center horizontally
        yBall = totalBlocksHeight + 20; // 20 pixels below the blocks

        // Ensure the ball doesn't spawn off-screen
        if (yBall > sceneHeigt - Ball.ballRadius * 2) {
            yBall = sceneHeigt - Ball.ballRadius * 2;
        }

        // Initialize the ball
        ball = new Ball(Ball.ballRadius, xBall, yBall);

    }


    private void initBreak() {
        rect = new Break(xBreak , yBreak , breakWidth , breakHeight);

    }


//    private boolean goDownBall                  = true;
//    private boolean goRightBall                 = false;
//    private boolean colideToBreak               = false;
//    private boolean colideToBreakAndMoveToRight = false;
//    private boolean colideToRightWall           = false;
//    private boolean colideToLeftWall            = false;
//    private boolean colideToRightBlock          = false;
//    private boolean colideToBottomBlock         = false;
//    private boolean colideToLeftBlock           = false;
//    private boolean colideToTopBlock            = false;
//
    public static double vX = 5.000;
    public static  double vY = 5.000;
//
//
//    private void resetColideFlags() {
//
//        colideToBreak = false;
//        colideToBreakAndMoveToRight = false;
//        colideToRightWall = false;
//        colideToLeftWall = false;
//
//        colideToRightBlock = false;
//        colideToBottomBlock = false;
//        colideToLeftBlock = false;
//        colideToTopBlock = false;
//    }
////
//    private void setPhysicsToBall() {
//
//        if (goDownBall) {
//            yBall += vY;
//        } else {
//            yBall -= vY;
//        }
//
//        if (goRightBall) {
//            xBall += vX;
//        } else {
//            xBall -= vX;
//        }
//        //reset the game state when the ball hits the top or bottom of the screen.
//        if (yBall <= 0) {
//            //vX = 1.000;
//            resetColideFlags();
//            goDownBall = true;
//            return;
//        }
//        if (yBall >= sceneHeigt) {
//            resetColideFlags();
//            goDownBall = false;
//            if (!isGoldStauts) {
//                heart--;
//                Soundeffects.playHeartDown();
//                //just show -1 on the screen
//                new Score().show(sceneWidth / 2, sceneHeigt / 2, -1, this);
//
//                if (heart == 0) {
//                    new Score().showGameOver(this);
//                    engine.stop();
//                }
//
//            }
//        }
//        //vertical collision
//        if (yBall >= yBreak - ballRadius) {
//            if (xBall >= xBreak && xBall <= xBreak + breakWidth) {
//                resetColideFlags();
//                colideToBreak = true;
//                goDownBall = false;
//                //determine horizontal direction and speed
//                double relation = (xBall - centerBreakX) / (breakWidth / 2);
//
//                if (Math.abs(relation) <= 0.3) {
//                    //vX = 0;
//                    vX = Math.abs(relation);
//                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
//                    vX = (Math.abs(relation) * 1.5) + (level / 3.500);
//                    //System.out.println("vX " + vX);
//                } else {
//                    vX = (Math.abs(relation) * 2) + (level / 3.500);
//                    //System.out.println("vX " + vX);
//                }
//
//                if (xBall - centerBreakX > 0) {
//                    colideToBreakAndMoveToRight = true;
//                } else {
//                    colideToBreakAndMoveToRight = false;
//                }
//                //System.out.println("Colide2");
//            }
//        }
//
//        if (xBall >= sceneWidth) {
//            resetColideFlags();
//            //vX = 1.000;
//            colideToRightWall = true;
//        }
//
//        if (xBall <= 0) {
//            resetColideFlags();
//            //vX = 1.000;
//            colideToLeftWall = true;
//        }
//
//        if (colideToBreak) {
//            if (colideToBreakAndMoveToRight) {
//                goRightBall = true;
//            } else {
//                goRightBall = false;
//            }
//        }
//
//        //Wall Colide
//
//        if (colideToRightWall) {
//
//            goRightBall = false;
//        }
//
//        if (colideToLeftWall) {
//
//            goRightBall = true;
//        }
//
//        //Block Colide
//
//        if (colideToRightBlock) {
//
//            goRightBall = false;
//        }
//
//        if (colideToLeftBlock) {
//
//            goRightBall = true;
//        }
//
//        if (colideToTopBlock) {
//
//            goDownBall = false;
//        }
//
//        if (colideToBottomBlock) {
//
//            goDownBall = true;
//        }
//
//
//    }


    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            //TODO win level todo...
            //System.out.println("You Win");

            nextLevel();
        }
    }

//    private void saveGame() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                new File(savePathDir).mkdirs();
//                File file = new File(savePath);
//                ObjectOutputStream outputStream = null;
//                try {
//                    outputStream = new ObjectOutputStream(new FileOutputStream(file));
//
//                    outputStream.writeInt(level);
//                    outputStream.writeInt(score);
//                    outputStream.writeInt(heart);
//                    outputStream.writeInt(destroyedBlockCount);
//
//
//                    outputStream.writeDouble(xBall);
//                    outputStream.writeDouble(yBall);
//                    outputStream.writeDouble(xBreak);
//                    outputStream.writeDouble(yBreak);
//                    outputStream.writeDouble(centerBreakX);
//                    outputStream.writeLong(time);
//                    outputStream.writeLong(goldTime);
//                    outputStream.writeDouble(vX);
//
//
//                    outputStream.writeBoolean(isExistHeartBlock);
//                    outputStream.writeBoolean(isGoldStauts);
//                    outputStream.writeBoolean(goDownBall);
//                    outputStream.writeBoolean(goRightBall);
//                    outputStream.writeBoolean(colideToBreak);
//                    outputStream.writeBoolean(colideToBreakAndMoveToRight);
//                    outputStream.writeBoolean(colideToRightWall);
//                    outputStream.writeBoolean(colideToLeftWall);
//                    outputStream.writeBoolean(colideToRightBlock);
//                    outputStream.writeBoolean(colideToBottomBlock);
//                    outputStream.writeBoolean(colideToLeftBlock);
//                    outputStream.writeBoolean(colideToTopBlock);
//
//                    ArrayList<BlockSerializable> blockSerializables = new ArrayList<BlockSerializable>();
//                    for (Block block : blocks) {
//                        if (block.isDestroyed) {
//                            continue;
//                        }
//                        blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
//                    }
//
//                    outputStream.writeObject(blockSerializables);
//
//                    new Score().showMessage("Game Saved", Main.this);
//
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        outputStream.flush();
//                        outputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//
//    }

//    private void loadGame() {
//
//        LoadSave loadSave = new LoadSave();
//        loadSave.read();
//
//        //Assigning Loaded Values to Local Variables:
//        isExistHeartBlock = loadSave.isExistHeartBlock;
//        isGoldStauts = loadSave.isGoldStauts;
//        goDownBall = loadSave.goDownBall;
//        goRightBall = loadSave.goRightBall;
//        colideToBreak = loadSave.colideToBreak;
//        colideToBreakAndMoveToRight = loadSave.colideToBreakAndMoveToRight;
//        colideToRightWall = loadSave.colideToRightWall;
//        colideToLeftWall = loadSave.colideToLeftWall;
//        colideToRightBlock = loadSave.colideToRightBlock;
//        colideToBottomBlock = loadSave.colideToBottomBlock;
//        colideToLeftBlock = loadSave.colideToLeftBlock;
//        colideToTopBlock = loadSave.colideToTopBlock;
//        level = loadSave.level;
//        score = loadSave.score;
//        heart = loadSave.heart;
//        destroyedBlockCount = loadSave.destroyedBlockCount;
//        xBall = loadSave.xBall;
//        yBall = loadSave.yBall;
//        xBreak = loadSave.xBreak;
//        yBreak = loadSave.yBreak;
//        centerBreakX = loadSave.centerBreakX;
//        time = loadSave.time;
//        goldTime = loadSave.goldTime;
//        vX = loadSave.vX;
//        //Clearing Existing Blocks and chocos and mysteries:
//        blocks.clear();
//        chocos.clear();
//        mysteries.clear();
//        //Repopulating the blocks from the Loaded Data:
//        for (BlockSerializable ser : loadSave.blocks) {
//            int r = new Random().nextInt(200);
//            blocks.add(new Block(ser.row, ser.j, colors[r % colors.length], ser.type));
//        }
//
//
//        try {
//            loadFromSave = true;
//            start(primaryStage);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }

    private void nextLevel() {
        if (isNextLevelCalled) {
            return;
        }
        isNextLevelCalled = true;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    vX = 1.000;

                    //engine.stop();
                    ball.resetColideFlags();
                    ball.setGoDownBall(true);

                    isGoldStauts = false;
                    isExistHeartBlock = false;


                    time = 0;
                    goldTime = 0;

                    engine.stop();
                    blocks.clear();
                    chocos.clear();
                    mysteries.clear();
                    destroyedBlockCount = 0;
                    start(primaryStage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void restartGame() {

        try {
            level = 0;
            heart = 3;
            score = 0;
            vX = 1.000;
            destroyedBlockCount = 0;
            ball.resetColideFlags();
            ball.setGoDownBall(true);

            isGoldStauts = false;
            isExistHeartBlock = false;
            time = 0;
            goldTime = 0;

            blocks.clear();
            chocos.clear();
            mysteries.clear();

            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                scoreLabel.setText("Score: " + score);
                heartLabel.setText("Heart : " + heart);

                rect.setX(Break.getxBreak());
                rect.setY(Break.getyBreak());
                ball.setCenterX(ball.getBallX());
                ball.setCenterY(ball.getBallY());

                for (Bonus choco : chocos) {
                    choco.block.setY(choco.y);
                }
                for (Bonus mystery : mysteries) {
                    mystery.block.setY(mystery.y);
                }
            }
        });

        //if smtg collide with ball
        if (ball.getBallY() >= Block.getPaddingTop() && ball.getBallY() <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop()) {
            for (final Block block : blocks) {
                int hitCode = block.checkHitToBlock(ball.getBallX(), ball.getBallY());
                if (hitCode != Block.NO_HIT) {
                    Soundeffects.playBlockHit();
                    score += 1;

                    new Score().show(block.x, block.y, 1, this);

                    block.rect.setVisible(false);
                    block.isDestroyed = true;
                    destroyedBlockCount++;
                    //System.out.println("size is " + blocks.size());
                    ball.resetColideFlags();

                    if (block.type == Block.BLOCK_CHOCO) {
                        final Bonus choco = new Bonus(block.row, block.column , block.type);
                        choco.timeCreated = time;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Platform.runLater(() -> {
                                    root.getChildren().add(choco.block);
                                });
                            }
                        });
                        chocos.add(choco);
                    }

                    if (block.type == Block.BLOCK_MYSTERY) {
                        final Bonus mystery = new Bonus(block.row, block.column , block.type);
                        mystery.timeCreated = time;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Platform.runLater(() -> {
                                    root.getChildren().add(mystery.block);
                                });

                            }
                        });
                        mysteries.add(mystery);
                    }


                    if (block.type == Block.BLOCK_STAR) {
                        Soundeffects.playGoldBlockHit();
                        goldTime = time;
                        ball.setFill(new ImagePattern(new Image("goldball.png")));
                        System.out.println("gold ball");
                        Platform.runLater(() -> {
                            root.getStyleClass().add("goldRoot");
                        });

                        isGoldStauts = true;
                    }

                    if (block.type == Block.BLOCK_HEART) {
                        heart++;
                        Soundeffects.playHeartUp();
                    }

                    ball.onUpdateBall(hitCode);

                }

                //TODO hit to break and some work here....
                //System.out.println("Break in row:" + block.row + " and column:" + block.column + " hit");
            }
        }
    }


    @Override
    public void onInit() {

    }

    @Override
    public void onPhysicsUpdate() {
        //if all destroyed move to next lvl
        checkDestroyedCount();
        ball.setPhysicsToBall();
        if (ball.hasHitSceneBottom()) {
            // Handle the scenario when the ball hits the bottom
            if (!isGoldStauts) {
                heart--;
                Soundeffects.playHeartDown();
                new Score().show(sceneWidth / 2, sceneHeigt / 2, -1, this);

                if (heart == 0) {
                    new Score().showGameOver(this);
                    engine.stop();
                }
            }
            ball.resetHitBottomFlag(); // Reset the flag after handling
        }

        xBall = ball.getBallX();
        yBall = ball.getBallY();
        if (time - goldTime > 5000) {
            ball.setFill(new ImagePattern(new Image("ball.png")));
            Platform.runLater(() -> {
                root.getStyleClass().remove("goldRoot");
            });
            isGoldStauts = false;
        }

        for (Bonus choco : chocos) {
            if (choco.y > sceneHeigt || choco.taken) {
                continue;
            }
            //Check for collision with break
            if (choco.y >= Break.getyBreak() && choco.y <= Break.getyBreak() + breakHeight && choco.x >= Break.getxBreak() && choco.x <= Break.getxBreak() + breakWidth) {
                Soundeffects.playItemCatch();
                System.out.println("You Got it and +3 score for you");
                choco.taken = true;
                score += 3;
                Platform.runLater(() -> {
                    choco.block.setVisible(false);
                    new Score().show(choco.x, choco.y, 3, this);
                });
                new Score().show(choco.x, choco.y, 3, this);
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
                if (Math.random() < 0.1) {
                    score -= 1;
                    new Score().show(mystery.x, mystery.y, -1, this);
                }else {
                    System.out.println("Oh No ! FREEZE for 5s");
                    Soundeffects.playFreezeTime();
                    breakStopped = true;
                    handlePenalty();
                }


            }
            //gravity
            mystery.y += ((time - mystery.timeCreated) / 1000.000) + 1.000;
        }

        //System.out.println("time is:" + time + " goldTime is " + goldTime);

    }


    @Override
    public void onTime(long time) {
        this.time = time;
    }

}
