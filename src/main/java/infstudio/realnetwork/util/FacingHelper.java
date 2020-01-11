package infstudio.realnetwork.util;

import net.minecraft.util.EnumFacing;

public class FacingHelper {

    private EnumFacing standard;

    public FacingHelper(EnumFacing standard) {
        this.standard = standard;
    }

    public EnumRelativeFacing getRelative(EnumFacing facing) {
        switch (standard) {
            case NORTH: {
                switch (facing) {
                    case SOUTH: return EnumRelativeFacing.BACK;
                    case EAST: return EnumRelativeFacing.RIGHT;
                    case WEST: return EnumRelativeFacing.LEFT;
                    case UP: return EnumRelativeFacing.TOP;
                    case DOWN: return EnumRelativeFacing.BOTTOM;
                }
            }
            case SOUTH: {
                switch (facing) {
                    case NORTH: return EnumRelativeFacing.BACK;
                    case EAST: return EnumRelativeFacing.LEFT;
                    case WEST: return EnumRelativeFacing.RIGHT;
                    case UP: return EnumRelativeFacing.TOP;
                    case DOWN: return EnumRelativeFacing.BOTTOM;
                }
            }
            case EAST: {
                switch (facing) {
                    case NORTH: return EnumRelativeFacing.LEFT;
                    case SOUTH: return EnumRelativeFacing.RIGHT;
                    case WEST: return EnumRelativeFacing.BACK;
                    case UP: return EnumRelativeFacing.TOP;
                    case DOWN: return EnumRelativeFacing.BOTTOM;
                }
            }
            case WEST: {
                switch (facing) {
                    case NORTH: return EnumRelativeFacing.RIGHT;
                    case SOUTH: return EnumRelativeFacing.LEFT;
                    case EAST: return EnumRelativeFacing.BACK;
                    case UP: return EnumRelativeFacing.TOP;
                    case DOWN: return EnumRelativeFacing.BOTTOM;
                }
            }
        }
        return  null;
    }

    public EnumFacing getFacing(EnumRelativeFacing reFacing) {
        switch (standard) {
            case NORTH: {
                switch (reFacing) {
                    case BACK: return EnumFacing.SOUTH;
                    case LEFT: return EnumFacing.WEST;
                    case RIGHT: return EnumFacing.EAST;
                    case TOP: return EnumFacing.UP;
                    case BOTTOM: return EnumFacing.DOWN;
                }
            }
            case SOUTH: {
                switch (reFacing) {
                    case BACK: return EnumFacing.NORTH;
                    case LEFT: return EnumFacing.EAST;
                    case RIGHT: return EnumFacing.WEST;
                    case TOP: return EnumFacing.UP;
                    case BOTTOM: return EnumFacing.DOWN;
                }
            }
            case EAST: {
                switch (reFacing) {
                    case BACK: return EnumFacing.WEST;
                    case LEFT: return EnumFacing.NORTH;
                    case RIGHT: return EnumFacing.SOUTH;
                    case TOP: return EnumFacing.UP;
                    case BOTTOM: return EnumFacing.DOWN;
                }
            }
            case WEST: {
                switch (reFacing) {
                    case BACK: return EnumFacing.EAST;
                    case LEFT: return EnumFacing.SOUTH;
                    case RIGHT: return EnumFacing.NORTH;
                    case TOP: return EnumFacing.UP;
                    case BOTTOM: return EnumFacing.DOWN;
                }
            }
        }
        return null;
    }

}
