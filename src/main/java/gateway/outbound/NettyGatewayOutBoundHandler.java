package gateway.outbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;

/**
 * @author ztz
 * @description TODO
 * @date 2020/10/31 16:19
 */
public class NettyGatewayOutBoundHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext context;


    public NettyGatewayOutBoundHandler(ChannelHandlerContext channelHandlerContext) {
        this.context = channelHandlerContext;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        context.writeAndFlush(msg);
//        ctx.flush();
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        context.flush();
    }
}
