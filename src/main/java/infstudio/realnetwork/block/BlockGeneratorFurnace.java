package infstudio.realnetwork.block;

import infstudio.realnetwork.tileentity.TileEntityGeneratorFurnace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGeneratorFurnace extends BlockGenerator {

    public BlockGeneratorFurnace() {
        super();
        setName("generator_furnace");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityGeneratorFurnace(0.1D, 6.0D, 0.0D, getName());
    }

}
