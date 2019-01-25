package me.gg.nettyinactiondemo.getstarted;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

/**
 * Created by danny on 2019/1/25.
 */
public class TimeEncoder extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        UnixTime m = (UnixTime) msg;
        System.out.println("TimeEncoder#1: " + m);
        ByteBuf encoded = ctx.alloc().buffer(8);
        encoded.writeBytes(m.getLabel().getBytes(CharsetUtil.UTF_8));
        encoded.writeInt((int)m.value());
        ctx.write(encoded, promise); // (1)
    }
}