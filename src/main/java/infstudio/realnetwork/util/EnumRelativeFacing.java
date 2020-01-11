package infstudio.realnetwork.util;

import net.minecraft.util.IStringSerializable;

public enum EnumRelativeFacing implements IStringSerializable {
    LEFT(0, "left"),
    RIGHT(1, "right"),
    TOP(2, "top"),
    BOTTOM(3, "bottom"),
    BACK(4, "back");

    private final int index;
    private final String name;

    private EnumRelativeFacing(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String getName() {
        return name;
    }

}
