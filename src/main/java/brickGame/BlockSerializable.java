package brickGame;

import java.io.Serializable;

public class BlockSerializable implements Serializable {
    public final int row;
    public final int j;
    public final int type;

    public int direction;

    public BlockSerializable(int row , int j , int type ,  int direction) {
        this.row = row;
        this.j = j;
        this.type = type;
        this.direction = direction;
    }
}
