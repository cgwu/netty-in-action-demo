package me.gg.nettyinactiondemo.ch12websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatcher;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * Listing 12.2 Handling text frames
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
@Slf4j
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final ChannelGroup group;

    public TextWebSocketFrameHandler(ChannelGroup group) {
        this.group = group;
    }

/*
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            ctx.pipeline().remove(HttpRequestHandler.class);
            group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + " joined"));
            group.add(ctx.channel());
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
*/

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            String requestUri = ((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestUri(); //请求路径: /ws?id=1
            String idVal = WebUtil.getSingleParam(requestUri,"id");
            ctx.channel().attr(AttributeKey.valueOf("id")).set(idVal);

            ctx.pipeline().remove(HttpRequestHandler.class);
            group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + " joined"));
            group.add(ctx.channel());
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("服务器接收到消息: {}", msg.text());
//        group.writeAndFlush(msg.retain());

/*        group.writeAndFlush(msg.retain(), new ChannelMatcher() {
            @Override
            public boolean matches(Channel channel) {
                return "123".equals(channel.attr(AttributeKey.valueOf("id")).get());
            }
        });*/

        String text = msg.text();
        if(text.indexOf(':') != -1){
            String id = text.substring(0, text.indexOf(':'));
            group.writeAndFlush(msg.retain(),
                    (Channel channel) ->
                            id.equals(channel.attr(AttributeKey.valueOf("id")).get())
            );
        }
        else {
            group.writeAndFlush(msg.retain());  // 发送所有
        }

    }

}
