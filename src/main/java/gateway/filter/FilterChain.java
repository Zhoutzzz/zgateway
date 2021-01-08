package gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

public class FilterChain extends ChannelInboundHandlerAdapter {
    private AbstractHttpRequestFilter head;
    private AbstractHttpRequestFilter tail;

    public void addFilter(AbstractHttpRequestFilter filter) {
        if (head == null) {
            this.head = filter;
            this.tail = filter;
            return;
        }
        tail.next = filter;
        tail = filter;
    }

    public void addFilter(AbstractHttpRequestFilter... filter) {
        for (AbstractHttpRequestFilter abstractHttpRequestFilter : filter) {
            addFilter(abstractHttpRequestFilter);
        }
    }

    private void handle(FullHttpRequest request, ChannelHandlerContext ctx) {
        if (head != null) {
            head.filter(request, ctx);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        handle((FullHttpRequest) msg, ctx);
        ctx.fireChannelRead(msg);
    }
}
