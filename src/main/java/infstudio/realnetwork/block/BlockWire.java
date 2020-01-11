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
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityWire(0.01f);
    }

}
