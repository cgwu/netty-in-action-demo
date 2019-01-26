package me.gg.nettyinactiondemo.getstarted;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * Created by danny on 2019/1/25.
 */
public class TimeEncoder2 extends MessageToByteEncoder<UnixTime> {

    @Override
    protected void encode(ChannelHandlerContext ctx, UnixTime m, ByteBuf out) throws Exception {
        System.out.println("TimeEncoder#2: " + m);
        System.out.println("Label len: "+m.getLabel().getBytes(CharsetUtil.US_ASCII).length);
        out.writeBytes(m.getLabel().getBytes(CharsetUtil.US_ASCII));
        out.writeInt((int)m.value());
        // 写入消息分隔符
        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes(CharsetUtil.UTF_8));
        out.writeBytes(delimiter);
    }

}