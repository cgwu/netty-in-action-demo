package me.gg.nettyinactiondemo.getstarted;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class TimeReplayingDecoder extends ReplayingDecoder<Void> { // (1)
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        System.out.println("ReplayingTimeDecoder readBytes >= 4");
        out.add(in.readBytes(8)); // (4)
    }
}

/*
 * 官方示例（很好):
 * https://netty.io/4.1/api/io/netty/handler/codec/ReplayingDecoder.html

public enum MyDecoderState {
    READ_LENGTH,
    READ_CONTENT;
}

public class IntegerHeaderFrameDecoder
        extends ReplayingDecoder<MyDecoderState> {

    private int length;

    public IntegerHeaderFrameDecoder() {
        // Set the initial state.
        super(MyDecoderState.READ_LENGTH);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx,
                          ByteBuf buf, List<Object> out) throws Exception {
        switch (state()) {
            case READ_LENGTH:
                length = buf.readInt();
                checkpoint(MyDecoderState.READ_CONTENT);
            case READ_CONTENT:
                ByteBuf frame = buf.readBytes(length);
                checkpoint(MyDecoderState.READ_LENGTH);
                out.add(frame);
                break;
            default:
                throw new Error("Shouldn't reach here.");
        }
    }
}
*/
