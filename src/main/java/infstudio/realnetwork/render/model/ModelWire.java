package infstudio.realnetwork.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelWire extends ModelBase {

    private final ModelRenderer post;
    private final ModelRenderer north;
    private final ModelRenderer south;
    private final ModelRenderer east;
    private final ModelRenderer west;
    private final ModelRenderer up;
    private final ModelRenderer down;

    public ModelWire() {
        textureWidth = 16;
        textureHeight = 16;
        post = new ModelRenderer(this);
        post.cubeList.add(new ModelBox(post, 0, 0, 6.0F, 6.0F, 6.0F, 4, 4, 4, 0.0F, false));
        north = new ModelRenderer(this);
        north.cubeList.add(new ModelBox(north, 0, 0, 6.0F, 6.0F, 10.0F, 4, 4, 6, 0.0F, false));
        south = new ModelRenderer(this);
        south.cubeList.add(new ModelBox(south, 0, 0, 6.0F, 6.0F, 0.0F, 4, 4, 6, 0.0F, false));
        east = new ModelRenderer(this);
        east.cubeList.add(new ModelBox(east, 0, 0, 10.0F, 6.0F, 6.0F, 6, 4, 4, 0.0F, false));
        west = new ModelRenderer(this);
        west.cubeList.add(new ModelBox(west, 0, 0, 0.0F, 6.0F, 6.0F, 6, 4, 4, 0.0F, false));
        up = new ModelRenderer(this);
        up.cubeList.add(new ModelBox(up, 0, 0, 6.0F, 0.0F, 6.0F, 4, 6, 4, 0.0F, false));
        down = new ModelRenderer(this);
        down.cubeList.add(new ModelBox(down, 0, 0, 6.0F, 10.0F, 6.0F, 4, 6, 4, 0.0F, false));
    }

    public void renderPost() {
        post.render(0.0625F);
    }

    public void renderNorth() {
        north.render(0.0625F);
    }

    public void renderSouth() {
        south.render(0.0625F);
    }

    public void renderEast() {
        east.render(0.0625F);
    }

    public void renderWest() {
        west.render(0.0625F);
    }

    public void renderUp() {
        up.render(0.0625F);
    }

    public void renderDown() {
        down.render(0.0625F);
    }

}
