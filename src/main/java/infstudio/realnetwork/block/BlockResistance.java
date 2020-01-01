package infstudio.realnetwork.block;

import infstudio.realnetwork.tileentity.TileEntityResistance;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockResistance extends BlockWireBase {

    public BlockResistance() {
        super();
        setName("resistance");
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityResistance(3.0f);
    }

}
