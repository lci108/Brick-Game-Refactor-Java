package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;



public class Bonus implements Serializable {
    public Rectangle block;

    public double x;
    public double y;
    public long timeCreated;
    public boolean taken = false;

    private  int blockType;

    public Bonus(int row, int column , int blockType) {
        this.blockType = blockType;
        x = (column * (Block.getWidth())) + Block.getPaddingH() + (Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + (Block.getHeight() / 2) - 15;

        draw();
    }

    private void draw() {
        block = new Rectangle();
        block.setWidth(30);
        block.setHeight(30);
        block.setX(x);
        block.setY(y);

        String url = chooseImageForType(this.blockType);
        block.setFill(new ImagePattern(new Image(url)));
    }
    private String chooseImageForType(int blockType) {
        if (blockType == Block.BLOCK_CHOCO) {
            return new Random().nextInt(20) % 2 == 0 ? "bonus1.png" : "bonus2.png";
        } else if (blockType == Block.BLOCK_MYSTERY) {
            return "mystery.png";
        }
        return null;
    }


}
