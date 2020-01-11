package infstudio.realnetwork.block;

import infstudio.realnetwork.tileentity.TileEntityResistance;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockResistance extends BlockMachineBase {

    public BlockResistance() {
        super();
        setName("resistance");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityResistance(3.0f);
    }

}
