package com.kong.gateway.inbound;

import com.kong.gateway.filter.HeaderHttpRequestFilter;
import com.kong.gateway.filter.HttpRequestFilter;
import com.kong.gateway.outbound.httpclient.HttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/11 21:03
 * @Description:
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

	private static final Logger loger = LoggerFactory.getLogger(HttpInboundHandler.class);
	private final List<String> proxyServer;
	private HttpOutboundHandler handler;
	private HttpRequestFilter filter = new HeaderHttpRequestFilter();
	public HttpInboundHandler(List<String> proxyServer) {
		this.proxyServer= proxyServer;
		this.handler = new HttpOutboundHandler(proxyServer);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		try {
			FullHttpRequest fullRequest = (FullHttpRequest) msg;

			handler.handle(fullRequest, ctx, filter);

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}
}
