package com.kong.gateway.inbound;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.Data;

import java.util.List;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/11 20:39
 * @Description:
 */
@Data
public class HttpInboundServer {
	private int port;
	private List<String> proxyServer;

	public HttpInboundServer(int port, List<String> proxyServer) {
		this.port = port;
		this.proxyServer = proxyServer;
	}

	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workGroup = new NioEventLoopGroup(8);

		try {
			ServerBootstrap server = new ServerBootstrap();
			server.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.TCP_NODELAY, true)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.childOption(ChannelOption.SO_REUSEADDR, true)
					.childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
					.childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
					.childOption(EpollChannelOption.SO_REUSEPORT, true)
					.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
			server.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.DEBUG))
					.childHandler(new HttpInboundInitializer(this.proxyServer));
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
}
