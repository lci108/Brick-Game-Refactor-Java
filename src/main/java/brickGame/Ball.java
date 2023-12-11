package brickGame;

import Controller.Controller;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
/**
 * The Ball class represents the game ball in the brick game.
 * It extends the Circle class and includes methods for handling ball physics and collisions.
 */
public class Ball extends Circle {
    /**
     * The radius of the ball.
     */
    public static final int ballRadius = 10;

    private double vX;
    private double vY;
    /**
     * Sets the status of the ball moving downwards.
     *
     * @param GODOWNBALL The new status of the ball moving downwards.
     */
    public  void setGoDownBall(boolean GODOWNBALL) {
        this.goDownBall = GODOWNBALL;
    }

    private  boolean goDownBall = true;
    private  boolean goRightBall = false;
    /**
     * Checks the downward movement status of the ball.
     *
     * @return {@code true} if the ball is moving downward, {@code false} otherwise.
     */
    public boolean isGoDownBall() {
        return this.goDownBall;
    }
    /**
     * Checks the rightward movement status of the ball.
     *
     * @return {@code true} if the ball is moving rightward, {@code false} otherwise.
     */
    public boolean isGoRightBall() {
        return this.goRightBall;
    }

    /**
     * Sets the status of the ball moving to the right.
     *
     * @param goRightBall The new status of the ball moving to the right.
     */
    public  void setGoRightBall(boolean goRightBall) {
        this.goRightBall = goRightBall;
    }
    /**
     * Checks if the ball collides with the right side of a block.
     *
     * @return {@code true} if the ball collides with the right side of a block, {@code false} otherwise.
     */
    public  boolean isColideToRightBlock() {
        return this.colideToRightBlock;
    }
    /**
     * Sets the collision status with the right block.
     *
     * @param colideToRightBlock The new collision status with the right block.
     */
    public void setColideToRightBlock(boolean colideToRightBlock) {
        this.colideToRightBlock = colideToRightBlock;
    }
    /**
     * Checks if the ball collides with the bottom side of a block.
     *
     * @return {@code true} if the ball collides with the bottom side of a block, {@code false} otherwise.
     */
    public boolean isColideToBottomBlock() {
        return this.colideToBottomBlock;
    }
    /**
     * Sets the collision status with the bottom block.
     *
     * @param colideToBottomBlock The new collision status with the bottom block.
     */
    public void setColideToBottomBlock(boolean colideToBottomBlock) {
        this.colideToBottomBlock = colideToBottomBlock;
    }
    /**
     * Checks if the ball collides with the left side of a block.
     *
     * @return {@code true} if the ball collides with the left side of a block, {@code false} otherwise.
     */
    public boolean isColideToLeftBlock() {
        return this.colideToLeftBlock;
    }
    /**
     * Sets the collision status with the left block.
     *
     * @param colideToLeftBlock The new collision status with the left block.
     */
    public void setColideToLeftBlock(boolean colideToLeftBlock) {
        this.colideToLeftBlock = colideToLeftBlock;
    }
    /**
     * Checks if the ball collides with the top side of a block.
     *
     * @return {@code true} if the ball collides with the top side of a block, {@code false} otherwise.
     */
    public boolean isColideToTopBlock() {
        return this.colideToTopBlock;
    }
    /**
     * Sets the collision status with the top block.
     *
     * @param colideToTopBlock The new collision status with the top block.
     */

    public void setColideToTopBlock(boolean colideToTopBlock) {
        this.colideToTopBlock = colideToTopBlock;
    }

    private  boolean colideToRightBlock = false;
    private boolean colideToBottomBlock = false;
    private boolean colideToLeftBlock = false;
    private boolean colideToTopBlock = false;

    private boolean hitSceneBottom = false;



    /**
     * Constructs a Ball object with the specified radius and initial position.
     *
     * @param radius    The radius of the ball.
     * @param initialX  The initial x-coordinate of the ball.
     * @param initialY  The initial y-coordinate of the ball.
     */
    public Ball( double radius, double initialX, double initialY) {
        super(radius, new ImagePattern(new Image("ball.png")));
        this.setCenterX(initialX);
        this.setCenterY(initialY);
    }

    /**
     * Resets the flag indicating that the ball hit the bottom of the scene.
     */

    public void resetHitBottomFlag() {
        hitSceneBottom = false;
    }
    /**
     * Sets the physics for the ball, including collisions with the scene boundaries and the break.
     */
    public void setPhysicsToBall() {

        // Use variables from Main class
        this.vX = 3;
        this.vY = 3;
        double xBreak = Break.getxBreak();
        double yBreak = Break.getyBreak();
        int breakWidth = Controller.breakWidth;
        double centerBreakX = Break.getCenterBreakx();
        int sceneWidth = Controller.sceneWidth;
        int sceneHeight = Controller.sceneHeigt;

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
    /**
     * Updates the ball based on the specified hit code.
     *
     * @param hitCode The hit code indicating the type of collision.
     */

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

    /**
     * Calculates the horizontal velocity based on the collision relation.
     *
     * @param relation The collision relation.
     * @return The calculated horizontal velocity.
     */
    private double calculateVelocityX(double relation) {
        if (Math.abs(relation) <= 0.3) {
            return Math.abs(relation);
        } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
            return (Math.abs(relation) * 1.5);
        } else {
            return (Math.abs(relation) * 2) ;
        }
    }
    /**
     * Resets the collision flags for the ball.
     */

    public  void resetColideFlags() {
        this.colideToRightBlock = false;
        this.colideToBottomBlock = false;
        this.colideToLeftBlock = false;
        this.colideToTopBlock = false;
    }
    /**
     * Gets the x-coordinate of the ball.
     *
     * @return The x-coordinate of the ball.
     */
    public double getBallX() {
        return this.getCenterX();
    }
    /**
     * Gets the y-coordinate of the ball.
     *
     * @return The y-coordinate of the ball.
     */
    public double getBallY() {
        return this.getCenterY();
    }
    /**
     * Checks if the ball hit the bottom of the scene.
     *
     * @return True if the ball hit the bottom, false otherwise.
     */
    public boolean hasHitSceneBottom() {
        return hitSceneBottom;
    }
}
