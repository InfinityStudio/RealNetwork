package infstudio.realnetwork.core;

import infstudio.realnetwork.block.BlockWireBase;
import infstudio.realnetwork.tileentity.*;
import infstudio.realnetwork.util.FuncSin;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class NetWork {

    private static final int dir[][] = {{0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1}, {-1, 0, 0}, {1, 0, 0}};
    private static final double EPS = 1e-8;
    
    private World worldIn;
    private BlockPos pos;
    private ArrayList<ArrayList<Node>> bcc;
    private ArrayList<Node> nodeList;
    private int cnt, clock, bccCnt, n;
    private ArrayList<Integer> dfn, low, bccID;
    private ArrayList<Boolean> vis;
    private boolean isBridge[][];
    private Stack<Node> stack;
    private Map<BlockPos, Integer> map;
    private Graph G1, G2;
    private double matrix[][];
    private FuncSin b[];
    private boolean bccPositive[];

    public NetWork(World worldIn, BlockPos pos) {
        if (!worldIn.isRemote) {
            this.worldIn = worldIn;
            this.pos = pos;
            initNetWork();
        }
    }

    private EnumFacing getFaceByIndex(int index) {
        switch (index) {
            case 0: return EnumFacing.DOWN;
            case 1: return EnumFacing.UP;
            case 2: return EnumFacing.NORTH;
            case 3: return EnumFacing.SOUTH;
            case 4: return EnumFacing.WEST;
            case 5: return EnumFacing.EAST;
        }
        return EnumFacing.NORTH;
    }

    private void initGraph(Node u) {
        ((TileEntityWireBase)worldIn.getTileEntity(u.getPos())).setPhi(new FuncSin[] {new FuncSin(), new FuncSin(), new FuncSin(), new FuncSin(), new FuncSin(), new FuncSin(), new FuncSin()});
        worldIn.notifyBlockUpdate(u.getPos(), worldIn.getBlockState(u.getPos()), worldIn.getBlockState(u.getPos()), 2);
        if (worldIn.getTileEntity(u.getPos()) instanceof TileEntityWire) {
            for (int i = 0; i < 6; ++i) {
                BlockPos vPos = new BlockPos(u.getPos().getX()+dir[i][0], u.getPos().getY()+dir[i][1], u.getPos().getZ()+dir[i][2]);
                Block vBlock = worldIn.getBlockState(vPos).getBlock();
                if (!(vBlock instanceof BlockWireBase)) continue;
                if (worldIn.getTileEntity(vPos) instanceof TileEntityMachineBase) {
                    TileEntityMachineBase vTile = (TileEntityMachineBase)worldIn.getTileEntity(vPos);
                    boolean flag = false;
                    for (int j = 0; j < 2; ++j) {
                        if (u.getPos().getX() == vPos.getX()+vTile.getPort()[j].getDirectionVec().getX() && u.getPos().getY() == vPos.getY()+vTile.getPort()[j].getDirectionVec().getY() && u.getPos().getZ() == vPos.getZ()+vTile.getPort()[j].getDirectionVec().getZ()) flag = true;
                    }
                    if (!flag) continue;
                }
                Node v;
                if (map.get(vPos) == null) {
                    v = new Node(++cnt, vPos);
                    map.put(vPos, cnt);
                    G1.addEdge(u, v, 0, getFaceByIndex(i));
                    initGraph(v);
                } else {
                    v = new Node(map.get(vPos), vPos);
                    G1.addEdge(u, v, 0, getFaceByIndex(i));
                }
            }
        } else if (worldIn.getTileEntity(u.getPos()) instanceof TileEntityMachineBase) {
            TileEntityMachineBase uTile = (TileEntityMachineBase)worldIn.getTileEntity(u.getPos());
            for (int i = 0; i < 2; ++i) {
                BlockPos vPos = new BlockPos(u.getPos().getX()+uTile.getPort()[i].getDirectionVec().getX(), u.getPos().getY()+uTile.getPort()[i].getDirectionVec().getY(), u.getPos().getZ()+uTile.getPort()[i].getDirectionVec().getZ());
                Block vBlock = worldIn.getBlockState(vPos).getBlock();
                if (!(vBlock instanceof BlockWireBase)) continue;
                if (worldIn.getTileEntity(vPos) instanceof TileEntityMachineBase) {
                    TileEntityMachineBase vTile = (TileEntityMachineBase)worldIn.getTileEntity(vPos);
                    boolean flag = false;
                    for (int j = 0; j < 2; ++j) {
                        if (u.getPos().getX() == vPos.getX()+vTile.getPort()[j].getDirectionVec().getX() && u.getPos().getY() == vPos.getY()+vTile.getPort()[j].getDirectionVec().getY() && u.getPos().getZ() == vPos.getZ()+vTile.getPort()[j].getDirectionVec().getZ()) flag = true;
                    }
                    if (!flag) continue;
                }
                Node v;
                if (map.get(vPos) == null) {
                    v = new Node(++cnt, vPos);
                    map.put(vPos, cnt);
                    G1.addEdge(u, v, 0, uTile.getPort()[i]);
                    initGraph(v);
                } else {
                    v = new Node(map.get(vPos), vPos);
                    G1.addEdge(u, v, 0, uTile.getPort()[i]);
                }
            }
        }
    }

    private void expandGraph(Node u) {
        while (vis.size() <= u.index) vis.add(false);
        vis.set(u.index, true);
        Node uc = new Node(u.index*7+6, u.getPos());
        nodeList.add(uc);
        if (G1.edges.size() <= u.index) return;
        double R = ((TileEntityWireBase) worldIn.getTileEntity(u.getPos())).getResistance()/G1.edges.get(u.index).size();
        for (int i = 0; i < G1.edges.get(u.index).size(); ++i) {
            Edge e = G1.edges.get(u.index).get(i);
            Node ui = new Node(u.index*7+e.facing.getIndex(), u.getPos());
            if (!nodeList.contains(ui)) nodeList.add(ui);
            G2.addEdge(uc, ui, R, e.facing);
            G2.addEdge(ui, uc, R, e.facing.getOpposite());
            while (vis.size() <= e.v.index) vis.add(false);
            Node vi = new Node(e.v.index*7+e.facing.getOpposite().getIndex(), e.v.getPos());
            if (!nodeList.contains(vi)) nodeList.add(vi);
            G2.addEdge(ui, vi, EPS, e.facing);
            if (!vis.get(G1.edges.get(u.index).get(i).v.index)) {
                expandGraph(G1.edges.get(u.index).get(i).v);
            }
        }
    }

    private void tarjan(Node u, Node par) {
        while (dfn.size() <= u.index) dfn.add(0);
        while (low.size() <= u.index) low.add(0);
        while (G2.edges.size() <= u.index) G2.edges.add(new ArrayList<>());
        clock++;
        dfn.set(u.index, clock);
        low.set(u.index, clock);
        for (int i = 0; i < G2.edges.get(u.index).size(); ++i) {
            Node v = G2.edges.get(u.index).get(i).v;
            while (dfn.size() <= v.index) dfn.add(0);
            while (low.size() <= v.index) low.add(0);
            if (dfn.get(v.index) == 0) {
                tarjan(v, u);
                low.set(u.index, Math.min(low.get(u.index), low.get(v.index)));
                if (low.get(v.index) > dfn.get(u.index)) {
                    isBridge[nodeList.indexOf(u)][nodeList.indexOf(v)] = isBridge[nodeList.indexOf(v)][nodeList.indexOf(u)] = true;
                }
            } else if (dfn.get(v.index) < dfn.get(u.index) && v.index != par.index) {
                low.set(u.index, Math.min(low.get(u.index), dfn.get(v.index)));
            }
        }
    }

    private void dfs(Node u) {
        while (vis.size() <= u.index) vis.add(false);
        while (bccID.size() <= u.index) bccID.add(-1);
        vis.set(u.index, true);
        bcc.get(bccCnt).add(u);
        bccID.set(u.index, bccCnt);
        for (int i = 0; i < G2.edges.get(u.index).size(); ++i) {
            Node v = G2.edges.get(u.index).get(i).v;
            if (isBridge[nodeList.indexOf(u)][nodeList.indexOf(v)] || isBridge[nodeList.indexOf(v)][nodeList.indexOf(u)]) continue;
            while (vis.size() <= v.index) vis.add(false);
            if (!vis.get(v.index)) {
                dfs(v);
            }
        }
    }

    private void initMatrix(int bccNum) {
        matrix = new double[n][n];
        b = new FuncSin[n];
        for (int i = 0; i < b.length; ++i) {
            b[i] = new FuncSin();
        }
        bccPositive = new boolean[bccCnt+1];
        for (int p = 0; p < n; ++p) {
            Node u = nodeList.get(p);
            if (bccID.get(u.index) != bccNum) continue;
            if (bcc.get(bccID.get(u.index)).size() == 1) {
                matrix[u.no][u.no] = 1;
            } else if (worldIn.getTileEntity(u.getPos()) instanceof TileEntityGenerator) {
                TileEntityGenerator tile = (TileEntityGenerator)worldIn.getTileEntity(u.getPos());
                EnumFacing port[] = tile.getPort();
                if (u.index%7 == port[0].getIndex()) {
                    matrix[u.no][u.no] = 1;
                    for (int i = 0; i < G2.edges.get(u.index).size(); ++i) {
                        Node v = G2.edges.get(u.index).get(i).v;
                        if (v.getPos().equals(u.getPos()) && v.index%7 == 6) {
                            matrix[u.no][nodeList.indexOf(v)] = -1;
                            break;
                        }
                    }
                    b[u.no] = new FuncSin(tile.getE());
                } else if (u.index%7 == port[1].getIndex()) {
                    for (int i = 0; i < G2.edges.get(u.index).size(); ++i) {
                        Edge e = G2.edges.get(u.index).get(i);
                        Node v = e.v;
                        if (bccID.get(v.index) != bccID.get(u.index)) continue;
                        if (u.getPos().equals(v.getPos())) {
                            matrix[u.no][u.no] += 1.0f/tile.getResistance();
                            matrix[u.no][nodeList.indexOf(v)] -= 1.0f/tile.getResistance();
                        } else {
                            matrix[u.no][u.no] += 1.0f/e.w;
                            matrix[u.no][nodeList.indexOf(v)] -= 1.0f/e.w;
                        }
                    }
                } else {
                    if (!bccPositive[bccID.get(u.index)]) {
                        bccPositive[bccID.get(u.index)] = true;
                        matrix[u.no][u.no] = 1;
                    } else {
                        for (int i = 0; i < G2.edges.get(u.index).size(); ++i) {
                            Edge e = G2.edges.get(u.index).get(i);
                            Node v = e.v;
                            if (bccID.get(v.index) != bccID.get(u.index)) continue;
                            if (v.getPos().equals(u.getPos()) && v.index%7 == port[1].getIndex()) {
                                matrix[u.no][u.no] += 1.0f/tile.getResistance();
                                matrix[u.no][nodeList.indexOf(v)] -= 1.0f/tile.getResistance();
                            } else {
                                for (int j = 0; j < G2.edges.get(v.index).size(); ++j) {
                                    Edge ee = G2.edges.get(v.index).get(j);
                                    Node vv = ee.v;
                                    if (u.getPos().equals(vv.getPos())) continue;
                                    if (bccID.get(v.index) != bccID.get(vv.index)) continue;
                                    matrix[u.no][nodeList.indexOf(v)] += 1.0f/ee.w;
                                    matrix[u.no][nodeList.indexOf(vv)] -= 1.0f/ee.w;
                                }
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < G2.edges.get(u.index).size(); ++i) {
                    Edge e = G2.edges.get(u.index).get(i);
                    Node v = e.v;
                    if (bccID.get(v.index) != bccID.get(u.index)) continue;
                    matrix[u.no][u.no] += 1.0f/e.w;
                    matrix[u.no][nodeList.indexOf(v)] -= 1.0f/e.w;
                }
            }
        }
    }

    private boolean gauss() {
        for (int i = 0, r; i < n; ++i) {
            r = i;
            for (int j = i+1; j < n; ++j) r = Math.abs(matrix[j][i]) > Math.abs(matrix[r][i]) ? j : r;
            if (Math.abs(matrix[r][i]) < EPS) {
                if (b[r].isZero()) return false;
                else continue;
            }
            if (r != i) {
                for (int j = 0; j < n; ++j) {
                    double temp = matrix[i][j];
                    matrix[i][j] = matrix[r][j];
                    matrix[r][j] = temp;
                }
                FuncSin temp = b[i];
                b[i] = b[r];
                b[r] = temp;
            }
            for (int j = 0; j < n; ++j) {
                if (j != i) {
                    double x = matrix[j][i]/matrix[i][i];
                    for (int k = 0; k < n; ++k) matrix[j][k] -= x*matrix[i][k];
                    b[j].add(FuncSin.multiply(x, b[i]).getOpposite());
                }
            }
        }
        for (int i = 0; i < n; ++i) {
            if (Math.abs(matrix[i][i]) > EPS) {
                b[i].multiply(1.0D/matrix[i][i]);
            }
        }
        return true;
    }

    private void initNetWork() {
        vis = new ArrayList<>();
        nodeList = new ArrayList<>();
        dfn = new ArrayList<>();
        low = new ArrayList<>();
        bccID = new ArrayList<>();
        stack = new Stack<>();
        bcc = new ArrayList<>();
        map = new HashMap<BlockPos, Integer>();
        G1 = new Graph();
        G2 = new Graph();
        cnt = 0;
        clock = 0;
        bccCnt = -1;
        map.put(pos, cnt);
        Node u = new Node(cnt, pos);
        initGraph(u);
        expandGraph(u);
        n = nodeList.size();
        isBridge = new boolean[n][n];
        for (int i = 0; i < n; ++i) {
            nodeList.get(i).no = i;
            while (dfn.size() <= nodeList.get(i).index) dfn.add(0);
            if (dfn.get(nodeList.get(i).index) == 0) {
                tarjan(nodeList.get(i), new Node(-1, null));
            }
        }
        vis.clear();
        for (int i = 0; i < n; ++i) {
            while (vis.size() <= nodeList.get(i).index) vis.add(false);
            if (!vis.get(nodeList.get(i).index)) {
                bccCnt++;
                bcc.add(new ArrayList<>());
                dfs(nodeList.get(i));
            }
        }
        for (int bccNum = 0; bccNum <= bccCnt; ++bccNum) {
            initMatrix(bccNum);
            if (gauss()) for (int i = 0; i < n; ++i) {
                u = nodeList.get(i);
                if (bccID.get(u.index) != bccNum) continue;
                TileEntityWireBase tile = (TileEntityWireBase)worldIn.getTileEntity(u.getPos());
                tile.setPhi(b[i], u.index%7);
                worldIn.notifyBlockUpdate(u.getPos(), worldIn.getBlockState(u.getPos()), worldIn.getBlockState(u.getPos()), 2);
            }
        }
    }

}
