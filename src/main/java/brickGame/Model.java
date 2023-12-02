package brickGame;


import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;


public class Model {
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void incLevel(){
        this.level++;
    }

    private int level = 0;

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }
    public void incHeart(){
        this.heart++;
    }
    public void decHeart(){
        this.heart--;
    }

    private int heart = 5;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void incScore(){
        this.score++;
    }


    public void inc3Score(){
        this.score = score + 3;
    }
    public void inc5Score(){
        this.score = score + 5;
    }

    private int score = 0;

    public int getDestroyedBlockCount() {
        return destroyedBlockCount;
    }

    public void setDestroyedBlockCount(int destroyedBlockCount) {
        this.destroyedBlockCount = destroyedBlockCount;
    }
    public void incDestroyedBlockCount() {
        this.destroyedBlockCount ++;
    }


    private int destroyedBlockCount = 0;

    private boolean isExistHeartBlock = false;

    private final CopyOnWriteArrayList<Block> blocks = new CopyOnWriteArrayList<>();

    // Constants
    private static final int CHANCE_DIVISOR = 500;
    private static final int CHOCO_CHANCE = 50;
    private static final int HEART_CHANCE = 150;
    private static final int STAR_CHANCE = 160;
    private static final int MYSTERY_CHANCE = 170;

    private static final int SPOOKY_CHANCE = 180;
    public CopyOnWriteArrayList<Block> setUpBoard(){
        switch (level) {
            case 1: //horizontal line
                // Layout for level 1
                for (int i = 0; i < 5; i++) {
                    int type = determineBlockType(new Random().nextInt(CHANCE_DIVISOR));
                    blocks.add(new Block(0, i, type));
                }
                break;

            case 2:// 3 vertical line
                for (int row = 0; row < 10; row++) {
                    for (int col = 0; col < 5; col += 2) {
                        int type = determineBlockType(new Random().nextInt(CHANCE_DIVISOR));
                        blocks.add(new Block(row, col,type));
                    }
                }
                break;
            case 3: //scatter
                // Layout for level 2
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if ((i + j) % 2 == 0) {
                            int type = determineBlockType(new Random().nextInt(CHANCE_DIVISOR));
                            blocks.add(new Block(i, j, type));
                        }
                    }
                }
                break;
            // ... Similarly for levels 3, 4, and 5
            case 4:// x
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        // Place blocks in positions forming an 'X'
                        if (i == j || i + j == 4) { // Diagonal from top-left to bottom-right and top-right to bottom-left
                            int type = determineBlockType(new Random().nextInt(CHANCE_DIVISOR));
                            blocks.add(new Block(i, j, type));
                        }
                    }
                }
                break;
            case 5://hollow square
                for (int row = 0; row < 7; row++) {
                    for (int col = 0; col < 5; col++) {
                        // Add blocks only on the edges, leave the inside hollow
                        if (row == 0 || row == 6 || col == 0 || col == 6) {
                            int type = determineBlockType(new Random().nextInt(CHANCE_DIVISOR));
                            blocks.add(new Block(row, col, type));
                        }
                    }
                }
                break;
            case 6://zig zag
                for (int row = 0; row < 10; row++) {
                    for (int col = 0; col < 5; col++) {
                        // Check if we are on a "zig" or "zag" row
                        if ((row % 10 < 5 && row % 5 == col) || // Zig (moving right)
                                (row % 10 >= 5 && 4 - (row % 5) == col)) { // Zag (moving left)
                            int type = determineBlockType(new Random().nextInt(CHANCE_DIVISOR));
                            blocks.add(new Block(row, col, type));
                        }
                    }
                }
                break;
            case 7:// smiley face

                // Positions for the smiley face
                int[][] smileyPositions = {
                        {1, 1}, {1, 3},  // Eyes
                        {4, 0}, {4, 1}, {4, 2}, {4, 3}, {4, 4}, {5, 2}, {6, 2} , // Smiling mouth
                        {5, 1}, {5, 3}   // Mouth edges
                };

                // Create blocks at specified positions
                for (int[] position : smileyPositions) {
                    int row = position[0];
                    int col = position[1];
                    int type = determineBlockType(new Random().nextInt(CHANCE_DIVISOR));
                    blocks.add(new Block(row, col, type));
                }

                break;
            case 8: //A
                for (int row = 0; row < 6; row++) {
                    for (int col = 0; col < 5; col++) {
                        // Conditions to create modified 'A'
                        if ((row == 3) || // Middle horizontal line
                                (row == 0 && col == 2) || // Top of 'A'
                                (row == 1 && (col == 1 || col == 3)) || // Second row modifications
                                (row == 2 && ((col == 0 && row < 4) || (col == 4 && row < 4))) || // Left and right sides of 'A'
                                (row >= 4 && (col == 0 || col == 4))) { // Stem of 'A'
                            int type = determineBlockType(new Random().nextInt(CHANCE_DIVISOR));
                            blocks.add(new Block(row, col, type));
                        }
                    }
                }

                break;

            default:
                break;
        }
        return blocks;
    }
    private int determineBlockType(int randomChance) {
        if (randomChance < CHOCO_CHANCE) {
            return Block.BLOCK_CHOCO;
        } else if (randomChance < HEART_CHANCE && !isExistHeartBlock) {
            isExistHeartBlock = true;
            return Block.BLOCK_HEART;
        } else if (randomChance < STAR_CHANCE) {
            return Block.BLOCK_STAR;
        } else if (randomChance < MYSTERY_CHANCE) {
            return Block.BLOCK_MYSTERY;
        } else if (randomChance < SPOOKY_CHANCE) {
            return Block.BLOCK_SPOOKY;
        } else {
            return Block.BLOCK_NORMAL;
        }
    }
    public boolean allBlocksDestroyed() {
        if(destroyedBlockCount == blocks.size()) {
            return true;
        }
        else{
            return false;
        }
    }


}
