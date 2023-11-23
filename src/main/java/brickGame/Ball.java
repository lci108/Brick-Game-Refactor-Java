package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.List;

public class Ball extends Circle {
    public static final int ballRadius = 10;

    private double vX;
    private double vY;
    private boolean goDownBall = true;
    private boolean goRightBall = false;


    private boolean colideToRightBlock = false;
    private boolean colideToBottomBlock = false;
    private boolean colideToLeftBlock = false;
    private boolean colideToTopBlock = false;

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

        // Use getters from Main class
        this.vX = Main.vX;
        this.vY = Main.vY;
        this.xBreak = Main.xBreak;
        this.yBreak = Main.yBreak;
        this.breakWidth = Main.breakWidth;
        this.centerBreakX = Main.centerBreakX;
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

        if (this.getCenterY() <= 0) {
            resetColideFlags();
            goDownBall = true;
        } else if (this.getCenterY() >= sceneHeight - ballRadius * 2) {
            resetColideFlags();
            goDownBall = false;
            hitSceneBottom = true;
        }

        if (this.getCenterY() >= yBreak - ballRadius &&
                this.getCenterX() >= xBreak &&
                this.getCenterX() <= xBreak + breakWidth) {
            resetColideFlags();
            goDownBall = false;

            double relation = (this.getCenterX() - centerBreakX) / (breakWidth / 2);
            vX = calculateVelocityX(relation);
            goRightBall = this.getCenterX() > centerBreakX;
        }

        if (this.getCenterX() >= sceneWidth) {
            resetColideFlags();
            goRightBall = false;
        } else if (this.getCenterX() <= 0) {
            resetColideFlags();
            goRightBall = true;
        }

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

    private void resetColideFlags() {
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
