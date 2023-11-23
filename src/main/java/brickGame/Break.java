package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import static brickGame.Main.*;


public class Break extends Rectangle {

    public static double getxBreak() {
        return xBreak;
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
    private static int halfBreakWidth = breakWidth / 2;






    public Break(double x, double y, int width, int height) {
        super(width, height, new ImagePattern(new Image("block.png")));
        this.setX(x);
        this.setY(y);
        this.breakWidth = width;
        this.xBreak = x;
        this.yBreak = y;
    }

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
                    if (xBreak < (Main.sceneWidth - breakWidth) && direction == RIGHT) {
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
