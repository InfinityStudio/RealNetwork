package infstudio.realnetwork.core;

import net.minecraft.util.math.BlockPos;

public class Node {

    private BlockPos pos;
    public int index, no;

    public Node(int index, BlockPos pos) {
        this.index = index;
        this.pos = pos;
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public boolean equals(Object obj) {
        return pos.equals(((Node)obj).getPos()) && index == ((Node)obj).index;
    }

}
