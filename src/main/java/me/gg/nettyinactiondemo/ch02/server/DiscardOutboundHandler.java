package me.gg.nettyinactiondemo.ch02.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by danny on 2019/1/23.
 */
@ChannelHandler.Sharable
public class DiscardOutboundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("DiscardOutboundHandler write called");
        ctx.writeAndFlush(msg);
//        ((ByteBuf)msg).retain();
        ReferenceCountUtil.release(msg);
        promise.setSuccess();
    }

}
