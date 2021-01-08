package gateway.listener;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.ReferenceCountUtil;

/**
 * @author ztz
 * @description TODO
 * @date 2020/11/2 23:47
 */
public class ZGatewayListener implements ChannelFutureListener {

    private ChannelHandlerContext context;
    private Object msg;

    public ZGatewayListener(ChannelHandlerContext handlerContext, Object msg) {
        this.context = handlerContext;
        this.msg = msg;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
//            FullHttpRequest f = (FullHttpRequest) msg;
//            FullHttpRequest req = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, f.uri());
//            req.headers().set(HttpHeaders.Names.HOST, "127.0.0.1");
//            req.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
//            req.headers().set(HttpHeaders.Names.CONTENT_LENGTH, f.content().readableBytes());
            try {
                future.channel().writeAndFlush(msg);
            } catch (Exception e) {
                future.channel().close();
                throw e;
            }
//            future.channel().closeFuture();
        } else {
            context.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.REQUEST_TIMEOUT));
            context.close();
        }
    }
}
