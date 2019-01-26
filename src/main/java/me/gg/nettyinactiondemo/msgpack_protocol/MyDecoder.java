package me.gg.nettyinactiondemo.msgpack_protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import me.gg.nettyinactiondemo.getstarted.UnixTime;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * Created by danny on 2019/1/26.
 */
public class MyDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> objs) throws Exception {
        final byte[] bytes;
        final int length = buf.readableBytes();
        bytes = new byte[length];
        //从数据包buf中获取要操作的byte数组
        buf.getBytes(buf.readerIndex(), bytes, 0, length);
        //将bytes反序列化成对象,并添加到解码列表中
        MessagePack msgpack = new MessagePack();
        objs.add(msgpack.read(bytes, UnixTime.class)); // List<Value>
    }

}
