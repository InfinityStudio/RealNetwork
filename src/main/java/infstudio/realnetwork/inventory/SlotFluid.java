package infstudio.realnetwork.inventory;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotFluid extends SlotItemHandler {

    public SlotFluid(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        if (stack.getItem().equals(Items.WATER_BUCKET)) return true;
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            FluidStack fluid = FluidStack.loadFluidStackFromNBT(tag);
            if (fluid != null) {
                if (fluid.getFluid().equals(FluidRegistry.WATER)) return true;
                else return false;
            } else return false;
        }
        return false;
    }

}
