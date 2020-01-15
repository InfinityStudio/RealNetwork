package infstudio.realnetwork.block;

import infstudio.realnetwork.tileentity.TileEntityGeneratorBattery;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGeneratorBattery extends BlockGenerator {

    public BlockGeneratorBattery() {
        super();
        setName("generator_battery");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityGeneratorBattery(1.0D, 0.0D, 0.0D, getName());
    }

    @Override
    public Class getTileEntity() {
        return TileEntityGeneratorBattery.class;
    }

}
