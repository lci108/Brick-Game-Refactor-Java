package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;



/**
 * The Bonus class represents a bonus object in the brick game.
 * It is used to model bonuses that appear when certain blocks are destroyed.
 */
public class Bonus implements Serializable {
    public Rectangle block;

    public double x;
    public double y;
    public long timeCreated;
    public boolean taken = false;

    private final int blockType;

    /**
     * Constructs a Bonus object with the specified row, column, and block type.
     *
     * @param row       The row of the associated block.
     * @param column    The column of the associated block.
     * @param blockType The type of the associated block.
     */
    public Bonus(int row, int column , int blockType) {
        this.blockType = blockType;
        x = (column * (Block.getWidth())) + Block.getPaddingH() + (Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + (Block.getHeight() / 2) - 15;

        draw();
    }
    /**
     * Draws the bonus by creating a Rectangle and setting its properties.
     */
    private void draw() {
        block = new Rectangle();
        block.setWidth(30);
        block.setHeight(30);
        block.setX(x);
        block.setY(y);

        String url = chooseImageForType(this.blockType);
        block.setFill(new ImagePattern(new Image(url)));
    }
    /**
     * Chooses the image URL for the bonus based on its block type.
     *
     * @param blockType The type of the associated block.
     * @return The URL of the image for the bonus.
     */
    private String chooseImageForType(int blockType) {
        if (blockType == Block.BLOCK_CHOCO) {
            return new Random().nextInt(20) % 2 == 0 ? "bonus1.png" : "bonus2.png";
        } else if (blockType == Block.BLOCK_MYSTERY) {
            return "mystery.png";
        }
        return null;
    }


}
