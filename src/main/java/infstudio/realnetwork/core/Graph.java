package infstudio.realnetwork.core;

import net.minecraft.util.EnumFacing;

import java.util.ArrayList;

public class Graph {

    public ArrayList<Node> nodes;
    public ArrayList<ArrayList<Edge>> edges;

    public Graph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addEdge(Node u, Node v, double w, EnumFacing facing) {
        while (edges.size() <= u.index) edges.add(new ArrayList<>());
        edges.get(u.index).add(new Edge(u, v, w, facing));
    }

}
