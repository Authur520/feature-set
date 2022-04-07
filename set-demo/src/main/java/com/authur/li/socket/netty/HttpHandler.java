package com.authur.li.socket.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;

/**
 * @author authur
 * @description:
 */
@Slf4j
public class HttpHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            String uri = fullHttpRequest.uri();
            if (uri.contains("/test")) {
                handlerTest(fullHttpRequest, ctx);
            }
        } catch (Exception ex) {
            ReferenceCountUtil.release(msg);
        }
    }

    private void handlerTest(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx) {
        FullHttpResponse response = null;
        try {
            String value = "hello,netty";
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());
        } catch (UnsupportedEncodingException e) {
            log.error("处理接口出错", e);
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
        } finally {
            if (fullHttpRequest != null) {
                if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
        }
    }

}
