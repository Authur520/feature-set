package com.authur.li.socket.netty;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author authur
 * @description:
 */
public class HttpInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel socketChannel) {
        ChannelPipeline p = socketChannel.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        p.addLast(new HttpHandler());
    }
}
