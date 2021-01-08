package gateway.route;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;

/**
 * @author ztz
 * @description TODO
 * @date 2020/11/4 21:06
 */
public class RouterExecute extends ChannelInboundHandlerAdapter{
    private List<String> endPointHost;

    private RouteStrategy strategy;

    private HttpEndpointRouter currentRouter;

    public RouterExecute(RouteStrategy strategy, List<String> endPointHost) {
        this.strategy = strategy;
        this.endPointHost = endPointHost;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (currentRouter == null) {
            currentRouter = selectRouter();
        }
        FullHttpRequest request = (FullHttpRequest) msg;
        String host = currentRouter.route(this.endPointHost);
        request.headers().add("Proxy-Host", host);

        ctx.fireChannelRead(msg);
    }

    private HttpEndpointRouter selectRouter() {
        return RouteFactory.getRoute(strategy);
    }
}
