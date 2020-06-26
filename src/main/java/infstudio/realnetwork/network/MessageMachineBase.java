package infstudio.realnetwork.network;

import infstudio.realnetwork.core.NetWork;
import infstudio.realnetwork.tileentity.TileEntitySwitch;
import infstudio.realnetwork.tileentity.TileEntityWireBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMachineBase implements IMessage {

    public NBTTagCompound compound;
    public int x, y, z;

    public MessageMachineBase() {

    }

    public MessageMachineBase(NBTTagCompound compound, BlockPos pos) {
        this.compound = compound;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        compound = ByteBufUtils.readTag(buf);
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, compound);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public static class Handler implements IMessageHandler<MessageMachineBase, IMessage> {

        @Override
        public IMessage onMessage(MessageMachineBase message, MessageContext ctx) {
            IThreadListener mainThread = ctx.side.isClient() ? Minecraft.getMinecraft() : (WorldServer) ctx.getServerHandler().player.world;
            mainThread.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    BlockPos pos = new BlockPos(message.x, message.y, message.z);
                    ctx.getServerHandler().player.world.getTileEntity(pos).readFromNBT(message.compound);
                    if (ctx.getServerHandler().player.world.getTileEntity(pos) instanceof TileEntitySwitch) {
                        TileEntitySwitch tile = (TileEntitySwitch)ctx.getServerHandler().player.world.getTileEntity(pos);
                        if (!tile.getStatus()) {
                            BlockPos pos0 = new BlockPos(pos.getX()+tile.getPort()[0].getDirectionVec().getX(), pos.getY()+tile.getPort()[0].getDirectionVec().getY(), pos.getZ()+tile.getPort()[0].getDirectionVec().getZ());
                            if (ctx.getServerHandler().player.world.getTileEntity(pos0) instanceof TileEntityWireBase) new NetWork(ctx.getServerHandler().player.world, pos0);
                            BlockPos pos1 = new BlockPos(pos.getX()+tile.getPort()[1].getDirectionVec().getX(), pos.getY()+tile.getPort()[1].getDirectionVec().getY(), pos.getZ()+tile.getPort()[1].getDirectionVec().getZ());
                            if (ctx.getServerHandler().player.world.getTileEntity(pos1) instanceof TileEntityWireBase) new NetWork(ctx.getServerHandler().player.world, pos1);
                        }
                    }
                    new NetWork(ctx.getServerHandler().player.world, pos);
                }
            });
            return null;
        }

    }

}
