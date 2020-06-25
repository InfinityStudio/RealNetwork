package infstudio.realnetwork.item;

import infstudio.realnetwork.RealNetwork;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemAppliance extends Item {

    protected String name;
    protected double capacity;

    public ItemAppliance() {
        super();
    }

    protected void setName(String name) {
        this.name = name;
        setMaxStackSize(1);
        setNoRepair();
        setCreativeTab(RealNetwork.rnTab);
        setTranslationKey(name);
        setRegistryName(name);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1.0D-(getEnergy(stack)/getCapacity(stack));
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return MathHelper.hsvToRGB(Math.max(0.0F, (float) (1 - getDurabilityForDisplay(stack))) / 3.0F, 1.0F, 1.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(String.format("%.1fJ/%.1fJ", this.getEnergy(stack), this.getCapacity(stack)));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!isInCreativeTab(tab)) return;
        ItemStack discharged = new ItemStack(this);
        items.add(discharged);
        ItemStack charged = new ItemStack(this);
        setEnergy(charged, ((ItemAppliance)charged.getItem()).getCapacity(charged));
        items.add(charged);
    }

    protected void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getCapacity(ItemStack stack) {
        return this.capacity;
    }

    public void setEnergy(ItemStack stack, double power) {
        NBTTagCompound compound = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        compound.setDouble("Energy", power);
        stack.setTagCompound(compound);
    }

    public double getEnergy(ItemStack stack) {
        NBTTagCompound compound = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        return compound.hasKey("Energy") ? compound.getDouble("Energy") : 0.0D;
    }

    public void incEnergy(ItemStack stack, double amount) {
        double power = getEnergy(stack)+amount, capacity = getCapacity(stack);
        if (power > capacity) power = capacity;
        setEnergy(stack, power);
    }

    public void decEnergy(ItemStack stack, double amount) {
        setEnergy(stack, getEnergy(stack)-amount);
    }

    public boolean isFull(ItemStack stack) {
        return getEnergy(stack) >= getCapacity(stack);
    }

}
