package gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

public abstract class AbstractHttpRequestFilter implements HttpRequestFilter{

    protected HttpRequestFilter next;

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        doFilter(fullRequest, ctx);
        if (this.next != null) {
            this.next.filter(fullRequest, ctx);
        }
    }

    public abstract void doFilter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);
}
