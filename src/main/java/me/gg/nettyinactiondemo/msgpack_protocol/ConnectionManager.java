package me.gg.nettyinactiondemo.msgpack_protocol;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import me.gg.nettyinactiondemo.getstarted.UnixTime;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by danny on 2019/1/26.
 */
public abstract class ConnectionManager {
    private static Map<ChannelId,Channel> ConnectionMap = new ConcurrentHashMap<ChannelId, Channel>();

    public static void add(Channel channel){
        if(!ConnectionMap.containsKey(channel.id()))
            ConnectionMap.put(channel.id(), channel);
    }

    public static void write(UnixTime ut){
        for(Channel ch: ConnectionMap.values()){
            ch.writeAndFlush(ut);
        }
    }

}
