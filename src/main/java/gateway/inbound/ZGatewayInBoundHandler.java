package gateway.inbound;

import gateway.outbound.NettyHttpClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.ReferenceCountUtil;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author ztz
 * @description TODO
 * @date 2020/10/30 14:55
 */
public class ZGatewayInBoundHandler extends ChannelInboundHandlerAdapter {

    //    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private static final NettyHttpClient client = new NettyHttpClient();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("remoteAddress---->" + ctx.channel().remoteAddress());
        System.out.println("localAddress---->" + ctx.channel().localAddress());
        System.out.println("channel---->" + ctx.channel().toString());
        try {
            if (msg instanceof FullHttpRequest) {
                FullHttpRequest fullRequest = (FullHttpRequest) msg;
                String proxyHost = fullRequest.headers().get("Proxy-Host");
                String[] hostAndPort = proxyHost.split(":");
                SocketAddress address = new InetSocketAddress(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
                client.connect(address, ctx, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR));
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}
