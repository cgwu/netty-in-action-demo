package me.gg.nettyinactiondemo.msgpack_protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import me.gg.nettyinactiondemo.getstarted.UnixTime;

public class MyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        //创建三个对象
//        person p1 = new person("name1",1);
//        person p2 = new person("name2",2);
//        person p3 = new person("name3",3);
//        person p4 = new person("name4",4);
//        person p5 = new person("name5",5);
//        //write
//        ctx.write(p1);
//        ctx.write(p2);
//        ctx.write(p3);
//        ctx.write(p4);
//        ctx.write(p5);
//        //flush
//        ctx.flush();

        UnixTime time = new UnixTime();
        time.setLabel("消息头###1");
        final ChannelFuture f1 = ctx.writeAndFlush(time); // (3)

        time.setLabel("消息头###2");
        final ChannelFuture f = ctx.writeAndFlush(time); // (3)

//        f.addListener((ChannelFutureListener) future -> {
//            assert f == future;
//            ctx.close();
//        }); // (4)

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("客户端接收到消息:"+(UnixTime)msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    //创建起步程序
    public static void  main(String [] argsStrings) throws Exception {
        //配置客户端端NIO线程组
        EventLoopGroup bGroup = new NioEventLoopGroup();
        //创建客户端启动辅助类
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bGroup).
                channel(NioSocketChannel.class).
                option(ChannelOption.TCP_NODELAY, true).
                option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000).
                handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        //添加对象系列化编解码器,同时提供粘包拆包支持
                        channel.pipeline().addLast("Byte2Msg Inbound", new LengthFieldBasedFrameDecoder(
                                65535, 0, 2, 0, 2));
                        channel.pipeline().addLast("对象解码器 Msg2Msg Inbound", new MyDecoder());
                        channel.pipeline().addLast("Msg2Msg Outbound", new LengthFieldPrepender(2));
                        channel.pipeline().addLast("对象编码器 Msg2Byte Outbound", new MyEncoder());
                        channel.pipeline().addLast("Inbound", new MyClientHandler());
                    }

                });

        //发起异步连接
        ChannelFuture future = bootstrap.connect("127.0.0.1", 11111).sync();
        try {
            //等待客户端链路关闭
            future.channel().closeFuture().sync();
        } finally {
            //优雅退出,释放资源
            bGroup.shutdownGracefully();
        }


    }

}
