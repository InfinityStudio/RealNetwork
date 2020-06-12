package infstudio.realnetwork.block;

import infstudio.realnetwork.tileentity.TileEntityMachineBase;
import infstudio.realnetwork.tileentity.TileEntityWire;
import infstudio.realnetwork.tileentity.TileEntityWireBase;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockWire extends BlockWireBase {

    protected static final AxisAlignedBB PILLAR_AABB = new AxisAlignedBB(0.375D, 0.375D, 0.375D, 0.625D, 0.625D, 0.625D);
    protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.375D, 0.375D, 0.625D, 0.625D, 0.625D, 1.0D);
    protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.375D, 0.375D, 0.375D, 0.625D, 0.625D);
    protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 0.375D);
    protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.625D, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D);
    protected static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.375D, 0.625D, 0.375D, 0.625D, 1.0D, 0.625D);
    protected static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.375D, 0.625D);

    public BlockWire() {
        super();
        setName("wire");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityWire(1e-8, getName());
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    public boolean canConnect(World world, BlockPos pos, BlockPos neighbor, EnumFacing facing) {
        if (world.getTileEntity(neighbor) instanceof TileEntityWireBase) {
            if (world.getTileEntity(neighbor) instanceof TileEntityMachineBase) {
                TileEntityMachineBase tile = (TileEntityMachineBase)world.getTileEntity(neighbor);
                return facing.equals(tile.getPort()[0].getOpposite()) || facing.equals(tile.getPort()[1].getOpposite());
            } else return true;
        }
        return false;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, PILLAR_AABB);
        if (canConnect(worldIn, pos, pos.north(), EnumFacing.NORTH)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_AABB);
        }
        if (canConnect(worldIn, pos, pos.south(), EnumFacing.SOUTH)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_AABB);
        }
        if (canConnect(worldIn, pos, pos.east(), EnumFacing.EAST)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_AABB);
        }
        if (canConnect(worldIn, pos, pos.west(), EnumFacing.WEST)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_AABB);
        }
        if (canConnect(worldIn, pos, pos.up(), EnumFacing.UP)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, UP_AABB);
        }
        if (canConnect(worldIn, pos, pos.down(), EnumFacing.DOWN)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, DOWN_AABB);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.25D, 0.25D, 0.25D, 0.75D, 0.75D, 0.75D);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public Class getTileEntity() {
        return TileEntityWire.class;
    }

}
