package infstudio.realnetwork.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelMachineBase extends ModelBase {

    public ModelRenderer base;
    public ModelRenderer front;

    public ModelMachineBase() {
        textureWidth = 64;
        textureHeight = 32;
        base = new ModelRenderer(this);
        base.cubeList.add(new ModelBox(base, 0, 0, 0.0F, 0.0F, 1.0F, 16, 16, 15, 0.0F, false));
        front = new ModelRenderer(this);
        front.cubeList.add(new ModelBox(front, 0, 0, 0.0F, 0.0F, 0.0F, 16, 16, 1, 0.0F, false));
    }

    public void renderBase() {
        base.render(0.0625F);
    }

    public void renderFront() {
        front.render(0.0625F);
    }

}
