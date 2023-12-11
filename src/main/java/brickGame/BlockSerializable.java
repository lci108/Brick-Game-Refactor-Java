package brickGame;

import java.io.Serializable;
/**
 * The BlockSerializable class represents a serializable version of a block in the brick game.
 * It is used to serialize and deserialize block information.
 */
public class BlockSerializable implements Serializable {
    /**
     * Represents the row position of the block.
     */
    public final int row;

    /**
     * Represents the j value.
     */
    public final int j;

    /**
     * Represents the type of the block.
     */
    public final int type;

    /**
     * Represents the direction of the block.
     */
    public int direction;
    /**
     * Constructs a BlockSerializable object with the specified row, column, type, and direction.
     *
     * @param row       The row of the block.
     * @param j         The column of the block.
     * @param type      The type of the block.
     * @param direction The direction of the block.
     */

    public BlockSerializable(int row , int j , int type ,  int direction) {
        this.row = row;
        this.j = j;
        this.type = type;
        this.direction = direction;
    }
}
