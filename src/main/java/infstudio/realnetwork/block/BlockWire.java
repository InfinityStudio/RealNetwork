package infstudio.realnetwork.block;

import infstudio.realnetwork.tileentity.TileEntityWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWire extends BlockWireBase {

    public BlockWire() {
        super();
        setName("wire");
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityWire(0.1f);
    }

}
