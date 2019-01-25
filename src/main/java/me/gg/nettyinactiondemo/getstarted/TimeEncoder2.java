package me.gg.nettyinactiondemo.getstarted;

import io.netty.buffer.ByteBuf;
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
        out.writeBytes(m.getLabel().getBytes(CharsetUtil.UTF_8));
        out.writeInt((int)m.value());
    }

}