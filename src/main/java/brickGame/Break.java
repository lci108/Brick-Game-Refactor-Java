package brickGame;

import Controller.Controller;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import static Controller.Controller.*;

/**
 * The Break class represents the paddle in the brick game.
 * It extends Rectangle and includes methods for moving the paddle.
 */
public class Break extends Rectangle {


    public static double getxBreak() {
        return xBreak;
    }

    public static void setxBreak(double xBreak) {
        Break.xBreak = xBreak;
    }

    public static void setyBreak(double yBreak) {
        Break.yBreak = yBreak;
    }

    public static void setCenterBreakx(double centerBreakx) {
        Break.centerBreakx = centerBreakx;
    }

    private static double xBreak;

    public static double getyBreak() {
        return yBreak;
    }

    private static double yBreak;
    private static int breakWidth;

    public static double getCenterBreakx() {
        return centerBreakx;
    }

    private static double centerBreakx;
    private static final int halfBreakWidth = breakWidth / 2;

    /**
     * Constructs a Break object with the specified x-coordinate, y-coordinate, width, and height.
     *
     * @param x      The x-coordinate of the top-left corner of the paddle.
     * @param y      The y-coordinate of the top-left corner of the paddle.
     * @param width  The width of the paddle.
     * @param height The height of the paddle.
     */
    public Break(double x, double y, int width, int height) {
        super(width, height, new ImagePattern(new Image("block.png")));
        this.setX(x);
        this.setY(y);
        breakWidth = width;
        xBreak = x;
        yBreak = y;
    }
    /**
     * Moves the paddle in the specified direction.
     *
     * @param direction The direction in which to move the paddle (LEFT or RIGHT).
     */

    public static void move(final int direction) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int sleepTime = 4;

                if (breakStopped) {
                    return;
                }

                for (int i = 0; i < 40; i++) {
                    final double newX;
                    if (xBreak < (Controller.sceneWidth - breakWidth) && direction == RIGHT) {
                        newX = xBreak + 1;
                    } else if (xBreak > 0 && direction == LEFT) {
                        newX = xBreak - 1;
                    } else {
                        break;
                    }

                    final double newCenterX = newX + halfBreakWidth;


                    xBreak = newX;
                    centerBreakx = newCenterX;

                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }


                }
            }
        }).start();
    }
    // You can add other methods specific to Break's functionality here if needed
}