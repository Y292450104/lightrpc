package rpc.xp.common;

import io.netty.buffer.AbstractReferenceCountedByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundInvoker;
import io.netty.channel.ChannelOutboundInvoker;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageToByteEncoder;
import sun.nio.ch.DirectBuffer;

public class ResponseEncoder extends MessageToByteEncoder<RpcResponse> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcResponse msg, ByteBuf out) throws Exception {
        byte[] serializeToByte = SerializationUtil.serializeToByte(msg);
        out.writeInt(serializeToByte.length);
        out.writeBytes(serializeToByte);


        // 查看 cleaner 虚引用的使用
        DirectBuffer directBuffer = null;
        sun.misc.Cleaner cleaner = null;

        // 引用计数
        AbstractReferenceCountedByteBuf abstractReferenceCountedByteBuf = null;
        ByteBuf byteBuf = null;

        ChannelHandlerContext channelHandler = null;
        ChannelInboundInvoker channelInboundInvoker = null;
        ChannelOutboundInvoker channelOutboundInvoker = null;

        // channelOutboundInvoker.write()
        // ReferenceCountUtils.release(msg);
        // Unpooled.buffer();

        ChannelPipeline channelPipeline = null;
    }

}
