package rpc.xp.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ResponseDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 如果小于则说明是半包读取，需要等到下次在读取
        if (in.readableBytes() < 4) {
            return;
        }

        // 如果小于数据包的长度，则重置到最初，等待下次处理，半包处理
        int index = in.readerIndex();
        int readInt = in.readInt();
        if (in.readableBytes() < readInt) {
            in.readerIndex(index);
            return;
        }

        // 读取当前包长度的字节数组，粘包的处理
        byte[] bytes = new byte[readInt];
        in.readBytes(bytes);
        RpcResponse response = SerializationUtil.deserializeFromByte(bytes, RpcResponse.class);
        out.add(response);
    }

}
