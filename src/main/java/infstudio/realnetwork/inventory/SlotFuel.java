package infstudio.realnetwork.inventory;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotFuel extends SlotItemHandler {

    public SlotFuel(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return TileEntityFurnace.isItemFuel(stack) || isBucket(stack);
    }

    @Override
    public int getItemStackLimit(@Nonnull ItemStack stack) {
        return isBucket(stack) ? 1 : super.getItemStackLimit(stack);
    }

    public boolean isBucket(ItemStack stack)
    {
        return stack.getItem() == Items.BUCKET;
    }

}
