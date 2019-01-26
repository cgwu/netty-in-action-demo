package me.gg.nettyinactiondemo.msgpack_protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import me.gg.nettyinactiondemo.getstarted.UnixTime;

@Sharable
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)  throws Exception {
        UnixTime ut = (UnixTime) msg;
        //直接获取request的msg
        System.out.println("服务器接收到消息:" + ut);

        ut.setLabel(ut.getLabel() + "$$$");
        final ChannelFuture f1 = ctx.writeAndFlush(ut); // (3)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    //创建起步程序
    public static void  main(String [] argsStrings) throws Exception {
        //配置服务端NIO线程组(boss线程、worker线程)
        EventLoopGroup bGroup = new NioEventLoopGroup();
        EventLoopGroup wGroup = new NioEventLoopGroup();
        //创建启动辅助类
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bGroup, wGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        //添加对象系列化编解码器,同时提供粘包拆包支持
                        channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                        channel.pipeline().addLast("对象解码器", new MyDecoder());
                        channel.pipeline().addLast(new LengthFieldPrepender(2));
                        channel.pipeline().addLast("对象编码器", new MyEncoder());
                        channel.pipeline().addLast(new MyServerHandler());
                    }

                });

        try {
            //监听本地端口,同步等待监听结果
            ChannelFuture future = bootstrap.bind(11111).sync();
            //等待服务端监听端口关闭,优雅退出
            future.channel().closeFuture().sync();
        } finally {
            bGroup.shutdownGracefully();
            wGroup.shutdownGracefully();
        }


    }
}
