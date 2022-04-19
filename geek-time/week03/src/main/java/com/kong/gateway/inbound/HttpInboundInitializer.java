package com.kong.gateway.inbound;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.List;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/11 20:58
 * @Description:
 */
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {
	private List<String> proxyServer;
	public HttpInboundInitializer(List<String> proxyServer) {
		this.proxyServer = proxyServer;
	}

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline p = socketChannel.pipeline();

		p.addLast(new HttpServerCodec());
		p.addLast(new HttpObjectAggregator(1024 * 1024));
		p.addLast(new HttpInboundHandler(this.proxyServer));
	}
}
