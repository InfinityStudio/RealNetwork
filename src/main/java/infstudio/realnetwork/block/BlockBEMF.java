package infstudio.realnetwork.block;

import infstudio.realnetwork.tileentity.TileEntityBEMF;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBEMF extends BlockResistance {

    public BlockBEMF() {
        super();
        setName("bemf");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBEMF(3.0f, 3.0f, getName());
    }

    @Override
    public Class getTileEntity() {
        return TileEntityBEMF.class;
    }

}
