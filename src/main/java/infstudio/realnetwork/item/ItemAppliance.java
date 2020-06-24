package infstudio.realnetwork.item;

import infstudio.realnetwork.RealNetwork;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemAppliance extends Item {

    protected String name;

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

    protected void setCapacity(int capacity) {
        this.setMaxDamage(capacity);
    }

    public int getCapacity(ItemStack stack) {
        return this.getMaxDamage(stack);
    }

    public void setPower(ItemStack stack, int power) {
        this.setDamage(stack, power);
    }

    public int getPower(ItemStack stack) {
        return this.getDamage(stack);
    }

    public void incPower(ItemStack stack, int amount) {
        int power = getPower(stack)+amount, capacity = getCapacity(stack);
        if (power > capacity) power = capacity;
        setPower(stack, power);
    }

    public void decPower(ItemStack stack, int amount) {
        setPower(stack, getPower(stack)-amount);
    }

}
