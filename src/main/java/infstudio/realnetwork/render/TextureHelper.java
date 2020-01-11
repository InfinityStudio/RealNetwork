package infstudio.realnetwork.render;

import infstudio.realnetwork.block.*;
import infstudio.realnetwork.tileentity.*;

public class TextureHelper {

    public TextureHelper() {

    }

    public String getNameFromTileEntity(TileEntityMachineBase tile) {
        if (tile instanceof TileEntityGenerator) {
            return new BlockGenerator().getName();
        } else if (tile instanceof TileEntityAmmeter) {
            return new BlockAmmeter().getName();
        } else if (tile instanceof TileEntityVoltmeter) {
            return new BlockVoltmeter().getName();
        } else if (tile instanceof TileEntityResistance) {
            return new BlockResistance().getName();
        }
        return "?";
    }

}
