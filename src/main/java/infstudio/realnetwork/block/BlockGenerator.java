package infstudio.realnetwork.block;

import infstudio.realnetwork.tileentity.TileEntityGenerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGenerator extends BlockWireBase {

    public BlockGenerator() {
        super();
        setName("generator");
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityGenerator(0.1f, 6.0f);
    }

}
