package gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class DefaultHttpRequestFilter extends AbstractHttpRequestFilter implements HttpRequestFilter{

    @Override
    public void doFilter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().set("GaLiLiGeiGei", "BoomBoomBoom");
    }
}
