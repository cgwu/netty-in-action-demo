package me.gg.nettyinactiondemo.msgpack_protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * Message => Byte 编码器
 * Created by danny on 2019/1/26.
 */
public class MyEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object obj, ByteBuf buf) throws Exception {
        MessagePack msgPack = new MessagePack();
        //序列化操作
        byte[] bytes = msgPack.write(obj);
        //netty操作,将对象序列化数组传入ByteBuf
        buf.writeBytes(bytes);

    }
}
