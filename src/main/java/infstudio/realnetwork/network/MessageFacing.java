package infstudio.realnetwork.network;

import infstudio.realnetwork.core.NetWork;
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

public class MessageFacing implements IMessage {

    public NBTTagCompound compound;
    public int x, y, z;

    public MessageFacing() {

    }

    public MessageFacing(NBTTagCompound compound, BlockPos pos) {
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

    public static class Handler implements IMessageHandler<MessageFacing, IMessage> {

        @Override
        public IMessage onMessage(MessageFacing message, MessageContext ctx) {
            IThreadListener mainThread = ctx.side.isClient() ? Minecraft.getMinecraft() : (WorldServer) ctx.getServerHandler().player.world;
            mainThread.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    ctx.getServerHandler().player.world.getTileEntity(new BlockPos(message.x, message.y, message.z)).readFromNBT(message.compound);
                    new NetWork(ctx.getServerHandler().player.world, new BlockPos(message.x, message.y, message.z));
                }
            });
            return null;
        }

    }

}
