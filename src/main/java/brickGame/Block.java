package brickGame;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;

import static brickGame.Ball.ballRadius;

public class Block implements Serializable {
    private static Block block = new Block(-1, -1,99);

    public int row;
    public int column;


    public boolean isDestroyed = false;

    private Color color;
    public int type;

    public int x;
    public int y;

    private int width = 80;
    private int height = 30;
    private int paddingTop = height * 2;
    private int paddingH = 40;
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


    public Block(int row, int column, int type) {
        this.row = row;
        this.column = column;
        this.type = type;
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
        }else{
            String imagePath = "block" + (new Random().nextInt(8)+1) + ".png"; // type + 1 to match your image naming
            Image image = new Image(imagePath);
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);        }

    }





    // Assuming the blocks are axis-aligned rectangles and the ball is a circle
    public int checkHitToBlock(double xBall, double yBall) {

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





    public static int getPaddingTop() {
        return block.paddingTop;
    }

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