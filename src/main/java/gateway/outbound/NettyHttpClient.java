package gateway.outbound;

/**
 * @author ztz
 * @description TODO
 * @date 2020/11/1 14:20
 */

import gateway.listener.ZGatewayListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

import java.net.SocketAddress;

public class NettyHttpClient {

    private static final Bootstrap b = new Bootstrap();
    private static final EventLoopGroup workGroup = new NioEventLoopGroup(12);

    public NettyHttpClient() {
        b.group(workGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
    }


    public void connect(SocketAddress address, ChannelHandlerContext context, Object msg) throws Exception {
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                ch.pipeline().addLast(new HttpClientCodec());
                //客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
//                    ch.pipeline().addLast(new HttpResponseDecoder());
                ch.pipeline().addLast(new HttpObjectAggregator(1024 * 1024));
                ch.pipeline().addLast(new NettyGatewayOutBoundHandler(context));
            }
        });

        b.connect(address).sync().addListener(new ZGatewayListener(context, msg));
    }

    public static void main(String[] args) throws Exception {
//        NettyHttpClient client = new NettyHttpClient();
//        client.connect("127.0.0.1", 8088);
    }
}
