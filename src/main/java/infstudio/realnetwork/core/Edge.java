package infstudio.realnetwork.core;

import net.minecraft.util.EnumFacing;

public class Edge {

    public Node u, v;
    public double w;
    public EnumFacing facing;

    public Edge(Node u, Node v, double w, EnumFacing facing) {
        this.u = u;
        this.v = v;
        this.w = w;
        this.facing = facing;
    }

}
