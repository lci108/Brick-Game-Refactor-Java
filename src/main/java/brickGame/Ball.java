package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.List;

public class Ball extends Circle {
    public static final int ballRadius = 10;

    private double vX;
    private double vY;

    public void setGoDownBall(boolean GODOWNBALL) {
        goDownBall = GODOWNBALL;
    }

    private static boolean goDownBall = true;
    private static boolean goRightBall = false;


    private static boolean colideToRightBlock = false;
    private static boolean colideToBottomBlock = false;
    private static boolean colideToLeftBlock = false;
    private static boolean colideToTopBlock = false;

    private double xBreak, yBreak;
    private int breakWidth;
    private int sceneWidth, sceneHeight;
    private double centerBreakX;
    private boolean isGoldStatus;
    private int level;
    private boolean hitSceneBottom = false;

    private Main main; // Reference to Main class

    // Updated constructor
    public Ball( double radius, double initialX, double initialY) {
        super(radius, new ImagePattern(new Image("ball.png")));
        this.setCenterX(initialX);
        this.setCenterY(initialY);
    }

    public void resetHitBottomFlag() {
        hitSceneBottom = false;
    }

    public void setPhysicsToBall() {

        // Use variables from Main class
        this.vX = Main.vX;
        this.vY = Main.vY;
        this.xBreak = Break.getxBreak();
        this.yBreak = Break.getyBreak();
        this.breakWidth = Main.breakWidth;
        this.centerBreakX = Break.getCenterBreakx();
        this.sceneWidth = Main.sceneWidth;
        this.sceneHeight = Main.sceneHeigt;
        this.level = Main.level;

        if (goDownBall) {
            this.setCenterY(this.getCenterY() + vY);
        } else {
            this.setCenterY(this.getCenterY() - vY);
        }

        if (goRightBall) {
            this.setCenterX(this.getCenterX() + vX);
        } else {
            this.setCenterX(this.getCenterX() - vX);
        }

        // Collision with top or bottom of the scene
        if (this.getCenterY() <= 0) {
            goDownBall = true;
            resetColideFlags();
        } else if (this.getCenterY() >= sceneHeight - ballRadius * 2) {
            goDownBall = false;
            hitSceneBottom = true;
            resetColideFlags();
            // Additional logic for handling when the ball hits the bottom
        }

        // Collision with break
        if (this.getCenterY() >= yBreak - ballRadius &&
                this.getCenterX() >= xBreak &&
                this.getCenterX() <= xBreak + breakWidth) {

            resetColideFlags();
            goDownBall = false;

            boolean hitLeftSide = this.getCenterX() < (xBreak + breakWidth / 2.0);
            if ((goRightBall && hitLeftSide) || (!goRightBall && !hitLeftSide)) {
                goRightBall = !goRightBall;
            }

            // Calculate the new horizontal velocity based on the collision
            double relation = (this.getCenterX() - centerBreakX) / (breakWidth / 2.0);
            vX = calculateVelocityX(relation);
        }

        // Collision with left or right walls of the scene
        if (this.getCenterX() <= 0) {
            goRightBall = true;
            resetColideFlags();
        } else if (this.getCenterX() >= sceneWidth) {
            goRightBall = false;
            resetColideFlags();
        }

        //Block Colide

        if (colideToRightBlock) {

            goRightBall = false;
        }

        if (colideToLeftBlock) {

            goRightBall = true;
        }

        if (colideToTopBlock) {

            goDownBall = false;
        }

        if (colideToBottomBlock) {

            goDownBall = true;
        }

        }




    public void onUpdateBall(int hitCode) {
        // Collision logic based on hitCode
        switch (hitCode) {
            case Block.HIT_RIGHT:
                colideToRightBlock = true;
                break;
            case Block.HIT_BOTTOM:
                colideToBottomBlock = true;
                break;
            case Block.HIT_LEFT:
                colideToLeftBlock = true;
                break;
            case Block.HIT_TOP:
                colideToTopBlock = true;
                break;
        }

    }


    private double calculateVelocityX(double relation) {
        if (Math.abs(relation) <= 0.3) {
            return Math.abs(relation);
        } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
            return (Math.abs(relation) * 1.5) + (level / 3.5);
        } else {
            return (Math.abs(relation) * 2) + (level / 3.5);
        }
    }

    public  void resetColideFlags() {
        colideToRightBlock = false;
        colideToBottomBlock = false;
        colideToLeftBlock = false;
        colideToTopBlock = false;
    }

    public double getBallX() {
        return this.getCenterX();
    }

    public double getBallY() {
        return this.getCenterY();
    }

    public boolean hasHitSceneBottom() {
        return hitSceneBottom;
    }
}
