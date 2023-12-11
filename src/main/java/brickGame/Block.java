package brickGame;


import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;

import static brickGame.Ball.ballRadius;
/**
 * The Block class represents the blocks in the brick game. Blocks can have different types and may be destroyed by the ball.
 * This class implements Serializable to allow for object serialization for loadsave
 */

public class Block implements Serializable {
    private static final Block block = new Block(-1, -1,99 ,0);

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int direction;

    public int row;
    public int column;


    public boolean isDestroyed = false;

    public int type;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int x;
    public int y;

    private final int width = 80;
    private final int height = 30;
    private final int paddingTop = height * 2;
    private final int paddingH = 40;

    public Rectangle getRect() {
        return rect;
    }

    public Rectangle rect;


    public static final int NO_HIT = -1;
    public static final int HIT_RIGHT = 0;
    public static final int HIT_BOTTOM = 1;
    public static final int HIT_LEFT = 2;
    public static final int HIT_TOP = 3;

    public static int BLOCK_NORMAL = 99;
    public static int BLOCK_CHOCO = 100;
    public static int BLOCK_STAR = 101;
    public static int BLOCK_HEART = 102;

    public static int BLOCK_MYSTERY =103;

    public static final int BLOCK_SPOOKY = 104;

    public static final int BLOCK_SPOOKED = 105;

    public static final int BLOCK_IMPENETRABLE = 106;
    /**
     * Constructs a Block object with the specified row, column, type, and direction.
     *
     * @param row       The row of the block.
     * @param column    The column of the block.
     * @param type      The type of the block.
     * @param direction The direction of the block 0 for normal blocks , 1 for going right and 0 for going left (only applicable to impenetrable blocks)
     */

    public Block(int row, int column, int type , int direction) {
        this.row = row;
        this.column = column;
        this.type = type;
        this.direction = direction;
        draw();
    }

    private void draw() {
        x = (column * width) + paddingH;
        y = (row * height) + paddingTop;

        rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setX(x);
        rect.setY(y);
        if (type == BLOCK_CHOCO) {
            Image image = new Image("choco.png");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_HEART) {
            Image image = new Image("heart.png");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_STAR) {
            Image image = new Image("star.png");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_MYSTERY) {
            Image image = new Image("mystery_block.png");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        }
        else if (type == BLOCK_SPOOKY){
            Image image = new Image("spooky.png");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if(type == BLOCK_SPOOKED) {
            Image image = new Image("spooked.png");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        }else if (type == BLOCK_IMPENETRABLE) {
                Image image = new Image("impenetrable.png"); // Replace with the actual image for the new block
                ImagePattern pattern = new ImagePattern(image);
                rect.setFill(pattern);
        }else{
            String imagePath = "block" + (new Random().nextInt(8)+1) + ".png"; // type + 1 to match your image naming
            Image image = new Image(imagePath);
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);        }

    }



    /**
     * Checks for a collision between the ball and the block.
     *
     * @param xBall The x-coordinate of the ball.
     * @param yBall The y-coordinate of the ball.
     * @return The hit code indicating the type of collision, or {@link #NO_HIT} if no collision occurred.
     */    public int checkHitToBlock(double xBall, double yBall ) {

        if (isDestroyed) {
            return NO_HIT;
        }

        double ballTop = yBall - ballRadius;
        double ballBottom = yBall + ballRadius;
        double ballLeft = xBall - ballRadius;
        double ballRight = xBall + ballRadius;

        // Check for collision with the bottom of the block
        if (ballRight > x && ballLeft < x + width) { // Overlapping in X
            if (ballBottom > y && ballTop < y + height) { // Overlapping in Y
                // Find out the depth of overlap in each direction
                double overlapBottom = ballBottom - y;
                double overlapTop = (y + height) - ballTop;
                double overlapLeft = ballRight - x;
                double overlapRight = (x + width) - ballLeft;

                // Find the minimum overlap which tells us which side was hit
                double minOverlap = Math.min(Math.min(overlapBottom, overlapTop), Math.min(overlapLeft, overlapRight));

                // Return the side where the minimum overlap occurred
                if (minOverlap == overlapBottom) {
                    return HIT_TOP; // Ball's bottom hit the block's top
                } else if (minOverlap == overlapTop) {
                    return HIT_BOTTOM; // Ball's top hit the block's bottom
                } else if (minOverlap == overlapLeft) {
                    return HIT_RIGHT; // Ball's left hit the block's right
                } else if (minOverlap == overlapRight) {
                    return HIT_LEFT; // Ball's right hit the block's left
                }
            }
        }

        return NO_HIT; // No collision occurred
    }
    /**
     * Gets the padding at the top of the blocks.
     *
     * @return The padding at the top of the blocks.
     */
    public static int getPaddingTop() {
        return block.paddingTop;
    }
    /**
     * Gets the horizontal padding for the blocks.
     *
     * @return The horizontal padding for the blocks.
     */

    public static int getPaddingH() {
        return block.paddingH;
    }

    public static int getHeight() {
        return block.height;
    }

    public static int getWidth() {
        return block.width;
    }

}