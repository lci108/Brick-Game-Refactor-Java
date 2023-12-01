package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Ball extends Circle {
    public static final int ballRadius = 10;

    private double vX;
    private double vY;

    public  void setGoDownBall(boolean GODOWNBALL) {
        this.goDownBall = GODOWNBALL;
    }

    private  boolean goDownBall = true;
    private  boolean goRightBall = false;

    public boolean isGoDownBall() {
        return this.goDownBall;
    }

    public boolean isGoRightBall() {
        return this.goRightBall;
    }

    public  void setGoRightBall(boolean goRightBall) {
        this.goRightBall = goRightBall;
    }

    public  boolean isColideToRightBlock() {
        return this.colideToRightBlock;
    }

    public void setColideToRightBlock(boolean colideToRightBlock) {
        this.colideToRightBlock = colideToRightBlock;
    }

    public boolean isColideToBottomBlock() {
        return this.colideToBottomBlock;
    }

    public void setColideToBottomBlock(boolean colideToBottomBlock) {
        this.colideToBottomBlock = colideToBottomBlock;
    }

    public boolean isColideToLeftBlock() {
        return this.colideToLeftBlock;
    }

    public void setColideToLeftBlock(boolean colideToLeftBlock) {
        this.colideToLeftBlock = colideToLeftBlock;
    }

    public boolean isColideToTopBlock() {
        return this.colideToTopBlock;
    }

    public void setColideToTopBlock(boolean colideToTopBlock) {
        this.colideToTopBlock = colideToTopBlock;
    }

    private  boolean colideToRightBlock = false;
    private boolean colideToBottomBlock = false;
    private boolean colideToLeftBlock = false;
    private boolean colideToTopBlock = false;
    private int level;
    private boolean hitSceneBottom = false;

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
        Model model = new Model();

        // Use variables from Main class
        this.vX = 3;
        this.vY = 3;
        double xBreak = Break.getxBreak();
        double yBreak = Break.getyBreak();
        int breakWidth = Controller.breakWidth;
        double centerBreakX = Break.getCenterBreakx();
        int sceneWidth = Controller.sceneWidth;
        int sceneHeight = Controller.sceneHeigt;
        this.level = model.getLevel();

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
            this.goDownBall = true;
            resetColideFlags();
        } else if (this.getCenterY() >= sceneHeight - ballRadius * 2) {
            this.goDownBall = false;
            hitSceneBottom = true;
            resetColideFlags();
            // Additional logic for handling when the ball hits the bottom
        }

        // Collision with break
        if (this.getCenterY() >= yBreak - ballRadius &&
                this.getCenterX() >= xBreak &&
                this.getCenterX() <= xBreak + breakWidth) {

            resetColideFlags();
            this.goDownBall = false;

            boolean hitLeftSide = this.getCenterX() < (xBreak + breakWidth / 2.0);
            if ((this.goRightBall && hitLeftSide) || (!this.goRightBall && !hitLeftSide)) {
                this.goRightBall = !goRightBall;
            }

            // Calculate the new horizontal velocity based on the collision
            double relation = (this.getCenterX() - centerBreakX) / (breakWidth / 2.0);
            vX = calculateVelocityX(relation);
        }

        // Collision with left or right walls of the scene
        if (this.getCenterX() <= 0) {
            this.goRightBall = true;
            resetColideFlags();
        } else if (this.getCenterX() >= sceneWidth) {
            this.goRightBall = false;
            resetColideFlags();
        }

        //Block Colide

        if (colideToRightBlock) {

            this.goRightBall = false;
        }

        if (colideToLeftBlock) {

            this.goRightBall = true;
        }

        if (colideToTopBlock) {

            this.goDownBall = false;
        }

        if (colideToBottomBlock) {

            this.goDownBall = true;
        }

        }




    public void onUpdateBall(int hitCode) {
        // Collision logic based on hitCode
        switch (hitCode) {
            case Block.HIT_RIGHT:
                this.colideToRightBlock = true;
                break;
            case Block.HIT_BOTTOM:
                this.colideToBottomBlock = true;
                break;
            case Block.HIT_LEFT:
                this.colideToLeftBlock = true;
                break;
            case Block.HIT_TOP:
                this.colideToTopBlock = true;
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
        this.colideToRightBlock = false;
        this.colideToBottomBlock = false;
        this.colideToLeftBlock = false;
        this.colideToTopBlock = false;
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
